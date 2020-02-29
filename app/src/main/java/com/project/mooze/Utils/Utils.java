package com.project.mooze.Utils;

import android.Manifest;
import android.app.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Utils {

    public static final String stripe_key = "pk_test_X72hcI4fGnsFcZhUxnY2R7Dl";

    public static void hideStatusBar(Activity activity){
        View decorView = activity.getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }


}
