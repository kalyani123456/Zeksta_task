package com.ominfo.zekstashop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ominfo.zekstashop.model.ProductListResponse;
import com.ominfo.zekstashop.repo.ProductListRepo;

import java.util.ArrayList;

public class ProductListViewModel extends AndroidViewModel {

    private final ProductListRepo repository ;


    public ProductListViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductListRepo(application);

    }

    public MutableLiveData<ArrayList<ProductListResponse>> loadData() {
        return repository.callAPI();
    }
}
