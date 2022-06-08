package com.jccppp.start

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.jccppp.start.jk.ConditionalJumpLogin
import com.jccppp.start.jk.IAcCallBack
import com.jccppp.start.jk.IOnLoginNext
import com.jccppp.start.jk.StartForResult


/**
 *   如果未登录需要登录, next it =true 已登录的后续操作  it =false  未登录的后续操作
 * */
inline fun <reified AC : FragmentActivity> FragmentActivity.launchActivityWithLogin(
    noinline next: ((Boolean) -> Unit)? = null,  //后续操作
    noinline intent: ((Intent) -> Unit)? = null,
) {

    val jump: ConditionalJumpLogin? = if (next == null) null else object : ConditionalJumpLogin {
        override fun jump(isLogin: Boolean): Boolean {
            next.invoke(isLogin)
            return true
        }
    }

    launchActivityWithLoginForBack<AC>(jump, intent)
}

/**
 *   如果未登录需要登录, next it =true 已登录的后续操作  it =false  未登录的后续操作  多了ForResult
 * */

inline fun <reified AC : FragmentActivity> IAcCallBack.launchActivityWithLoginForResult(
    acBack: StartForResult,
    noinline next: ((Boolean) -> Unit)? = null,  //后续操作
    noinline intent: ((Intent) -> Unit)? = null,
) {

    val jump: ConditionalJumpLogin? = if (next == null) null else object : ConditionalJumpLogin {
        override fun jump(isLogin: Boolean): Boolean {
            next.invoke(isLogin)
            return true
        }
    }
    getResultDeque().offerFirst(acBack)

    getAcCallContext()?.insideStartUpActivityWithLoginForResult<AC>(
        this, jump, intent
    )
}


/**
 *   如果未登录需要登录, jump ConditionalJumpLogin return false 已登录也不做跳转
 * */
inline fun <reified AC : FragmentActivity> FragmentActivity.launchActivityWithLoginForBack(
    jump: ConditionalJumpLogin? = null,  //后续操作,true 为登录成功，false为登录失败
    noinline intent: ((Intent) -> Unit)? = null,
) {

    insideStartUpActivityWithLoginForResult<AC>(null, jump, intent)

}

inline fun <reified AC : FragmentActivity> Fragment.launchActivityWithLoginForBack(
    jump: ConditionalJumpLogin? = null,  //后续操作,true 为登录成功，false为登录失败
    noinline intent: ((Intent) -> Unit)? = null,
) {

    activity?.launchActivityWithLoginForBack<AC>(jump, intent)

}


//外部就不要调佣这个了,除非没有符合条件的
inline fun <reified AC : FragmentActivity> FragmentActivity.insideStartUpActivityWithLoginForResult(
    acb: IAcCallBack? = null,
    jump: ConditionalJumpLogin? = null,  //后续操作,true 为登录成功，false为登录失败
    noinline intent: ((Intent) -> Unit)? = null,
) {

    if (LaunchUtil.getIsLogin()) {
        if (jump?.jump(true) != false) {
            if (acb == null) {
                launchActivity<AC>(intent)
            } else {
                acb.getAcCallContext()?.launchActivity<AC>(intent = intent)
            }
        }

    } else {
        LaunchUtil.getStartLogin(this, object : IOnLoginNext {
            override fun isLogin(login: Boolean) {
                if (login) {
                    if (jump?.jump(login) != false) {
                        if (acb == null) {
                            launchActivity<AC>(intent)
                        } else {
                            acb.getAcCallContext()?.launchActivity<AC>(intent = intent)
                        }
                    }
                }
            }
        })
    }
}


inline fun <reified AC : FragmentActivity> IAcCallBack.launchActivityWithLoginForResultBack(
    acBack: StartForResult,
    jump: ConditionalJumpLogin? = null,  //后续操作,true 为登录成功，false为登录失败
    noinline intent: ((Intent) -> Unit)? = null,
) {
    getResultDeque().offerFirst(acBack)
    getAcCallContext()?.insideStartUpActivityWithLoginForResult<AC>(
        this, jump = jump, intent = intent
    )
}

inline fun <reified AC : FragmentActivity> Fragment.launchActivityWithLogin(
    noinline next: ((Boolean) -> Unit)? = null,  //后续操作,true 为登录成功，false为登录失败
    noinline intent: ((Intent) -> Unit)? = null,
) {
    activity?.launchActivityWithLogin<AC>(next, intent)
}


inline fun <reified AC : FragmentActivity> FragmentActivity.launchActivity(
    noinline intent: ((Intent) -> Unit)? = null
) {
    startActivity(Intent(this, AC::class.java).also {
        intent?.invoke(it)
    })
}



inline fun <reified AC : FragmentActivity> IAcCallBack.launchActivityForResult(
    acBack: StartForResult,
    noinline intent: ((Intent) -> Unit)? = null
) {
    getResultDeque().offerFirst(acBack)
    getAcCallContext()?.let {
        it.launchActivity<AC>(intent = intent)
    }

}

inline fun <reified AC : FragmentActivity> Fragment.launchActivity(
    noinline intent: ((Intent) -> Unit)? = null
) {
    activity?.launchActivity<AC>(intent)
}




