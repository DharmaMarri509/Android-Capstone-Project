package com.example.invoice_management

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.create
import java.time.LocalDate

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
        val userId = getSharedPreferences("user", MODE_PRIVATE).getInt("userId", -1)


        saveBtn.setOnClickListener{
            val clientName = client.text.toString()
            val amount = amount.text.toString().toDouble()
            val date = date
            val description = description.toString()
            val invoice:Invoice = Invoice(clientName,amount, LocalDate.now(),description)
            scope.launch {
                val response = invoiceApi.saveInvoice(userId,invoice)
                if (response.isSuccessful) {
                   Toast.makeText(this@AddInvoiceActivity,"invoice saved successfully",Toast.LENGTH_SHORT).show()

                } else {
                    Log.e("InvoiceListActivity", "Error fetching in adding invoices: ${response.code()}")
                }
            }
        }


    }
}