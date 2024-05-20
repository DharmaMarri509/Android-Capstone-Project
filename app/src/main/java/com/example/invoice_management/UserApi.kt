package com.example.invoice_management


import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @POST("/user/save")
    fun saveUser(@Body user:User):Call<User>

    @GET("/user/login")
   fun getUser(
        @Query("userName") userName:String,
        @Query("password") password:String
    ):Call<Int>



}