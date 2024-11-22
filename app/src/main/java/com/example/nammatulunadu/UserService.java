package com.example.nammatulunadu;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {
    @FormUrlEncoded
    @POST("signup.php")
    Call<Responsemsg> signup(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("login.php")
    Call<Responsemsg> login(@Field("username") String username, @Field("password") String password);
}
