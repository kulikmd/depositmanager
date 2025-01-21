package com.example.depositmanager.ui

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.depositmanager.R
import com.example.depositmanager.DatabaseManager

class DepositEditActivity : AppCompatActivity() {

    private lateinit var dbManager: DatabaseManager
    private lateinit var bankNameEditText: EditText
    private lateinit var amountEditText: EditText
    private lateinit var interestRateEditText: EditText
    private lateinit var startDateEditText: EditText
    private lateinit var endDateEditText: EditText
    private lateinit var capitalizationFlagCheckBox: CheckBox
    private lateinit var endOfTermFlagCheckBox: CheckBox
    private lateinit var interestPaymentDayEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    private var depositId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposit_edit)

        dbManager = DatabaseManager(this)
        dbManager.open()

        bankNameEditText = findViewById(R.id.bankNameEditText)
        amountEditText = findViewById(R.id.amountEditText)
        interestRateEditText = findViewById(R.id.interestRateEditText)
        startDateEditText = findViewById(R.id.startDateEditText)
        endDateEditText = findViewById(R.id.endDateEditText)
        capitalizationFlagCheckBox = findViewById(R.id.capitalizationFlagCheckBox)
        endOfTermFlagCheckBox = findViewById(R.id.endOfTermFlagCheckBox)
        interestPaymentDayEditText = findViewById(R.id.interestPaymentDayEditText)
        saveButton = findViewById(R.id.saveButton)
        cancelButton = findViewById(R.id.cancelButton)

        depositId = intent.getLongExtra("deposit_id", -1)
        Log.d("DepositEditActivity", "Received deposit_id: $depositId")

        if (depositId != -1L) {
            loadDepositData(depositId)
        }

        saveButton.setOnClickListener {
            saveDeposit()
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun loadDepositData(id: Long) {
        val cursor = dbManager.getDepositById(id)
        if (cursor.moveToFirst()) {
            Log.d("DepositEditActivity", "Loading deposit data for id: $id")
            bankNameEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("bank_name")))
            amountEditText.setText(cursor.getDouble(cursor.getColumnIndexOrThrow("amount")).toString())
            interestRateEditText.setText(cursor.getDouble(cursor.getColumnIndexOrThrow("interest_rate")).toString())
            startDateEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("start_date")))
            endDateEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("end_date")))
            capitalizationFlagCheckBox.isChecked = cursor.getInt(cursor.getColumnIndexOrThrow("capitalization_flag")) == 1
            endOfTermFlagCheckBox.isChecked = cursor.getInt(cursor.getColumnIndexOrThrow("end_of_term_flag")) == 1
            interestPaymentDayEditText.setText(cursor.getInt(cursor.getColumnIndexOrThrow("interest_payment_day")).toString())
            Log.d("DepositEditActivity", "Loaded deposit data successfully for id: $id")
        } else {
            Log.e("DepositEditActivity", "Failed to load deposit data for id: $id")
        }
        cursor.close()
    }

    private fun saveDeposit() {
        val bankName = bankNameEditText.text.toString()
        val amount = amountEditText.text.toString().toDoubleOrNull() ?: 0.0
        val interestRate = interestRateEditText.text.toString().toDoubleOrNull() ?: 0.0
        val startDate = startDateEditText.text.toString()
        val endDate = endDateEditText.text.toString()
        val capitalizationFlag = if (capitalizationFlagCheckBox.isChecked) 1 else 0
        val endOfTermFlag = if (endOfTermFlagCheckBox.isChecked) 1 else 0
        val interestPaymentDay = interestPaymentDayEditText.text.toString().toIntOrNull() ?: 0

        val values = ContentValues().apply {
            put("bank_name", bankName)
            put("amount", amount)
            put("interest_rate", interestRate)
            put("start_date", startDate)
            put("end_date", endDate)
            put("capitalization_flag", capitalizationFlag)
            put("end_of_term_flag", endOfTermFlag)
            put("interest_payment_day", interestPaymentDay)
        }

        if (depositId == -1L) {
            dbManager.insertDeposit(values)
        } else {
            dbManager.updateDeposit(depositId, values)
        }

        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.close()
    }
}
