package com.example.invoice_management

import java.time.LocalDate
import java.util.Date


data class Invoice(
    var clientName: String,
    var amount :Double,
    var date: LocalDate,
    var description: String,
)
