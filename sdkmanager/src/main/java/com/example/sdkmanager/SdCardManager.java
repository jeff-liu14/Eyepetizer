package com.example.sdkmanager;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author moment
 * @date 2018/3/22.
 */

public class SdCardManager {
    public static SdCardManager sdCardManager;
    public static final String SDCARD_NAME = "sdcard";
    public static final String CACHE_NAME = "cache";
    /**
     * SD卡中应用固定存储路径
     * Android/data/xx.xx.xx/files/xxx
     */
    public static String DOWNLOAD_RELATIVE_PATH = File.separator + "Android" + File.separator + "data" + File.separator + "com.cn.maimeng" + File.separator +
            "files" + File.separator + "download";

    public enum DownloadPath {
        SDCARD,
        CACHE
    }

    public static SdCardManager getInstance() {
        if (sdCardManager == null) {
            synchronized (SdCardManager.class) {
                sdCardManager = new SdCardManager();
            }
        }
        return sdCardManager;
    }

    public WeakReference<Application> weakReference;

    public SdCardManager init(Application application, String applicationId) {
        weakReference = new WeakReference<Application>(application);
        if (!TextUtils.isEmpty(applicationId)) {
            DOWNLOAD_RELATIVE_PATH = File.separator + "Android" + File.separator + "data" + File.separator + applicationId + File.separator +
                    "files" + File.separator + "download";
        }
        try {
            StorageUtils.createDir(application);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return this;
    }

    public SdCardManager changePath(DownloadPath path) {
        ACache aCache = ACache.get(weakReference.get(), "download");
        switch (path) {
            case SDCARD: {
                if (aCache != null) {
                    String real = getDiskDownloadDir();
                    if (!TextUtils.isEmpty(real)) {
                        aCache.put("path", real);
                        aCache.put("type", SDCARD_NAME);
                    }
                }
            }
            break;
            case CACHE: {
                if (aCache != null) {
                    String real = getCacheDownloadDir();
                    if (!TextUtils.isEmpty(real)) {
                        aCache.put("path", real);
                        aCache.put("type", CACHE_NAME);
                    }
                }
            }
            break;
            default:
                break;
        }
        return this;
    }

    /**
     * 获取当前使用的存储路径
     * 在下载时获取存储路径是使用
     *
     * @return 当前存储路径
     */
    public String getPathDir() {
        ACache aCache = ACache.get(weakReference.get(), "download");
        String path = aCache.getAsString("path");
        return path;
    }

    /**
     * 判断当前是否为SD卡
     *
     * @return
     */
    public boolean isDiskNow() {
        ACache aCache = ACache.get(weakReference.get(), "download");
        String type = aCache.getAsString("type");
        if (SDCARD_NAME.equals(type)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断当前存储路径是否为空
     *
     * @return
     */
    public boolean isNullPath() {
        ACache aCache = ACache.get(weakReference.get(), "download");
        String type = aCache.getAsString("type");
        if (TextUtils.isEmpty(type)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断SD卡是否可用
     *
     * @return
     */
    public boolean isDiskAvailable() {
        List<StorageVolumeUtil.MyStorageVolume> mountedList = StorageUtils.getStorageInfos(weakReference.get());
        if (mountedList.isEmpty() || mountedList.size() < 2) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取SD卡存储路径
     *
     * @return
     */
    public String getDiskDownloadDir() {
        return getPath(weakReference.get(), DownloadPath.SDCARD);
    }

    /**
     * 获取手机内存存储路径
     *
     * @return
     */
    public String getCacheDownloadDir() {
        return getPath(weakReference.get(), DownloadPath.CACHE);
    }

    /**
     * 获取存路径
     *
     * @param application
     * @param downloadPath
     * @return
     */
    private String getPath(Application application, DownloadPath downloadPath) {
        List<StorageVolumeUtil.MyStorageVolume> mountedList = StorageUtils.getStorageInfos(application);
        switch (downloadPath) {
            case SDCARD: {
                if (mountedList != null && mountedList.size() > 1) {
                    if (!mountedList.get(1).mPrimary) {
                        String path = mountedList.get(1).mPath + DOWNLOAD_RELATIVE_PATH;
                        return path;
                    } else if (!mountedList.get(0).mPrimary) {
                        String path = mountedList.get(0).mPath + DOWNLOAD_RELATIVE_PATH;
                        return path;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
            case CACHE: {
                if (mountedList != null && mountedList.size() > 1) {
                    if (mountedList.get(0).mPrimary) {
                        String path = mountedList.get(0).mPath + DOWNLOAD_RELATIVE_PATH;
                        return path;
                    } else if (mountedList.get(1).mPrimary) {
                        String path = mountedList.get(1).mPath + DOWNLOAD_RELATIVE_PATH;
                        return path;
                    } else {
                        return null;
                    }
                } else if (mountedList != null && mountedList.size() > 0) {
                    if (mountedList.get(0).mPrimary) {
                        String path = mountedList.get(0).mPath + DOWNLOAD_RELATIVE_PATH;
                        return path;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
            default: {
                if (mountedList != null && mountedList.size() > 1) {
                    if (mountedList.get(0).mPrimary) {
                        String path = mountedList.get(0).mPath + DOWNLOAD_RELATIVE_PATH;
                        return path;
                    } else if (mountedList.get(1).mPrimary) {
                        String path = mountedList.get(1).mPath + DOWNLOAD_RELATIVE_PATH;
                        return path;
                    } else {
                        return null;
                    }
                } else if (mountedList != null && mountedList.size() > 0) {
                    if (mountedList.get(0).mPrimary) {
                        String path = mountedList.get(0).mPath + DOWNLOAD_RELATIVE_PATH;
                        return path;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * 获取SD卡内存路径（用于计算内存大小）
     *
     * @return
     */
    public String getSdcardName() {
        List<StorageVolumeUtil.MyStorageVolume> mountedList = StorageUtils.getStorageInfos(weakReference.get());
        if (mountedList != null && mountedList.size() > 1) {
            if (!mountedList.get(1).mPrimary) {
                String path = mountedList.get(1).mPath;
                return path;
            } else if (!mountedList.get(0).mPrimary) {
                String path = mountedList.get(0).mPath;
                return path;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 获取手机内存路径（用于计算内存大小）
     *
     * @return
     */
    public String getCacheName() {
        List<StorageVolumeUtil.MyStorageVolume> mountedList = StorageUtils.getStorageInfos(weakReference.get());
        if (mountedList != null && mountedList.size() > 1) {
            if (mountedList.get(0).mPrimary) {
                String path = mountedList.get(0).mPath;
                return path;
            } else if (mountedList.get(1).mPrimary) {
                String path = mountedList.get(1).mPath;
                return path;
            } else {
                return null;
            }
        } else if (mountedList != null && mountedList.size() > 0) {
            if (mountedList.get(0).mPrimary) {
                String path = mountedList.get(0).mPath;
                return path;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
