package com.vedmitryapps.githubprofiles.presenter;

import android.os.Bundle;

public interface Presenter {

    void loadMore();
    void onPause();
    boolean isLoading();
    void reload();
    void onSaveInstanceState(Bundle outState);

} 