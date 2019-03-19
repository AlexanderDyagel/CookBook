package utils;

import android.util.Log;

public class Utils {
    public static final int IMAGE_REQUEST_CODE = 48;
    public static final int CAMERA_REQUEST_CODE = 14;
    public static final String TAG = "cookbooklogs";

    public static void log(String text){
        Log.d(TAG, text);
    }
}
