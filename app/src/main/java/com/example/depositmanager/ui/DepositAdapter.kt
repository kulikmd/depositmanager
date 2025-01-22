package com.example.depositmanager.ui

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CursorAdapter
import android.widget.TextView
import com.example.depositmanager.R

class DepositAdapter(context: Context, cursor: Cursor?, private val onItemClickListener: (Long) -> Unit) : CursorAdapter(context, cursor, 0) {

    private val selectedItems = mutableSetOf<Long>()

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.item_deposit, parent, false)
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val bankNameTextView: TextView = view.findViewById(R.id.bankNameTextView)
        val amountTextView: TextView = view.findViewById(R.id.amountTextView)
        val interestRateTextView: TextView = view.findViewById(R.id.interestRateTextView)
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)

        val bankName = cursor.getString(cursor.getColumnIndexOrThrow("bank_name"))
        val amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"))
        val interestRate = cursor.getDouble(cursor.getColumnIndexOrThrow("interest_rate"))
        val id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"))

        bankNameTextView.text = bankName
        amountTextView.text = amount.toString()
        interestRateTextView.text = interestRate.toString()

        checkBox.isChecked = selectedItems.contains(id)
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedItems.add(id)
            } else {
                selectedItems.remove(id)
            }
        }

        view.setOnClickListener {
            onItemClickListener(id)
        }
    }

    fun getSelectedItems(): Set<Long> {
        return selectedItems
    }
}
