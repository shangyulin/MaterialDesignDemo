package com.example.shangyulin.materialdesigndemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

/**
 * Created by shangyulin on 2018/4/3.
 */

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.FruitViewHolder> implements View.OnClickListener, ItemTouchHelperAdapter{

    private Context context;
    private List<Fruit> fruits;

    private RecyclerView recyclerView;

    /* 回调接口 */
    public addOnItemClickListener listener;



    public interface addOnItemClickListener{
        void onItemClick(String name);
    }

    public void setAddOnItemClickListener(addOnItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 与recycleView绑定
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    /**
     * 解绑
     * @param recyclerView
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }


    @Override
    public void onClick(View view) {
        int position = recyclerView.getChildAdapterPosition(view);
        listener.onItemClick(fruits.get(position).getName());
    }



    public FruitAdapter(Context context, List<Fruit> list) {
        this.context = context;
        this.fruits = list;
    }

    @Override
    public FruitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.fruit_item, null);
        FruitViewHolder holder = new FruitViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(FruitViewHolder holder, int position) {
        Fruit fruit = fruits.get(position);
        holder.name.setText(fruit.getName());
        Glide.with(context).load(fruit.getImageId()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return fruits.size();
    }

    class FruitViewHolder extends RecyclerView.ViewHolder{
        View view;
        ImageView image;
        TextView name;

        public FruitViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            image = itemView.findViewById(R.id.fruit_image);
            name = itemView.findViewById(R.id.image_name);
        }
    }


    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(fruits, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        fruits.remove(position);
        notifyItemRemoved(position);
    }
}
