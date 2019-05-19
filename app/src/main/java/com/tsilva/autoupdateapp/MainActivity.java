package com.tsilva.autoupdateapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    Update updateLib;
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);

        updateLib = new Update();
        textView1.setText("Current version: " + updateLib.getCurrentVersion(getApplicationContext()));
    }

    public void checkUpdate(View view)
    {
        String path = new UrlPathGenerator()
                .getUserRepos(REPOSITORY_DATA.USER, REPOSITORY_DATA.REPO);
        JsonData jsonData = new JsonData(new DownloadWebContent().getContent(path));
        System.out.println("IS APP OUTDATED: " + updateLib.isOutdated(jsonData));
//        System.out.println("versionUrlMapList: " + jsonData.getVersionUrlMap());
//        System.out.println("VERSION LIST: " + jsonData.getVersionList());
    }
}