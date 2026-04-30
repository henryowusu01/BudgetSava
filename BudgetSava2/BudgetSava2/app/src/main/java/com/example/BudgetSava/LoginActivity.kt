package com.example.BudgetSava

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moneyways.R
import com.example.BudgetSava.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity:AppCompatActivity() {

    //Limits access to only inside the class AND lets me delay initialization of a non-null variable
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginBtn: Button
    private lateinit var doNotHaveAcc: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //load login page
        setContentView(R.layout.activity_login)

        //assign xml field/buttons
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginBtn =  findViewById(R.id.loginBtn)
        doNotHaveAcc =  findViewById(R.id.textView9)

        //for a user who doesn't have an account
        doNotHaveAcc.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        loginBtn.setOnClickListener {
            val username = emailInput.text.toString()
            val password = passwordInput.text.toString()

            //Check if empty
            if(username.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Please  fill all field",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //block to login user in RoomDb in background then will show toast and success
            CoroutineScope(Dispatchers.IO).launch {

                //Attempt to fetch the user from the database
                val existingUser =  AppDatabase.getDatabase(this@LoginActivity).appDao().getUser(username, password)

                withContext(Dispatchers.Main){

                    //if user exists
                    if(existingUser != null){
                        Toast.makeText(this@LoginActivity, "Welcome ${existingUser.username}", Toast.LENGTH_SHORT).show()


                        val intent = Intent(this@LoginActivity, DashboardActivity :: class.java)
                        intent.putExtra("username", existingUser.username)
                        startActivity(intent)
                    }else
                    {
                        Toast.makeText(this@LoginActivity,"Invalid. No user: $username",Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

    }
}


