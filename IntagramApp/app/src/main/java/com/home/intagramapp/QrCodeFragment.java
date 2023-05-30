package com.home.intagramapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;



public class QrCodeFragment extends Fragment {
    ImageButton btnscan;
    ImageView ivqrcode;
    FirebaseUser firebaseUser;








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qr_code, container, false);
        ivqrcode = view.findViewById(R.id.qrcode);
        btnscan = view.findViewById(R.id.scan);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        generateQrCode();
        btnscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                intentIntegrator.setPrompt("");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();
            }
        });


        return view;
    }

    public void generateQrCode(){
//        if (firebaseUser != null){
//            String userID = firebaseUser.getUid();
//            try {
//                MultiFormatWriter writer = new MultiFormatWriter();
//                BitMatrix bitMatrix = writer.encode(userID, BarcodeFormat.QR_CODE, 500,500);
//                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
//                ivqrcode.setImageBitmap(bitmap);
//            } catch (WriterException e) {
//                e.printStackTrace();
//            }
//        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        if (firebaseUser != null) {
            // Lấy userID của user
            String userID = firebaseUser.getUid();

            // Tham chiếu đến node chứa username theo userID
            // Giả sử bạn lưu trữ username trong node Users/userID/username
            DatabaseReference usernameRef = databaseReference.child("Users").child(userID).child("username");

            // Sử dụng phương thức get() để lấy giá trị của username một lần
            usernameRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        // Nếu thành công, lấy giá trị của username từ dataSnapshot
                        String username = task.getResult().getValue(String.class);

                        // Sử dụng username để tạo mã QR như trước
                        try {
                            MultiFormatWriter writer = new MultiFormatWriter();
                            BitMatrix matrix = writer.encode(username, BarcodeFormat.QR_CODE, 500, 500);
                            BarcodeEncoder encoder = new BarcodeEncoder();
                            Bitmap bitmap = encoder.createBitmap(matrix);
                            ivqrcode.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                    }
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null){
            Toast.makeText(getActivity(), "QR code value: " + result.getContents(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(), "thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}