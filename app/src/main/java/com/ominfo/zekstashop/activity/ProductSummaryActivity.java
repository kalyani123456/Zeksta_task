package com.ominfo.zekstashop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.zekstashop.adapter.Adapter;
import com.ominfo.zekstashop.R;
import com.ominfo.zekstashop.adapter.SummaryAdapter;
import com.ominfo.zekstashop.model.ProductListResponse;
import com.ominfo.zekstashop.viewmodel.ProductListViewModel;

import java.util.ArrayList;

public class ProductSummaryActivity extends AppCompatActivity implements SummaryAdapter.ListItemSelectListener {
    SummaryAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<ProductListResponse> modelRecyclerArrayList;
    AppCompatButton btnSubmitProduct;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_summary);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        modelRecyclerArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        btnSubmitProduct = findViewById(R.id.btnSubmitProduct);

        initRecycler();

        btnSubmitProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             finishAffinity();
            }
        });
    }

    private void initRecycler() {
        Intent intent = getIntent();
        if(intent!=null) {
            Bundle args = intent.getBundleExtra("BUNDLE");
            modelRecyclerArrayList = (ArrayList<ProductListResponse>) args.getSerializable("ARRAYLIST");
            adapter = new SummaryAdapter(this, this, modelRecyclerArrayList, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onItemClick(ArrayList<ProductListResponse> mData) {
        modelRecyclerArrayList = mData;
    }
}