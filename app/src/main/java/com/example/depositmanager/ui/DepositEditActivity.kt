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

        saveButton.setOnClickListener {
            saveDeposit()
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun saveDeposit() {
        Log.d("DepositEditActivity", "saveDeposit called")

        val bankName = bankNameEditText.text.toString()
        val amount = amountEditText.text.toString().toDoubleOrNull()
        val interestRate = interestRateEditText.text.toString().toDoubleOrNull()
        val startDate = startDateEditText.text.toString()
        val endDate = endDateEditText.text.toString()
        val capitalizationFlag = if (capitalizationFlagCheckBox.isChecked) 1 else 0
        val endOfTermFlag = if (endOfTermFlagCheckBox.isChecked) 1 else 0
        val interestPaymentDay = interestPaymentDayEditText.text.toString().toIntOrNull()

        if (amount == null || interestRate == null || interestPaymentDay == null) {
            Log.e("DepositEditActivity", "Invalid input data")
            return
        }

        Log.d("DepositEditActivity", "Bank Name: $bankName")
        Log.d("DepositEditActivity", "Amount: $amount")
        Log.d("DepositEditActivity", "Interest Rate: $interestRate")
        Log.d("DepositEditActivity", "Start Date: $startDate")
        Log.d("DepositEditActivity", "End Date: $endDate")
        Log.d("DepositEditActivity", "Capitalization Flag: $capitalizationFlag")
        Log.d("DepositEditActivity", "End of Term Flag: $endOfTermFlag")
        Log.d("DepositEditActivity", "Interest Payment Day: $interestPaymentDay")

        val values = ContentValues().apply {
            put("bank_name", bankName)
            put("amount", amount)
            put("interest_rate", interestRate)
            put("start_date", startDate)
            put("end_date", endDate)
            put("capitalization_flag", capitalizationFlag)
            put("end_of_term_flag", endOfTermFlag)
            put("interest_payment_day", interestPaymentDay)
            // Добавьте значения для reserved1, reserved2 и т.д. по аналогии
        }

        val result = dbManager.insertDeposit(values)
        Log.d("DepositEditActivity", "Insert result: $result")

        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.close()
    }
}
