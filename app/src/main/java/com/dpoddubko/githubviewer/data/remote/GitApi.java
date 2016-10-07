package com.dpoddubko.githubviewer.data.remote;

import com.dpoddubko.githubviewer.data.model.Directory;
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

    @GET("users/{user}/starred")
    Call<List<UserRepo>> getStars(@Path("user") String user);

    @GET("repos/{user}/{repo}/contents")
    Call<List<Directory>> getDir(@Path("user") String user, @Path("repo") String repo);

    class Factory {
        private static GitApi service;

        private Factory() {
            // hidden
        }

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
