package com.example.sphtech.model.db;

import android.Manifest;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.sphtech.model.bean.BeanYear;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


@RunWith(AndroidJUnit4.class)
public class DbHelperTest {

    private DataDao dataDao;
    private MyDatabase mDb;

    @Before
    public void setUp() throws Exception {
        init();
    }

    @Test
    public void init() {
        mDb = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), MyDatabase.class).build();
        dataDao = mDb.dataDao();
    }

    @Test
    public void getInstance() {
        List<BeanYear> list = new ArrayList<>();
        BeanYear beanYear = new BeanYear();
        beanYear.year = "2018";
        list.add(beanYear);
        dataDao.insertYearList(list);
        dataDao.getAllBeanYears()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<BeanYear>>() {
                    @Override
                    public void accept(List<BeanYear> beanYears) throws Exception {
                        Assert.assertEquals(beanYears.size(), 1);
                    }
                });
    }
}