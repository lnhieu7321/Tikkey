package com.home.intagramapp.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.home.intagramapp.MainActivity;
import com.home.intagramapp.PostActivity;
import com.home.intagramapp.R;

import java.io.File;
import java.util.HashMap;

public class UploadProduct3Fragment extends Fragment {
    Button add, remove, next;
    TextView count;
    ImageView product;

    TextView pricing, product_name;

    String myUrl = "";

    StorageTask uploadTask ;
    StorageReference storageReference;
    FirebaseStorage storage = FirebaseStorage.getInstance();



    int counts = 1;


    private String name;
    private String desc;
    private String price;
    private String sto;
    private String cate;
    private String curr;
    private boolean danger;
    private boolean pub;
    private boolean show;


    private static final String ARG_IMAGE = "image";
    private static final String ARG_EDIT_TEXT_NAME = "name";
    private static final String ARG_EDIT_TEXT_DESC = "desc";
    private static final String ARG_EDIT_TEXT_PRICE = "price";
    private static final String ARG_EDIT_TEXT_STOCK = "sto";
    private static final String ARG_SPINNER_CATEGORY = "cate";
    private static final String ARG_SPINNER_CURRENCY = "curr";
    private static final String ARG_SWITCH_PUBLIC = "pub";
    private static final String ARG_SWITCH_SHOW_LIVE = "show";
    private static final String ARG_SWITCH_DANGEROUS = "danger";

    Uri imageUrl = Uri.fromFile(new File(ARG_IMAGE));

    public static UploadProduct3Fragment newInstance(String image, String name,String desc, String price, String sto,String cate,String curr, boolean danger,boolean pub, boolean show) {
        UploadProduct3Fragment fragment = new UploadProduct3Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMAGE, image);
        args.putString(ARG_EDIT_TEXT_NAME, name);
        args.putString(ARG_EDIT_TEXT_DESC, desc);
        args.putString(ARG_EDIT_TEXT_PRICE, price);
        args.putString(ARG_EDIT_TEXT_STOCK, sto);
        args.putString(ARG_SPINNER_CATEGORY, cate);
        args.putString(ARG_SPINNER_CURRENCY, curr);
        args.putBoolean(ARG_SWITCH_PUBLIC, pub);
        args.putBoolean(ARG_SWITCH_SHOW_LIVE, show);
        args.putBoolean(ARG_SWITCH_DANGEROUS, danger);
        fragment.setArguments(args);
        return fragment;
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_product3, container, false);
        add = view.findViewById(R.id.add);
        remove = view.findViewById(R.id.remove);
        next = view.findViewById(R.id.next);
        count = view.findViewById(R.id.count);
        product = view.findViewById(R.id.product);
        pricing = view.findViewById(R.id.pricing);
        product_name = view.findViewById(R.id.product_name);

        if (getArguments() != null) {
            String image = getArguments().getString(ARG_IMAGE);
            String name = getArguments().getString(ARG_EDIT_TEXT_NAME);
//            String desc = getArguments().getString(ARG_EDIT_TEXT_DESC);
            String price = getArguments().getString(ARG_EDIT_TEXT_PRICE);
//            String sto = getArguments().getString(ARG_EDIT_TEXT_STOCK);
//            String cate = getArguments().getString(ARG_SPINNER_CATEGORY);
//            String curr = getArguments().getString(ARG_SPINNER_CURRENCY);
//            boolean Pub = getArguments().getBoolean(ARG_SWITCH_PUBLIC);
//            boolean show = getArguments().getBoolean(ARG_SWITCH_SHOW_LIVE);
//            boolean danger = getArguments().getBoolean(ARG_SWITCH_DANGEROUS);

            product.setImageURI (Uri.parse(image));
            product_name.setText (name);
            pricing.setText (price);
        }



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counts++;
                count.setText(String.valueOf(counts));
                remove.setEnabled(true);
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (counts > 1) {
                    counts--;
                    count.setText(String.valueOf(counts));
                }else{

                    count.setText(String.valueOf(counts));
                }
            }
        });
         next.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 uploadImage();
             }
         });



        return view;
    }

    private String getFileExtensions(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait");
        progressDialog.show();





        if(getArguments().getString(ARG_IMAGE) != null){
            StorageReference filereferance = storage.getReference().child(System.currentTimeMillis()
                    + "."+ getFileExtensions(Uri.parse(getArguments().getString(ARG_IMAGE))));



            uploadTask = filereferance.putFile(Uri.parse(getArguments().getString(ARG_IMAGE)));
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){

                        throw  task.getException();

                    }
                    return filereferance.getDownloadUrl();

                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if(task.isSuccessful()){

                        Uri downloadUrl = task.getResult();

                        myUrl = downloadUrl.toString();
                        Bundle args = getArguments();
                        if (args != null) {
                            name = args.getString(ARG_EDIT_TEXT_NAME);
                            desc = args.getString(ARG_EDIT_TEXT_DESC);
                            price = args.getString(ARG_EDIT_TEXT_PRICE);
                            sto = args.getString(ARG_EDIT_TEXT_STOCK);
                            cate = args.getString(ARG_SPINNER_CATEGORY);
                            curr = args.getString(ARG_SPINNER_CURRENCY);
                            danger = args.getBoolean(ARG_SWITCH_DANGEROUS);
                            pub = args.getBoolean(ARG_SWITCH_PUBLIC);
                            show = args.getBoolean(ARG_SWITCH_SHOW_LIVE);
                        }

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product");

                        String productid = reference.push().getKey();
                        HashMap<String,Object> product = new HashMap<>();
                        product.put("productid",productid);
                        product.put("productimage",myUrl);
                        product.put("name",name);
                        product.put("description",desc);
                        product.put("price",price);
                        product.put("stock",sto);
                        product.put("category",cate);
                        product.put("currency",curr);
                        product.put("Public",pub);
                        product.put("ShowLive",show);
                        product.put("DangerousGood",danger);
                        product.put("Number",count.getText().toString());
                        product.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());

                        reference.child(productid).setValue(product);

                        progressDialog.dismiss();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                    else {
                        Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getActivity(), ""+ e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        else {
            Toast.makeText(getActivity(), "no Image Selected!", Toast.LENGTH_SHORT).show();
        }


    }



}