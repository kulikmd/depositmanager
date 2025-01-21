// app/src/main/java/com/example/depositmanager/ui/DepositListActivity.kt
package com.example.depositmanager.ui

import android.content.Intent
import android.os.Bundle
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

        // Добавьте обработчики для deleteButton и editButton
    }

    override fun onResume() {
        super.onResume()
        // Обновите список депозитов
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.close()
    }
}
