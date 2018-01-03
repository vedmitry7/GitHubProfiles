package com.vedmitryapps.githubprofiles;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.vedmitryapps.githubprofiles.model.ApiFactory;
import com.vedmitryapps.githubprofiles.model.User;

import java.io.IOException;
import java.util.List;

public class UsersLoader extends AsyncTaskLoader<List<User>> {

    private int since;

    public UsersLoader(Context context, int since) {
        super(context);
        this.since = since;
    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<User> loadInBackground() {
        try {
            return ApiFactory.getApi().getUser(since).execute().body();
        } catch (IOException e) {
            return null;
        }
    }
}
