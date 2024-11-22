package com.example.nammatulunadu;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PlaceService {

    @FormUrlEncoded
    @POST("insert_place.php")
    Call<Responsemsg> addPlace(
            @Field("name") String name,
            @Field("category") String category,
            @Field("description") String description
    );
    @GET("get_placep.php")
    Call<ApiResponce> getPlacep(
            @Query("name") String name
    );
    @GET("get_placec.php")
    Call<List<Place>> getCategoriesplace(
            @Query("name") String name
    );
    @FormUrlEncoded
    @POST("insert_category.php")
    Call<Responsemsg> addCategory(
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("delete_place.php")
    Call<Responsemsg> deletePlace(
            @Field("name") String name
    );
    @FormUrlEncoded
    @POST("delete_category.php")
    Call<Responsemsg> deleteCategory(
            @Field("name") String name
    );
    @GET("get_places.php")
    Call<List<Place>> getPlaces();

    @FormUrlEncoded
    @POST("checkCategory.php")
    Call<Responsemsg> checkCategory(
            @Field("name") String categoryName
    );
}
