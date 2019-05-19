package com.tsilva.autoupdateapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class Update
{
    private String appVersion = "";
    private String lastVersion = "";
    private String lastVersionUrl = "";

    public Update()
    {}

    public String getCurrentVersion(Context context)
    {
        try
        {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(new ContextWrapper(context).getPackageName(), 0);
            appVersion = packageInfo.versionName;
            return appVersion;
        }
        catch(PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isOutdated(JsonData jsonData)
    {
        List<String> versionList = jsonData.getVersionList();
        int lastElement = versionList.size() - 1;
        String[] currentVersionStrArray = appVersion.split("\\.");
        String[] lastVersionStrArray = versionList.get(lastElement).split("\\.");
        boolean isCurrentVersionLower = false;

        if(Integer.parseInt(lastVersionStrArray[0]) > Integer.parseInt(currentVersionStrArray[0]))
        {
            isCurrentVersionLower = true;
        }
        else if(Integer.parseInt(lastVersionStrArray[0]) == Integer.parseInt(currentVersionStrArray[0]))
        {
            if(Integer.parseInt(lastVersionStrArray[1]) > Integer.parseInt(currentVersionStrArray[1]))
            {
                isCurrentVersionLower = true;
            }
            else if(Integer.parseInt(lastVersionStrArray[1]) == Integer.parseInt(currentVersionStrArray[1]))
            {
                if(Integer.parseInt(lastVersionStrArray[2]) > Integer.parseInt(currentVersionStrArray[2]))
                {
                    isCurrentVersionLower = true;
                }
            }
        }

        if(isCurrentVersionLower)
        {
            lastVersion = versionList.get(lastElement);
            lastVersionUrl = jsonData.getVersionUrlMap().get(lastVersion);
        }
        else
        {
            lastVersion = appVersion;
        }
        return isCurrentVersionLower;
    }

    //TODO: get content and unzip


    public String getLastVersion()
    {
        return lastVersion;
    }

    public String getLastVersionUrl()
    {
        return lastVersionUrl;
    }
}