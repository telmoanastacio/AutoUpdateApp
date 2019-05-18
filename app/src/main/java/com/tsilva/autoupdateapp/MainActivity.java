package com.tsilva.autoupdateapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void checkUpdate(View view)
    {
        UrlPathGenerator urlPathGenerator = new UrlPathGeneratorImpl();
        System.out.println(urlPathGenerator.getUserRepos(REPOSITORY_DATA.USER, REPOSITORY_DATA.REPO));
    }
}