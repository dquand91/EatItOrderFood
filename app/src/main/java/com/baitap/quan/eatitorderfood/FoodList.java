package com.baitap.quan.eatitorderfood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.baitap.quan.eatitorderfood.Listener.ItemClickListenerCustom;
import com.baitap.quan.eatitorderfood.Model.Food;
import com.baitap.quan.eatitorderfood.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static com.baitap.quan.eatitorderfood.Common.KEY_INTENT_MENU_ID;

public class FoodList extends AppCompatActivity {

	FirebaseDatabase firebaseDatabase;
	DatabaseReference databaseReference;
	private RecyclerView mRecyclerFood;

	String catergoryId="";
	FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_list);


		// Init FireBase
		firebaseDatabase = FirebaseDatabase.getInstance();
		databaseReference = firebaseDatabase.getReference("Foods");


		// Load menu
		mRecyclerFood = (RecyclerView) findViewById(R.id.recycler_Food);
		mRecyclerFood.setHasFixedSize(true);
		mRecyclerFood.setLayoutManager(new LinearLayoutManager(this));

		if (getIntent() != null ){
			catergoryId = getIntent().getStringExtra(KEY_INTENT_MENU_ID);
		} if (!catergoryId.isEmpty() && catergoryId != null){
			loadMenuFromFireBase();
		}


	}

	private void loadMenuFromFireBase() {
		adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
				Food.class, R.layout.item_food_recyclerlist, FoodViewHolder.class, databaseReference.orderByChild("MenuId").equalTo(catergoryId)) {
			@Override
			protected void populateViewHolder(FoodViewHolder viewHolder, final Food model, int position) {
				viewHolder.tvNameFood.setText(model.getName());
				Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imgFood);
				final Food food = model;
				viewHolder.setListener(new ItemClickListenerCustom() {
					@Override
					public void onItemClick(View view, int position, boolean isLongClick) {
						Toast.makeText(FoodList.this, "" + model.getName(), Toast.LENGTH_SHORT).show();
					}
				});
			}
		};
		mRecyclerFood.setAdapter(adapter);
	}
}


