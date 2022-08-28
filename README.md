
**一个帮助你简单启动Activity的框架,带登录判断,带参数携带,基于kotlin语法实现**

**请添加 maven { url 'https://jitpack.io' }**
```
implementation 'com.github.jccppp:launchac:1.0.0'
```



开始使用

1、首先初始化下(如果不使用登录判断可以不初始化) 初始化后能判断是否登录判断跳转需要对于目标Activity界面,如果未登陆可跳转至登陆界面登录成功后自动跳转Activity界面

流程 A界面 -> B 界面 但B 界面需要登录才能打开,此时框架就能帮你处理 A界面 -> Login 成功-> B 界面


  <img width="446" alt="image" src="https://user-images.githubusercontent.com/28549918/172647110-8d5354f6-283e-4f04-bbfa-47c939606826.png">  

* Kotlin代码

```
 LaunchUtil.init({
            LoginActivity.isLogin
        }, { ac, login ->
            LoginFragHelper.login(ac) {
                login.isLogin(it)
            }
        })
```
*传入你判断是否登录 *  
` {LoginActivity.isLogin}` 
```

{ ac, login ->
    //未登录条件触发   ac为发起的acitivity的回调 可用来启动LoginActivity,login 调用 login.isLogin(boolean//传入是否登录成功)
   //看不懂请看 LoginFragHelper demo
  }
```
* JAVA代码

```
LaunchUtil.init(new Function0<Boolean>() {
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
```

        
2、使用如下

```
launchAc<OneActivity>()//启动OneActivity不带参数
```

```
launchAc<OneActivity>("canShu" to "hello1" , "custom" to "hello3") //启动带参数传法
```

  
接收       
<img width="395" alt="截屏2022-06-09 23 24 12" src="https://user-images.githubusercontent.com/28549918/172887718-7b02bc53-8b8a-47e5-aa47-0e6ed10f1f6c.png">

等价于
       
<img width="355" alt="image" src="https://user-images.githubusercontent.com/28549918/172887883-2d5873ae-2841-4035-aa61-9249d0362024.png">

        
3、使用startActivityForResult(轻松解决startActivityForResult,获取回调直接处理,不用再处理onActivityResult)
首先


注意⚠️先 实现 IAcCallBack 接口并委托 AcCallBackHelper


<img width="669" alt="image" src="https://user-images.githubusercontent.com/28549918/172888018-25d23ae6-9e9e-4bb5-b45e-5be0f6c511a5.png">

       
        IAcCallBack by AcCallBackHelper()
        
        并在onCreate时候调用 initAcCallBackHelper 方法
 
 ```
  launchAcForResult<OneActivity> { code, data ->
                if (code == RESULT_OK) Toast.makeText(
                    this@MainActivity,
                    "OneActivity页面传值${data?.getStringExtra("data")}",
                    Toast.LENGTH_LONG
                ).show()
            }
```

被启动的OneActivity像MainActivity传递返回值
 ```
 findViewById<View>(R.id.view).setOnClickListener {
            setBack("data" to "哈哈啊哈")
        }
  ```
            
4、登录判断请查看demo

            
            
         
        
