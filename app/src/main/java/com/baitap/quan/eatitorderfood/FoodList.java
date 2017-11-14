package com.baitap.quan.eatitorderfood;

import android.content.Intent;
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

import static com.baitap.quan.eatitorderfood.Common.KEY_INTENT_FOOD_ID;
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
						// start Food detail activity
						Intent intentFoodDetail = new Intent(FoodList.this, FoodDetail.class);


						// ở đây để lấy được cái id của food với định dạng data như bên dưới.
						// Tức là lấy được cái số 01, 02, 03 của mỗi item
//						"Foods" : {
//							"01" : {
//								"Description" : "",
//										"Discount" : "0",
//										"Image" : "http://medifoods.my/images/menu/p1_ginger_pao.jpg",
//										"MenuId" : "11",
//										"Name" : "GINGER PAO",
//										"Price" : "1000"
//							},
//							"02" : {
//								"Description" : "",
//										"Discount" : "0",
//										"Image" : "http://medifoods.my/images/menu/p2_coconut_pao.jpg",
//										"MenuId" : "11",
//										"Name" : "COCONUT PAO",
//										"Price" : "1000"
//							},
//							"03" : {
//								"Description" : "",
//										"Discount" : "0",
//										"Image" : "http://medifoods.my/images/menu/p3_red_bean_pao.jpg",
//										"MenuId" : "11",
//										"Name" : "RED BEAN PAO",
//										"Price" : "1000"
//							},
//						}
						intentFoodDetail.putExtra(KEY_INTENT_FOOD_ID, adapter.getRef(position).getKey());

						startActivity(intentFoodDetail);

					}
				});
			}
		};
		mRecyclerFood.setAdapter(adapter);
	}
}


