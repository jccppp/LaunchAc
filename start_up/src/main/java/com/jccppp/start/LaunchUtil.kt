package com.jccppp.start

import androidx.fragment.app.FragmentActivity
import com.jccppp.start.jk.IOnLoginNext

/**
 * @Author      :zwz
 * @Date        :  2022/6/7 9:40
 * @Description : 描述
 */
object LaunchUtil {

    private var isLogin: (() -> Boolean)? = null
    private var startLogin: ((FragmentActivity, IOnLoginNext) -> Unit)? = null

    @JvmStatic
    fun init(isLogin: () -> Boolean, startLogin: (FragmentActivity, IOnLoginNext) -> Unit) {
        this.isLogin = isLogin
        this.startLogin = startLogin

    }

    fun getIsLogin(): Boolean {
        if (isLogin == null) RuntimeException("请调用 LaunchUtil.isLogin ")
        return isLogin!!.invoke()
    }

    fun getStartLogin(ac: FragmentActivity, next: IOnLoginNext) {
        if (startLogin == null) RuntimeException("请调用 LaunchUtil.isLogin ")
        startLogin!!.invoke(ac, next)
    }
}