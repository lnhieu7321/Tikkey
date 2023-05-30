package com.home.intagramapp.Fragment;



import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.home.intagramapp.MainActivity;
import com.home.intagramapp.PostActivity;
import com.home.intagramapp.R;

public class CustomChoseDialog extends Fragment {
    TextView textView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_custom_chose, container, false);

        textView = view.findViewById(R.id.add_pic_video);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                startActivity(intent);
            }


        });
        return view;
    }
}
