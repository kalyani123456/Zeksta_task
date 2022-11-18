package com.ominfo.zekstashop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.ominfo.zekstashop.adapter.Adapter;
import com.ominfo.zekstashop.R;
import com.ominfo.zekstashop.model.ProductListResponse;
import com.ominfo.zekstashop.viewmodel.ProductListViewModel;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity implements Adapter.ListItemSelectListener {
    Adapter adapter;
    RecyclerView recyclerView;
    ArrayList<ProductListResponse> modelRecyclerArrayList;
    ProductListViewModel productListViewModel;
    AppCompatButton btnSubmitProduct;
    // assigning ID of the toolbar to a
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        modelRecyclerArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        btnSubmitProduct = findViewById(R.id.btnSubmitProduct);
        productListViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);

        initRecycler();


        productListViewModel.loadData().observe(this, new Observer<ArrayList<ProductListResponse>>() {
            @Override
            public void onChanged(ArrayList<ProductListResponse> countries) {
                if(countries!=null){
                    modelRecyclerArrayList = countries;
                    adapter.updateList(modelRecyclerArrayList);
                }
            }
        });

        btnSubmitProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modelRecyclerArrayList = validateProductList();
                if(modelRecyclerArrayList.size()>0) {
                    Intent intent = new Intent(ProductListActivity.this, ProductSummaryActivity.class);
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST", (Serializable) modelRecyclerArrayList);
                    intent.putExtra("BUNDLE", args);
                    startActivity(intent);
                }else
                {
                    Toast.makeText(ProductListActivity.this,"Please add products!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initRecycler() {
        adapter = new Adapter(this,this,modelRecyclerArrayList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

    }

    private ArrayList<ProductListResponse> validateProductList(){
        ArrayList<ProductListResponse> tempList = new ArrayList<>();
        for(int i=0;i<modelRecyclerArrayList.size();i++){
            ProductListResponse data = modelRecyclerArrayList.get(i);
            if(!TextUtils.isEmpty(data.getSelected_brand()) && !TextUtils.isEmpty(data.getSelected_color())
             && (!TextUtils.isEmpty(data.getQty()) && !data.getQty().equals("0"))){
                tempList.add(data);
            }
        }
        return tempList;
    }

    @Override
    public void onItemClick(ArrayList<ProductListResponse> mData) {
        modelRecyclerArrayList = mData;
        //adapter.updateList(modelRecyclerArrayList);
    }
}