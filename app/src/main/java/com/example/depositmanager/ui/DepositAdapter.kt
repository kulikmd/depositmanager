package com.example.depositmanager.ui

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import com.example.depositmanager.R

class DepositAdapter(context: Context, cursor: Cursor) : CursorAdapter(context, cursor, 0) {

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.item_deposit, parent, false)
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val bankNameTextView: TextView = view.findViewById(R.id.bankNameTextView)
        val amountTextView: TextView = view.findViewById(R.id.amountTextView)
        val interestRateTextView: TextView = view.findViewById(R.id.interestRateTextView)

        val bankName = cursor.getString(cursor.getColumnIndexOrThrow("bank_name"))
        val amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"))
        val interestRate = cursor.getDouble(cursor.getColumnIndexOrThrow("interest_rate"))

        bankNameTextView.text = bankName
        amountTextView.text = amount.toString()
        interestRateTextView.text = interestRate.toString()
    }
}
