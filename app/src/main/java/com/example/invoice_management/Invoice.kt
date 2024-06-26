package com.example.invoice_management

import java.time.LocalDate
import java.util.Date


data class Invoice(
    var invoiceId: Int = 0,
    var clientName: String,
    var invoiceAmount :Double,
    var createdDate: String,
    var description: String,
)
