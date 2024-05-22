package com.example.invoice_management



import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.time.format.DateTimeFormatter






class InvoiceAdapter(private val context: Context,private val invoices: List<Invoice>) : RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder>() {

    class InvoiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val clientNameTextView: TextView = itemView.findViewById(R.id.clientName)
        val amountTextView: TextView = itemView.findViewById(R.id.amount)
        val dateTextView: TextView = itemView.findViewById(R.id.date)
       //image view of the file.
      //  val imageView1 = itemView.findViewById<ImageView>(R.id.imageView1)
        //edit imageview
        val imageView2: ImageView = itemView.findViewById(R.id.imageView2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.invoice_item_list, parent, false)
        return InvoiceViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        val currentInvoice = invoices[position]
        holder.clientNameTextView.text = currentInvoice.clientName
        holder.amountTextView.text = currentInvoice.invoiceAmount.toString()
        holder.dateTextView.text = currentInvoice.createdDate.toString()


        //open the detailed view of the invoice and show the delete button..
        holder.itemView.setOnClickListener{
            val invoiceId = currentInvoice.invoiceId
            val clientName = currentInvoice.clientName
            val invoiceAmount = currentInvoice.invoiceAmount
            val creationDate = currentInvoice.createdDate
            val description = currentInvoice.description
            Log.i("invoiceId","invoice id in the adapter is : $invoiceId")
            //get the invoice id
            val invSharedPref:SharedPreferences = context.getSharedPreferences("invoice_data",Context.MODE_PRIVATE)
            val editor = invSharedPref.edit()

            editor.putInt("invoice_id", invoiceId)
            editor.putString("name",clientName)
            editor.putString("amount",invoiceAmount.toString())
            editor.putString("date",creationDate)
            editor.putString("desc",description)

            editor.apply()
            val intent = Intent(context, DeleteActivity::class.java)
            context.startActivity(intent)


        }

        //code to edit the invoice
        holder.imageView2.setOnClickListener{
            val invoiceId = currentInvoice.invoiceId
            val clientName = currentInvoice.clientName
            val invoiceAmount = currentInvoice.invoiceAmount
            val creationDate = currentInvoice.createdDate
            val description = currentInvoice.description
            Log.i("invoiceId","invoice id in the adapter is : $invoiceId")
            //get the invoice id
            val invSharedPref:SharedPreferences = context.getSharedPreferences("invoice_data",Context.MODE_PRIVATE)
            val editor = invSharedPref.edit()

            editor.putInt("invoice_id", invoiceId)
            editor.putString("name",clientName)
            editor.putString("amount",invoiceAmount.toString())
            editor.putString("date",creationDate)
            editor.putString("desc",description)

            editor.apply()
            val intent = Intent(context, EditInvoiceActivity::class.java)
            context.startActivity(intent)
        }

    }

    override fun getItemCount() = invoices.size



}
