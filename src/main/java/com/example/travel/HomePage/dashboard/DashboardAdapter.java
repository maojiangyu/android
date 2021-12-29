package com.example.travel.HomePage.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.travel.DataBase.Scenery;
import com.example.travel.R;

import java.util.ArrayList;

public class DashboardAdapter extends  RecyclerView.Adapter<DashboardAdapter.ViewHolder> {
    private ArrayList<Scenery> SceneryList = new ArrayList<Scenery>();
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder {
        //内部静态类，用以定义TxApter.View的泛型
        ImageView txImage;
        TextView txContent;
        TextView txName;
        TextView txLike;
        View txView;//这个是用于整个子项的控制的控件

        public ViewHolder(View view) {
            super(view);
            txView = view;//这个是整个子项的控件
            txImage = view.findViewById(R.id.sceneryImage);
            txName = view.findViewById(R.id.sceneryTitle);
            txContent = view.findViewById(R.id.sceneryInfo);
            txLike = view.findViewById(R.id.likes);
        }
    }
    public DashboardAdapter(ArrayList<Scenery> SceneryList, Context context, OnItemClickListener onItemClickListener) {
        //链表的赋值
        this.SceneryList = SceneryList;
        this.mContext = context;
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public DashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_fragment_item, parent, false);
        //将子项的布局通过LayoutInflater引入
        final ViewHolder holder = new ViewHolder(view);
        holder.txView.setOnClickListener(new View.OnClickListener() {
            //这里是子项的点击事件，RecyclerView的特点就是可以对子项里面单个控件注册监听，这也是为什么RecyclerView要摒弃ListView的setOnItemClickListener方法的原因
            @Override
            public void onClick(View v) {
                Scenery tx = SceneryList.get(holder.getAdapterPosition());
                Toast.makeText(v.getContext(), "已经删除！", Toast.LENGTH_LONG).show();
                SceneryList.remove(tx);//所谓的删除就是将子项从链表中remove
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(DashboardAdapter.ViewHolder holder, final int position) {
        Scenery tx = SceneryList.get(position);//创建前面实体类的对象
        holder.txImage.setImageBitmap(tx.getImg());
        holder.txContent.setText(tx.getInfo());
        holder.txName.setText(tx.getName());//将具体值赋与子项对应的控件
        holder.txLike.setText(tx.getFavorite()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return SceneryList.size();
    }

    public interface OnItemClickListener {
        void onClick(int pos);
    }
}
