package com.baitap.quan.eatitorderfood.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baitap.quan.eatitorderfood.Listener.ItemClickListenerCustom;
import com.baitap.quan.eatitorderfood.R;

/**
 * Created by luong.duong.quan on 11/14/2017.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder {

public ImageView imgFood;
public TextView tvNameFood;
public ItemClickListenerCustom mListener;

	public FoodViewHolder(View itemView) {
		super(itemView);
		imgFood = (ImageView) itemView.findViewById(R.id.image_food);
		tvNameFood = (TextView) itemView.findViewById(R.id.name_food);

			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					mListener.onItemClick(view, getAdapterPosition(), false);
				}
			});
	}

	public void setListener(ItemClickListenerCustom listener) {
		this.mListener = listener;
	}
}
