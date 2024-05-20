package com.example.invoice_management

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface InvoiceApi {

    @GET("/invoice/getInvoicesList")
    fun getInvoices(@Query("userId") userId:Int):Call<List<Invoice>>

    @DELETE("/invoice/")
    suspend fun deleteInvoice(@Query("invoiceId") invoiceId:Int):Response<String>

    @POST("/invoice/save")
    fun saveInvoice(@Query("userId") userId:Int, @Body invoice:Invoice):Call<Invoice>
}