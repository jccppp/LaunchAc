package com.jccppp.start.config

import android.content.Intent
import com.jccppp.start.jk.ConditionalJumpLogin
import com.jccppp.start.jk.IAcBaseCallBack
import com.jccppp.start.jk.StartForResult

class LaunchAcConfig private constructor(
    val withLogin: Boolean?,
    val result: StartForResult?,
    val condition: ConditionalJumpLogin?,
    val intent: IAcBaseCallBack<Intent>?,
    val copyGlobal: Boolean,
) {

    companion object {
        @JvmStatic
        fun create(init: (Builder.() -> Unit)? = null): LaunchAcConfig {
            val builder = Builder()
            if (init != null)
                builder.init()
            return builder.build()
        }
    }

    constructor(builder: Builder) : this(
        builder.withLogin,
        builder.result,
        builder.condition,
        builder.intent,
        builder.copyGlobal,
    )

    class Builder {
        var withLogin: Boolean? = null
            private set
        var result: ((Int,Intent?) -> Unit)? = null
            private set
        var condition: ((Boolean) -> Boolean)? = null
            private set
        var intent: ((Intent) -> Unit)? = null
            private set
        var copyGlobal: Boolean = false
            private set


        fun withLogin(withLogin: Boolean = true) = apply {
            this.withLogin = withLogin
        }

        fun result(result: (code:Int,result:Intent?) -> Unit) = apply {
            this.result = result
        }

        fun condition(condition: (isLogin:Boolean) -> Boolean) = apply {
            this.condition = condition
        }

        fun intent(intent: (intent:Intent)->Unit) = apply {
            this.intent = intent
        }

        fun copyGlobal(copyGlobal: Boolean ) = apply {
            this.copyGlobal = copyGlobal
        }


        fun build(): LaunchAcConfig {

            return LaunchAcConfig(this)
        }
    }
}