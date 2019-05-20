package com.tsilva.autoupdateapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
{
    Update updateLib;
    byte[] data;
    DownloadFileContent downloadFileContent;
    String filename = "";
    String folder = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filename = "AutoUpdateApp.apk";
        folder = getFilesDir().toString() + File.separator;
        File app = new File(folder + filename);
        if(app.exists())
        {
            app.delete();
        }

        TextView textView1 = findViewById(R.id.textView1);
        updateLib = new Update();
        textView1.setText("current version: "
                + updateLib.getCurrentVersion(getApplicationContext()) + "\n");
    }

    public void checkUpdate(View view)
    {
        String path = new UrlPathGenerator()
                .getUserRepos(REPOSITORY_DATA.USER, REPOSITORY_DATA.REPO);
        JsonData jsonData = new JsonData(new DownloadWebContent().getContent(path));
        System.out.println("IS APP OUTDATED: " + updateLib.isOutdated(jsonData));
        System.out.println("versionUrlMapList: " + jsonData.getVersionUrlMap());
        System.out.println("VERSION LIST: " + jsonData.getVersionList());
        if(updateLib.isOutdated(jsonData))
        {
            TextView textView1 = findViewById(R.id.textView1);
            textView1.setText("current version: "
                    + updateLib.getCurrentVersion(getApplicationContext())
                    + "\nupdate available: " + updateLib.getLastVersion());
            Button button2 = findViewById(R.id.button2);
            button2.setEnabled(true);
            button2.setVisibility(View.VISIBLE);
        }
    }

    public void update(View view)
    {
        LinearLayout linearLayout2 = findViewById(R.id.linearLayout2);
        linearLayout2.setVisibility(View.VISIBLE);
        ProgressBar progressBar1 = findViewById(R.id.progressBar1);
        System.out.println("=== DOWNLOADING UPDATE ===");
        downloadFileContent = new DownloadFileContent();
        downloadFileContent.setSizeBytes(updateLib.getLastVersionSize());
        downloadFileContent.setProgressBar(progressBar1);
        data = null;
        Thread downloadFileContentThread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    data = downloadFileContent.execute(updateLib.getLastVersionUrl()).get();
                }
                catch(InterruptedException | ExecutionException e)
                {
                    e.printStackTrace();
                }
            }
        };
        downloadFileContentThread.setName("downloadFileContent");
        downloadFileContentThread.start();

        Thread threadDoAfterComplete = new Thread()
        {
            @Override
            public void run()
            {
                while(!downloadFileContent.isComplete() || data == null)
                {}
                System.out.println("=== OUT OF WAIT LOOP ===");
                // put instructions
//                for(byte b : data)
//                {
//                    System.out.print((char) b);
//                }
                // insert code here
                // update functions

//                filename = "AutoUpdateApp.apk";
//                folder = getFilesDir().toString() + File.separator;
//                File app = new File(folder + filename);
//                if(app.exists())
//                {
//                    app.delete();
//                }
                System.out.println("=== PATH: " + folder + filename);
                File directory = new File(folder);
                if (!directory.exists())
                {
                    directory.mkdirs();
                }

                FileOutputStream outputStream;

                try
                {
                    outputStream = new FileOutputStream(folder + filename);
                    System.out.println("===WRITING FILE===");
                    outputStream.write(data);
                    System.out.println("===WRITING COMPLETE===");
                    outputStream.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                        .setDataAndType(FileProvider.getUriForFile(getApplicationContext(), "com.tsilva.autoupdateapp.fileprovider", new File(folder + filename)),
                                "application/vnd.android.package-archive");
                promptInstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                promptInstall.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(promptInstall);
            }
        };
        threadDoAfterComplete.setName("thread-complete-listener");
        threadDoAfterComplete.start();

        System.out.println("=== FINISHED DOWNLOADING UPDATE ===");
    }
}