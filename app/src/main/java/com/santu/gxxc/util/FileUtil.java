package com.santu.gxxc.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ChuyaoShi on 16/11/11.
 */

public class FileUtil {

    public static String getQQShareLocalImage(Context context) {
        String imageName = "icon_share_local.png";
        File imageFile = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            imageFile = new File(context.getExternalFilesDir(null), imageName);
        } else {
            imageFile = new File(context.getFilesDir(), imageName);
        }
        if (imageFile.exists())
            return imageFile.getAbsolutePath();
        else {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = context.getAssets().open(imageName);
                outputStream = new FileOutputStream(imageFile);
                int len;
                byte[] buff = new byte[2048];
                while ((len = inputStream.read(buff)) != -1) {
                    outputStream.write(buff, 0, len);
                }
                outputStream.flush();
                return imageFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (outputStream != null)
                        outputStream.close();
                    if (inputStream != null)
                        inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

}
