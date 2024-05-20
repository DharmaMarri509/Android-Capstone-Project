package com.example.invoice_management

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.invoice_management.R
import retrofit2.Retrofit

class EditInvoiceActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var invoiceApi: InvoiceApi
    private lateinit var clientName: TextView
    private lateinit var invoiceAmount: TextView
    private lateinit var creationDate: TextView
    private lateinit var description: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_invoice)

    }
}