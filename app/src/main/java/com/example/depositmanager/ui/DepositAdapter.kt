package com.example.depositmanager.ui

import android.content.Context
import android.database.Cursor
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CursorAdapter
import android.widget.TextView
import com.example.depositmanager.R

class DepositAdapter(context: Context, cursor: Cursor?, private val onItemClickListener: (Long) -> Unit) : CursorAdapter(context, cursor, 0) {

    private val selectedItems = SparseBooleanArray()

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_deposit, parent, false)
        val viewHolder = ViewHolder(view)
        view.tag = viewHolder
        return view
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val viewHolder = view.tag as ViewHolder

        val bankName = cursor.getString(cursor.getColumnIndexOrThrow("bank_name"))
        val amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"))
        val interestRate = cursor.getDouble(cursor.getColumnIndexOrThrow("interest_rate"))
        val id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"))

        viewHolder.bankNameTextView.text = bankName
        viewHolder.amountTextView.text = amount.toString()
        viewHolder.interestRateTextView.text = interestRate.toString()

        // Устанавливаем ID в тег чекбокса
        viewHolder.checkBox.tag = id

        // Отключаем слушатель перед изменением состояния
        viewHolder.checkBox.setOnCheckedChangeListener(null)

        // Устанавливаем правильное состояние чекбокса
        viewHolder.checkBox.isChecked = selectedItems.get(id.toInt(), false)

        // Включаем слушатель с защитой от неправильного обновления
        viewHolder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            val itemId = buttonView.tag as Long
            selectedItems.put(itemId.toInt(), isChecked)
            Log.d("DepositAdapter", "CheckBox state changed for id: $itemId, isChecked: $isChecked")
        }

        view.setOnClickListener {
            onItemClickListener(id)
        }
    }

    fun getSelectedItems(): Set<Long> {
        val selectedIds = mutableSetOf<Long>()
        for (i in 0 until selectedItems.size()) {
            val key = selectedItems.keyAt(i)
            if (selectedItems.get(key)) {
                selectedIds.add(key.toLong())
            }
        }
        return selectedIds
    }

    private class ViewHolder(view: View) {
        val bankNameTextView: TextView = view.findViewById(R.id.bankNameTextView)
        val amountTextView: TextView = view.findViewById(R.id.amountTextView)
        val interestRateTextView: TextView = view.findViewById(R.id.interestRateTextView)
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)
    }
}
