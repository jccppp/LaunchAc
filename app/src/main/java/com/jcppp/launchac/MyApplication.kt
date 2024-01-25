package com.jcppp.launchac

import android.app.Application
import android.content.Intent
import com.jccppp.start.LaunchUtil
import com.jccppp.start.config.LaunchAcConfig

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        /*  LaunchUtil.init({
              LoginActivity.isLogin
          }, { ac, login ->
              LoginFragHelper.login(ac) {
                  login.isLogin()

              }
          })*/

        LaunchUtil.init(LaunchAcConfig.create {
            copyGlobal(false).intent {
                it.putExtra("canShu", "11111")
            }
        }, {
            LoginActivity.isLogin
        }) { ac, login ->
            LoginFragHelper.login(ac) {
                if (it) //登陆成功
                    login.isLogin()
                else {
                    //登录失败
                }
            }
        }

    }
}