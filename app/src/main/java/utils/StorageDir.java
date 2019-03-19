package utils;

import android.content.Context;
import android.os.Environment;
import androidx.core.os.EnvironmentCompat;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StorageDir {
    private String currentPhotoPath;

    public File createImageFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss", new Locale("ru")).format(new Date());
        String imageFileName = "jpeg_" + timeStamp + "_";
        Utils.log(timeStamp);
        Utils.log(imageFileName);
        //File storageDir = Environment.getExternalStoragePublicDirectory(
                //Environment.DIRECTORY_PICTURES);
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Utils.log(storageDir.getAbsolutePath());
        File image = File.createTempFile(
                imageFileName,  /* префикс */
                ".jpg",         /* расширение */
                storageDir      /* директория */
        );
        Utils.log(image.getAbsolutePath());
        // сохраняем пусть для использования с интентом ACTION_VIEW
        currentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }
}
