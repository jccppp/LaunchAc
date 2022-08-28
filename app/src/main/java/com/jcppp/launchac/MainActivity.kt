package com.jcppp.launchac

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jccppp.start.*
import com.jccppp.start.jk.IAcCallBack

class MainActivity : AppCompatActivity(), IAcCallBack by AcCallBackHelper() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAcCallBackHelper()

        findViewById<View>(R.id.tv1).setOnClickListener {
            launchAc<OneActivity>()
        }

        findViewById<View>(R.id.tv2).setOnClickListener {
            launchAc<OneActivity>("canShu" to "hello1" , "custom" to "hello3")
        }
        findViewById<View>(R.id.tv3).setOnClickListener {
            launchAcWithLogin<OneActivity>()
        }
        var index = 1
        findViewById<View>(R.id.tv4).setOnClickListener {
            launchAcWithLoginAndJump<OneActivity>("canShu" to "hello2",
                jump = { isLogin -> isLogin && index++ % 2 == 0 }
            )
        }
        findViewById<View>(R.id.tv5).setOnClickListener {

            launchAcForResult<OneActivity> { code, data ->
                if (code == RESULT_OK) Toast.makeText(
                    this@MainActivity,
                    "OneActivity页面传值${data?.getStringExtra("data")}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}