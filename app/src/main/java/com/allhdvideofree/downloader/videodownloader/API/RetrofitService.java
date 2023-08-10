package com.allhdvideofree.downloader.videodownloader.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitService
{
    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseBody> data(@Field("data") String JsonObject);
}
