package com.baitap.quan.eatitorderfood.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baitap.quan.eatitorderfood.R;

/**
 * Created by luong.duong.quan on 11/13/2017.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {




	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_recyclerlist, parent, false);

		return new ViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {

	}

	@Override
	public int getItemCount() {
		return 0;
	}

	class ViewHolder extends RecyclerView.ViewHolder {

		public ViewHolder(View itemView) {
			super(itemView);
		}
	}
}
