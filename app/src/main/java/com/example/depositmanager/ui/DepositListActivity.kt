package com.example.depositmanager.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.depositmanager.R
import com.example.depositmanager.DatabaseManager

class DepositListActivity : AppCompatActivity() {

    private lateinit var dbManager: DatabaseManager
    private lateinit var depositListView: ListView
    private lateinit var addButton: Button
    private lateinit var deleteButton: Button
    private lateinit var editButton: Button
    private lateinit var depositAdapter: DepositAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposit_list)

        dbManager = DatabaseManager(this)
        dbManager.open()

        depositListView = findViewById(R.id.depositListView)
        addButton = findViewById(R.id.addButton)
        deleteButton = findViewById(R.id.deleteButton)
        editButton = findViewById(R.id.editButton)

        addButton.setOnClickListener {
            val intent = Intent(this, DepositEditActivity::class.java)
            startActivity(intent)
        }

        deleteButton.setOnClickListener {
            val selectedItems = depositAdapter.getSelectedItems()
            for (id in selectedItems) {
                dbManager.deleteDeposit(id)
            }
            updateDepositList()
        }

        editButton.setOnClickListener {
            val selectedItems = depositAdapter.getSelectedItems()
            if (selectedItems.size == 1) {
                val id = selectedItems.first()
                val intent = Intent(this, DepositEditActivity::class.java)
                intent.putExtra("deposit_id", id)
                Log.d("DepositListActivity", "Editing deposit with id: $id")
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateDepositList()
    }

    private fun updateDepositList() {
        val cursor = dbManager.getAllDeposits()
        depositAdapter = DepositAdapter(this, cursor)
        depositListView.adapter = depositAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.close()
    }
}
