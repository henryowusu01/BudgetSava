package com.example.BudgetSava

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moneyways.R
import com.example.BudgetSava.adapter.ExpenseAdapter
import com.example.BudgetSava.data.AppDatabase
import com.example.BudgetSava.dialog.DateRangePickerDialog
import kotlinx.coroutines.launch

class ReportViewActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var dateRangePicker: Button
    private var startDate: String? = null
    private var endDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reportview)

        database = AppDatabase.getDatabase(this)
        recyclerView = findViewById(R.id.expensesRecyclerView)
      //  dateRangePicker = findViewById(R.id.dateRangePicker) // this is broken for now

        recyclerView.layoutManager = LinearLayoutManager(this)

        dateRangePicker.setOnClickListener {
            showDateRangePickerDialog()
        }

        loadExpenses()
    }

    private fun showDateRangePickerDialog() {
        val dialog = DateRangePickerDialog(this,
            { start, end ->
                startDate = start
                endDate = end
                loadExpenses()
            })
        dialog.show()
    }

    private fun loadExpenses() {
        lifecycleScope.launch {
            val userId = getCurrentUserId()
            val expensesWithCategories = database.appDao().getExpensesWithCategories(
                userId = userId,
                startDate = startDate,
                endDate = endDate
            )

            val adapter = ExpenseAdapter(expensesWithCategories) { photoPath ->
                // Handle photo click
                val uri = Uri.parse(photoPath)
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, "image/*")
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                startActivity(intent)
            }
            recyclerView.adapter = adapter
        }
    }

    private fun getCurrentUserId(): Long {
        // You must replace this with your actual session/user management logic
        return 1L
    }
}