package com.example.dynamicformactivity.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.dynamicformactivity.app.AppConstants;

import java.io.IOException;
import java.io.InputStream;

public class AppUtils {

    public static String getDynamicFormInput(Context context) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(AppConstants.FORM_INPUT_FILE);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }

    private AppUtils() {

    }
}
