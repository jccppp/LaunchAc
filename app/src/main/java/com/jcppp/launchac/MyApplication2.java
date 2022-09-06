package com.jcppp.launchac;

import android.app.Application;
import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.jccppp.start.LaunchUtil;
import com.jccppp.start.config.LaunchAcConfig;
import com.jccppp.start.jk.IOnLoginNext;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class MyApplication2 extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LaunchUtil.init(LaunchAcConfig.Companion.create(new Function1<LaunchAcConfig.Builder, Unit>() {
            @Override
            public Unit invoke(LaunchAcConfig.Builder builder) {
                builder.copyGlobal(true).intent(new Function1<Intent, Unit>() {
                    @Override
                    public Unit invoke(Intent intent) {
                        intent.putExtra("canShu","11111");
                        return null;
                    }
                }).result(new Function2<Integer, Intent, Unit>() {
                    @Override
                    public Unit invoke(Integer integer, Intent intent) {
                        return null;
                    }
                });

                return null;
            }
        }), new Function0<Boolean>() {
            @Override
            public Boolean invoke() {
                return LoginActivity.Companion.isLogin();
            }
        }, new Function2<FragmentActivity, IOnLoginNext, Unit>() {
            @Override
            public Unit invoke(FragmentActivity fragmentActivity, IOnLoginNext iOnLoginNext) {
                LoginFragHelper.Companion.login(fragmentActivity, new Function1<Boolean, Unit>() {
                    @Override
                    public Unit invoke(Boolean aBoolean) {
                        iOnLoginNext.isLogin(aBoolean);
                        return null;
                    }
                });
                return null;
            }
        });
    }
}
