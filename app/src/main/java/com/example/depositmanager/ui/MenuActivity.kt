package com.example.depositmanager.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.depositmanager.R
import com.example.depositmanager.DatabaseManager
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MenuActivity : AppCompatActivity() {

    private lateinit var dbManager: DatabaseManager
    private lateinit var exportButton: Button
    private lateinit var importButton: Button
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        dbManager = DatabaseManager(this)
        dbManager.open()

        exportButton = findViewById(R.id.exportButton)
        importButton = findViewById(R.id.importButton)
        backButton = findViewById(R.id.backButton)

        exportButton.setOnClickListener {
            exportDatabase()
        }

        importButton.setOnClickListener {
            importDatabase()
        }

        backButton.setOnClickListener {
            goBackToDepositList()
        }
    }

    private fun goBackToDepositList() {
        val intent = Intent(this, DepositListActivity::class.java)
        startActivity(intent)
    }

    private fun exportDatabase() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/x-sqlite3"
            putExtra(Intent.EXTRA_TITLE, "deposits.db")
        }
        startActivityForResult(intent, EXPORT_REQUEST_CODE)
    }

    private fun importDatabase() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/octet-stream"
        }
        startActivityForResult(intent, IMPORT_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                EXPORT_REQUEST_CODE -> {
                    data?.data?.let { uri ->
                        Log.d("MenuActivity", "Export URI: $uri")
                        exportDatabaseToUri(uri)
                    }
                }
                IMPORT_REQUEST_CODE -> {
                    data?.data?.let { uri ->
                        Log.d("MenuActivity", "Import URI: $uri")
                        contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        importDatabaseFromUri(uri)
                    }
                }
            }
        }
    }

    private fun exportDatabaseToUri(uri: Uri) {
        val dbFile = File(dbManager.getDatabasePath())
        val inputStream = FileInputStream(dbFile)
        val outputStream = contentResolver.openOutputStream(uri)
        inputStream.copyTo(outputStream!!)
        inputStream.close()
        outputStream.close()
        Log.d("MenuActivity", "Database exported successfully to $uri")
    }

    private fun importDatabaseFromUri(uri: Uri) {
        val dbFile = File(dbManager.getDatabasePath())
        val inputStream = contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(dbFile)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        Log.d("MenuActivity", "Database imported successfully from $uri")
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.close()
    }

    companion object {
        private const val EXPORT_REQUEST_CODE = 1
        private const val IMPORT_REQUEST_CODE = 2
    }
}
