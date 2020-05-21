package com.project.mooze.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.project.mooze.Activity.OrderActivity;
import com.project.mooze.Activity.RestaurantActivity;
import com.project.mooze.R;

import java.io.IOException;
import java.util.Objects;
import java.util.zip.Inflater;

import javax.xml.transform.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static androidx.core.content.ContextCompat.checkSelfPermission;


public class QRfragment extends Fragment implements ZXingScannerView.ResultHandler {


    private ZXingScannerView mScannerView;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private View qr_fragment;
    private Button button_allow;
    private  SharedPreferences sharedPreferences;


    public QRfragment() {
        // Required empty public constructor
    }


    public static QRfragment newInstance(String param1, String param2) {
        QRfragment fragment = new QRfragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        checkUserCameraPermissions();
        mScannerView = new ZXingScannerView(getActivity());
        qr_fragment = inflater.inflate(R.layout.fragment_qrfragment, container, false);
        button_allow = qr_fragment.findViewById(R.id.button_allow);
        sharedPreferences = getActivity().getSharedPreferences("PREFS",0);
        configureButton();
        //If the user dont give the acces to his camera we show another layout telling him he need to if he want to use QR reader
        if (checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            checkUserCameraPermissions();
            return qr_fragment;
        } else {
            mScannerView.stopCamera();
            return mScannerView;
        }


    }

    private void configureButton(){
        button_allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserCameraPermissions();
            }
        });
    }

   /* private void manageCamera() {
        if (!checkIfVisible()) {
            mScannerView.stopCamera();
        }
    }

    private boolean checkIfVisible() {
        SharedPreferences preferences = getActivity().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        return preferences.getBoolean("isQRcode", true);
    }*/


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(com.google.zxing.Result rawResult) {
        Vibrator vibrator = (Vibrator) getActivity().getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
        String result = rawResult.getText();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        editor.putInt("RESTOID", Integer.parseInt(result));
        editor.apply();
        startActivity(intent);
        mScannerView.resumeCameraPreview(this);
    }

    private boolean checkUserCameraPermissions() {
        if (checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
            }
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }

        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



}
