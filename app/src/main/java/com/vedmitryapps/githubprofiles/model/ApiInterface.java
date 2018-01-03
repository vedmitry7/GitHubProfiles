package com.vedmitryapps.githubprofiles.model;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("users")
    Call<List<User>> getUser(@Query("since") int since);
}