package com.miaxis.esmanage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.miaxis.esmanage.R;
import com.miaxis.esmanage.entity.Car;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder>  {

    private Context mContext;
    private List<Car> carList;
    private onRecyclerItemClickerListener mListener;

    public List<Car> getEscortList() {
        return carList;
    }

    public void setEscortList(List<Car> carList) {
        this.carList = carList;
    }

    public void setItemListener(onRecyclerItemClickerListener mListener) {
        this.mListener = mListener;
    }

    public CarListAdapter(Context mContext, List<Car> carList) {
        this.mContext = mContext;
        this.carList = carList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_car, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int fPosition = position;
        final Car car = carList.get(position);
        if (car != null) {
            holder.sdvCar.setImageURI(car.getCarphoto());
            holder.tvCarPlateNo.setText(car.getPlateno());
            holder.llCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onRecyclerItemClick(view, car, fPosition);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if (carList == null) {
            return 0;
        }
        return carList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sdv_car)
        SimpleDraweeView sdvCar;
        @BindView(R.id.tv_car_plate_no)
        TextView tvCarPlateNo;
        @BindView(R.id.ll_car)
        LinearLayout llCar;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface onRecyclerItemClickerListener {
        void onRecyclerItemClick(View view, Object data, int position);
    }
}
