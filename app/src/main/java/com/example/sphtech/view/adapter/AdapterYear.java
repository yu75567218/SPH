package com.example.sphtech.view.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sphtech.R;
import com.example.sphtech.model.bean.BeanYear;
import com.example.sphtech.databinding.ItemYearBinding;

import java.util.List;

public class AdapterYear extends RecyclerView.Adapter<AdapterYear.ViewHolder> {

    Context context;
    List<BeanYear> beanYears;

    public AdapterYear(Context context, List<BeanYear> beanYears) {
        this.context = context;
        this.beanYears = beanYears;
    }

    @NonNull
    @Override
    public AdapterYear.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemYearBinding itemYearBinding = DataBindingUtil.inflate(LayoutInflater.from(this.context), R.layout.item_year, parent, false);
        return new ViewHolder(itemYearBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterYear.ViewHolder holder, int position) {
        final BeanYear beanYear = beanYears.get(position);
        ItemYearBinding itemYearBinding = holder.getBinding();
        itemYearBinding.setBean(beanYear);
        itemYearBinding.q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem(1, beanYear);
            }
        });
        itemYearBinding.q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem(2, beanYear);
            }
        });
        itemYearBinding.q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem(3, beanYear);
            }
        });
        itemYearBinding.q4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItem(4, beanYear);
            }
        });
    }

    private void clickItem(int item, BeanYear beanYear) {
        StringBuilder sb = new StringBuilder();
        switch (item) {
            case 1:
                if (!beanYear.isReduceQ1) {
                    return;
                }
                sb.append(beanYear.year);
                sb.append("年Q1季度移动网络发送的数据量为：");
                sb.append(beanYear.quarterData1);
                sb.append("，该季度数据量下降。");
                show(sb.toString());
                break;
            case 2:
                if (!beanYear.isReduceQ2) {
                    return;
                }
                sb.append(beanYear.year);
                sb.append("年Q2季度移动网络发送的数据量为：");
                sb.append(beanYear.quarterData2);
                sb.append("，该季度数据量下降。");
                show(sb.toString());
                break;
            case 3:
                if (!beanYear.isReduceQ3) {
                    return;
                }
                sb.append(beanYear.year);
                sb.append("年Q3季度移动网络发送的数据量为：");
                sb.append(beanYear.quarterData3);
                sb.append("，该季度数据量下降。");
                show(sb.toString());
                break;
            case 4:
                if (!beanYear.isReduceQ4) {
                    return;
                }
                sb.append(beanYear.year);
                sb.append("年Q4季度移动网络发送的数据量为：");
                sb.append(beanYear.quarterData4);
                sb.append("，该季度数据量下降。");
                show(sb.toString());
                break;
        }
    }

    private void show(String msg) {
        new AlertDialog.Builder(context).setTitle("提示")
                .setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    @Override
    public int getItemCount() {
        return beanYears == null ? 0 : beanYears.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemYearBinding binding;

        public ViewHolder(ItemYearBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemYearBinding getBinding() {
            return binding;
        }

    }
}
