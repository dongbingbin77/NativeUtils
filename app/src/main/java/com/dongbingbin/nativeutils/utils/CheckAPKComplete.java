package com.dongbingbin.nativeutils.utils;

import android.app.Activity;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class CheckAPKComplete {
    public static long getDexCrc(Context context){
        long result = 0L;
        try {

            ZipFile zipfile = new ZipFile(context.getPackageCodePath());

            ZipEntry dexentry = zipfile.getEntry("classes.dex");

            result = dexentry.getCrc();
        } catch (Exception e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        }
        return result;
    }


    public static String apkSHA1(Context context){
        String apkPath = context.getPackageCodePath();
        String result = "";
        MessageDigest msgDigest = null;

        try {

            msgDigest = MessageDigest.getInstance("SHA-1");

            byte[] bytes = new byte[1024];

            int byteCount;

            FileInputStream fis = new FileInputStream(new File(apkPath));

            while ((byteCount = fis.read(bytes)) > 0)

            {

                msgDigest.update(bytes, 0, byteCount);

            }

            BigInteger bi = new BigInteger(1, msgDigest.digest());

            result = bi.toString(16);

            fis.close();

            //这里添加从服务器中获取哈希值然后进行对比校验

        } catch (Exception e) {

            e.printStackTrace();

        }
        return result;
    }
}
