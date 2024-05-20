package com.example.invoice_management

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.invoice_management.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class EditInvoiceActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var invoiceApi: InvoiceApi
    private lateinit var clientName: TextView
    private lateinit var invoiceAmount: TextView
    private lateinit var creationDate: TextView
    private lateinit var description: TextView
    private lateinit var saveBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_invoice)

        retrofit = RetrofitClient.create()
        invoiceApi = retrofit.create(InvoiceApi::class.java)

        clientName = findViewById(R.id.clientNameInput)
        invoiceAmount = findViewById(R.id.invoiceAmountInput)
        creationDate = findViewById(R.id.invoiceDateInput)
        description = findViewById(R.id.descriptionInput)
        saveBtn = findViewById(R.id.button2)

        //getting the data from the shared preferences..
        val invSharedPref: SharedPreferences = getSharedPreferences("invoice_data", Context.MODE_PRIVATE)
        val id:Int = invSharedPref.getInt("invoice_id",0)
        val iname = invSharedPref.getString("name","")
        val iamount = invSharedPref.getString("amount","")
        val idate = invSharedPref.getString("date","")
        val idesc = invSharedPref.getString("desc","")

        //set the values to text views..
        clientName.text = iname
        invoiceAmount.text= iamount
        creationDate.text = idate
        description.text = idesc

        saveBtn.setOnClickListener{
            //get the details from the ui to update the values
            val name = clientName.text.toString()
            val amount = invoiceAmount.text.toString().toDouble()
            val date = creationDate.text.toString()
            val desc = description.text.toString()

            val invoice = Invoice(id,name,amount,date,desc)


            val call = invoiceApi.updateInvoice(id, invoice)
            Log.i("id in update","invoice id in update activity is : $id")
            call.enqueue(object : Callback<Invoice> {
                override fun onResponse(call: Call<Invoice>, response: Response<Invoice>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@EditInvoiceActivity, "Invoice updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@EditInvoiceActivity, "Failed to update invoice", Toast.LENGTH_SHORT).show()
                        Log.i("error in adding invoice","${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<Invoice>, t: Throwable) {
                    Toast.makeText(this@EditInvoiceActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }


    }
}