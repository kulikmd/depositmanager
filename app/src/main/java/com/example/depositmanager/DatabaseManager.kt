// app/src/main/java/com/example/depositmanager/dbconnector/DatabaseManager.kt
package com.example.depositmanager

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class DatabaseManager(context: Context) {

    private val dbHelper = DatabaseHelper(context)
    private lateinit var db: SQLiteDatabase

    fun open() {
        db = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
    }

    fun insertDeposit(values: ContentValues): Long {
        return db.insert(DatabaseHelper.TABLE_DEPOSITS, null, values)
    }

    fun updateDeposit(id: Long, values: ContentValues): Int {
        return db.update(DatabaseHelper.TABLE_DEPOSITS, values, "id = ?", arrayOf(id.toString()))
    }

    fun deleteDeposit(id: Long): Int {
        return db.delete(DatabaseHelper.TABLE_DEPOSITS, "id = ?", arrayOf(id.toString()))
    }

    fun getAllDeposits(): Cursor {
        return db.query(DatabaseHelper.TABLE_DEPOSITS, null, null, null, null, null, null)
    }

    fun getDepositById(id: Long): Cursor {
        return db.query(DatabaseHelper.TABLE_DEPOSITS, null, "id = ?", arrayOf(id.toString()), null, null, null)
    }
}
