package com.baitap.quan.eatitorderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baitap.quan.eatitorderfood.Listener.ItemClickListenerCustom;
import com.baitap.quan.eatitorderfood.Model.Category;
import com.baitap.quan.eatitorderfood.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.baitap.quan.eatitorderfood.Common.KEY_INTENT_MENU_ID;

public class Home extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	FirebaseDatabase firebaseDatabase;
	DatabaseReference databaseReference;

	TextView tvFullName;
	private RecyclerView mRecyclerMenu;
	private List<Category> mListCategory;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("Menu");

		setSupportActionBar(toolbar);


		// Init FireBase
		firebaseDatabase = FirebaseDatabase.getInstance();
		databaseReference = firebaseDatabase.getReference("Category");

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent intentCart = new Intent(Home.this, CartActivity.class);
				startActivity(intentCart);

			}
		});

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		// Set Name for user
		View headerView = navigationView.getHeaderView(0);
		tvFullName = (TextView)  headerView.findViewById(R.id.tv_UserName_navigation);
		tvFullName.setText(Common.currentUser.getName());

		// Load menu
		mRecyclerMenu = (RecyclerView) findViewById(R.id.recyclerMenu);
		mRecyclerMenu.setHasFixedSize(true);
		mRecyclerMenu.setLayoutManager(new LinearLayoutManager(this));

		loadMenuFromFireBase();
	}

	private void loadMenuFromFireBase() {

		FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter =
				new FirebaseRecyclerAdapter<Category, MenuViewHolder>(
						Category.class, R.layout.item_menu_recyclerlist, MenuViewHolder.class,databaseReference) {
			@Override
			protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
				viewHolder.tvNameMenu.setText(model.getName());
				Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imgMenu);
				final Category itemClicked = model;
				viewHolder.setListener(new ItemClickListenerCustom() {
					@Override
					public void onItemClick(View view, int position, boolean isLongClick) {
						Toast.makeText(Home.this, ""+itemClicked.getName(), Toast.LENGTH_SHORT).show();

						Intent intentFood = new Intent(Home.this, FoodList.class);
						intentFood.putExtra(KEY_INTENT_MENU_ID, String.valueOf(position+1));
						startActivity(intentFood);
					}
				});
			}
		};
		mRecyclerMenu.setAdapter(adapter);
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement


		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.nav_menu) {

		} else if (id == R.id.nav_Cart) {
			Intent cartIntent = new Intent(Home.this, CartActivity.class);
			startActivity(cartIntent);

		} else if (id == R.id.nav_order) {
			Intent orderIntent = new Intent(Home.this, OrderStatus.class);
			startActivity(orderIntent);

		} else if (id == R.id.nav_signout) {
			Intent signOutIntent = new Intent(Home.this, SignIn.class);
			signOutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(signOutIntent);

		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
