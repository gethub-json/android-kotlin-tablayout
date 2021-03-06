# android-kotlin-tablayout
# MVVM+LiveData初稿已经完成，等待优化，目前可以用来学习参考，有问题可以加我微信1140965679
一个基于`kotlin`的`demo`，主要是两个功能，一个是底部导航，使用了`BottomNavigationView`，首页一个`tablayout`+`viewpager`+
`fragment`，一个小型架构，使用mvvm实现与后台交互，可以自己扩展。
```markdown
mvvm+dagger2+Livedata,BottomNavigationView实现底部导航切换，viewpager+tablayout实现首页头部导航，架构是目前最主流的架构
mvvm

```
 [简书上可以看到图](https://www.jianshu.com/p/bec10a8ed752)
![testAnim.gif](https://upload-images.jianshu.io/upload_images/4752376-56518b90e7abd559.gif?imageMogr2/auto-orient/strip)
`mvvm架构模式`


    Model：实体模型、数据的获取、存储等等

    View：Activity、fragment、view、adapter、xml等等

    ViewModel：负责完成View与Model间的交互和业务逻辑，基于DataBinding改变UI

### 1:使用 `kotlin`，代码更简单
### 2:使用 `BottomNavigationView`，底部导航简单实现
### 3:2020年4月2号更新，底部导航栏动画
```
 <com.google.android.material.bottomnavigation.BottomNavigationView
            android:id="@+id/bnv_menu"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            app:itemBackground="@color/color_main"
            app:itemIconTint="@drawable/main_bottom"
            app:itemTextColor="@drawable/main_bottom"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintVertical_bias="1.0"
            tools:ignore="MissingConstraints"
            tools:layout_editor_absoluteX="0dp"
            app:labelVisibilityMode="labeled"
            app:menu="@menu/menu_bottom_navigation_view"/>
```
菜单menu_bottom_navigation_view布局代码
```
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <item
        android:id="@+id/questionFragment"
        android:enabled="true"
        android:icon="@drawable/main_01"
        android:title="首页"
        app:showAsAction="ifRoom" />
    <item
        android:id="@+id/learnFragment"
        android:enabled="true"
        android:icon="@drawable/main_02"
        android:title="课堂"
        app:showAsAction="ifRoom" />
    <item
        android:id="@+id/lookFragment"
        android:enabled="true"
        android:icon="@drawable/main_03"
        android:title="头条"
        android:tooltipText="14sp"
        app:showAsAction="ifRoom" />
    <item
        android:id="@+id/chatFragment"
        android:enabled="true"
        android:icon="@drawable/main_05"
        android:title="消息"
        app:showAsAction="ifRoom" />
    <item
        android:id="@+id/mineFragment"
        android:enabled="true"
        android:icon="@drawable/main_04"
        android:title="我的"
        app:showAsAction="ifRoom" />

</menu>
```
这里代码直接和Fragment做了关联设置[BottomNavigationView]以与[Navcontroller]一起使用。这将调用
[android.view.menu item.onnavdestinationselected]选择菜单项时。当目标更改时，NavigationView
中的选定项将自动更新,nav_host_fragment是fragment的id
```
 navController = Navigation.findNavController(this, R.id.nav_host_fragment)
 bnv_menu.setupWithNavController(navController)
```
### 3:使用 `lifeCycle`框架管理activity和fragement生命后期
```
  private fun initLocationListener() {
        myLocationListener = LifeCycleListener(this, lifecycle,null)
        lifecycle.addObserver(myLocationListener)

    }
```
### 其中LifeCycleListener是自己写的，里面主要是activity/fragment的生命周期函数代码如下：
```
class LifeCycleListener(
    context: Context?, lifecycle:Lifecycle,
    callback: Callback?
) :LifecycleObserver{

    var  enabled :Boolean=false
    var  context:Context= context!!
    var  lifecycle:Lifecycle=lifecycle
    var  callback: Callback? =callback


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create(){
        callback?.refresh("ON_CREATE")
        Log.d("addObserver","ON_CREATE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
            callback?.refresh("ON_START")
        Log.d("addObserver","ON_START")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume(){
        callback?.refresh("ON_RESUME")
        Log.d("addObserver","ON_RESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause(){
        callback?.refresh("ON_PAUSE")
        Log.d("addObserver","ON_PAUSE")
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        callback?.refresh("ON_STOP")
        Log.d("addObserver","ON_STOP")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destory() {
        callback?.refresh("ON_DESTROY")
        Log.d("addObserver","ON_DESTROY")
    }


}
```

4:使用 `dagger2`，解耦
这里只有一个javabean，就是用户对象，userbean，使用dagger2
第一步 这里userbean是java写的，不是kotlin，使用MainModule添加的`@module`注解
第二步 在`provUserBean`方法上添加的`@Provides`注解，然后在UserBeanComPonent类里面添加`@Component`,重新Rebuild，获取
`DaggerUserBeanComPonent`对象,使用DaggerUserBeanComPonent对象添加依赖
```
DaggerUserBeanComPonent.create().inject(this)
//或者 DaggerUserBeanComPonent.builder().build().inject(this)
```
第四步 
```markdown
 @Inject
    lateinit var userBean: UserBean
    //就可以使用userbean了
```
5:使用 `dataBinding`框架，统一规范处理view
  使用databinding把moudle与view绑定
  ```markdown
 homeNewFragmentBinding.userBean=userBean.apply {
            this.id=id
            this.nickName=nickName
        }
```
在要使用userbean的xml里面声明
```markdown
<data>
        <import type="android.view.View"/>
        <variable
            name="userBean"
            type="com.fenboshi.fboshi.bean.UserBean" />
    </data>
```
使用
```markdown
 <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="15dp"
        android:text="@{userBean.id}"/>

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="15dp"
        android:text="@{userBean.nickName}"/>
```
### 6:使用 `greenDao` 数据库框架，保存数据到本地会用到


### 7:LiveData管理数据
HomeNewViewModel继承ViewModel,使用MutableLiveData来管理数据，以便数据更新是通知片段代码如下：
```markdown
class HomeNewViewModel :ViewModel(){

    private  var articelBean: MutableLiveData<List<ArticelBean>>? = null
    fun getListData() :LiveData<List<ArticelBean>>{

        var userRepository=ArticelRepository()
        return  userRepository!!.getArticel()
    }

    fun setArtice(articelBeanList: List<ArticelBean>){
        articelBean?.value =articelBeanList
    }


}
```
### FragmentNewFragment使用ViewModel说明
```markdown
//HomeNewFragment通过ViewModelProviders获取viewModel实列
var homeNewViewModel= ViewModelProviders.of(this).get<HomeNewViewModel>()
//观察数据改变，更新UI
var nameObserver =object:androidx.lifecycle.Observer<List<ArticelBean>>{
            override fun onChanged(articelBean: List<ArticelBean>?) {
               updataUI(articelBean!!)
            }
        }
//注册一个观察者，获取数据请查看ViewModel的getListData（）方法    
homeNewViewModel.getListData().observe(this,nameObserver)
//gitList()方法使用的是Repository管理的用户异步请求，这里多封装一层是为了请求服务器这里更灵活，易维护
 fun getListData() :LiveData<List<ArticelBean>>{

        var userRepository=ArticelRepository()
        return  userRepository!!.getArticel()
}

```
请求服务器模块独立,mediatorLiveData的value有更新的时候，UI就更新了，这里请求服务器使用了一个第三方的后端服务leancloud，
如果请求自己的数据库，按需求更改。
```markdown
fun   getArticel(): LiveData<List<ArticelBean>> {
        var mediatorLiveData= MediatorLiveData<List<ArticelBean>>()
        //获取数据
        val query = AVQuery<AVObject>("Article")
        query.findInBackground()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<AVObject>> {
                override fun onNext(listArtice: List<ArticelBean>) {
                    Log.d("UserBean","event"+"onNext" )
             
                    mediatorLiveData.value=articelBeanList

                }
                override fun onComplete() {
                    Log.d("UserBean","event"+"onComplete" )
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d("UserBean","onSubscribe"+ d)
                }

                override fun onError(e: Throwable) {
                    Log.d("UserBean","onError"+e )
                }


            })
        return  mediatorLiveData
        }
```
###  8:新添加功能，lottie动画，实现kotlin与lottie的完美结合，优化文章列表UI

(1):lottie再xml中使用
```markdown
//lottie_loop 是否循环播放动画
//data_1.json 动画文件

 <com.airbnb.lottie.LottieAnimationView
            android:id="@+id/animation_view"
            android:layout_width="35dp"
            android:layout_height="35dp"
            app:lottie_fileName="data_1.json"
            app:lottie_loop="false"
            app:lottie_autoPlay="true"
            android:layout_alignParentRight="true"/>
```
     
[有问题在这里提问](https://github.com/gethub-json/android-kotlin-tablayout)
[源代码地址](https://github.com/gethub-json/android-kotlin-tablayout)
![image](https://github.com/gethub-json/android-kotlin-tablayout/blob/master/app/demo.jpg)
####新添加功能，搭建了mvvm架构，包括异步请求，dagger2管理对象 `mvvm`设计，目前代码可以实现`dagger2`管理对象，moudle提供数据，viewmoudle链接moudle和view，以`databinding`管理view，
####显示数据，mvvm架构持续迭代中，异步请求完数据就是这个样子的


![image](https://github.com/gethub-json/android-kotlin-tablayout/blob/master/app/demo2.jpg)