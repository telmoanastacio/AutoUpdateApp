package com.tsilva.autoupdateapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    String appVersion = "";
    Update updateLib;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateLib = new Update();
        appVersion = updateLib.getCurrentVersion(getApplicationContext());
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