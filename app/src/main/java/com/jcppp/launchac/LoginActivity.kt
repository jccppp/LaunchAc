package com.jcppp.launchac

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity()  {

    companion object {
        var isLogin = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<View>(R.id.view).setOnClickListener {
            isLogin = true
            setResult(RESULT_OK)
            finish()
        }
    }
}