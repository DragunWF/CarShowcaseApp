package com.example.carshowcaseapp.helpers;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carshowcaseapp.R;
import com.google.android.material.snackbar.Snackbar;

public class Utils {
    public static void longToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void toast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void snackbar(String message, View view) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static String getText(EditText text) {
        return String.valueOf(text.getText());
    }

    public static double round(double number) {
        return Math.round(number * 100) / 100.0;
    }

    public static int getImageIdByName(String name) {
        switch (name) {
            case "coupe":
                return R.drawable.coupe;
            case "sedan":
                return R.drawable.sedan;
            case "sedan2":
                return R.drawable.sedan2;
            case "ferrari":
                return R.drawable.ferrari;
            case "minivan":
                return R.drawable.minivan;
            case "suv":
                return R.drawable.suv;
            case "suv2":
                return R.drawable.suv2;
            default:
                return R.drawable.ic_launcher_background;
        }
    }

    public static String toImageName(String name) {
        if (name.isEmpty()) {
            return "";
        }
        String lower = name.toLowerCase();
        return String.join("", lower.split(" "));
    }
}
