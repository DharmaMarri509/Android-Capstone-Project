package com.example.invoice_management

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

class LoginActivity : AppCompatActivity() {

    private lateinit var userName: EditText
    private lateinit var password: EditText
    private lateinit var btn: Button

    private lateinit var retrofit: Retrofit
    private lateinit var userApi: UserApi
    private val scope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        retrofit = RetrofitClient.create()
        userApi = retrofit.create(UserApi::class.java)

        userName = findViewById(R.id.u_name)
        password = findViewById(R.id.pswrd)
        btn = findViewById(R.id.login_button)

        btn.setOnClickListener{

            val name = userName.text.toString()
            val pwd = password.text.toString()
            if (name.isNotEmpty() && pwd.isNotEmpty()) {
                performLogin(name, pwd)
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performLogin(name: String, password: String) {
        val call = userApi.getUser(name,password)

        call.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    // if the loginResponse is not null then only the block will executed
                    loginResponse?.let {
                        // Handle successful login

                        //storing the userid using shared preferences..
                        val sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE)
                        val editor = sharedPref.edit()
                        val userId = response.body() ?: -1 // Handle potential null response
                        editor.putInt("user_id", userId)
                        Log.i("user id in the login activity", "user id is $userId")
                        editor.apply()


                        Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()


                        // Navigate to the next activity
                        val intent = Intent(this@LoginActivity,InvoiceListActivity::class.java)
                        startActivity(intent)

                    }
                } else {
                    // Handle unsuccessful response
                    Toast.makeText(this@LoginActivity, "Login failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Int>, t: Throwable) {
                // Handle error
                Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}