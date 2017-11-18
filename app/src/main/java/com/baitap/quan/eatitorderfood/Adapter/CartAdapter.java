package com.baitap.quan.eatitorderfood.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.baitap.quan.eatitorderfood.Model.Order;
import com.baitap.quan.eatitorderfood.R;
import com.baitap.quan.eatitorderfood.ViewHolder.CartViewHolder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by User on 11/18/2017.
 */

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    List<Order> mListOrder = new ArrayList<>();
    private Context mContext;


    public CartAdapter(List<Order> listOrder, Context context){
        mListOrder = listOrder;
        mContext = context;
    }


    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);


        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        if (holder != null){
            // TextDrawable này là lấy từ thư viện bên ngoài
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(""+mListOrder.get(position).getQuantity(), Color.RED);
            holder.img_cart_count.setImageDrawable(drawable);

            Locale locale = new Locale("en","US");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            int price = (Integer.parseInt(mListOrder.get(position).getPrice()))*(Integer.parseInt(mListOrder.get(position).getQuantity()));
            holder.tv_price.setText(fmt.format(price));
            holder.tv_cart_name.setText(mListOrder.get(position).getProductName());
        }
    }

    @Override
    public int getItemCount() {
        return mListOrder.size();
    }
}
