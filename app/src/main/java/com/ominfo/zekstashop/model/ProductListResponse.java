
package com.ominfo.zekstashop.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;
public class ProductListResponse implements Serializable {

    @SerializedName("brands")
    private List<BrandData> mBrands;
    @SerializedName("colors")
    private List<String> mColors;
    @SerializedName("picture")
    private String mPicture;
    @SerializedName("selected_color")
    private String selected_color;
    @SerializedName("selected_brand")
    private String selected_brand;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("productName")
    private String mProductName;
    @SerializedName("_id")
    private String m_id;
    @SerializedName("qty")
    private String qty;

    public String getSelected_brand() {
        return selected_brand;
    }

    public void setSelected_brand(String selected_brand) {
        this.selected_brand = selected_brand;
    }

    public String getSelected_color() {
        return selected_color;
    }

    public void setSelected_color(String selected_color) {
        this.selected_color = selected_color;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public List<BrandData> getBrands() {
        return mBrands;
    }

    public void setBrands(List<BrandData> brands) {
        mBrands = brands;
    }

    public List<String> getColors() {
        return mColors;
    }

    public void setColors(List<String> colors) {
        mColors = colors;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
