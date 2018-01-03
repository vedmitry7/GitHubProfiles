package com.vedmitryapps.githubprofiles.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.vedmitryapps.githubprofiles.R;
import com.vedmitryapps.githubprofiles.model.User;
import com.vedmitryapps.githubprofiles.presenter.PresenterImpl;
import com.vedmitryapps.githubprofiles.view.adapters.RecyclerViewUserAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.errorContainer)
    RelativeLayout mErrorContainer;

    private RecyclerViewUserAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private PresenterImpl mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initRecycler();

        mPresenter = new PresenterImpl(this, savedInstanceState);
    }

    private void initRecycler() {
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewUserAdapter(new ArrayList<User>(), this);
        mRecyclerView.setAdapter(mAdapter);

        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                if (!mPresenter.isLoading()) {
                    if ( (visibleItemCount+firstVisibleItems) >= totalItemCount) {
                        mPresenter.loadMore();
                    }
                }
            }
        };
        mRecyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @OnClick(R.id.buttonReload)
    void restart(android.view.View view){
        mPresenter.reload();
    }


    @Override
    public void showError() {
        mErrorContainer.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideError() {
        mErrorContainer.setVisibility(android.view.View.GONE);
    }

    @Override
    public void showLoadingIndicator() {
        mProgressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        mProgressBar.setVisibility(android.view.View.GONE);
    }

    @Override
    public void updateList(List<User> users) {
        mAdapter.update(users);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public LoaderManager getLoader(){
        return getSupportLoaderManager();
    }
}
