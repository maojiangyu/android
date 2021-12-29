package com.example.travel.HomePage.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.travel.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    private List<Item> mItems;
    private Context mContext;
    private ItemAdapter.OnItemClickListener mOnItemClickListener;

    public ItemAdapter(List<Item> items) {
        mItems = items;
    }

        public ItemAdapter(List<Item> items, OnItemClickListener onItemClickListener) {
        //链表的赋值
            this.mItems = items;
            this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.setData(mItems.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public interface OnItemClickListener {
        void onClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView;
        public TextView content;
        public Item item;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.mapImage);
            textView = (TextView) itemView.findViewById(R.id.mapTitle);
            content = (TextView) itemView.findViewById(R.id.mapContent);
            view = itemView;
        }

        public void setData(Item item) {
            this.item = item;
            imageView.setImageResource(item.getDrawableResource());
            textView.setText(item.getTitle());
            content.setText(item.getContent());
        }

    }

}
