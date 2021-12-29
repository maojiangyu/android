package com.example.travel.HomePage.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.travel.R;

import java.util.List;

public class NearAdapter extends RecyclerView.Adapter<NearAdapter.ViewHolder>{
    private List<NearItem> mTxList;
    private NearAdapter.OnItemClickListener mOnItemClickListener;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        //内部静态类，用以定义TxApter.View的泛型
        ImageView txImage;
        TextView txName;
        TextView txContent;
        TextView txDistance;
        TextView point;//这两个是在子项布局里面具体的控件
        View txView;//这个是用于整个子项的控制的控件


        public ViewHolder(View view) {
            super(view);
            txView = view;//这个是整个子项的控件
            txImage = view.findViewById(R.id.nearImage);
            txName = view.findViewById(R.id.nearTitle);
            txContent = view.findViewById(R.id.nearContent);
            txDistance = view.findViewById(R.id.distance);
            point = view.findViewById(R.id.point);
            //通过R文件的id查找，找出子项的具体控件
        }
    }
    public NearAdapter(List<NearItem> txList,Context context, NearAdapter.OnItemClickListener onItemClickListener) {
        //链表的赋值
        mTxList = txList;
        this.mContext = context;
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public NearAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xixinear, parent, false);
        //将子项的布局通过LayoutInflater引入
        final NearAdapter.ViewHolder holder = new NearAdapter.ViewHolder(view);
        holder.txView.setOnClickListener(new View.OnClickListener() {
            //这里是子项的点击事件，RecyclerView的特点就是可以对子项里面单个控件注册监听，这也是为什么RecyclerView要摒弃ListView的setOnItemClickListener方法的原因
            @Override
            public void onClick(View v) {
                NearItem tx = mTxList.get(holder.getAdapterPosition());
                Toast.makeText(v.getContext(), "已经删除！", Toast.LENGTH_LONG).show();
                mTxList.remove(tx);//所谓的删除就是将子项从链表中remove
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(NearAdapter.ViewHolder holder, final int position) {
        NearItem tx = mTxList.get(position);//创建前面实体类的对象
        holder.txImage.setImageResource(tx.getImageId());
        holder.txName.setText(tx.getName());
        holder.txDistance.setText(tx.getDistance());
        holder.txContent.setText(tx.getContent());
        holder.point.setText(tx.getPoint());//将具体值赋与子项对应的控件
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
