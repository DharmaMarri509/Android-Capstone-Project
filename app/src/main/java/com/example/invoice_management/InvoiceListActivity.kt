package com.example.invoice_management

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.time.LocalDate
import kotlin.Exception

class InvoiceListActivity : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var retrofit: Retrofit
    private lateinit var invoiceApi: InvoiceApi
    private  lateinit var invoiceAdapter: InvoiceAdapter
    private val scope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice_list)
        retrofit = RetrofitClient.create()
        invoiceApi = retrofit.create(InvoiceApi::class.java)
        recyclerView = findViewById(R.id.invoice_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //sending the static data..........
//        var in1 = Invoice("risabh",3500.00,LocalDate.now(),"bought 40 books")
//        var in2 = Invoice("shewag",4000.00,LocalDate.now(),"bought 10 bats")
//        var invoices = mutableListOf(in1,in2)
//        invoiceAdapter = InvoiceAdapter(invoices)



        val userId = getSharedPreferences("user_data", MODE_PRIVATE).getInt("user_id", -1)
        Log.i("invoice activity","userId in invoicelist $userId")


        Log.i("invoice activity","userId in scope $userId")
        val call = invoiceApi.getInvoices(userId)
        Log.i("invoice activity","userId in after making call $userId")
        call.enqueue(object : Callback<List<Invoice>> { override fun onResponse(call: Call<List<Invoice>>, response: Response<List<Invoice>>) {
            if (response.isSuccessful) {
                val invoices = response.body() ?: emptyList()
                Log.i("invoice activity","list of invoices $invoices")
                invoiceAdapter = InvoiceAdapter(invoices)
                recyclerView.adapter = invoiceAdapter
            }
        }

            override fun onFailure(call: Call<List<Invoice>>, t: Throwable) {
                // Handle error
                Log.e("error message", "$t.message")
            }
        })





            //to handle the add the invoice
        findViewById<FloatingActionButton>(R.id.invoices_list_fab).setOnClickListener {

            val intent = Intent(this, AddInvoiceActivity::class.java)
            startActivity(intent)
        }


    }

}