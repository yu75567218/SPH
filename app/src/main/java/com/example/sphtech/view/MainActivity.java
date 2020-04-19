package com.example.sphtech.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.sphtech.view.adapter.AdapterYear;
import com.example.sphtech.model.bean.BeanYear;
import com.example.sphtech.databinding.ActivityMainBinding;
import com.example.sphtech.viewmodel.MainViewModel;

import java.util.List;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.rvYear.setLayoutManager(new LinearLayoutManager(this));
        mainBinding.rvYear.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        getDataFromDb(); // 先从本都数据库获取数据，从网络拉取到最新数据后，会刷新页面
        getDataFromWeb(); // 去网络拉去最新数据保存到本地
    }

    private void getDataFromDb() {
        // 从数据库获取数据，数据更新会驱动UI刷新
        mainViewModel.getDataFromDb()
                .subscribe(new Consumer<List<BeanYear>>() {
                    @Override
                    public void accept(List<BeanYear> beanYears) throws Exception {
                        mainBinding.rvYear.setAdapter(new AdapterYear(MainActivity.this, beanYears));
                    }
                });
    }

    private void getDataFromWeb() {
        // 请求网络数据
        mainViewModel.getDataFromWeb(this);
    }

}
