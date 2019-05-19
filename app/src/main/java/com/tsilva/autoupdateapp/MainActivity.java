package com.tsilva.autoupdateapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
{
    Update updateLib;
    byte[] data;
    DownloadFileContent downloadFileContent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        }
        Button button2 = findViewById(R.id.button2);
        button2.setEnabled(true);
        button2.setVisibility(View.VISIBLE);
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
                // TODO: install downloaded apk
                // insert code here
            }
        };
        threadDoAfterComplete.setName("thread-complete-listener");
        threadDoAfterComplete.start();

        System.out.println("=== FINISHED DOWNLOADING UPDATE ===");
    }
}