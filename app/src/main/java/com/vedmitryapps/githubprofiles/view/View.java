package com.vedmitryapps.githubprofiles.view;

import android.content.Context;
import android.support.v4.app.LoaderManager;

import com.vedmitryapps.githubprofiles.model.User;

import java.util.List;

public interface View {

    void showError();
    void hideError();
    void showLoadingIndicator();
    void hideLoadingIndicator();
    void updateList(List<User> users);
    Context getContext();
    LoaderManager getLoader();


}