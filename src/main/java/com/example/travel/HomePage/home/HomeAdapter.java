package com.example.travel.HomePage.home;

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

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{
    private List<Scenery> mTxList;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder {
        //内部静态类，用以定义TxApter.View的泛型
        ImageView txImage;
        TextView txContent;
        TextView txName;//这两个是在子项布局里面具体的控件
        View txView;//这个是用于整个子项的控制的控件

        public ViewHolder(View view) {
            super(view);
            txView = view;//这个是整个子项的控件
            txImage = view.findViewById(R.id.fengjingImage);
            txName = view.findViewById(R.id.fengjingTitle);//通过R文件的id查找，找出子项的具体控件
            txContent = view.findViewById(R.id.fengjingcontent);
        }
    }
    public HomeAdapter(List<Scenery> txList, Context context, OnItemClickListener onItemClickListener) {
        //链表的赋值
        mTxList = txList;
        this.mContext = context;
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_fragment_item, parent, false);
        //将子项的布局通过LayoutInflater引入
        final ViewHolder holder = new ViewHolder(view);
        holder.txView.setOnClickListener(new View.OnClickListener() {
            //这里是子项的点击事件，RecyclerView的特点就是可以对子项里面单个控件注册监听，这也是为什么RecyclerView要摒弃ListView的setOnItemClickListener方法的原因
            @Override
            public void onClick(View v) {
                Scenery tx = mTxList.get(holder.getAdapterPosition());
                Toast.makeText(v.getContext(), "已经删除！", Toast.LENGTH_LONG).show();
                mTxList.remove(tx);//所谓的删除就是将子项从链表中remove
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, final int position) {
        Scenery tx = mTxList.get(position);//创建前面实体类的对象
        holder.txImage.setImageResource(tx.getImgPath());
        holder.txContent.setText(tx.getInfo());
        holder.txName.setText(tx.getName());//将具体值赋与子项对应的控件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTxList.size();
    }

    public interface OnItemClickListener {
        void onClick(int pos);
    }

}
