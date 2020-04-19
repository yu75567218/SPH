package com.example.sphtech.viewmodel;


import android.content.Context;
import android.text.TextUtils;
import android.util.ArrayMap;

import androidx.lifecycle.ViewModel;

import com.example.sphtech.model.bean.BeanQuarter;
import com.example.sphtech.model.bean.BeanQuarterTotal;
import com.example.sphtech.model.bean.BeanYear;
import com.example.sphtech.model.db.DbHelper;
import com.example.sphtech.model.http.Api;
import com.example.sphtech.model.http.ApiObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    // 从数据库获取数据
    public Flowable<List<BeanYear>> getDataFromDb() {
        return DbHelper.getInstance().dataDao().getAllBeanYears()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    // 请求网络数据
    public void getDataFromWeb(Context context) {
        Api.getInstance().search("a807b7ab-6cad-4aa6-87d0-e283a7353a0f", 100, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<BeanQuarterTotal>(context) {
                    @Override
                    public void onNext(BeanQuarterTotal beanQuarter) {
                        formatData(beanQuarter);
                    }

                });
    }
    // 把服务器取下的数据转换成便于客户端使用和保存的数据格式
    private void formatData(BeanQuarterTotal beanQuarter) {
        Observable.just(beanQuarter)
                .map(new Function<BeanQuarterTotal, List<BeanYear>>() {
                    @Override
                    public List<BeanYear> apply(BeanQuarterTotal beanQuarterTotal) throws Exception {
                        Map<String, BeanYear> beanYearMap = formatDataMap(beanQuarterTotal);
                        List<BeanYear> beanYearList = new ArrayList<>(beanYearMap.values());
                        return beanYearList;
                    }
                }).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<BeanYear>>() {
                    @Override
                    public void accept(List<BeanYear> beanYears) throws Exception {
                        DbHelper.getInstance().dataDao().replaceBeanYears(beanYears);
                    }
                });
    }

    // 吧数据格式转化成Map类型
    private Map<String, BeanYear> formatDataMap(BeanQuarterTotal beanQuarterTotal) {
        Map<String, BeanYear> beanYearMap = new ArrayMap<>();
        if (beanQuarterTotal != null && beanQuarterTotal.records.size() > 0) {
            for (BeanQuarter beanQuarter1 : beanQuarterTotal.records) {
                String year = getYear(beanQuarter1.quarter);
                String quarter = getQuarter(beanQuarter1.quarter);
                // 显示2008年至2018年的数据
                if (Integer.valueOf(year) < 2008 || Integer.valueOf(year) > 2018) {
                    continue;
                }
                BeanYear beanYear;
                if (beanYearMap.containsKey(year)) {
                    beanYear = beanYearMap.get(year);
                } else {
                    beanYear = new BeanYear();
                    beanYear.year = year;
                    beanYearMap.put(year, beanYear);
                }
                beanYear.total += Double.valueOf(beanQuarter1.volume_of_mobile_data);
                switch (quarter) {
                    // 第一季度
                    case "1":
                        beanYear.quarterData1 = beanQuarter1.volume_of_mobile_data;
                        beanYear.isReduceQ1 = false;
                        break;
                    // 第二季度
                    case "2":
                        beanYear.quarterData2 = beanQuarter1.volume_of_mobile_data;
                        beanYear.isReduceQ2 = Double.valueOf(beanYear.quarterData2) < Double.valueOf(beanYear.quarterData1);
                        break;
                    // 第三季度
                    case "3":
                        beanYear.quarterData3 = beanQuarter1.volume_of_mobile_data;
                        beanYear.isReduceQ3 = Double.valueOf(beanYear.quarterData3) < Double.valueOf(beanYear.quarterData2);
                        break;
                    // 第四季度
                    case "4":
                        beanYear.quarterData4 = beanQuarter1.volume_of_mobile_data;
                        beanYear.isReduceQ4 = Double.valueOf(beanYear.quarterData4) < Double.valueOf(beanYear.quarterData3);
                        break;
                }
            }
        }
        return beanYearMap;
    }

    // 获取年份
    private String getYear(String quarter) {
        if (TextUtils.isEmpty(quarter)) {
            return "";
        }
        if (quarter.length() <= 4) {
            return quarter;
        }
        return quarter.substring(0, 4);
    }

    // 获取季度
    private String getQuarter(String quarter) {
        if (TextUtils.isEmpty(quarter)) {
            return "";
        }
        return quarter.substring(quarter.length() - 1);
    }

}
