
---------------------------------------------------------------------
Application 继承 BaseApplication 父类

重写 String httpBaseApi() 方法， 当服务器接口只有一个域名的时候 此方法必须重写
调用父类
// 系统异常捕捉，方便本地测试查看crash log
initCrashHandler(AppConstans.ERROR_LOG_PATH);
// 初始化系统日志系统，传入log 全局tag
initLogSystem("PT");
// 初始化图片加载系统
initImageLoader(AppConstans.CACHE_PATH);

----------------------------------------------------------------------

Activity 继承 BaseMVPActivity

Fragment 继承 BaseFragment

----------------------------------------------------------------------

框架内下载库
https://github.com/lingochamp/FileDownloader