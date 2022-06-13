package com.jcppp.launchac

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jccppp.start.argumentNullable
import com.jccppp.start.set
import com.jccppp.start.setBack

class OneActivity : AppCompatActivity() {

   private var canShu by argumentNullable<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one)

        intent?.getStringExtra("canShu")

        Toast.makeText(this, "上个页面传值$canShu", Toast.LENGTH_LONG).show()

        findViewById<View>(R.id.view).setOnClickListener {
            setBack {
                it["data"]="哈哈啊哈"
            }

        }

        //可以二次赋值
        canShu = ""
    }
}