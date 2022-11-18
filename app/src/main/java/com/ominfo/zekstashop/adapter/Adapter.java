package com.ominfo.zekstashop.adapter;


import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.zekstashop.R;
import com.ominfo.zekstashop.model.BrandData;
import com.ominfo.zekstashop.model.ProductListResponse;
import com.ominfo.zekstashop.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private final LayoutInflater inflater;
    private ArrayList<ProductListResponse> dataModelArrayList;
    private final ListItemSelectListener listItemSelectListener;
    private final Activity activity;
    private Context context;

    public Adapter(Context ctx, Activity act, ArrayList<ProductListResponse> dataModelArrayList, ListItemSelectListener listItemSelectListener){

        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
        this.listItemSelectListener = listItemSelectListener;
        this.activity = act;
        this.context = ctx;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_product_list_layout, parent, false);
        //holder.setIsRecyclable(false)
        return new MyViewHolder(view,listItemSelectListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //holder.AutoComWeight.setText("0");
        //holder.setIsRecyclable(false);
        if(dataModelArrayList!=null) {

            holder.tvProductName.setText(dataModelArrayList.get(position).getProductName());
            holder.tvPriceValue.setText(dataModelArrayList.get(position).getPrice().trim());

            AppUtils.loadImageURL(context, dataModelArrayList.get(position).getPicture(),
                    holder.ivProduct, null);

            if (dataModelArrayList.get(position).getColors().size() == 0) {
                holder.radioColor1.setVisibility(View.GONE);
                holder.tvColor1.setVisibility(View.GONE);
                holder.radioColor2.setVisibility(View.GONE);
                holder.radioColor3.setVisibility(View.GONE);
                holder.tvColor2.setVisibility(View.GONE);
                holder.tvColor3.setVisibility(View.GONE);
            } else if (dataModelArrayList.get(position).getColors().size() == 1) {
                holder.tvColor1.setText(dataModelArrayList.get(position).getColors().get(0));
                holder.radioColor2.setVisibility(View.GONE);
                holder.radioColor3.setVisibility(View.GONE);
                holder.tvColor2.setVisibility(View.GONE);
                holder.tvColor3.setVisibility(View.GONE);
            } else if (dataModelArrayList.get(position).getColors().size() == 2) {
                holder.tvColor1.setText(dataModelArrayList.get(position).getColors().get(0));
                holder.tvColor2.setText(dataModelArrayList.get(position).getColors().get(1));
                holder.radioColor3.setVisibility(View.GONE);
                holder.tvColor3.setVisibility(View.GONE);
            } else {
                holder.tvColor1.setText(dataModelArrayList.get(position).getColors().get(0));
                holder.tvColor2.setText(dataModelArrayList.get(position).getColors().get(1));
                holder.tvColor3.setText(dataModelArrayList.get(position).getColors().get(2));
            }
            holder.radioColor1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        holder.radioColor2.setChecked(false);
                        holder.radioColor3.setChecked(false);
                        dataModelArrayList.get(position).setSelected_color(holder.tvColor1.getText().toString());
                        notifyItemChanged(position, dataModelArrayList.get(position));
                        listItemSelectListener.onItemClick(dataModelArrayList);
                    }
                }
            });
            holder.radioColor2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        holder.radioColor1.setChecked(false);
                        holder.radioColor3.setChecked(false);
                        dataModelArrayList.get(position).setSelected_color(holder.tvColor2.getText().toString());
                        notifyItemChanged(position, dataModelArrayList.get(position));
                        listItemSelectListener.onItemClick(dataModelArrayList);
                    }
                }
            });
            holder.radioColor3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        holder.radioColor2.setChecked(false);
                        holder.radioColor1.setChecked(false);
                        dataModelArrayList.get(position).setSelected_color(holder.tvColor3.getText().toString());
                        notifyItemChanged(position, dataModelArrayList.get(position));
                        listItemSelectListener.onItemClick(dataModelArrayList);
                    }
                }
            });
            setDropdownBrandList(holder.AutoComBrand, dataModelArrayList.get(position).getBrands(), position);
            holder.AutoComWeight.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                @Override
                public void onTextChanged(final CharSequence s, int start, int before,
                                          int count) {

                }

                @Override
                public void afterTextChanged(final Editable s) {
                    //avoid triggering event when text is empty
                    if (s.length() > 0) {
                        dataModelArrayList.get(position).setQty(s.toString());//position,new VehicleDetailsLrImage(,mListData.get(position).getImage(),mListData.get(position).getImageUri()));mContext.notifyDataSetChanged();
                        notifyItemChanged(position, dataModelArrayList.get(position));
                        listItemSelectListener.onItemClick(dataModelArrayList);
                    }
                }
            });
        }else{
            //holder.setIsRecyclable(true);
        }
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatTextView tvProductName,tvPriceValue,tvColor1,tvColor2,tvColor3;
        RadioButton radioColor1, radioColor2, radioColor3;
        AppCompatAutoCompleteTextView AutoComWeight;
        ListItemSelectListener listItemSelectListener;
        CircleImageView ivProduct;
        AppCompatAutoCompleteTextView AutoComBrand;

        public MyViewHolder(@NonNull View itemView, ListItemSelectListener listItemSelectListener) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.iv_flag);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPriceValue = itemView.findViewById(R.id.tvPriceValue);
            AutoComBrand = itemView.findViewById(R.id.AutoComBrand);
            AutoComWeight = itemView.findViewById(R.id.AutoComWeight);
            radioColor1 = itemView.findViewById(R.id.radioColor1);
            radioColor2 = itemView.findViewById(R.id.radioColor2);
            radioColor3 = itemView.findViewById(R.id.radioColor3);
            tvColor1 = itemView.findViewById(R.id.tvColor1);
            tvColor2 = itemView.findViewById(R.id.tvColor2);
            tvColor3 = itemView.findViewById(R.id.tvColor3);

            this.listItemSelectListener = listItemSelectListener;
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            listItemSelectListener.onItemClick(dataModelArrayList);
        }
    }

    public interface ListItemSelectListener {
        void onItemClick(ArrayList<ProductListResponse> mData);
    }

    public void filteredlist(ArrayList<ProductListResponse> filterlist){
        dataModelArrayList = filterlist;
        notifyDataSetChanged();
    }
    public void updateList(ArrayList<ProductListResponse> updateList){
        dataModelArrayList = updateList;
        notifyDataSetChanged();
    }

    //set value to gender dropdown
    private void setDropdownBrandList(AppCompatAutoCompleteTextView AutoComBrand,List<BrandData> brandList,int tempPos) {
        try {
            int pos = 0;
            if (brandList != null && brandList.size() > 0) {
                String[] mDropdownList = new String[brandList.size()];
                for (int i = 0; i < brandList.size(); i++) {
                    mDropdownList[i] = String.valueOf(brandList.get(i).getName());
                }
                //AutoComTextViewVehNo.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        context,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //AutoComTextViewVehNo.setThreshold(1);
                AutoComBrand.setAdapter(adapter);
                //mSelectedColor = mDropdownList[pos];
                AutoComBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      dataModelArrayList.get(tempPos).setSelected_brand(parent.getItemAtPosition(position).toString());
                        notifyItemChanged(position, dataModelArrayList.get(position));
                      listItemSelectListener.onItemClick(dataModelArrayList);
                    }
                });
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
