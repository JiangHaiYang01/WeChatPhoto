# WeChatPhoto
仿微信朋友圈点击小图，变成大图，放大缩小



高仿微信朋友圈，点击查看大图，放大 缩小，可自定义



# 前言

相信大家都用过微信，在微信盆友圈中，点点击一个小图片的时候，会很自然的切换到大图模式，然后再点击一下就缩回去，这个动画效果非常好看，为了到达这个效果首先确认微信的动画效果哪些




# 实现目的

- 点击小图 渐变切换到大图，背景渐变成黑色
- 点击大图 渐变到小图，背景变成原来的样式
- 往下拖拽,背景的颜色根据往下拖拽的进度改变，越往下 颜色越浅
- 图片可以放大，缩小
- 可以滑动
- 小图到大图的过程中有一个图片加载进度


> 目前这个使我们需要完成的需求，eumm  还是有点烦人的



# 演示效果

最终的项目效果如下图所示




[![Watch the video](https://raw.github.com/GabLeRoux/WebMole/master/ressources/WebMole_Youtube_Video.png)](https://allens-blog.oss-cn-beijing.aliyuncs.com/allens-blog/zdcp8.mp4)



# 实现讲解


## 滑动分析

一下子让拿到这个需求，其实还是比较懵逼的，不过饭要一口一口吃，既然这个是仿造微信的，他的最基本的需求就是 点击小图 然后显示一个大图，并且能够切换，分析可最基本的需求，一个activity + viewpager  搞定 打完收工，动画什么的都是浮云~~


> 滑动  viewpager + activity  ,点击跳转到新的activity 中

完成了第一个基本的需求

产品说:"不行，要有动画"


> 项目中使用的是viewpager2 算尝个鲜，实际使用中也没发现问题


## 点击小图切换到大图的过程  狸猫换代太子

仔细的看一下上面的动画，一个小图的activityA  跳转到 大图的 activityB ，我想到的一个思路是 当跳转到activityB 以后，首先在B 的位置上画一个 和A上一模一样的imageview  然后 缩放的 大图的位置

那么问题来了，如何在 activityB 上显示一个和activityA 上相同的imageview 呢

我想到的办法是 将 activityA 的ImageView 记录下来，然后在 activityB 中 将 imageview 的 宽 高 和 图片的 scaleType 设置的和他相同 这样图片的大小 和样式就和  activityA 中一样了


> 如何将 activityB 的图片位置确定下来

我们一开始得到的 activityA 中的 imageview  我们是知道他的xy 坐标的 只需要将这个坐标给到 activityB 中就行了


代码如下

```java
 override fun beforeTransition(
        photoId: Int,
        fullView: ImageView,
        thumbnailView: ImageView
    ) {
        fullView.scaleType = thumbnailView.scaleType
        fullView.layoutParams = fullView.layoutParams.apply {
            width = thumbnailView.width
            height = thumbnailView.height
            val location = getLocationOnScreen(thumbnailView)
            when (fullView.parent) {
                is ConstraintLayout -> {
                    val constraintSet = ConstraintSet().apply {
                        clone(fullView.parent as ConstraintLayout)
                        clear(photoId, ConstraintSet.START)
                        clear(photoId, ConstraintSet.TOP)
                        clear(photoId, ConstraintSet.BOTTOM)
                        clear(photoId, ConstraintSet.RIGHT)
                        //重新建立约束
                        connect(
                            photoId, ConstraintSet.TOP, ConstraintSet.PARENT_ID,
                            ConstraintSet.TOP, location[1]
                        )
                        connect(
                            photoId, ConstraintSet.START, ConstraintSet.PARENT_ID,
                            ConstraintSet.START, location[0]
                        )
                    }
                    constraintSet.applyTo(fullView.parent as ConstraintLayout)
                }
                else -> {
                    if (this is ViewGroup.MarginLayoutParams) {
                        marginStart = location[0]
                        topMargin = location[1]
                    }
                }
            }


        }

    }

```


> activityB 中的图片 变大

上面的 beforeTransition 方法 我们已经可以 从 activityA 跳转到 activityB 并且在activityB 中绘制了一个和activityA 中相同大小，位置，样式的图片，接着 我们要把这个图片变大。。既然是变大，肯定少不了动画，分析一下这个由小到大，


图片的宽高不在是原来小图的  位置也不再是小图的 ，样式也不再是小图的，真正的变成了太子。。


```java

   override fun startTransition(fullView: ImageView, thumbnailView: ImageView) {
        fullView.scaleType = ImageView.ScaleType.FIT_CENTER
        fullView.layoutParams = fullView.layoutParams.apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.MATCH_PARENT
            if (this is ViewGroup.MarginLayoutParams) {
                marginStart = 0
                topMargin = 0
            }
        }
    }

    override fun transitionSet(durationTime: Long): Transition {
        return TransitionSet().apply {
            addTransition(ChangeBounds())
            addTransition(ChangeImageTransform())
            duration = durationTime
            interpolator = DecelerateInterpolator()
        }
    }
```

> 背景的颜色变成黑色

拿到父类的view 改变他的背景颜色透明度 加上一个 渐变的动画效果就行了

```java

   //修改进入的时候背景 渐变 黑色
    fun start(
        parent: View,
        originalScale: Float,
        duration: Long
    ) {
        val valueAnimator = ValueAnimator()
        valueAnimator.duration = duration
        valueAnimator.setFloatValues(originalScale, 1f)
        valueAnimator.addUpdateListener { animation ->
            parent.setBackgroundColor(
                ColorTool.getColorWithAlpha(Color.BLACK, (animation.animatedValue as Float))
            )
        }
        valueAnimator.start()
    }
```


## 大图变成小图

上面的小图到大图的 看官理解啦，那么大图到小图 就返回来就行啦


## 图片的放大，缩小

这个图片的放大缩小，我用的是开源的框架  [PhotoView](https://github.com/chrisbanes/PhotoView)，



## 手指拖动改变大小和背景颜色


先看一下效果



<video src='http://allens-blog.oss-cn-beijing.aliyuncs.com/allens-blog/gv1o4.mp4' type='video/mp4' controls='controls'  width='50%' height='50%'>
</video>




分析一下 这里有两个动画，
- 下拉的位置不大，返回原来的状态，
- 下拉的位置很大，直接变成小图


因为 PhotoView 不知道 图片的 drag 所以 在 PhotoView 的基础上进行拓展

```java

object ImageDragHelper {


    //是否正在移动
    private var isDragIng = false

    //是否正在动画
    var isAnimIng = false


    fun startDrag(
        image: PhotoView,
        holder: ViewPagerAdapter.PhotoViewHolder,
        listener: OnDragAnimListener
    ) {
        image.setOnViewDragListener(object : OnViewDragListener {
            override fun onDrag(dx: Float, dy: Float) {
            }

            override fun onScroll(x: Float, y: Float) {
                //这里 需要判断一下滑动的角度  防止和 viewpager 滑动冲突
                if (isDragIng) {
                    //正在播放动画 不给滑动
                    if (isAnimIng) {
                        return
                    }
                    //图片处于缩放状态
                    if (image.scale != 1.0f) {
                        return
                    }
                    drag(image, x, y, listener)
                } else {
                    if (abs(x) > 30 && abs(y) > 60) {
                        //正在播放动画 不给滑动
                        if (isAnimIng) {
                            return
                        }
                        //图片处于缩放状态
                        if (image.scale != 1.0f) {
                            return
                        }
                        // 一开始向上滑动无效的
                        if (y > 0) {
                            isDragIng = true
                            drag(image, x, y, listener)
                        }
                    }
                }
            }

            override fun onScrollFinish() {
                if (isDragIng)
                    listener.onEndDrag(image,holder)
            }

            override fun onScrollStart() {
                isDragIng = false
            }
        })

    }

    private fun drag(image: PhotoView, x: Float, y: Float, listener: OnDragAnimListener) {
        listener.onStartDrag(image, x, y)
    }
}
```

知道了 x y 就好处理了


```java

            //图片的缩放 和位置变化
            val fixedOffsetY = y - 0
            val fraction = abs(max(-1f, min(1f, fixedOffsetY / image.height)))
            val fakeScale = 1 - min(0.4f, fraction)
            image.scaleX = fakeScale
            image.scaleY = fakeScale
            image.translationY = fixedOffsetY * dampingData
            image.translationX = x / 2 * dampingData
```

> 判断是缩回去 还是 返回原来的样子，

```java

  override fun onEndDrag(
            image: PhotoView,
            holder: ViewPagerAdapter.PhotoViewHolder
        ) {
            setViewPagerEnable(true)
            setZoomable(image, true)

            var fraction = setFraction()
            if (fraction > 1f) {
                fraction = 1f
            } else if (fraction < 0f) {
                fraction = 0f
            }
            if (abs(image.translationY) < image.height * fraction) {
                AnimDragHelper.start(
                    getPhotoViewId(),
                    setDuration(),
                    image,
                    getImageArrayList()[mCurrentIndex],
                    holder,
                    enterAnimListener,
                    afterTransitionListener
                )
                //背景颜色变化
                AnimBgEnterHelper.start(parentView, currentScale, setDuration())
            } else {
                exitAnim(currentScale, holder)
            }
        }
```


## 自定义的图片加载器

这里原本想着放到框架内部，又想到 郭婶在 litepal 中说的 做减法，所以没有放在内部，而是作为demo 的形式放在了外部， 开发者可以更大程度的自定义样式

demo 用到的是 glide ,


## 图片加载进度

这个功能其实不属于框架本身的样式，所以我放在了demno 中 供大家参考，有兴趣的小伙伴可以参考 [Glide 图片加载进度](https://allens.icu/posts/9a2c02b8/#more)



# 下载



Step 1. Add the JitPack repository to your build file


Add it in your root build.gradle at the end of repositories:

```
	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```

Step 2. Add the dependency

```
	dependencies {
	        implementation 'com.github.JiangHaiYang01:WeChatPhoto:Tag'
	}
```

> 当前最新版本

[![](https://www.jitpack.io/v/JiangHaiYang01/WeChatPhoto.svg)](https://www.jitpack.io/#JiangHaiYang01/WeChatPhoto)

# 使用

## step1 继承 ``LargerAct``


继承 LargerAct 类型可自定义 sample 中方式的是string

```java


class SampleAct : LargerAct<String>() {


    companion object {
        const val IMAGE = "images"
        const val INDEX = "index"
    }


    //添加数据源
    override fun getData(): ArrayList<String>? {
        return intent.getStringArrayListExtra(IMAGE)
    }

    //长按事件
    override fun onLongClickListener() {
        Toast.makeText(this, "长按图片", Toast.LENGTH_LONG).show()
    }

    //item 布局
    override fun getItemLayout(): Int {
        return R.layout.item_def
    }

    //一定要返回一个 PhotoView 的id  内部处理还是需要用到的
    override fun getPhotoViewId(): Int {
        return R.id.image
    }

    //当前是第几个图片  index 和 image 一一对应
    override fun getIndex(): Int {
        return intent.getIntExtra(INDEX, 0)
    }

    //设置持续时间
    override fun setDuration(): Long {
        return 2000
    }

    //默认拖动时候的阻尼系数   [0.0f----1.0f] 越小越难滑动
    override fun setDamping(): Float {
        return 1.0f
    }

    //设置下拉的参数 [0.0f----1.0f] 越小越容易退出
    override fun setFraction(): Float {
        return 0.5f
    }

    //设置原来的图片源
    override fun getImageArrayList(): ArrayList<ImageView> {
        return ImagesHelper.images
    }

    //处理自己的业务逻辑
    override fun itemBindViewHolder(
        isLoadFull: Boolean,
        itemView: View,
        position: Int,
        data: String?
    ) {
        if (data == null) {
            return
        }
        //这里用到了自己写的一个 进度条 可自定义
        val progressView = itemView.findViewById<CircleProgressView>(R.id.progress)
        val imageView = itemView.findViewById<PhotoView>(R.id.image)

        //Glide 加载图片的进度 具体可参考代码
        ProgressInterceptor.addListener(data, object : ProgressListener {
            override fun onProgress(progress: Int) {
                progressView.visibility = View.VISIBLE
                progressView.progress = progress
            }
        })

        //这里为了演示效果  取消了缓存  正常使用是不需要的
        val options = RequestOptions()
        if (isLoadFull)
            options
                .placeholder(imageView.drawable)
                .override(imageView.width, imageView.height)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)


        Glide.with(this)
            .load(data)
            .apply(options)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.i(TAG, "图片加载失败")
                    progressView.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.i(TAG, "图片加载成功")
                    progressView.visibility = View.GONE
                    return false
                }
            })
            .into(imageView)
    }


}

```

## step2 添加theme

```xml

        <activity
            android:name=".larger.SampleAct"
            android:screenOrientation="portrait"
            android:theme="@style/custom_larger" />
```

## step3 跳转

```java
 private fun startAct(
        index: Int,
        images: ArrayList<String>
    ) {
        val intent = Intent(this, SampleAct::class.java)
        //传入图片信息 按需求自定义
        intent.putStringArrayListExtra(SampleAct.IMAGE, images)
        //传入当前的 index  用于处理viewpager
        intent.putExtra(SampleAct.INDEX, index)
        startActivity(intent)
    }
```

# 更新说明

> 0.0.2

在0.0.1 版本 实际使用的时候发现一个问题 当图片是 FIT_XY 或者 CENTER_CROP 等 对图片裁剪的时候 小图到大图的过程中会有问题

- 兼容了 CENTER_CROP 等状态的处理 （MATRIX 和 CENTER 不兼容）

# 未处理的问题

在 原本图片的 style 是  MATRIX  或者 CENTER 的时候，图片不能很好的从 原来的样式 渐渐切换到 大图的样式，这里的原因我还不知道为啥，知道的的小伙伴可以 说一下

# apk 下载体验

[0.0.2版本](http://allens-blog.oss-cn-beijing.aliyuncs.com/allens-blog/arjp8.apk)

[0.0.1版本](http://allens-blog.oss-cn-beijing.aliyuncs.com/allens-blog/v86p6.apk)




# Github

[Github](https://github.com/JiangHaiYang01/WeChatPhoto)
[个人博客](https://allens.icu)



# 写在最后

这边博客其实之前就写完了，直接放出效果图 + 使用方式，我想着其实写不是关键，还是想将自己写的时候的一些心得分享一下