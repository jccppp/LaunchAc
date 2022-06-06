package com.jccppp.start.jk

import android.content.Intent

interface StartForResult {

    fun result(code: Int, data: Intent?)
}
