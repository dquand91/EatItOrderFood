package com.baitap.quan.eatitorderfood;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baitap.quan.eatitorderfood.Database.DataBaseQuan;
import com.baitap.quan.eatitorderfood.Model.Food;
import com.baitap.quan.eatitorderfood.Model.Order;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static com.baitap.quan.eatitorderfood.Common.KEY_INTENT_FOOD_ID;

public class FoodDetail extends AppCompatActivity {

	TextView food_Name,food_Price,food_Description;
	ImageView food_Image;
	CollapsingToolbarLayout collapsingToolbarLayout;
	FloatingActionButton btnCart;
	ElegantNumberButton numberButton;

	String foodId="";

	FirebaseDatabase firebaseDatabase;
	DatabaseReference databaseFoods;

	Food currentFood;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_detail);

		// Init FireBase
		firebaseDatabase = FirebaseDatabase.getInstance();
		databaseFoods = firebaseDatabase.getReference("Foods");

		//Initialize view
		numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
		btnCart = (FloatingActionButton)findViewById(R.id.btnCart);
		food_Description = (TextView)findViewById(R.id.food_description);
		food_Name = (TextView)findViewById(R.id.food_name);
		food_Price = (TextView)findViewById(R.id.food_price);
		food_Image = (ImageView) findViewById(R.id.img_food);

		collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
		collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
		collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

		if(getIntent() != null){
			foodId = getIntent().getStringExtra(KEY_INTENT_FOOD_ID);
		} if (!foodId.isEmpty()){
			getDetailFood(foodId);
		}

		btnCart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Thêm 1 Order vào DataBase
				// Tức là thêm 1 món ăn và số lượng của nó vào database
				// Để tạo ra 1 order trong bảng "OrderDetail"
				new DataBaseQuan(getBaseContext()).addToCart(new Order(
						foodId,
						currentFood.getName(),
						numberButton.getNumber(),
						currentFood.getPrice(),
						currentFood.getDiscount()
				));
				Toast.makeText(FoodDetail.this, "Added To Cart", Toast.LENGTH_SHORT).show();

			}
		});


	}

	private void getDetailFood(final String foodId) {
		databaseFoods.child(foodId).addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {

				currentFood = dataSnapshot.getValue(Food.class);

				Picasso.with(getBaseContext()).load(currentFood.getImage()).into(food_Image);
				collapsingToolbarLayout.setTitle(currentFood.getName());

				food_Price.setText(currentFood.getPrice());

				food_Name.setText(currentFood.getName());

				food_Description.setText(currentFood.getDescription());

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
	}
}
