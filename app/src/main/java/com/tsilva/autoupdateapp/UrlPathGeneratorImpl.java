package com.tsilva.autoupdateapp;

public class UrlPathGeneratorImpl implements UrlPathGenerator
{
    public static final String API_URL = "https://api.github.com";
    public UrlPathGeneratorImpl()
    {}

    // https://api.github.com/repos/telmoanastacio/AutoUpdateAppRepo/releases
    public String getUserRepos(String user, String repo)
    {
        return API_URL + "/repos/" + user + "/" + repo + "/releases";
    }
}