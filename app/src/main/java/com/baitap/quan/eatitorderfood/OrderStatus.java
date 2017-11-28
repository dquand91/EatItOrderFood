package com.baitap.quan.eatitorderfood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.baitap.quan.eatitorderfood.Model.Request;
import com.baitap.quan.eatitorderfood.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatus extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        //Init
        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Common.currentUser.getPhoneNumber());
    }

    private void loadOrders(String phoneNumber) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_item_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone").equalTo(phoneNumber)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                viewHolder.tv_order_id.setText(adapter.getRef(position).getKey());
                viewHolder.tv_order_status.setText(convertCodetoStatus(model.getStatus()));
                viewHolder.tv_order_address.setText(model.getAddress());
                viewHolder.tv_order_phone.setText(model.getPhone());
            }
        };

        recyclerView.setAdapter(adapter);
    }

    private String convertCodetoStatus(String status) {
        if (status.equals("0")){
            return "Placed";
        } else if(status.equals("1")) {
            return "On My Way";
        } else {
            return "Shipped";
        }
    }
}
