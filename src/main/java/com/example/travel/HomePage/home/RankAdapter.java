package com.example.travel.HomePage.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel.R;

import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder>{
    private List<RankItem> mTxList;
    private RankAdapter.OnItemClickListener mOnItemClickListener;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView txImage;
        TextView txName;
        TextView txAddress;
        TextView txComments;
        View txView;


        public ViewHolder(View view) {
            super(view);
            txView = view;
            txImage = view.findViewById(R.id.recommended_image);
            txName = view.findViewById(R.id.recommended_title);
            txAddress = view.findViewById(R.id.recommended_address);
            txComments = view.findViewById(R.id.recommended_comments);
        }
    }
    public RankAdapter(List<RankItem> txList, Context context, RankAdapter.OnItemClickListener onItemClickListener) {
        mTxList = txList;
        this.mContext = context;
        this.mOnItemClickListener = onItemClickListener;
    }

    public RankAdapter(List<RankItem> txList,Context context){
        mTxList = txList;
        this.mContext = context;
    }

    @Override
    public RankAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_item, parent, false);
        final RankAdapter.ViewHolder holder = new RankAdapter.ViewHolder(view);
        holder.txView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RankItem tx = mTxList.get(holder.getAdapterPosition());
                mTxList.remove(tx);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RankAdapter.ViewHolder holder, final int position) {
        RankItem tx = mTxList.get(position);
        holder.txImage.setImageResource(tx.getImageId());
        holder.txName.setText(tx.getTitle());
        holder.txAddress.setText(tx.getAddress());
        holder.txComments.setText(tx.getComments());
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
