package com.tsilva.autoupdateapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileContent extends AsyncTask<String, String, byte[]>
{
    private int sizeBytes;
    private ProgressBar progressBar1 = null;
    private int progress = 0;
    private boolean isComplete = false;

    public DownloadFileContent()
    {}

    public DownloadFileContent(int sizeBytes)
    {
        this.sizeBytes = sizeBytes;
    }

    @Override
    protected byte[] doInBackground(String... f_url)
    {
        int count;
        try
        {
            URL url = new URL(f_url[0]);
            URLConnection connection = url.openConnection();
            connection.connect();
            int fileLength = connection.getContentLength();
            progressBar1.setMax(fileLength);
            progressBar1.setProgress(0);
            System.out.println("=== FILE SIZE === " + getSizeBytes());
            InputStream input = new BufferedInputStream(url.openStream(), getSizeBytes());

            OutputStream output = new ByteArrayOutputStream(fileLength);

            byte[] data = new byte[1024];

            while((count = input.read(data)) != -1)
            {
                progress = progress + count;
                progressBar1.setProgress(progress);

                output.write(data, 0, count);
            }
            isComplete = true;
            System.out.println("=== IS COMPLETE === " + isComplete());
            input.close();

            return ((ByteArrayOutputStream) output).toByteArray();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public int getSizeBytes()
    {
        return sizeBytes;
    }

    public void setSizeBytes(int sizeBytes)
    {
        this.sizeBytes = sizeBytes;
    }

    public ProgressBar getProgressBar()
    {
        return progressBar1;
    }

    public void setProgressBar(ProgressBar progressBar)
    {
        this.progressBar1 = progressBar;
    }

    public boolean isComplete()
    {
        return isComplete;
    }

    public void setComplete(boolean complete)
    {
        isComplete = complete;
    }
}