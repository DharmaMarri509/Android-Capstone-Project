package com.example.invoice_management

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class DeleteActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var invoiceApi: InvoiceApi
    private lateinit var clientName: TextView
    private lateinit var invoiceAmount: TextView
    private lateinit var creationDate: TextView
    private lateinit var description: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        retrofit = RetrofitClient.create()
        invoiceApi = retrofit.create(InvoiceApi::class.java)

        clientName = findViewById(R.id.textView6)
        invoiceAmount = findViewById(R.id.textView8)
        creationDate = findViewById(R.id.textView10)
        description = findViewById(R.id.textView12)

        //getting the data from the shared preferences..
        val invSharedPref: SharedPreferences = getSharedPreferences("invoice_data", Context.MODE_PRIVATE)
        val id = invSharedPref.getInt("invoice_id",0)
        val iname = invSharedPref.getString("name","")
        val iamount = invSharedPref.getString("amount","")
        val idate = invSharedPref.getString("date","")
        val idesc = invSharedPref.getString("desc","")

        //set the values to text views..
        clientName.text = iname
        invoiceAmount.text= iamount
        creationDate.text = idate
        description.text = idesc



        val btn = findViewById<Button>(R.id.button3)
        btn.setOnClickListener {
            val builder = AlertDialog.Builder(this)


            builder.setTitle("Delete Invoice")
            builder.setMessage("Are you sure you want to delete?")
            builder.setCancelable(false)

            builder.setPositiveButton("Yes") { _, _ ->


                Log.i("delete Activity","invoice id in delete activity is: $id")
                // Call deleteInvoice API
                // getting the id from the above shared preference.
                val call = invoiceApi.deleteInvoice(id)

                call.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@DeleteActivity, "Deleted successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@DeleteActivity, InvoiceListActivity::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this@DeleteActivity, "Failed to delete invoice", Toast.LENGTH_SHORT).show()
                            Log.e("delete activity","error in delete activity is : ${response.errorBody()}")
                            // Handle failure
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@DeleteActivity, "Network Error", Toast.LENGTH_SHORT).show()
                        Log.e("delete activity","Network Error: ${t.message}")

                    }
                })


            }

            builder.setNegativeButton("No") { _, _ ->

            }
            builder.create().show()
        }


    }
}