// app/src/main/java/com/example/depositmanager/dbconnector/DatabaseHelper.kt
package com.example.depositmanager

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "deposits.db"
        private const val DATABASE_VERSION = 1
        val TABLE_DEPOSITS = "deposits"
    }

    override fun onCreate(db: SQLiteDatabase) {
        createDepositsTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DEPOSITS")
        onCreate(db)
    }

    private fun createDepositsTable(db: SQLiteDatabase) {
        val CREATE_DEPOSITS_TABLE = ("CREATE TABLE $TABLE_DEPOSITS ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "bank_name TEXT,"
                + "amount REAL,"
                + "interest_rate REAL,"
                + "start_date TEXT,"
                + "end_date TEXT,"
                + "capitalization_flag INTEGER,"
                + "end_of_term_flag INTEGER,"
                + "interest_payment_day INTEGER,"
                + "reserved1 TEXT,"
                + "reserved2 TEXT,"
                + "reserved3 TEXT,"
                + "reserved4 TEXT,"
                + "reserved5 TEXT,"
                + "reserved6 TEXT,"
                + "reserved7 TEXT,"
                + "reserved8 TEXT,"
                + "reserved9 TEXT,"
                + "reserved10 TEXT)")
        db.execSQL(CREATE_DEPOSITS_TABLE)
    }
}
