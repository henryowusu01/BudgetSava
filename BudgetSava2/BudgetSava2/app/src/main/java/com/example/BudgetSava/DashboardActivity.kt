package com.example.BudgetSava

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.moneyways.R

class DashboardActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //shows dashboard
        setContentView(R.layout.activity_dashboard)

        //Get users from intent
        val username = intent.getStringExtra("username")

        //Uses TextView and set the actual name
        val greetingUsername = findViewById<TextView>(R.id.greetingText)
        greetingUsername.text = "Hello, $username"

        //This adds a expense
        val button = findViewById<Button>(R.id.addExpenseButton)
        button.setOnClickListener {
            //load  add expense page
            //setContentView(R.layout.activity_add_expense)
            navigateToAddExpense()
        }
    }

    private fun navigateToAddExpense() {
        val intent = Intent(this, AddExpenseActivity::class.java)
        startActivity(intent)
    }
}
