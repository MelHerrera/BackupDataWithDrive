package com.example.backupdata

import android.app.backup.BackupManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var txtEnterData: EditText
    private lateinit var txtShowData: EditText
    private lateinit var btnEnterData: Button
    private lateinit var btnShowData: Button
    private var backupManager: BackupManager? = null
    private var prefs: SharedPreferences? = null
    private var edit: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        prefs = getSharedPreferences(BackupData.PREFS_TEST, Context.MODE_PRIVATE)
        edit = prefs?.edit()
        backupManager = BackupManager(this)

        if (showData().isNotEmpty())
            btnShowData.isEnabled = true

        btnEnterData.setOnClickListener{
            saveData("save",txtEnterData.text.toString())
            btnShowData.isEnabled = true
        }

        btnShowData.setOnClickListener {
            txtShowData.setText(showData())
        }
        btnShowData.isEnabled = true
    }

    private fun saveData(key: String, value: String) {
        edit?.putString(key, value)
        edit?.commit()
        Log.d("Test", "Calling backup...")
        backupManager?.dataChanged()
    }

    private fun showData(): String {
        return prefs?.getString("save", "").toString()
    }

    private fun initializeViews(){
        txtEnterData = findViewById(R.id.enter_data)
        txtShowData = findViewById(R.id.load_data)
        btnEnterData = findViewById(R.id.save)
        btnShowData = findViewById(R.id.show)
    }
}