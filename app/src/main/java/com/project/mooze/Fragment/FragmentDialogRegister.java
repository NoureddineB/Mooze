package com.project.mooze.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.project.mooze.Activity.RegisterActivity;
import com.project.mooze.R;

public class FragmentDialogRegister extends DialogFragment {

    FragmentDialogRegister c = this;

    public static FragmentDialogRegister newInstance() {
        FragmentDialogRegister frag = new FragmentDialogRegister();
        Bundle args = new Bundle();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_register, container, false);


        Button b = v.findViewById(R.id.bouton_ok_dialog);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v2) {
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);
                c.dismiss();
            }
        });




        return v;
    }

}
