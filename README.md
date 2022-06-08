请添加 maven { url 'https://jitpack.io' }

implementation 'com.github.jccppp:launchac:0.0.3'

开始使用

首先初始化下


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
        
        
