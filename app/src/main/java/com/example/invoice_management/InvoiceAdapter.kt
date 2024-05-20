package com.example.invoice_management



import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.time.format.DateTimeFormatter






class InvoiceAdapter(private val invoices: List<Invoice>) : RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder>() {

    class InvoiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val clientNameTextView: TextView = itemView.findViewById(R.id.clientName)
        val amountTextView: TextView = itemView.findViewById(R.id.amount)
        val dateTextView: TextView = itemView.findViewById(R.id.date)
       //image view of the file.
      //  val imageView1 = itemView.findViewById<ImageView>(R.id.imageView1)
        //edit imageview
        val imageView2 = itemView.findViewById<ImageView>(R.id.imageView2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.invoice_item_list, parent, false)
        return InvoiceViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        val currentInvoice = invoices[position]
        holder.clientNameTextView.text = currentInvoice.clientName
        holder.amountTextView.text = currentInvoice.invoiceAmount.toString()
        holder.dateTextView.text = currentInvoice.createdDate.toString()


        //open the detailed view of the invoice
        holder.itemView.setOnClickListener{

        }

        //code to edit the invoice
        holder.imageView2.setOnClickListener{

        }

    }

    override fun getItemCount() = invoices.size



}
