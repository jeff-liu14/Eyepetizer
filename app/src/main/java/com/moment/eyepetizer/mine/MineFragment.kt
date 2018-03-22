package com.moment.eyepetizer.mine

import android.widget.Toast
import com.example.sdkmanager.PathDialog
import com.example.sdkmanager.SdCardManager
import com.moment.eyepetizer.R
import com.moment.eyepetizer.base.BaseFragment
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.mine_fragment.*

/**
 * Created by moment on 2018/2/2.
 */
class MineFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.mine_fragment

    override fun initView() {
        ll_download.setOnClickListener {
            showPathDialog()
        }
    }

    override fun initData() = Unit

    private fun showPathDialog() {
        RxPermissions(activity)
                .request(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe({ aBoolean ->
                    if (aBoolean!!) {
                        val dialog = PathDialog(activity)
                        dialog.setOnPathChangeLisenter {
                            getDownloadPath()
                            dialog.dismiss()
                        }
                        dialog.show()
                        dialog.setCanceledOnTouchOutside(true)
                    } else {
                        Toast.makeText(activity, "无此权限，无法打开此功能！", Toast.LENGTH_SHORT).show()
                    }
                }) { }
    }

    private fun getDownloadPath() {
        if (SdCardManager.getInstance().isDiskNow) {
            val builder = StringBuilder(activity.getString(R.string.download_path_dialog_sdcard) + ":")
            builder.append(SdCardManager.getInstance().diskDownloadDir + "")
            tv_download.text = builder.toString()
        } else {
            val builder = StringBuilder(activity.getString(R.string.download_path_dialog_phone) + ":")
            builder.append(SdCardManager.getInstance().cacheDownloadDir + "")
            tv_download.text = builder.toString()
        }
    }

    override fun onResume() {
        super.onResume()
    }
}
