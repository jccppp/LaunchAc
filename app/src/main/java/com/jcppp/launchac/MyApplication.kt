package com.jcppp.launchac

import android.app.Application
import com.jccppp.start.LaunchUtil

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

       /* LaunchUtil.init({
            LoginActivity.isLogin
        }, { ac, login ->
            LoginFragHelper.login(ac) {
                login.isLogin(it)

            }
        })*/

    }
}