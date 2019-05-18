package com.tsilva.autoupdateapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class Update
{
    String appVersion = "";

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
}