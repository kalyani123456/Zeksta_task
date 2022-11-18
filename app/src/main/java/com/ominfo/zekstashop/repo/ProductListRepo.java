package com.ominfo.zekstashop.repo;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.util.JsonMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ominfo.zekstashop.model.BrandData;
import com.ominfo.zekstashop.model.ProductListResponse;
import com.ominfo.zekstashop.util.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListRepo {

    private final MutableLiveData<ArrayList<ProductListResponse>> allCountries;
    private final ArrayList<ProductListResponse> countryList;
    private final Application application;


    public ProductListRepo(Application application) { //application is subclass of context

         //cant call abstract func but since instance is there we can do this
        countryList = new ArrayList<>();
        allCountries = new MutableLiveData<>();
        this.application = application;

    }

    public MutableLiveData<ArrayList<ProductListResponse>> callAPI(){

         String str = AppUtils.loadJSONFromAsset(application);
         //JSONObject dataArray = null;
        JSONArray dataArray = null;
        try {
            assert str != null;
            dataArray =  new JSONArray(str);
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject res = dataArray.getJSONObject(i);
                ProductListResponse modelRecycler = new ProductListResponse();
                modelRecycler.set_id(res.getString("_id"));
                modelRecycler.setPrice(res.getString("price"));
                modelRecycler.setPicture(res.getString("picture"));

                Type listTypeColors = new TypeToken<List<String>>() {}.getType();
                List<String> colors = new Gson().fromJson(res.getString("colors") , listTypeColors);

                modelRecycler.setColors(colors);
                modelRecycler.setProductName(res.getString("productName"));

                Type listType = new TypeToken<List<BrandData>>() {}.getType();
                List<BrandData> brands = new Gson().fromJson(res.getString("brands") , listType);
                modelRecycler.setBrands(brands);
                countryList.add(modelRecycler);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        allCountries.setValue(countryList);

        return allCountries;

    }

}
