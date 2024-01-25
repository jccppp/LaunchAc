package com.jccppp.start.jk

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.LinkedBlockingDeque

/**
 * @Author      :zwz
 * @Date        :  2021/11/25 19:21
 * @Description : 描述
 */
interface IAcCallBack {

    fun getResultDeque(): LinkedBlockingDeque<StartForResult>

    fun getResultLauncher(): ActivityResultLauncher<Intent>

    fun <Host : LifecycleOwner> Host.initAcCallBackHelper()

    fun getAcCallContext(): Context?

}