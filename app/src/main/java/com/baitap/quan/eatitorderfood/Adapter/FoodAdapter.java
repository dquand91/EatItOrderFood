package com.baitap.quan.eatitorderfood.Adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baitap.quan.eatitorderfood.Model.Food;
import com.baitap.quan.eatitorderfood.R;
import com.baitap.quan.eatitorderfood.ViewHolder.FoodViewHolder;

import java.util.List;

/**
 * Created by luong.duong.quan on 11/14/2017.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder> {

	List<Food> mlistFood;


	public FoodAdapter(List<Food> listCategory){
		mlistFood = listCategory;
	}


	@Override
	public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_recyclerlist, parent, false);

		return new FoodViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(FoodViewHolder holder, int position) {

		Food instanceFood = mlistFood.get(position);
		holder.imgFood.setImageURI(Uri.parse(instanceFood.getImage()));
		holder.tvNameFood.setText(instanceFood.getName());


	}

	@Override
	public int getItemCount() {
		return mlistFood.size();
	}

}
