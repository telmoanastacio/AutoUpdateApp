package com.tsilva.autoupdateapp;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

class DownloadWebContent extends AsyncTask<String, Void, String>
{
    public DownloadWebContent()
    {}

    @Override
    // this method is not to be directly called
    protected String doInBackground(String[] strings)
    {
        // Just want to accept one URL at a time
        if(strings.length == 1)
        {
            String result = "";
            URL url = null;
            HttpsURLConnection httpsConnection = null;
            InputStream in = null;
            InputStreamReader inputStreamReader = null;
            int current;

            try
            {
                url = new URL(strings[0]);
                httpsConnection = (HttpsURLConnection) url.openConnection();
                if(httpsConnection.getResponseCode() < HttpsURLConnection.HTTP_BAD_REQUEST
                        && httpsConnection.getResponseCode() != -1)
                {
                    in = httpsConnection.getInputStream();
                }
                else
                {
                    in = httpsConnection.getErrorStream();
                }
                inputStreamReader = new InputStreamReader(in);
                current = inputStreamReader.read();
                while(current != -1)
                {
                    result += (char) current;
                    current = inputStreamReader.read();
                }
                return result;
            }
            catch(java.io.IOException e)
            {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public String getContent(String url)
    {
        String content = "";
        try
        {
            content = new DownloadWebContent().execute(url).get();
        }
        catch(ExecutionException | InterruptedException e)
        {
            e.printStackTrace();
        }
        return content;
    }
}