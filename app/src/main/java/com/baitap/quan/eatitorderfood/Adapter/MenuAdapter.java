package com.baitap.quan.eatitorderfood.Adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baitap.quan.eatitorderfood.Model.Category;
import com.baitap.quan.eatitorderfood.R;
import com.baitap.quan.eatitorderfood.ViewHolder.MenuViewHolder;

import java.util.List;

/**
 * Created by luong.duong.quan on 11/13/2017.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {

	List<Category> mlistCategory;


	public MenuAdapter(List<Category> listCategory){
		mlistCategory = listCategory;
	}


	@Override
	public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_recyclerlist, parent, false);

		return new MenuViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(MenuViewHolder holder, int position) {

		Category instanceCategory = mlistCategory.get(position);
		holder.imgMenu.setImageURI(Uri.parse(instanceCategory.getImage()));
		holder.tvNameMenu.setText(instanceCategory.getName());


	}

	@Override
	public int getItemCount() {
		return mlistCategory.size();
	}

}
