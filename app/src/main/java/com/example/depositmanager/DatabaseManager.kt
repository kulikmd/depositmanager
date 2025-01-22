package com.example.depositmanager

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

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
        return db.update(DatabaseHelper.TABLE_DEPOSITS, values, "_id = ?", arrayOf(id.toString()))
    }

    fun deleteDeposit(id: Long): Int {
        return db.delete(DatabaseHelper.TABLE_DEPOSITS, "_id = ?", arrayOf(id.toString()))
    }

    fun getAllDeposits(): Cursor {
        return db.query(DatabaseHelper.TABLE_DEPOSITS, null, null, null, null, null, null)
    }

    fun getDepositById(id: Long): Cursor {
        Log.d("DatabaseManager", "Fetching deposit with id: $id")
        return db.query(DatabaseHelper.TABLE_DEPOSITS, null, "_id = ?", arrayOf(id.toString()), null, null, null)
    }

    fun getDatabasePath(): String {
        return dbHelper.readableDatabase.path
    }
}
