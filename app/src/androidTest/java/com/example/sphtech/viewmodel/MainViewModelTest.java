package com.example.sphtech.viewmodel;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.sphtech.App;
import com.example.sphtech.model.bean.BeanQuarterTotal;
import com.example.sphtech.model.bean.BeanYear;
import com.example.sphtech.model.http.Api;
import com.example.sphtech.model.http.ApiObserver;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainViewModelTest {

    MainViewModel mainViewModel;

    @Before
    public void setUp() throws Exception {
        mainViewModel = new MainViewModel();
    }

    @Test
    public void getDataFromDb() {
        mainViewModel.getDataFromDb().subscribe(new Consumer<List<BeanYear>>() {
            @Override
            public void accept(List<BeanYear> beanYears) throws Exception {
                Assert.assertEquals(beanYears.size(), 11);
            }
        });
    }

    @Test
    public void getDataFromWeb() {
        final CountDownLatch latch = new CountDownLatch(1);
        Api.getInstance().search("a807b7ab-6cad-4aa6-87d0-e283a7353a0f", 10, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiObserver<BeanQuarterTotal>(InstrumentationRegistry.getInstrumentation().getContext()) {
                    @Override
                    public void onNext(BeanQuarterTotal beanQuarter) {
                        //callback方法执行完毕侯，唤醒测试方法执行线程
                        latch.countDown();
                        Assert.assertEquals(beanQuarter.records.size(), 10);
                    }

                });
        try {
            //测试方法线程会在这里暂停, 直到loadData()方法执行完毕, 才会被唤醒继续执行
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}