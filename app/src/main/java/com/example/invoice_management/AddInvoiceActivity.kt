package com.example.invoice_management

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class AddInvoiceActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var invoiceApi: InvoiceApi
    private val scope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private lateinit var client: EditText
    private lateinit var amount: EditText
    private lateinit var date: EditText
    private lateinit var description: EditText
    private lateinit var saveBtn: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_invoice)
        retrofit = RetrofitClient.create()
        invoiceApi = retrofit.create(InvoiceApi::class.java)

        client = findViewById(R.id.clientNameInput)
        amount = findViewById(R.id.invoiceAmountInput)
        date = findViewById(R.id.invoiceDateInput)
        description = findViewById(R.id.descriptionInput)
        saveBtn = findViewById(R.id.button2)
        val userId = getSharedPreferences("user_data", MODE_PRIVATE).getInt("user_id", -1)


        saveBtn.setOnClickListener{
            val clientName = client.text.toString()
            val amount = amount.text.toString().toDouble()
            val date = date.text.toString()
            val desc = description.text.toString()
            Log.i("addinvoice activity","description before call is $desc")
            val invoice = Invoice(0,clientName,amount,date,desc)
            Log.i("addinvoice activity","description is $desc")

            //

            if(clientName.isNotBlank() && clientName.length >=5 && amount>=3000) {

                val call: Call<Invoice> = invoiceApi.saveInvoice(userId, invoice)

                call.enqueue(object : Callback<Invoice> {
                    override fun onResponse(call: Call<Invoice>, response: Response<Invoice>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@AddInvoiceActivity,
                                "Invoice created successfully",
                                Toast.LENGTH_SHORT
                            ).show()
//                            val intent = Intent(this@AddInvoiceActivity,InvoiceListActivity::class.java)
//                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@AddInvoiceActivity,
                                "Failed to create invoice",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.i("error in adding invoice", "${response.errorBody()}")
                        }
                    }

                    override fun onFailure(call: Call<Invoice>, t: Throwable) {
                        Toast.makeText(
                            this@AddInvoiceActivity,
                            "Error: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }else{
                Toast.makeText(this@AddInvoiceActivity,"please provide crrect credentials",Toast.LENGTH_SHORT).show()
            }


        }


    }
}