package com.example.nammatulunadu;
import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface ApiService {
    @GET("get_places.php")
    Call<List<Place>> getPlaces();

    @GET("get_categories.php")
    Call<List<Category>> getCategories();

    @GET("check_server.php")
    Call<Responsemsg> check_server();
}

