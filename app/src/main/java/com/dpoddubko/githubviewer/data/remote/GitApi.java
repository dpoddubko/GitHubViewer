package com.dpoddubko.githubviewer.data.remote;

import com.dpoddubko.githubviewer.data.model.UserRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitApi {
    String BASE_URL = "https://api.github.com/";

    @GET("users/{user}/repos")
    Call<List<UserRepo>> getRepos(@Path("user") String user);

    class Factory {
        private Factory() {
        }
        private static GitApi service;
        public static GitApi getInstance() {
            if (service == null) {
                service = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build()
                        .create(GitApi.class);
            }
            return service;
        }
    }
}
