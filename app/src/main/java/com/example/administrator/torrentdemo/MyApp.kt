package com.example.administrator.torrentdemo

import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import android.util.Log.getStackTraceString
import com.xunlei.downloadlib.XLTaskHelper


/**
 *
 * author hechao
 * date 2018/6/28 0028
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        XLTaskHelper.init(this)
    }

    override fun getPackageName(): String {
        return if (Log.getStackTraceString(Throwable()).contains("com.xunlei.downloadlib")) {
            "com.xunlei.downloadprovider"
        } else super.getPackageName()
    }

    override fun getPackageManager(): PackageManager {
        return if (Log.getStackTraceString(Throwable()).contains("com.xunlei.downloadlib")) {
            DelegateApplicationPackageManager(super.getPackageManager())
        } else super.getPackageManager()
//        return DelegateApplicationPackageManager(super.getPackageManager())
    }
}