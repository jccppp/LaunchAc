package com.jcppp.launchac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jccppp.start.*
import com.jccppp.start.jk.ConditionalJumpLogin
import com.jccppp.start.jk.IAcCallBack
import com.jccppp.start.jk.StartForResult

class MainActivity : AppCompatActivity(), IAcCallBack by AcCallBackHelper() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAcCallBackHelper()

        findViewById<View>(R.id.tv1).setOnClickListener {
            launchActivity<OneActivity>()
        }

        findViewById<View>(R.id.tv2).setOnClickListener {
            launchActivity<OneActivity> {
                it["canShu"] = "hello1"
            }
        }
        findViewById<View>(R.id.tv3).setOnClickListener {
            launchActivityWithLogin<OneActivity>()
        }
        var index = 1
        findViewById<View>(R.id.tv4).setOnClickListener {
            launchActivityWithLoginForBack<OneActivity>(
                jump = object : ConditionalJumpLogin {
                    override fun jump(isLogin: Boolean): Boolean {
                        return isLogin && index++ % 2 == 0
                    }
                }
            ) {
                it["canShu"] = "hello2"
            }

        }
        findViewById<View>(R.id.tv5).setOnClickListener {

            launchActivityForResult<OneActivity>(acBack = object : StartForResult {
                override fun result(code: Int, data: Intent?) {
                    if (code == RESULT_OK) Toast.makeText(
                        this@MainActivity,
                        "OneActivity页面传值${data?.getStringExtra("data")}",
                        Toast.LENGTH_LONG
                    ).show()
                }

            })
        }
    }
}