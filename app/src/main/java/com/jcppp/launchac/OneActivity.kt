package com.jcppp.launchac

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jccppp.start.argument
import com.jccppp.start.argumentNullable
import com.jccppp.start.setBack

class OneActivity : AppCompatActivity() {

    private var canShu by argumentNullable<String>()  //可空
    // private var canShu by argument("")             // 不为空,如未传值则取默认值
    //等价         intent?.getStringExtra("canShu")

//    private var aaaa by argumentNullable<String>("custom")
    private var aaaa by argument("","custom")
    //指定key为 "custom"   等价         intent?.getStringExtra("custom")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one)

        if (!canShu.isNullOrEmpty())
            Toast.makeText(this, "上个页面传值$canShu", Toast.LENGTH_LONG).show()

        findViewById<View>(R.id.view).setOnClickListener {
            setBack("data" to "哈哈啊哈")
        }

        //可以二次赋值
        canShu = ""
    }
}