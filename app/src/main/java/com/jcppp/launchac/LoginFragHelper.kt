package com.jcppp.launchac

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.jccppp.start.AcCallBackHelper
import com.jccppp.start.jk.IAcCallBack
import com.jccppp.start.jk.StartForResult
import com.jccppp.start.launchActivity
import com.jccppp.start.launchActivityForResult

class LoginFragHelper : Fragment(), IAcCallBack by AcCallBackHelper() {

    companion object {

        private val TAG = "LoginFragHelper"

        fun login(activity: FragmentActivity, call: ((Boolean) -> Unit)) {
            val supportFragmentManager = activity.supportFragmentManager

            val findFragment = supportFragmentManager.findFragmentByTag(TAG)

            if (findFragment is LoginFragHelper) {
                findFragment.call = call
                findFragment.goLogo()
            } else {
                supportFragmentManager.beginTransaction()
                    .add(LoginFragHelper().also { it.call = call }, TAG)
                    .commitAllowingStateLoss()
            }
        }

    }

    var call: ((Boolean) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAcCallBackHelper()
        goLogo()

    }

    fun goLogo() {
        launchActivityForResult<LoginActivity>(object : StartForResult {
            override fun result(code: Int, data: Intent?) {
                call?.invoke(code == AppCompatActivity.RESULT_OK)
                parentFragmentManager.beginTransaction().remove(this@LoginFragHelper)
                    .commitAllowingStateLoss()
            }
        })
    }

}