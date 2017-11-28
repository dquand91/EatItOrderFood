package com.baitap.quan.eatitorderfood.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.baitap.quan.eatitorderfood.Listener.ItemClickListenerCustom;
import com.baitap.quan.eatitorderfood.R;

/**
 * Created by User on 11/25/2017.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_order_id, tv_order_status, tv_order_phone, tv_order_address;
    public ItemClickListenerCustom mListener;


    public OrderViewHolder(View itemView) {
        super(itemView);

        tv_order_id = itemView.findViewById(R.id.order_item_id);
        tv_order_status = itemView.findViewById(R.id.order_item_status);
        tv_order_phone = itemView.findViewById(R.id.order_item_phone);
        tv_order_address = itemView.findViewById(R.id.order_item_address);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(view, getAdapterPosition(), false);
            }
        });
    }
}
