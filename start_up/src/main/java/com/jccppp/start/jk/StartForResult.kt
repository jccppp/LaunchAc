package com.jccppp.start.jk

import android.content.Intent

fun interface StartForResult {

    fun result(code: Int, data: Intent?)
}
