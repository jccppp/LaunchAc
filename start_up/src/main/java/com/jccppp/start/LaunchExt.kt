package com.jccppp.start

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jccppp.start.config.LaunchAcConfig
import com.jccppp.start.jk.IAcBaseCallBack
import com.jccppp.start.jk.IAcCallBack
import com.jccppp.start.jk.IOnLoginNext
import com.jccppp.start.jk.StartForResult


inline fun <reified AC : Activity> Activity.launchAc(
    vararg parameter: Pair<String, Any?>,
    noinline builder: (LaunchAcConfig.Builder.() -> Unit)? = null
) {

    val javaClass: Class<*> = AC::class.java

    var create: LaunchAcConfig? = null

    if (builder != null) {
        create = LaunchAcConfig.create(builder)
    }

    insideStartUpActivityWithLoginForResult(javaClass, this, create, parameter = parameter)
}


inline fun <reified AC : Activity> Fragment.launchAc(
    vararg parameter: Pair<String, Any?>,
    noinline builder: (LaunchAcConfig.Builder.() -> Unit)? = null
) {

    val javaClass: Class<*> = AC::class.java

    var create: LaunchAcConfig? = null

    if (builder != null) {
        create = LaunchAcConfig.create(builder)
    }

    insideStartUpActivityWithLoginForResult(javaClass, this, create, parameter = parameter)

}

inline fun <reified AC : Activity> Context.launchAc(
    vararg parameter: Pair<String, Any?>,
    noinline builder: (LaunchAcConfig.Builder.() -> Unit)? = null
) {

    val javaClass: Class<*> = AC::class.java

    var create: LaunchAcConfig? = null

    if (builder != null) {
        create = LaunchAcConfig.create(builder)
    }

    insideStartUpActivityWithLoginForResult(javaClass, this, create, parameter = parameter)

}



//外部就不要调佣这个了,除非没有符合条件的
fun insideStartUpActivityWithLoginForResult(
    javaClass: Class<*>,
    any: Any,
    mConfig: LaunchAcConfig?,
    vararg parameter: Pair<String, Any?>,
) {

    val globalConfig = LaunchUtil.getConfig()

    val copy = (mConfig?.copyGlobal ?: globalConfig?.copyGlobal) == true

    val withLogin = mConfig?.withLogin ?: if (copy) globalConfig?.withLogin else null

    val isLogin = LaunchUtil.getIsLogin()

    val stop = (mConfig?.condition ?: if (copy) globalConfig?.condition else null)?.jump(
        isLogin
    ) == false

    val intent = mConfig?.intent ?: if (copy) globalConfig?.intent else null

    val result = mConfig?.result ?: if (copy) globalConfig?.result else null

    if (stop) {
        return
    }

    if (withLogin == true) {
        if (isLogin) {
            _jump(any, javaClass, intent, result, parameter = parameter)
        } else {
            val ac: Context? =
                if (any is Activity) {
                    any
                } else if (any is Fragment) {
                    any.requireActivity()
                } else if (any is IAcCallBack) {
                    any.getAcCallContext()!!
                } else if (any is Context) {
                    any
                } else null

            val onNext = IOnLoginNext {
                _jump(
                    any,
                    javaClass,
                    intent,
                    result,
                    parameter = parameter
                )
            }

            if (ac != null) {
                LaunchUtil.getStartLogin(ac, onNext)
            } else {
                onNext.isLogin()
            }

        }

    } else {
        _jump(any, javaClass, intent, result, parameter = parameter)
    }


}

private fun _jump(
    any: Any,
    javaClass: Class<*>,
    intent: IAcBaseCallBack<Intent>?,
    result: StartForResult?,
    vararg parameter: Pair<String, Any?>
) {
    if (any is IAcCallBack) {
        any._jump(javaClass, intent, result, parameter = parameter)
    } else if (any is Fragment) {
        any._jump(javaClass, intent, parameter = parameter)
    } else if (any is Context) {
        any._jump(javaClass, intent, parameter = parameter)
    }

}

private fun Fragment._jump(
    javaClass: Class<*>,
    intent: IAcBaseCallBack<Intent>?,
    vararg parameter: Pair<String, Any?>,
) {

    requireActivity()._jump(javaClass, intent, parameter = parameter)

}

private fun Context._jump(
    javaClass: Class<*>,
    intent: IAcBaseCallBack<Intent>?,
    vararg parameter: Pair<String, Any?>,
) {
    startActivity(Intent(this, javaClass).also { i ->
        intent?.invoke(i)
        i.add(*parameter)
        if (this is Application) {
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    })
}


private fun IAcCallBack._jump(
    javaClass: Class<*>,
    intent: IAcBaseCallBack<Intent>?,
    result: StartForResult?,
    vararg parameter: Pair<String, Any?>,
) {

    if (result != null) {
        getResultDeque().offerFirst(result)
        getAcCallContext()?.let {
            getResultLauncher().launch(Intent(it, javaClass).also { i ->
                intent?.invoke(i)
                i.add(*parameter)
            })
        }
    } else {
        getAcCallContext()?.let {
            it._jump(javaClass, intent, parameter = parameter)
        }
    }
}

//设置bundle
fun <T : Fragment> T.setBundle(
    vararg parameter: Pair<String, Any?>,
    control: ((Bundle) -> Unit)? = null
): T = apply {
    arguments = Bundle().apply {
        parameter.forEach {
            this[it.first] = it.second
        }
        control?.invoke(this)
    }
}


private fun Intent.add(vararg parameter: Pair<String, Any?>) = apply {
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

fun Context?.setBack(
    vararg parameter: Pair<String, Any?>,
    resultCode: Int = Activity.RESULT_OK,
    finish: Boolean = true,
) {
    (this as? Activity)?.setBack(*parameter, resultCode = resultCode, finish = finish)
}

