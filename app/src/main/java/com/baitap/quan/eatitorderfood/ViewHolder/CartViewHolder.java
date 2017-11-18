package com.baitap.quan.eatitorderfood.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baitap.quan.eatitorderfood.Listener.ItemClickListenerCustom;
import com.baitap.quan.eatitorderfood.R;

/**
 * Created by User on 11/18/2017.
 */

public class CartViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_cart_name, tv_price;
    public ImageView img_cart_count;
    public ItemClickListenerCustom mListener;

    public void setTv_cart_name(TextView txt_cart_name){
        this.tv_cart_name = txt_cart_name;
    }

    public CartViewHolder(View itemView) {
        super(itemView);

        tv_cart_name = (TextView)itemView.findViewById(R.id.cart_item_name);
        tv_price = (TextView)itemView.findViewById(R.id.cart_item_price);

        img_cart_count = (ImageView)itemView.findViewById(R.id.cart_item_count);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(view, getAdapterPosition(), false);
            }
        });
    }
}
