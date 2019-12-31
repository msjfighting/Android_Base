package com.msj.android_project.http;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserMgrService {
    @GET("login")
    Call<UserInfoModel> login(@Query("key") String key, @Query("phone") String phone,@Query("passwd") String passwd);
}
