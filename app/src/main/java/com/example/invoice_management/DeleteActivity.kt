package com.example.invoice_management

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Retrofit

class DeleteActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var invoiceApi: InvoiceApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        val btn = findViewById<Button>(R.id.button3)
        btn.setOnClickListener {
            val builder = AlertDialog.Builder(this)


            builder.setTitle("Delete Invoice")
            builder.setMessage("Are you sure you want to delete?")

            builder.setPositiveButton("Yes") { dialog, which ->
                // implement code to handle request
                Toast.makeText(this@DeleteActivity, "Deleted Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, InvoiceListActivity::class.java)
                startActivity(intent)
            }

            builder.setNegativeButton("No") { dialog, which ->

            }

            builder.create().show()
        }


    }
}