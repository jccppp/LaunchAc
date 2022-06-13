请添加 maven { url 'https://jitpack.io' }

implementation 'com.github.jccppp:launchac:0.0.6'

开始使用

     1.0:首先初始化下(如果不适用登录判断可以不初始化)
<img width="446" alt="image" src="https://user-images.githubusercontent.com/28549918/172647110-8d5354f6-283e-4f04-bbfa-47c939606826.png">

        LaunchUtil.init({
            LoginActivity.isLogin}, { ac, login ->
            LoginFragHelper.login(ac) {
                login.isLogin(it)
            }
        })
        {LoginActivity.isLogin}  //传入你判断是否登录
        { ac, login ->
           //未登录条件触发   ac为发起的acitivity的回调 可用来启动LoginActivity,login 调用 login.isLogin(boolean//传入是否登录成功)
           //看不懂请看 LoginFragHelper demo
        }
        JAVA 代码
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
        
2.0:使用如下
        launchActivity<OneActivity>()  //启动OneActivity不带参数
        
         launchActivity<OneActivity> {  //启动OneActivity带参数
                it["canShu"] = "hello1"
            }
  
接收       
<img width="395" alt="截屏2022-06-09 23 24 12" src="https://user-images.githubusercontent.com/28549918/172887718-7b02bc53-8b8a-47e5-aa47-0e6ed10f1f6c.png">

等价于
       
<img width="355" alt="image" src="https://user-images.githubusercontent.com/28549918/172887883-2d5873ae-2841-4035-aa61-9249d0362024.png">

        
3.0:使用startActivityForResult
首先
       
<img width="669" alt="image" src="https://user-images.githubusercontent.com/28549918/172888018-25d23ae6-9e9e-4bb5-b45e-5be0f6c511a5.png">
       
        IAcCallBack by AcCallBackHelper()
        
        并在onCreate时候调用 initAcCallBackHelper
        
        MainActivity 启动
        launchActivityForResult<OneActivity>(acBack = object : StartForResult {
                override fun result(code: Int, data: Intent?) {
                    if (code == RESULT_OK) Toast.makeText(
                        this@MainActivity,
                        "OneActivity页面传值${data?.getStringExtra("data")}",
                        Toast.LENGTH_LONG
                    ).show()
                }

            })
            OneActivity 像MainActivity 返回值
           findViewById<View>(R.id.view).setOnClickListener {
            setResult(RESULT_OK, Intent().also { it["data"]="哈哈啊哈" })
            finish()}
            
4.0:登录判断请查看demo
            
            
         
        
