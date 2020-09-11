


> 建议在 [个人博客](https://allens.icu/posts/4a05dc12/#more) 中查看,阅读体验更佳

高仿微信朋友圈，点击查看大图，放大 缩小，可自定义

<!-- more -->


# 前言

之前写过一个  [高仿微信查看大图 放大缩小](https://allens.icu/posts/30acc017/) ,开源以后，有小伙伴提出疑问，想要加入视屏的功能，反思了一下，自己当初写这个轮子时候，为什么没有考虑到这个问题，后来经过修改，吧兼容视频放入轮子中

> 举一反三

- 除了保留当初设计的图片功能，还需要保留拓展，给以后的其他功能加入
- 能够兼容各种模式在一起
- 开发者只需要图片功能，不需要视屏，那么就不需要视屏的轮子，
- 可以自行拓展想要的布局，和可修改的拓展性


# apk 下载体验



[apk 点击下载](https://gitee.com/_Allens/BlogImage/raw/master/image/20200911111134.apk)


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
	        implementation 'com.github.JiangHaiYang01.WeChatPhoto:LargerGlide:0.0.4'
	}
```

最新版本 [![](https://www.jitpack.io/v/JiangHaiYang01/WeChatPhoto.svg)](https://www.jitpack.io/#JiangHaiYang01/WeChatPhoto)


提供默认的图片加载器

```java
 implementation 'com.github.JiangHaiYang01.WeChatPhoto:LargerGlide:0.0.4'
```

提供默认的视屏加载器
```java
implementation 'com.github.JiangHaiYang01.WeChatPhoto:LargerLoadVideo:0.0.4'
```

提供默认的进度加载样式

```java
implementation 'com.github.JiangHaiYang01.WeChatPhoto:LargerProgress:0.0.4'
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
    .withListType()//这里展示的是列表类型的
    .setImageLoad(GlideImageLoader(context))   //图片加载器
    .setIndex(position)//下标
    .setRecyclerView(recyclerView)//recyclerview
    .setData(data) //添加默认的数据源
    .start(context)//跳转

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
    .withListType()//这里展示的是列表类型的
    .setImageLoad(GlideImageLoader(context))   //图片加载器
    .setVideoLoad(LargerVideoLoad(context))//视屏加载器
    .setIndex(position)//下标
    .setRecyclerView(recyclerView)//recyclerview
    .setData(data) //添加默认的数据源
    .start(context)//跳转

```




![](https://gitee.com/_Allens/BlogImage/raw/master/image/20200911092159.gif)


## 处理加载进度



添加默认的进度加载样式

```java
implementation 'com.github.JiangHaiYang01.WeChatPhoto:LargerProgress:0.0.4'
```


```java
Larger.create()
    .withListType()//这里展示的是列表类型的
    .setImageLoad(GlideImageLoader(context))   //图片加载器
    .setProgress(ProgressLoader(ProgressLoader.ProgressType.FULL)) //添加进度显示
    .setIndex(position)//下标
    .setRecyclerView(recyclerView)//recyclerview
    .setData(data) //添加默认的数据源
    .start(context)//跳转

```

这里提供了两种模式的加载进度样式

### FULL

![](https://gitee.com/_Allens/BlogImage/raw/master/image/20200911092404.gif)

### NONE

![](https://gitee.com/_Allens/BlogImage/raw/master/image/20200911092550.gif)




## 设置缩放比例

双击图片，可以变大（默认1.5f），再次双击会变得更大(默认3.0f)，当最大的时候，双击变成 正常比例(1.0f)，

当然也可以上设置自己想要的比例,


```java
Larger.create()
    .withListType()//这里展示的是列表类型的
    .setImageLoad(GlideImageLoader(context))   //图片加载器
    .setMediumScale(4f)//设置中间比例 不能超过最大比例
    .setMaxScale(4f)//设置最大比例
    .setIndex(position)//下标
    .setRecyclerView(recyclerView)//recyclerview
    .setData(data) //添加默认的数据源
    .start(context)//跳转


```

这样就可以设置成，双击一下 变成4f 再次双击变成1f


## 自定义布局

需要使用 LargerImageView 作为图片信息

下面是查看原图示例

```xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">


    <com.starot.larger.image.LargerImageView
        android:id="@+id/item_custom_image"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />


    <TextView
        android:id="@+id/item_custom_tv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="10dp"
        android:layout_marginStart="20dp"
        android:layout_marginBottom="20dp"
        android:background="#BEBEBE"
        android:paddingLeft="20dp"
        android:paddingTop="6dp"
        android:paddingRight="20dp"
        android:paddingBottom="6dp"
        android:text="查看原图"
        android:textColor="#ffffff"
        android:textSize="12sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```


因为是需要点击加载大图，所以 需要将 自动加载大图设置成false ``setAutomatic(false)``

使用 ``setCustomListener`` 设置自定义的布局

> 参数说明

第一个参数:当前布局的id
第二个参数:准备展示的LargerImageView id
第三个参数:自定义的接口处理

```java
  private val listener = object : OnCustomImageLoadListener {
        override fun onCustomImageLoad(
            listener: OnImageLoadListener?,
            view: View,
            position: Int,
            data: LargerBean
        ) {
            val textView = view.findViewById<TextView>(R.id.item_custom_tv)
            val fullUrl = data.fullUrl
            if (fullUrl != null) {
                listener?.checkCache(fullUrl, object : OnImageCacheListener {
                    override fun onCache(hasCache: Boolean) {
                        if (hasCache) {
                            textView.visibility = View.GONE
                        }
                    }
                })

                textView.setOnClickListener {
                    listener?.load(
                        fullUrl,
                        true,
                        view.findViewById(R.id.item_custom_image)
                    )
                }
            } else {
                textView.visibility = View.GONE
            }
        }
    }


Larger.create()
    .withListType()//这里展示的是列表类型的
    .setImageLoad(GlideImageLoader(context))   //图片加载器
    .setMediumScale(4f)//设置中间比例 不能超过最大比例
    .setMaxScale(4f)//设置最大比例
    .setIndex(position)//下标
    .setAutomatic(false)//设置不自动加载大图
    .setCustomListener(R.layout.item_custom_image,R.id.item_custom_image,listener)//自定义布局
    .setRecyclerView(recyclerView)//recyclerview
    .setData(data) //添加默认的数据源
    .start(context)//跳转


```

## 设置像抖音那样的上下滑动方式

```java

Larger.create()
    .withListType()//这里展示的是列表类型的
    .setImageLoad(GlideImageLoader(context))   //图片加载器
    .setIndex(position)//下标
    .setOrientation(Orientation.ORIENTATION_VERTICAL)//滑动的方向
    .setRecyclerView(recyclerView)//recyclerview
    .setData(data) //添加默认的数据源
    .start(context)//跳转

```


## 单个View 的方式

使用 withSingle  通过 ``setImagesWithSingle``设置单个图片信息，

这里使用list 主要是考虑 万一有两个呢

```java
  Larger.create()
        .withSingle()//这里展示的单个view
        .setImageLoad(GlideImageLoader(this))   //图片加载器
        .setDuration(300)//动画持续时间
        .setImagesWithSingle(arrayListOf(src_image))//设置imageView
        .setProgress(ProgressLoader(ProgressLoader.ProgressType.FULL)) //添加进度显示
        .setData(list) //添加默认的数据源
        .start(this)
```

## 设置背景颜色


```java
  Larger.create()
        .withSingle()//这里展示的单个view
        .setImageLoad(GlideImageLoader(this))   //图片加载器
        .setDuration(300)//动画持续时间
        .setBackgroundColor(Color.Red)//默认是黑色 可以设置自己需要的颜色
        .setImagesWithSingle(arrayListOf(src_image))//设置imageView
        .setProgress(ProgressLoader(ProgressLoader.ProgressType.FULL)) //添加进度显示
        .setData(list) //添加默认的数据源
        .start(this)
```


# 最后

整个架构用fragment 的方式去处理，方便后续的拓展，视屏部分使用的是  [JiaoZiVideoPlayer](https://github.com/Jzvd/JiaoZiVideoPlayer) 作为播放器，eumm 可以替换自己喜欢的播放器去处理，



# github

[Github](https://github.com/JiangHaiYang01/WeChatPhoto)
[博客说明](https://allens.icu/posts/4a05dc12/#more)


