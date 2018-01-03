package com.vedmitryapps.githubprofiles.model;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.vedmitryapps.githubprofiles.Constants.URL_BASE;

public class ApiFactory {

    private static ApiInterface sApi;

    public static ApiInterface getApi(){
        if(sApi == null){
            Retrofit.Builder builder = new Retrofit.Builder().
                    baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create());

            sApi = builder.build().create(ApiInterface.class);
        }
        return sApi;
    }
}
