package com.baitap.quan.eatitorderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.baitap.quan.eatitorderfood.Listener.ItemClickListenerCustom;
import com.baitap.quan.eatitorderfood.Model.Food;
import com.baitap.quan.eatitorderfood.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.baitap.quan.eatitorderfood.Common.KEY_INTENT_FOOD_ID;
import static com.baitap.quan.eatitorderfood.Common.KEY_INTENT_MENU_ID;

public class FoodList extends AppCompatActivity {

	FirebaseDatabase firebaseDatabase;
	DatabaseReference databaseReference;
	private RecyclerView mRecyclerFood;

	String catergoryId="";
	FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    // Search Functionality
    MaterialSearchBar searchBarFood;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();



	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_list);


		// Init FireBase
		firebaseDatabase = FirebaseDatabase.getInstance();
        // Ở đây sẽ trả ra 1 Foodlist
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

		// Search
        searchBarFood = (MaterialSearchBar) findViewById(R.id.searchBarFood);
        searchBarFood.setHint("Enter your food");
        loadSuggest(); // load suggest from FireBase

        searchBarFood.setLastSuggestions(suggestList);
        searchBarFood.setCardViewElevation(10);

        // Chỗ này để bắt sự kiên mỗi khi nhập 1 chữ sẽ cập nhật lại list kết quả
        searchBarFood.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                List<String> suggest = new ArrayList<String>();
                for (String search: suggestList){
                    if(search.toLowerCase().contains(searchBarFood.getText().toLowerCase())){
                        suggest.add(search);
                    }
                }
                searchBarFood.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        searchBarFood.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                // When Search Bar is close
                // Restore original suggest adapter
                if (!enabled){
                    mRecyclerFood.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                // When Searcj finish
                // Show result of search adapter
                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


	}

    private void startSearch(CharSequence text) {

        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.item_food_recyclerlist,
                FoodViewHolder.class,
                databaseReference.orderByChild("Name").equalTo(text.toString())
        ) {
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
                        intentFoodDetail.putExtra(KEY_INTENT_FOOD_ID, searchAdapter.getRef(position).getKey());

                        startActivity(intentFoodDetail);
                    }
                });
            }
        };
        mRecyclerFood.setAdapter(searchAdapter);

    }

    private void loadSuggest() {

        // Chỗ này nó sẽ lên database server lấy về danh sách food theo categoryID (ví dụ là danh sách tất cả món Xào)
        // Xong sắp xếp theo MenuID
        // Khi mà lấy được xong nó sẽ trả Call back onDataChange
        // Ta lấy dataSnapshot của Callback và add vào danh sách gợi ý (suggestList)
        databaseReference.orderByChild("MenuId").equalTo(catergoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            Food item = postSnapshot.getValue(Food.class);
                            suggestList.add(item.getName());
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

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


