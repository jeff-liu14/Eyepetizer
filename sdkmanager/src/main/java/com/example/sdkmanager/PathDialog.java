package com.example.sdkmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * @author moment
 * @date 2018/3/13.
 */

public class PathDialog extends AlertDialog {

    private RelativeLayout rl_sdcard, rl_phone, root;
    private TextView tv_phone_size, tv_sdcard_size;
    private RadioButton rb_phone, rb_sdcard;
    private OnPathChangeListener changeListener;

    public void setOnPathChangeListener(OnPathChangeListener changeListener) {
        this.changeListener = changeListener;
    }


    public PathDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.path_dialog);


        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {

        // 判断SD卡是否可用
        if (SdCardManager.getInstance().isDiskAvailable()) {
            rl_sdcard.setVisibility(View.VISIBLE);
            String sdcard = SdCardManager.getInstance().getSdcardName();
            String size = "剩余"
                    + StorageVolumeUtil.getSizeStr(StorageVolumeUtil.getAvailableSize(sdcard)) + "可用，共"
                    + StorageVolumeUtil.getSizeStr(StorageVolumeUtil.getTotalSize(sdcard));
            tv_sdcard_size.setText("" + size);
        } else {
            rl_sdcard.setVisibility(View.GONE);
        }

        // 获取手机存储路径
        String phone = SdCardManager.getInstance().getCacheName();
        // 计算路径大小
        String size = "剩余"
                + StorageVolumeUtil.getSizeStr(StorageVolumeUtil.getAvailableSize(phone)) + "可用，共"
                + StorageVolumeUtil.getSizeStr(StorageVolumeUtil.getTotalSize(phone));
        tv_phone_size.setText("" + size);
        if (SdCardManager.getInstance().isDiskNow()) {
            rb_sdcard.setChecked(true);
            rb_phone.setChecked(false);
        } else {
            rb_sdcard.setChecked(false);
            rb_phone.setChecked(true);
        }
        rb_phone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SdCardManager.getInstance().changePath(SdCardManager.DownloadPath.CACHE);
                    if (changeListener != null) {
                        changeListener.onPathChange(SdCardManager.DownloadPath.CACHE);
                    }

                } else {
                    SdCardManager.getInstance().changePath(SdCardManager.DownloadPath.SDCARD);
                    if (changeListener != null) {
                        changeListener.onPathChange(SdCardManager.DownloadPath.SDCARD);
                    }
                }
            }
        });
        rb_sdcard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SdCardManager.getInstance().changePath(SdCardManager.DownloadPath.SDCARD);
                    if (changeListener != null) {
                        changeListener.onPathChange(SdCardManager.DownloadPath.SDCARD);
                    }
                } else {
                    SdCardManager.getInstance().changePath(SdCardManager.DownloadPath.CACHE);
                    if (changeListener != null) {
                        changeListener.onPathChange(SdCardManager.DownloadPath.CACHE);
                    }
                }
            }
        });

        rl_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SdCardManager.getInstance().isDiskAvailable()) {
                    rb_phone.setChecked(!rb_phone.isChecked());
                }
            }
        });

        rl_sdcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SdCardManager.getInstance().isDiskAvailable()) {
                    rb_sdcard.setChecked(!rb_sdcard.isChecked());
                }
            }
        });
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        rl_sdcard = findViewById(R.id.rl_sdcard);
        rl_phone = findViewById(R.id.rl_phone);
        tv_phone_size = findViewById(R.id.tv_phone_size);
        tv_sdcard_size = findViewById(R.id.tv_sdcard_size);
        rb_phone = findViewById(R.id.rb_phone);
        rb_sdcard = findViewById(R.id.rb_sdcard);
        root = findViewById(R.id.root);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface OnPathChangeListener {
        /**
         * 切换存储路径
         *
         * @param downloadPath
         */
        void onPathChange(SdCardManager.DownloadPath downloadPath);
    }
}
