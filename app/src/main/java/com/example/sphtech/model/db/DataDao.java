package com.example.sphtech.model.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.sphtech.model.bean.BeanYear;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class DataDao {

    @Insert
    public abstract void insertYearList(List<BeanYear> beanYears);

    @Query("select * from BeanYear")
    public abstract Flowable<List<BeanYear>> getAllBeanYears();

    @Query("delete from BeanYear")
    public abstract void deleteAllBeanYears();

    @Insert
    public void replaceBeanYears(List<BeanYear> beanProjects) {
        if (beanProjects == null || beanProjects.size() <= 0) {
            return;
        }
        deleteAllBeanYears();
        insertYearList(beanProjects);
    }
}
