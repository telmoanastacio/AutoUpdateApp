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
    private Map<String, Map<String, String>> versionUrlMap = new HashMap<>();

    public JsonData(String content)
    {
        try
        {
            JSONArray jsonArray = new JSONArray(content);
            arrSize = jsonArray.length();

            for(int i = 0; i < arrSize; i++)
            {
                String version = "";
                Map<String, String> sizeUrlMap = null;
                try
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    version = jsonObject.getString("tag_name");
                    String size = jsonObject
                            .getJSONArray("assets")
                            .getJSONObject(0)
                            .getString("size");
                    String apkUrl = jsonObject
                            .getJSONArray("assets")
                            .getJSONObject(0)
                            .getString("browser_download_url");
                    sizeUrlMap = new HashMap<>();
                    sizeUrlMap.put("size", size);
                    sizeUrlMap.put("apkUrl", apkUrl);
                    versionUrlMap.put(version, sizeUrlMap);
                }
                catch(JSONException e)
                {
                    versionUrlMap.put(version, sizeUrlMap);
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

    public Map<String, Map<String, String>> getVersionUrlMap()
    {
        return versionUrlMap;
    }
}