package com.example.administrator.torrentdemo

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.xunlei.downloadlib.XLTaskHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.canonicalName
    private lateinit var mContext: Context
    private val SAVE_PATH = "/sdcard/"
    private val FTP_URL = "ftp://ygdy8:ygdy8@yg45.dydytt.net:7231/阳光电影www.ygdy8.com.匿名者.BD.720p.中英双字幕.mkv"
    private val THUNDER_URL = "thunder://QUFmdHA6Ly9nOmdAdHYua2FpZGEzNjUuY29tOjMxMDAvJUU2JUI3JUIxJUU1JUFFJUFCJUU4JUFFJUExJUU3JUIyJUE0JUU4JUFGJUFEMDMubWt2Wlo="
    private val MAGNET_URL = "magnet:?xt=urn:btih:7B2C226636737170BB5A6C884433BFDE73A8840F"
    private lateinit var mTaskHelper: XLTaskHelper
    private var mTaskId = 0L
    private var mPlayUrl: String = ""
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
        mTaskHelper = XLTaskHelper.instance(mContext)

        btn_download.setOnClickListener {
            if (TextUtils.isEmpty(edit_query.text)) {
                Toast.makeText(mContext, "请粘贴种子下载地址", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val url = edit_query.text.toString()

            if (url.startsWith("magnet:")) {
                downloadMagent(url)
            } else download(url)
        }

        btn_stop.setOnClickListener {
            mTaskHelper.stopTask(mTaskId)
            compositeDisposable.clear()
        }

        btn_play.setOnClickListener {
            if (!videoView.isPlaying) {
                videoView.setVideoPath(mPlayUrl)
                videoView.start()
            }
        }
    }

    private fun download(url: String) {
        mTaskId = mTaskHelper.addThunderTask(url, SAVE_PATH, null)
        compositeDisposable.add(
                Observable.interval(0, 1, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            val taskInfo = mTaskHelper.getTaskInfo(mTaskId)
                            textName.text = "视频名称： \n ${mTaskHelper.getFileName(url)} \n 文件大小： ${convertFileSize(taskInfo.mFileSize)} \n 下载速度： ${convertFileSize(taskInfo.mDownloadSpeed)} \n 已下载： ${convertFileSize(taskInfo.mDownloadSize)} \n status ${taskInfo.mTaskStatus}"
                            Log.e(TAG, "------------>>taskInfo $mTaskId ${mTaskHelper.getTaskInfo(mTaskId).mDownloadSpeed}")
                            mPlayUrl = mTaskHelper.getLocalUrl(SAVE_PATH + mTaskHelper.getFileName(url))
                        }))
    }

    private fun downloadMagent(url: String) {
        val fileName = mTaskHelper.getFileName(url)
        val taskId = mTaskHelper.addMagentTask(url, SAVE_PATH, fileName)
        compositeDisposable.add(Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val taskInfo = mTaskHelper.getTaskInfo(taskId)
                    textName.text = "视频名称： \n ${taskInfo.mFileName} \n 文件大小： ${convertFileSize(taskInfo.mFileSize)} \n 下载速度： ${convertFileSize(taskInfo.mDownloadSpeed)} \n 已下载： ${convertFileSize(taskInfo.mDownloadSize)} \n status ${taskInfo.mTaskStatus}"
                    Log.e(TAG, "------------>>taskInfo $mTaskId ${mTaskHelper.getTaskInfo(mTaskId).mDownloadSpeed}")
                    if (taskInfo.mTaskStatus == 2) {
                        compositeDisposable.clear()
                        mTaskHelper.stopTask(taskId)
                        downloadTorrent(SAVE_PATH.plus(fileName))
                    }
                })
    }

    private fun downloadTorrent(path: String) {
        val torrentInfo = mTaskHelper.getTorrentInfo(path)
        Log.e(TAG, "torrentInfo ${torrentInfo.mSubFileInfo.size} ${torrentInfo.mSubFileInfo[0].mFileName} ${torrentInfo.mSubFileInfo[0].mSubPath}")
        mTaskId = mTaskHelper.addTorrentTask(path, SAVE_PATH, intArrayOf(0))
        compositeDisposable.add(Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val taskInfo = mTaskHelper.getTaskInfo(mTaskId)
                    textName.text = "视频名称： \n ${torrentInfo.mSubFileInfo[0].mFileName} \n 文件大小： ${convertFileSize(taskInfo.mFileSize)} \n 下载速度： ${convertFileSize(taskInfo.mDownloadSpeed)} \n 已下载： ${convertFileSize(taskInfo.mDownloadSize)} \n status ${taskInfo.mTaskStatus}"
                    Log.e(TAG, "------------>>taskInfo $mTaskId ${mTaskHelper.getTaskInfo(mTaskId).mDownloadSpeed} ${mTaskHelper.getLocalUrl(SAVE_PATH.plus(torrentInfo.mSubFileInfo[0].mFileName))}")
                    mPlayUrl = mTaskHelper.getLocalUrl(SAVE_PATH.plus(torrentInfo.mSubFileInfo[0].mFileName))
                })
    }

    private fun convertFileSize(size: Long): String {
        val kb: Long = 1024
        val mb = kb * 1024
        val gb = mb * 1024

        return when {
            size >= gb -> String.format("%.1f GB", size.toFloat() / gb)
            size >= mb -> {
                val f = size.toFloat() / mb
                String.format(if (f > 100) "%.0f M" else "%.1f M", f)
            }
            size >= kb -> {
                val f = size.toFloat() / kb
                String.format(if (f > 100) "%.0f K" else "%.1f K", f)
            }
            else -> String.format("%d B", size)
        }
    }
}
