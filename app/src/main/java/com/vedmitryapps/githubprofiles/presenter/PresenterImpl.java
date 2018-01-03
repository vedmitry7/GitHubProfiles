package com.vedmitryapps.githubprofiles.presenter;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.vedmitryapps.githubprofiles.UsersLoader;
import com.vedmitryapps.githubprofiles.model.User;
import com.vedmitryapps.githubprofiles.view.View;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static com.vedmitryapps.githubprofiles.Constants.KEY_SINCE;

public class PresenterImpl implements Presenter {

    private View mView;
    private Realm mRealm;
    private LoaderUsersCallback mCallback;
    private List<User> mUsers = new ArrayList<>();
    private boolean mIsLoading;
    private int mSince;

    public PresenterImpl(View view, Bundle savedInstanceState){
        mView = view;
        mRealm = Realm.getInstance(new RealmConfiguration.Builder(view.getContext()).deleteRealmIfMigrationNeeded().build());
        mCallback = new LoaderUsersCallback();

        if(savedInstanceState != null){
            loadFromRealm();
            mSince = savedInstanceState.getInt(KEY_SINCE);
            if(mUsers.size()==0){
                loadData(false);
            }
            view.updateList(mUsers);
        } else {
            loadData(false);
            clearRealm();
        }
    }

    public void loadData(boolean restart) {
        mIsLoading = true;
        mView.showLoadingIndicator();
        if(restart){
            mView.getLoader().restartLoader(mSince, Bundle.EMPTY, mCallback);
        } else {
            mView.getLoader().initLoader(mSince, Bundle.EMPTY, mCallback);
        }
    }

    @Override
    public void loadMore() {
        loadData(false);
    }

    @Override
    public void onPause() {
        saveUsersToRealm();
    }

    @Override
    public boolean isLoading() {
        return mIsLoading;
    }

    @Override
    public void reload() {
        mView.hideError();
        mView.showLoadingIndicator();
        loadData(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_SINCE, mSince);
    }

    private void saveUsersToRealm(){
        for (int i = 0; i < mUsers.size(); i++) {
            User user = new User();
            user.setId(mUsers.get(i).getId());
            user.setAvatarUrl(mUsers.get(i).getAvatarUrl());
            user.setLogin(mUsers.get(i).getLogin());
            user.setHtmlUrl(mUsers.get(i).getHtmlUrl());
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(user);
            mRealm.commitTransaction();
        }
    }

    private void loadFromRealm(){
        RealmResults<User> mUsers = mRealm.where(User.class).findAll();
        this.mUsers.addAll(mUsers);

    }
    private void clearRealm() {
        RealmResults<User> result = mRealm.where(User.class).findAll();
        for (int i = result.size()-1; i >= 0 ; i--) {
            mRealm.beginTransaction();
            result.get(i).removeFromRealm();
            mRealm.commitTransaction();
        }
    }

    public class LoaderUsersCallback implements LoaderManager.LoaderCallbacks<List<User>>{

        @Override
        public Loader<List<User>> onCreateLoader(int id, Bundle args) {
            return new UsersLoader(mView.getContext(), mSince);
        }

        @Override
        public void onLoadFinished(Loader<List<User>> loader, List<User> data) {
            mIsLoading = false;

            if(data == null){
                mView.hideLoadingIndicator();
                mView.showError();

            } else {
               mView.hideLoadingIndicator();
                mUsers.addAll(data);
                mView.updateList(mUsers);
                mSince = data.get(data.size() - 1).getId();
            }
        }

        @Override
        public void onLoaderReset(Loader<List<User>> loader) {

        }
    }
}