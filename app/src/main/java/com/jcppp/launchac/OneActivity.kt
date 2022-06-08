package com.jcppp.launchac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jccppp.start.argumentNullable
import com.jccppp.start.set

class OneActivity : AppCompatActivity() {

    var canShu by argumentNullable<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one)

        Toast.makeText(this, "上个页面传值$canShu", Toast.LENGTH_LONG).show()

        findViewById<View>(R.id.view).setOnClickListener {
            setResult(RESULT_OK, Intent().also { it["data"]="哈哈啊哈" })
            finish()
        }

        //可以二次赋值
        canShu = ""
    }
}