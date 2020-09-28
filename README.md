


高仿微信朋友圈，点击查看大图，放大 缩小，可自定义

<!-- more -->


# 前言

之前写过一个  [高仿微信查看大图 放大缩小](https://allens.icu/posts/30acc017/) ,开源以后，有小伙伴提出疑问，想要加入视屏的功能，反思了一下，自己当初写这个轮子时候，为什么没有考虑到这个问题，后来经过修改，吧兼容视频放入轮子中

> 举一反三

- 除了保留当初设计的图片功能，还需要保留拓展，给以后的其他功能加入
- 能够兼容各种模式在一起
- 开发者只需要图片功能，不需要视屏，那么就不需要视屏的轮子，
- 可以自行拓展想要的布局，和可修改的拓展性




# 下载

这里将每个部分模块化，可以单独依赖，减少体积

```java
	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```

必须添加的模块，也是基础模块

```java
	dependencies {
	         implementation 'com.github.JiangHaiYang01.WeChatPhoto:Larger:0.0.5'
	}
```

最新版本 [![](https://www.jitpack.io/v/JiangHaiYang01/WeChatPhoto.svg)](https://www.jitpack.io/#JiangHaiYang01/WeChatPhoto)


提供默认的图片加载器

```java
 implementation 'com.github.JiangHaiYang01.WeChatPhoto:LargerGlide:0.0.5'
```

提供默认的视屏加载器
```java
 implementation 'com.github.JiangHaiYang01.WeChatPhoto:LargerLoadVideo:0.0.5'
```




# 使用介绍

## 加载列表类型的图片


自定义数据类型，需要继承 ``LargerBean``  并且设置这个类型的是想要改成哪种模式，当前支持的模式 图片 和 视屏模式

```java
@Parcelize
class ImageBean : LargerBean() {
    override fun getType(): LargerDataEnum {
        return LargerDataEnum.IMAGE
    }

}
```

> 需要导入 图片加载器

在需要的地方启动 下面是最基础的用法， 就能实现之前版本的需求

```java
Larger.create()
    .withImageMulti()//这里展示的是列表类型的
    .setImageLoad(GlideImageLoader(context))   //图片加载器
    .setIndex(position)//下标
    .setDuration(3000)//动画持续时间
    .setRecyclerView(recyclerView)//recyclerview
    .setData(data) //添加默认的数据源
    .start(context)
```




![](https://gitee.com/_Allens/BlogImage/raw/master/image/20200911091816.gif)


## 加载视屏

和图片一样需要自定义数据类型

```
@Parcelize
class VideoBean : LargerBean() {
    override fun getType(): LargerDataEnum {
        return LargerDataEnum.Video
    }

}
```

导入图片加载器和视屏加载器

```java
Larger.create()
    .withVideoMulti()//这里展示的是列表类型的
    .setImageLoad(GlideImageLoader(context))   //图片加载器
    .setVideoLoad(LargerVideoLoad(context))//视屏加载器
    .setIndex(position)//下标
    .setRecyclerView(recyclerView)//recyclerview
    .setData(data) //添加默认的数据源
    .start(context)//跳转

```




![](https://gitee.com/_Allens/BlogImage/raw/master/image/20200911092159.gif)


# 更多属性

```

    //是否直接向上就能够拖动，微信直接向上不可以拖动，这里默认false
    var upCanMove: Boolean = DefConfig.def_up_can_move,

    //是否自动加载下一页，默认不自动加载下一页
    var loadNextFragment :Boolean = DefConfig.def_loadNextFragment,

    //是否打印日志
    var debug: Boolean = DefConfig.def_debug,

    //单个或者多个图片
    var images: List<View>? = null,

    //列表
    var recyclerView: RecyclerView? = null,

    //使用的类型
    var largerType: LargerEnum = LargerEnum.LISTS,

    //持续时间
    var duration: Long = DefConfig.def_duration,

    //是否自动加载大图
    var automatic: Boolean = DefConfig.def_automatic,

    //最大缩放比例 （2 - f)
    var maxScale: Float = DefConfig.def_max_scale,

    //双击中间的大小 需要小于等于 max
    var mediumScale: Float = DefConfig.def_medium_scale,

    //最小缩放比例 (0.1f-0.7f)
    var minScale: Float = DefConfig.def_min_scale,

    //数据集合
    var data: List<OnLargerType>? = null,

    //当前的下标
    var position: Int = 0,

    //列表的布局
    var layoutId: Int? = null,

    //大图的ImageViewID
    var fullViewId: Int? = null,

    //获取加载框的id
    var progressId: Int? = null,

    //默认的背景颜色
    var backgroundColor: Int = DefConfig.def_back_color,

    //自定义
    var customImageLoadListener: OnCustomImageLoadListener? = null,

    //图片加载器
    var imageLoad: OnImageLoadListener? = null,

    //加载y样式
    var progressType: ImageProgressLoader.ProgressType = DefConfig.def_progress_type,

    //是否使用加载框
    var progressLoaderUse: Boolean = DefConfig.def_progress_use,

    //设置滑动方向
    var orientation: Orientation = DefConfig.orientation,

    //视屏加载器
    var videoLoad: OnVideoLoadListener? = null
```



# 最后

整个架构用fragment 的方式去处理，方便后续的拓展，视屏部分使用的是  [JiaoZiVideoPlayer](https://github.com/Jzvd/JiaoZiVideoPlayer) 作为播放器，eumm 可以替换自己喜欢的播放器去处理，



# github

[Github](https://github.com/JiangHaiYang01/WeChatPhoto)
[博客说明](https://allens.icu/posts/4a05dc12/#more)


