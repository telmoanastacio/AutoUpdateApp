package com.tsilva.autoupdateapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    String appVersion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Update updateLib = new Update();
        appVersion = updateLib.getCurrentVersion(getApplicationContext());
    }

    public void checkUpdate(View view)
    {
        String path = new UrlPathGenerator()
                .getUserRepos(REPOSITORY_DATA.USER, REPOSITORY_DATA.REPO);
        System.out.println("===RETRIEVING CONTENT===");
        System.out.println("CONTENT: " + new DownloadWebContent().getContent(path));
        System.out.println("===CONTENT RETRIEVED===");
    }
}