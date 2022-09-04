package com.jccppp.start.jk;

import android.content.Intent;

import org.jetbrains.annotations.Nullable;

public interface StartForResult {

    void result(int code, @Nullable Intent data);
}
