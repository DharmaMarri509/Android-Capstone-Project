package com.example.invoice_management

data class InvoiceDto(
    var invoiceId: Int,
    var clientName: String,
    var invoiceAmount :Double,
    var createdDate: String,
    var description: String,
)
