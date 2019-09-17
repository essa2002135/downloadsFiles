package org.redsoft.downloadsfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;
import com.downloader.Status;
import com.downloader.utils.Utils;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static String dirPath;

    int downloadId;
    Context context;
    Status status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        PackageManager m = getPackageManager();
        String s = getPackageName();
        PackageInfo p = null;
        try {
            p = m.getPackageInfo(s, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        s = p.applicationInfo.dataDir;

        Log.i("this is path",s);

        File rootDataDir = getApplicationContext().getFilesDir();
        Log.i("FILEROOT ", rootDataDir.toString());


        PRDownloader.initialize(getApplicationContext());


        // Enabling database for resume support even after the application is killed:
        // Setting timeout globally for the download network requests:
        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setReadTimeout(30_000)
                .setConnectTimeout(30_000)
                .build();
        PRDownloader.initialize(getApplicationContext(), config);





         downloadId = PRDownloader.download("http://techslides.com/demos/sample-videos/small.mp4", rootDataDir.toString(), "fromNewmacsmall.mp4")
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        Log.i("Download start","Downloading is progress");


                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {

                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        Log.i("Done","Downloading is Done");

                    }

                    @Override
                    public void onError(Error error) {

                    }
                });

         status = PRDownloader.getStatus(downloadId);

        Log.i("get the Statues",status+"satatuses");




    }

    public void downloadnow(View v)
    {
        PRDownloader.resume(downloadId);

        Log.i("get the Statues",status.name()+"satatuses");

    }
}
