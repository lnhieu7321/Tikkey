package com.home.intagramapp.Fragment;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


import com.theartofdev.edmodo.cropper.CropImage;


import com.home.intagramapp.R;



public class UploadProductStep1Fragment extends Fragment {
    Button next, open_gallery, btn_open_size, cancel;
    ImageView image_add;
    Uri imageUrl;
    EditText product_name, description, pricing, stock;
    Spinner sp_category,sp_currency;
    Switch sw_public,sw_show_live,sw_dangerous;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_product_step1, container, false);
        image_add = view.findViewById(R.id.image_add);
        cancel = view.findViewById(R.id.cancel);
        product_name = view.findViewById(R.id.product_name);
        description = view.findViewById(R.id.description);
        pricing = view.findViewById(R.id.pricing);
        stock = view.findViewById(R.id.stock);
        sp_category = view.findViewById(R.id.sp_category);
        sp_currency = view.findViewById(R.id.sp_currency);
        sw_dangerous = view.findViewById(R.id.sw_dangerous);
        sw_public = view.findViewById(R.id.sw_public);
        sw_show_live = view.findViewById(R.id.sw_show_live);


        open_gallery = view.findViewById(R.id.open_gallery);
        open_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CropImage.activity()
//                        .setAspectRatio(1,1)
//                        .start(getContext(),UploadProductStep1Fragment.this);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1); // Request code là 1
            }
        });

        btn_open_size = view.findViewById(R.id.btn_open_size);
        btn_open_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(1,1)
                        .start(getContext(),UploadProductStep1Fragment.this);
            }
        });

        next = view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment fragment = new UploadProduct3Fragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                assert fragmentManager != null;
//                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit ();
                String name = product_name.getText().toString();
                String desc = description.getText().toString();
                String price  = pricing.getText().toString();
                String sto  = stock.getText().toString();
                String image = String.valueOf(imageUrl);
                String cate = sp_category.getSelectedItem().toString();
                String curr = sp_currency.getSelectedItem().toString();
                boolean pub = sw_public.isChecked();
                boolean danger = sw_dangerous.isChecked();
                boolean show = sw_show_live.isChecked();

                UploadProduct3Fragment uploadProduct3Fragment = UploadProduct3Fragment.newInstance(image, name, desc, price,sto, cate,curr,show,danger,pub);
                // Thay thế Fragment A bằng Fragment B
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, uploadProduct3Fragment);
                transaction.addToBackStack(null);
                transaction.commit();
//
//




            }

        });
        return view;

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            // Lấy đường dẫn của ảnh đã chọn từ thư viện
            imageUrl = data.getData();
            // Hiển thị ảnh lên imageview
            image_add.setImageURI(imageUrl);
        }
    }
}