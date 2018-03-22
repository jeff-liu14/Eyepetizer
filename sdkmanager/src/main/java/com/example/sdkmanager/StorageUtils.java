package com.example.sdkmanager;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * @author moment
 * @date 2018/3/22.
 */

public class StorageUtils {

    public static List<StorageVolumeUtil.MyStorageVolume> getStorageInfos(Application application) {
        List<StorageVolumeUtil.MyStorageVolume> list = StorageVolumeUtil.getVolumeList(application);
        List<StorageVolumeUtil.MyStorageVolume> mountedList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isMounted(application)) {
                    if (!TextUtils.isEmpty(list.get(i).mPath)) {
                        mountedList.add(list.get(i));
                    }
                }
            }
        }
        return mountedList;
    }

    public static void createDir(Application application) {
        File[] files;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            files = application.getExternalFilesDirs("download");
            for (File file : files) {
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
        }
        File file = getExternalStorageDirectory();
        if (!file.exists()) {
            file.mkdirs();
        }

        File file1 = application.getExternalFilesDir("download");
        if (!file1.exists()) {
            file1.mkdirs();
        }

        File sdcard = new File(SdCardManager.getInstance().getDiskDownloadDir());
        if (!sdcard.exists()) {
            sdcard.mkdirs();
        }
        File cache = new File(SdCardManager.getInstance().getCacheDownloadDir());
        if (!cache.exists()) {
            cache.mkdirs();
        }
    }

}
