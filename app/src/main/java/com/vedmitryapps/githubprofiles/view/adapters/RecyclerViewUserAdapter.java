package com.vedmitryapps.githubprofiles.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vedmitryapps.githubprofiles.R;
import com.vedmitryapps.githubprofiles.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewUserAdapter extends RecyclerView.Adapter<RecyclerViewUserAdapter.ViewHolder> {

    private List<User> mUsers;
    private Context mContext;


    public RecyclerViewUserAdapter(List<User> users, Context context) {
        this.mUsers = users;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        User user = mUsers.get(position);
        viewHolder.login.setText(user.getLogin());
        viewHolder.link.setText(user.getHtmlUrl());
        Glide.with(mContext).load(user.getAvatarUrl()).into(viewHolder.avatar);
    }


    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.login)
        TextView login;
        @BindView(R.id.profile_link)
        TextView link;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void update(List<User> users){
        this.mUsers = users;
        notifyDataSetChanged();
    }
}