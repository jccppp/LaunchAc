package com.jcppp.launchac

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.jccppp.start.*
import com.jccppp.start.jk.IAcCallBack

class MainActivity : FragmentActivity(), IAcCallBack by AcCallBackHelper() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAcCallBackHelper()

        findViewById<View>(R.id.tv1).setOnClickListener {
            launchAc<OneActivity>()
        }

        findViewById<View>(R.id.tv6).setOnClickListener {
            applicationContext.launchAc<OneActivity> {
                copyGlobal(true)
            }
        }


        findViewById<View>(R.id.tv2).setOnClickListener {
            launchAc<OneActivity>("canShu" to "hello1", "custom" to "hello3") {
                copyGlobal(true)
            }
        }
        findViewById<View>(R.id.tv3).setOnClickListener {
            launchAc<OneActivity>() {
                withLogin()
            }
        }
        var index = 1

        findViewById<View>(R.id.tv4).setOnClickListener {
            launchAc<OneActivity>("canShu" to "hello2") {
                condition { isLogin ->
                    val jump = index++ % 2 == 0
                    if (!jump) Toast.makeText(
                        this@MainActivity,
                        "条件不符合跳转",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    jump
                }

            }

        }
        findViewById<View>(R.id.tv5).setOnClickListener {

            launchAc<OneActivity>() {
                result { code, data ->
                    if (code == RESULT_OK) Toast.makeText(
                        this@MainActivity,
                        "OneActivity页面传值${data?.getStringExtra("data")}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
    }
}