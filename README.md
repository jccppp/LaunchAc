
**一个帮助你简单启动Activity的框架,带登录判断,带参数携带**

**原理基于Kotlin扩展函数**

#### 请添加仓库地址
```
   maven { url 'https://jitpack.io' }
```

```
   implementation 'com.github.jccppp:launchac:1.2.0'
```


####开始使用

**1、** 在Fragment or Activity 中启动一个新的Activity

```    
   launchAc<OneActivity>()
```
**等价于**

```
  val intent = Intent(this,OneActivity::class.java)
            
    startActivity(intent)
```

<br />
跳转新Activity并携带参数  

<br />

``` 
   launchAc<OneActivity>("canShu" to "hello1", "custom" to 1)
``` 
**等价于**
``` 
  val intent = Intent(this,OneActivity::class.java)
            intent.putExtra("canShu","hello1")
            intent.putExtra("custom",1)
    startActivity(intent)
``` 
在OneActivity接收参数可使用
``` 
private var canShu by argumentNullable<String>()   注: 变量名称必须与传参时的"canShu"一致
``` 
或者指定参数名

``` 
 private var aaaa by argument(1,"custom")   注:此时参数名就可以为aaaa或者随意,因为已指定key为"custom"
``` 

也可直接使用  
``` 
val custom = intent.getStringExtra("custom")
``` 

**2、** 在Fragment or Activity 中启动一个新的Activity并且带回调。ps：就是以前的 onActivityResult


注意⚠️先 实现 IAcCallBack 接口并委托 AcCallBackHelper


<img width="669" alt="image" src="https://user-images.githubusercontent.com/28549918/172888018-25d23ae6-9e9e-4bb5-b45e-5be0f6c511a5.png">

       
IAcCallBack by AcCallBackHelper()
并在onCreate时候调用 initAcCallBackHelper 方法
 

``` 

  launchAc<OneActivity>() {
                result { code, data ->
                    if (code == RESULT_OK) Toast.makeText(
                        this@MainActivity,
                        "OneActivity页面传值${data?.getStringExtra("data")}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
``` 
**等价于**
``` 
  val intent = Intent(this,OneActivity::class.java)
   startActivityForResult(intent,requestCode)

   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode){

        }
    }

``` 

####进阶使用 使用场景

可使用判断是否符合已登录才可跳转的Activity，如我的用户信息界面等。
假设用户未登录并且想打开UserInfoActivity界面
点击跳转UserInfoActivity(未登录)->跳转登录界面->登录成功->自动跳转至UserInfoActivity界面


**1、首先初始化下(建议在MyApplication的onCreate初始化,本框架不会获取任何隐私相关,只是单纯初始化,无合规风险,在此初始化可规避内存不足重建导致初始化失败问题)**


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
参数1、传入你判断是否登录 
```
   {
     LoginActivity.isLogin  //boolean类型
   } 
```
```
{ ac, login ->
    //未登录条件触发   ac为发起的Acitivity的 可用来启动LoginActivity,login 调用 login.isLogin(boolean//传入是否登录成功)
   //看不懂请看 LoginFragHelper demo
  }
```
