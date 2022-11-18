package com.ominfo.zekstashop.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BrandData implements Serializable {

        @SerializedName("id")
        private Long mId;
        @SerializedName("name")
        private String mName;

        public Long getId() {
            return mId;
        }

        public void setId(Long id) {
            mId = id;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

}
