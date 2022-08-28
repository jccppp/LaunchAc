package com.jccppp.start

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.jccppp.start.jk.ConditionalJumpLogin
import com.jccppp.start.jk.IAcCallBack
import com.jccppp.start.jk.IOnLoginNext
import com.jccppp.start.jk.StartForResult


/**
 *   如果未登录需要登录, next it = true 已登录的后续操作  it = false  未登录的后续操作
 * */
inline fun <reified AC : FragmentActivity> FragmentActivity.launchAcWithLogin(
    vararg parameter: Pair<String, Any?>,
    noinline next: ((Boolean) -> Unit)? = null,  //后续操作
) {

    val jump: ConditionalJumpLogin? = if (next == null) null else ConditionalJumpLogin { isLogin ->
        next.invoke(isLogin)
        true
    }

    launchAcWithLoginAndJump<AC>(parameter = parameter, jump)
}

/**
 *   如果未登录需要登录, next it =true 已登录的后续操作  it = false  未登录的后续操作  多了ForResult
 * */

inline fun <reified AC : FragmentActivity> IAcCallBack.launchAcWithLoginForResult(
    vararg parameter: Pair<String, Any?>,
    noinline next: ((Boolean) -> Unit)? = null,  //后续操作
    acBack: StartForResult,
) {

    val jump: ConditionalJumpLogin? = if (next == null) null else ConditionalJumpLogin { isLogin ->
        next.invoke(isLogin)
        true
    }
    getResultDeque().offerFirst(acBack)

    getAcCallContext()?.insideStartUpActivityWithLoginForResult<AC>(
        this, jump, parameter = parameter
    )
}


/**
 *   如果未登录需要登录, jump ConditionalJumpLogin return true 跳转目标Ac return false 已登录也不做跳转
 *   带登录条件和其他条件的跳转  两者皆满足才会跳转 ,例如登录之后 用户不是VIP,但目标Ac为VIP才可跳转
 * */
inline fun <reified AC : FragmentActivity> FragmentActivity.launchAcWithLoginAndJump(
    vararg parameter: Pair<String, Any?>,
    jump: ConditionalJumpLogin?,  //后续操作,true 为登录成功，false为登录失败
) {

    insideStartUpActivityWithLoginForResult<AC>(null, jump, parameter = parameter)

}

inline fun <reified AC : FragmentActivity> Fragment.launchAcWithLoginAndJump(
    vararg parameter: Pair<String, Any?>,
    jump: ConditionalJumpLogin? //后续操作,true 为登录成功，false为登录失败
) {
    activity?.launchAcWithLoginAndJump<AC>(parameter = parameter, jump)
}

inline fun <reified AC : FragmentActivity> Context.launchAcWithLoginAndJump(
    vararg parameter: Pair<String, Any?>,
    jump: ConditionalJumpLogin? //后续操作,true 为登录成功，false为登录失败
) {
    (this as? FragmentActivity)?.launchAcWithLoginAndJump<AC>(parameter = parameter, jump)
}


//外部就不要调佣这个了,除非没有符合条件的
inline fun <reified AC : FragmentActivity> FragmentActivity.insideStartUpActivityWithLoginForResult(
    acb: IAcCallBack? = null,
    jump: ConditionalJumpLogin? = null,  //后续操作,true 为登录成功，false为登录失败
    vararg parameter: Pair<String, Any?>,
) {

    if (LaunchUtil.getIsLogin()) {
        if (jump?.jump(true) != false) {
            if (acb == null) {
                launchAc<AC>(parameter = parameter)
            } else {
                acb._launchAc<AC>(parameter = parameter)
            }
        }

    } else {
        LaunchUtil.getStartLogin(this, object : IOnLoginNext {
            override fun isLogin(login: Boolean) {
                if (login) {
                    if (jump?.jump(login) != false) {
                        if (acb == null) {
                            launchAc<AC>(parameter = parameter)
                        } else {
                            acb._launchAc<AC>(parameter = parameter)
                        }
                    }
                }
            }
        })
    }
}


inline fun <reified AC : FragmentActivity> IAcCallBack.launchAcWithLoginForResultAndJump(
    vararg parameter: Pair<String, Any?>,
    acBack: StartForResult,
    jump: ConditionalJumpLogin?  //后续操作,true 为登录成功，false为登录失败
) {
    getResultDeque().offerFirst(acBack)
    getAcCallContext()?.insideStartUpActivityWithLoginForResult<AC>(
        this, jump = jump, parameter = parameter
    )
}

inline fun <reified AC : FragmentActivity> Fragment.launchAcWithLogin(
    vararg parameter: Pair<String, Any?>,
    noinline next: ((Boolean) -> Unit)? = null,  //后续操作,true 为登录成功，false为登录失败
) {
    activity?.launchAcWithLogin<AC>(parameter = parameter, next)
}

inline fun <reified AC : FragmentActivity> Context?.launchAcWithLogin(
    vararg parameter: Pair<String, Any?>,
    noinline next: ((Boolean) -> Unit)? = null,  //后续操作,true 为登录成功，false为登录失败
) {
    (this as? FragmentActivity)?.launchAcWithLogin<AC>(parameter = parameter, next)
}


inline fun <reified AC : FragmentActivity> FragmentActivity.launchAc(
    vararg parameter: Pair<String, Any?>,
) {
    startActivity(Intent(this, AC::class.java).also {
        it.add(*parameter)
    })
}


inline fun <reified AC : FragmentActivity> IAcCallBack._launchAc(
    vararg parameter: Pair<String, Any?>,
) {
    getAcCallContext()?.let { it ->
        getResultLauncher().launch(Intent(it, AC::class.java).also {
            it.add(*parameter)
        })
    }
}


inline fun <reified AC : FragmentActivity> IAcCallBack.launchAcForResult(
    vararg parameter: Pair<String, Any?>,
    acBack: StartForResult
) {
    getResultDeque().offerFirst(acBack)
    _launchAc<AC>(parameter = parameter)

}

inline fun <reified AC : FragmentActivity> Fragment.launchAc(
    vararg parameter: Pair<String, Any?>
) {
    activity?.launchAc<AC>(parameter = parameter)
}

inline fun <reified AC : FragmentActivity> Context?.launchAc(
    vararg parameter: Pair<String, Any?>
) {
    (this as? FragmentActivity)?.launchAc<AC>(parameter = parameter)
}

fun Activity.setBack(
    vararg parameter: Pair<String, Any?>,
    resultCode: Int = Activity.RESULT_OK,
    finish: Boolean = true,
) {
    setResult(resultCode, Intent().also {
        it.add(*parameter)
    })

    if (finish) finish()
}

fun Intent.add(vararg parameter: Pair<String, Any?>) = apply {
    parameter.forEach {
        this[it.first] = it.second
    }
}


fun Fragment.setBack(
    vararg parameter: Pair<String, Any?>,
    resultCode: Int = Activity.RESULT_OK,
    finish: Boolean = true,
) {
    requireActivity().setBack(*parameter, resultCode = resultCode, finish = finish)
}

fun Context?.setBack(
    vararg parameter: Pair<String, Any?>,
    resultCode: Int = Activity.RESULT_OK,
    finish: Boolean = true,
) {
    (this as? FragmentActivity)?.setBack(*parameter, resultCode = resultCode, finish = finish)
}








