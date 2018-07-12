package com.miaxis.esmanage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miaxis.esmanage.R;
import com.miaxis.esmanage.entity.Escort;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EscortListAdapter extends RecyclerView.Adapter<EscortListAdapter.ViewHolder>  {

    private Context mContext;
    private List<Escort> escortList;
    private onRecyclerItemClickerListener mListener;

    public List<Escort> getEscortList() {
        return escortList;
    }

    public void setEscortList(List<Escort> escortList) {
        this.escortList = escortList;
    }

    public void setItemListener(onRecyclerItemClickerListener mListener) {
        this.mListener = mListener;
    }

    public EscortListAdapter(Context mContext, List<Escort> escortList) {
        this.mContext = mContext;
        this.escortList = escortList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_escort, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int fPosition = position;
        final Escort escort = escortList.get(position);
        if (escort != null) {
            Fresco.getImagePipeline().clearCaches();
            holder.sdvEscort.setImageURI(escort.getPhotoUrl());
            holder.tvEscortName.setText(escort.getEsname());
            holder.tvEscortCode.setText(escort.getEscode());
            holder.llEscort.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onRecyclerItemClick(view, escort, fPosition);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if (escortList == null) {
            return 0;
        }
        return escortList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sdv_escort)
        SimpleDraweeView sdvEscort;
        @BindView(R.id.tv_escort_name)
        TextView tvEscortName;
        @BindView(R.id.tv_escort_code)
        TextView tvEscortCode;
        @BindView(R.id.ll_escort)
        LinearLayout llEscort;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface onRecyclerItemClickerListener {
        void onRecyclerItemClick(View view, Object data, int position);
    }
}
