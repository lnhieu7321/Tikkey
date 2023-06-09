package com.home.intagramapp.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.home.intagramapp.Fragment.PostDetailFragment;
import com.home.intagramapp.R;

import java.util.List;


import Model.Product;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.ViewHolder> {
    private Context context ;
    private List<Product> mProduct;
    public TextView category;


    public MyProductAdapter(Context context, List<Product> mProduct) {
        this.context = context;
        this.mProduct = mProduct;
    }



    @NonNull
    @Override
    public MyProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);
        category = view.findViewById(R.id.product_name);
        return new MyProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyProductAdapter.ViewHolder holder, int position) {
        final Product product = mProduct.get(position);
        Glide.with(context).load(product.getProductimage()).into(holder.product_image);
        category.setText(product.getName());

        holder.product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("productid",product.getProductid());
                editor.apply();
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PostDetailFragment()).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mProduct.size();
    }


    public  class  ViewHolder extends RecyclerView.ViewHolder{

        public ImageView product_image;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            product_image = itemView.findViewById(R.id.post_image);


        }
    }
}
