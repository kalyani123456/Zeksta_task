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

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    private ArrayList<ProductListResponse> dataModelArrayList;
    private final ListItemSelectListener listItemSelectListener;
    private final Activity activity;
    private Context context;
    AppCompatAutoCompleteTextView AutoComBrand;


    public SummaryAdapter(Context ctx, Activity act, ArrayList<ProductListResponse> dataModelArrayList, ListItemSelectListener listItemSelectListener){

        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
        this.listItemSelectListener = listItemSelectListener;
        this.activity = act;
        this.context = ctx;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_product_summary_layout, parent, false);

        return new MyViewHolder(view,listItemSelectListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(dataModelArrayList!=null) {
            //holder.setIsRecyclable(false);
            try {
                String splitPrice[] = dataModelArrayList.get(position).getPrice().split("\\$");
                String totalSplitPrice = splitPrice[1].replaceAll(",", "");
                double total =  Double.parseDouble(totalSplitPrice) * Integer.parseInt(dataModelArrayList.get(position).getQty());
                holder.tvTotalPriceValue.setText("$" + String.format("%.2f", total) + " (Qty " + dataModelArrayList.get(position).getQty() + " price)");
            }catch (Exception e){
                e.printStackTrace();
            }
            holder.tvProductName.setText(dataModelArrayList.get(position).getProductName());
            holder.tvPriceValue.setText(dataModelArrayList.get(position).getPrice());
            holder.tvQtyValue.setText(dataModelArrayList.get(position).getQty());
            holder.tvSelectedBrandValue.setText(dataModelArrayList.get(position).getSelected_brand());
            holder.tvSelectedColorValue.setText(dataModelArrayList.get(position).getSelected_color());

            AppUtils.loadImageURL(context, dataModelArrayList.get(position).getPicture(),
                    holder.ivProduct, null);

        }else{
            //holder.setIsRecyclable(true);
        }
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatTextView tvProductName,tvQtyValue,tvPriceValue,tvSelectedColorValue,tvSelectedBrandValue,tvTotalPriceValue;
        ListItemSelectListener listItemSelectListener;
        CircleImageView ivProduct;

        public MyViewHolder(@NonNull View itemView, ListItemSelectListener listItemSelectListener) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.iv_flag);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvQtyValue = itemView.findViewById(R.id.tvQtyValue);
            tvPriceValue = itemView.findViewById(R.id.tvPriceValue);
            tvSelectedColorValue = itemView.findViewById(R.id.tvSelectedColorValue);
            tvSelectedBrandValue = itemView.findViewById(R.id.tvSelectedBrandValue);
            tvTotalPriceValue = itemView.findViewById(R.id.tvTotalPriceValue);

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

}
