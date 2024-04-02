package com.example.eventsua;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface Interface {
    @FormUrlEncoded
    @POST("dbconnection.php") //php file
    Call<Response>login(@Field("email")String email,@Field("password")String password,@Field("type")int type);
    @FormUrlEncoded
    @POST("dbconnection.php") //php file
    Call<Response>show_all(@Field("type")int type);
    @FormUrlEncoded
    @POST("dbconnection.php") //php file
    Call<Response>show_my(@Field("iduser")int iduser, @Field("type")int type);
    @FormUrlEncoded
    @POST("dbconnection.php") //php file
    Call<Response>show_event(@Field("id_event")int id_event, @Field("type")int type);
    @FormUrlEncoded
    @POST("dbconnection.php") //php file
    Call<Response>buy(@Field("iduser")int iduser, @Field("id_ticket")int id_ticket, @Field("type")int type);
}
