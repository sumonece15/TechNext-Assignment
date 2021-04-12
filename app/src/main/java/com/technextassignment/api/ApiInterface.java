package com.technextassignment.api;
import com.technextassignment.models.ServerResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET(HttpParams.ENDPOINT)
    Call<ServerResponse> getBlogData();

}

