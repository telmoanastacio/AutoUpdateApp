package com.tsilva.autoupdateapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonData
{
    private int arrSize;
    private Map<String, String> versionUrlMap = new HashMap<>();

    public JsonData(String content)
    {
        try
        {
            JSONArray jsonArray = new JSONArray(content);
            arrSize = jsonArray.length();

            for(int i = 0; i < arrSize; i++)
            {
                try
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String version = jsonObject.getString("tag_name");
                    String apkZipUrl = jsonObject.getString("zipball_url");
                    versionUrlMap.put(version, apkZipUrl);
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    public List<String> getVersionList()
    {
        List<String> versionList = new ArrayList<>();

        for(Object version : versionUrlMap.keySet().toArray())
        {
            versionList.add(version.toString());
        }
        Collections.sort(versionList);
        return versionList;
    }

    public Map<String, String> getVersionUrlMap()
    {
        return versionUrlMap;
    }
}