package com.jccppp.start

import androidx.fragment.app.FragmentActivity
import com.jccppp.start.config.LaunchAcConfig
import com.jccppp.start.jk.IOnLoginNext

/**
 * @Author      :zwz
 * @Date        :  2022/6/7 9:40
 * @Description : 描述
 */
object LaunchUtil {

    private var config: LaunchAcConfig? = null
    private var isLogin: (() -> Boolean)? = null
    private var startLogin: ((FragmentActivity, IOnLoginNext) -> Unit)? = null

    @JvmStatic
    fun init(
        isLogin: () -> Boolean,
        startLogin: (FragmentActivity, IOnLoginNext) -> Unit
    ) {
        init(null, isLogin, startLogin)
    }

    @JvmStatic
    fun init(
        config: LaunchAcConfig?,
        isLogin: () -> Boolean, //是否登录
        startLogin: (FragmentActivity, IOnLoginNext) -> Unit  //未登录唤起登录
    ) {
        this.config = config
        this.isLogin = isLogin
        this.startLogin = startLogin

    }

    fun getConfig() = config

    fun getIsLogin(): Boolean {
        if (isLogin == null) return true
        return isLogin!!.invoke()
    }

    fun getStartLogin(ac: FragmentActivity, next: IOnLoginNext) {
        startLogin?.invoke(ac, next)
    }
}