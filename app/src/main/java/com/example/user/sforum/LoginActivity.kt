package com.example.user.sforum

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Intent

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        laliga_button.setOnClickListener {
            startMainActivity("455")
        }

        premierleague_button.setOnClickListener {
            startMainActivity("445")
        }

        ligue1_button.setOnClickListener {
            startMainActivity("450")
        }

        seriea_button.setOnClickListener {
            startMainActivity("456")
        }

    }

    fun startMainActivity (id : String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}
