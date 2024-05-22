package com.example.invoice_management


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var userName: EditText
    private lateinit var password: EditText
    private lateinit var emailId: EditText
    private lateinit var btn: Button
    private lateinit var loginText: TextView

    private lateinit var retrofit: Retrofit
    private val scope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private lateinit var userApi: UserApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        retrofit = RetrofitClient.create()
        userApi = retrofit.create(UserApi::class.java)

        loginText = findViewById(R.id.textView4)
        loginText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        createApiCall()

    }

    private fun createApiCall() {
        userName = findViewById(R.id.username)
        password = findViewById(R.id.password)
        emailId = findViewById(R.id.email)
        btn = findViewById(R.id.button)
        btn.setOnClickListener {
            val name: String = userName.getText().toString()
            val pswd = password.getText().toString()
            val email = emailId.getText().toString()

//            scope.launch {
//                val user = User(name, pswd, email)
//                val call = userApi.saveUser(user)
//                if (call.isSuccessful) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        "user registered successfully",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } else {
//                    Toast.makeText(this@MainActivity, "user not registered", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }

            if (name.isNotBlank() && pswd.isNotBlank() && email.isNotBlank()) {
                val user = User(name, pswd, email)
                registerUser(user)
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun registerUser(user: User) {
        val call = userApi.saveUser(user)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }


}






