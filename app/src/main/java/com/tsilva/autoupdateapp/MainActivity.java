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
        textView1.setText("current version: "
                + updateLib.getCurrentVersion(getApplicationContext()) + "\n");
    }

    public void checkUpdate(View view)
    {
        String path = new UrlPathGenerator()
                .getUserRepos(REPOSITORY_DATA.USER, REPOSITORY_DATA.REPO);
        JsonData jsonData = new JsonData(new DownloadWebContent().getContent(path));
        System.out.println("IS APP OUTDATED: " + updateLib.isOutdated(jsonData));
//        System.out.println("versionUrlMapList: " + jsonData.getVersionUrlMap());
//        System.out.println("VERSION LIST: " + jsonData.getVersionList());
        int lastVersionIndex = jsonData.getVersionList().size() - 1;
        if(updateLib.isOutdated(jsonData))
        {
            textView1.setText("current version: "
                    + updateLib.getCurrentVersion(getApplicationContext())
                    + "\nupdate available: " + jsonData.getVersionList().get(lastVersionIndex));
        }
    }

    public void update(View view)
    {}
}