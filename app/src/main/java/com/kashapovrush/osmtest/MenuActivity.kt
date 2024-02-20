package com.kashapovrush.osmtest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CategoryFragment.newInstance())
                .commit()
        }
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MenuActivity::class.java)
        }
    }
}