// app/src/main/java/com/example/depositmanager/ui/MainActivity.kt
package com.example.depositmanager.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.depositmanager.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Переход к экрану списка депозитов
        val intent = Intent(this, DepositListActivity::class.java)
        startActivity(intent)
        finish() // Закрываем MainActivity, так как она больше не нужна
    }
}
