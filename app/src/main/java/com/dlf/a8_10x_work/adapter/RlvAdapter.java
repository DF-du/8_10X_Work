package com.dlf.a8_10x_work.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dlf.a8_10x_work.R;
import com.dlf.a8_10x_work.bean.Bean;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvAdapter extends RecyclerView.Adapter{
public ArrayList<Bean.DataBean.ListBean> list;
public int mCheckPosition=-1;
private Context context;

public RlvAdapter(Context context,ArrayList<Bean.DataBean.ListBean>list){
        this.context=context;
        this.list=list;
        }

@NonNull
@Override
public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
final View view= LayoutInflater.from(context).inflate(R.layout.item_rlv,parent,false);
        return new ViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,int position){
        DecimalFormat format=new DecimalFormat("0.00");
        Bean.DataBean.ListBean bean=list.get(position);
        ViewHolder vh=(ViewHolder)holder;
        Glide.with(context).load(bean.getPic()).into(vh.ivPic);
        vh.tvName.setText(bean.getName());
        vh.tvStockCount.setText("库存:"+bean.getStockCount()+"个");
        vh.tvPrice.setText("销售:"+bean.getPrice()+"个");
        String sellCount=format.format(bean.getSellCount());
        vh.tvSellCount.setText(sellCount+"元");
        vh.rb.setChecked(bean.isCheckBox());
        vh.rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
@Override
public void onCheckedChanged(CompoundButton buttonView,boolean isChecked){
        if(buttonView.isPressed()){
        mCheckPosition=position;
        bean.setCheckBox(isChecked);
        for(int i=0;i<list.size();i++){
final Bean.DataBean.ListBean listBean=list.get(i);
        if(i!=position){
        listBean.setCheckBox(false);
        }else{
        listBean.setCheckBox(true);
        }
        }
        notifyDataSetChanged();
        }
        }
        });
        }

@Override
public int getItemCount(){
        return list.size();
        }

public void addData(Bean bean){
        list.addAll(bean.getData().getList());
        notifyDataSetChanged();
        }

static
class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.rb)
    RadioButton rb;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_stock_count)
    TextView tvStockCount;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_sell_count)
    TextView tvSellCount;

    ViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
}