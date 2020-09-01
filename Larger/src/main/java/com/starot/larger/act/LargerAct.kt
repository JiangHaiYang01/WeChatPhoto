package com.starot.larger.act

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.starot.larger.Larger
import com.starot.larger.R
import com.starot.larger.adapter.ViewPagerAdapter
import com.starot.larger.config.BaseLargerConfig
import com.starot.larger.config.LargerConfig
import com.starot.larger.enums.FullType
import com.starot.larger.impl.*
import com.starot.larger.utils.Drag
import com.starot.larger.utils.LogUtils
import com.starot.larger.utils.PageChange
import com.starot.larger.view.image.PhotoView
import kotlinx.android.synthetic.main.activity_larger_base.*


abstract class LargerAct<T> : AppCompatActivity(),
    ViewPagerAdapter.OnBindViewHolderListener,
    AnimListener,
    OnItemViewListener<T>,
    OnLoadProgressPrepareListener,
    OnLoadProgressListener,
    OnReLoadFullImage,
    PageChange.PageChangeListener,
    OnDragAnimListener {

    var largerConfig: LargerConfig? = null

    //根视图
    private lateinit var parentView: View

    //数据源
    private var data: List<T>? = null

    //当前的index
    private var mCurrentIndex = 0

    //动画时间
    private var duration: Long = 300

    //缩略图
    private var thumbnailView: ImageView? = null

    //进入的动画加载完成
    private var isLoadEnter = false

    //是否在播放动画
    private var isAnimIng = MutableLiveData<Boolean>().also {
        it.value = false
    }

    //是否自动加载大图
    private var automatic = true

    //使用liveData 记录 加载进度变化
    private var progressLiveData: MutableLiveData<Int> = MutableLiveData()

    //使用liveData 记录 加载变化
    private var progressViewLiveData: MutableLiveData<Boolean> = MutableLiveData()

    //保存变化的 holder
    //todo 这个map 可以进行优化的
    private val holderMap: HashMap<Int, RecyclerView.ViewHolder> = HashMap()


    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(0, 0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_larger_base)


        //开始之前
        beforeCreate()
        //获取通用配置
        largerConfig = Larger.config
        //动画持续时间
        duration = getDuration()
        //是否自动加载大图
        automatic = getAutomaticLoadFullImage()

        //是否正在动画
        isAnimIng.observe(this, {
            //记录在Drag 中
            Drag.isAnimIng = it
            //viewpager 在动画过程中不能滑动
            setViewPagerEnable(!it)
        })

        //注册监听liveData
        registerLiveData()

        //数据源
        data = getData()
        //根视图
        parentView = larger_parent
        //当前的下标
        mCurrentIndex = getIndex()
        //获取当前列表的imageView
        thumbnailView = getThumbnailView(mCurrentIndex)
        //设置适配器
        larger_viewpager.adapter = ViewPagerAdapter(data, getItemLayout(), this)
        //不需要平滑过渡了
        larger_viewpager.setCurrentItem(mCurrentIndex, false)
        //viewpager 滑动 index 更改
        PageChange().register(viewPager2 = larger_viewpager, listener = this)
    }

    private fun registerLiveData() {
        //将监听变化的liveData 通过接口保存
        largerConfig?.imageLoad?.onPrepareLoadProgress(progressLiveData)
        largerConfig?.videoLoad?.onPrepareLoadProgress(progressLiveData)
        //将监听变化的liveData
        largerConfig?.imageLoad?.onPrepareProgressView(progressViewLiveData)
        largerConfig?.videoLoad?.onPrepareProgressView(progressViewLiveData)

        //对于加载进度条的逻辑判断
        progressViewLiveData.observe(this, {
            onProgressChange(this, it)
            //同时状态还要通知到自定义的listener
            //获取当前列表的imageView
            //滑动的时候 判断是否已经有缓存了 有缓冲 就加载高清图
            val viewHolder = holderMap[mCurrentIndex]
            if (viewHolder != null)
                Larger.listConfig?.customItemViewListener?.itemImageFullLoad(
                    viewHolder.itemView,
                    mCurrentIndex
                )

        })

        //监听 加载大图进度变化
        progressLiveData.observe(this, {
            //大图加载进度
            onLoadProgress(it)
        })
    }


    //设置viewpager 是否可以滑动
    private fun setViewPagerEnable(isUserInputEnabled: Boolean) {
        larger_viewpager.isUserInputEnabled = isUserInputEnabled //true:滑动，false：禁止滑动
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holderMap[position] = holder
        when (holder) {
            is ViewPagerAdapter.PhotoViewHolder -> {
                //通用方法
                itemCommand(holder, position, data?.get(position))
                //用户自己处理加载逻辑
                itemBindViewHolder(false, holder.itemView, position, data?.get(position))
                //首次进入加载完成之后不再展示动画
                if (isLoadEnter) {
                    return
                }
                val fullImageView = holder.itemView.findViewById<View>(getFullViewId())
                if (fullImageView == null) {
                    LogUtils.i("prepare enterAnimStart  fullId ${getFullViewId()} get view is null")
                    return
                }
                enterAnimStart(
                    parentView,
                    duration,
                    fullImageView,
                    thumbnailView,
                    holder
                )
            }
        }
    }

    //通用方法
    private fun itemCommand(holder: ViewPagerAdapter.PhotoViewHolder, position: Int, get: T?) {
        val view = if (Larger.type == FullType.Image) {
            holder.itemView.findViewById<View>(getFullViewId())
        } else {
            holder.itemView.findViewById<View>(getVideoViewId())
        }
        if (view == null) {
            LogUtils.i("itemCommand fullId: ${getFullViewId()} get view is null")
            return
        }
        //单点击退出
        view.setOnClickListener {
            LogUtils.i("单点击退出")
            if (isAnimIng.value!!) {
                //当前正在播放动画 不可点击
                LogUtils.i("当前正在播放动画 不可点击")
                return@setOnClickListener
            }
            singleExitAnim(view, holder)
        }

        //拖动
        when (view) {
            is PhotoView -> {
                Drag.startDrag(this, view, holder, this)
            }
        }
    }

    //单点击退出
    private fun singleExitAnim(
        image: View,
        holder: ViewPagerAdapter.PhotoViewHolder
    ) {
        thumbnailView = getThumbnailView(mCurrentIndex)
        exitAnimStart(
            parentView,
            duration,
            image,
            thumbnailView,
            holder
        )
    }

    //进入动画结束
    override fun onEnterAnimEnd() {
        isAnimIng.postValue(false)
    }

    //进入动画开始
    override fun onEnterAnimStart() {
        isLoadEnter = true
        isAnimIng.postValue(true)
    }


    //退出动画结束
    override fun onExitAnimEnd() {
        isAnimIng.postValue(false)
        finish()
        overridePendingTransition(0, 0)
    }

    //退出动画开始
    override fun onExitAnimStart() {
        isAnimIng.postValue(true)
    }

    //viewpager 滑动监听
    override fun onPageChange(pos: Int) {
        mCurrentIndex = pos
        //获取当前列表的imageView
        thumbnailView = getThumbnailView(mCurrentIndex)
        //滑动的时候 判断是否已经有缓存了 有缓冲 就加载高清图
        val viewHolder = holderMap[mCurrentIndex]
        if (viewHolder != null && getIndex() != pos) {
            if (Larger.type == FullType.Image) {
                LogUtils.i("view  pager 滑动 当前是图片模式 触发加载大图的逻辑")
                onReLoadFullImage(viewHolder)
            } else if (Larger.type == FullType.Audio) {
                LogUtils.i("view  pager 滑动 当前是audio模式 ")
                onLoadAudio(viewHolder)
            }

        }
    }

    override fun onLoadAudio(holder: RecyclerView.ViewHolder) {
        //将audioView 显示出来
        val videoView = holder.itemView.findViewById<View>(getVideoViewId())
        videoView.visibility = View.VISIBLE
        val image = holder.itemView.findViewById<View>(getFullViewId())
        image.visibility = View.GONE
        if (videoView is VideoView) {
            //将VideoView 背景设置成 小图的缩略图
            if (image is ImageView) {
                videoView.background = image.drawable
            }

            onItemLoadAudio(
                largerConfig?.videoLoad,
                holder.itemView,
                mCurrentIndex,
                videoView,
                data = data?.get(mCurrentIndex)
            )
        } else {
            LogUtils.i("加载视屏 但是呢 这个 fullViewId 获取的不是 videoView")
        }
    }

    override fun onReLoadFullImage(holder: RecyclerView.ViewHolder) {
        //判断是否有缓存 有缓存直接加载 没缓存 则变量生效
        LogUtils.i("判断是否有缓存 有缓存直接加载 没缓存 则变量生效")
        getImageHasCache(
            holder.itemView,
            mCurrentIndex,
            data?.get(mCurrentIndex),
            object : OnCheckImageCacheListener {
                override fun onNoCache() {
                    LogUtils.i("onNoCache")
                    if (automatic) {
                        itemBindViewHolder(
                            true,
                            holder.itemView,
                            mCurrentIndex,
                            data?.get(mCurrentIndex)
                        )
                    }
                }

                override fun onHasCache() {
                    LogUtils.i("onHasCache")
                    itemBindViewHolder(
                        true,
                        holder.itemView,
                        mCurrentIndex,
                        data?.get(mCurrentIndex)
                    )
                }
            }
        )
    }


    //点击返回
    override fun onBackPressed() {
        //todo 点击返回暂时无效化 后续参考其他大厂 是否加入
    }

    //不在继续播放
    override fun onStopVideo(view: VideoView) {
        largerConfig?.videoLoad?.stop()
    }


    override fun onPrepareProgressView(progressViewLiveData: MutableLiveData<Boolean>) {
    }

    override fun onPrepareLoadProgress(progressLiveData: MutableLiveData<Int>) {
    }

    //加载图片的变化
    override fun onProgressChange(context: Context, isGone: Boolean) {
        largerConfig?.imageProgress?.onProgressChange(this, isGone)
    }


    //加载图片的进度
    override fun onLoadProgress(progress: Int) {
        largerConfig?.imageProgress?.onLoadProgress(progress)
    }


    //onCreate 第一件时间
    abstract fun beforeCreate()

    //是否自动加载大图
    open fun getAutomaticLoadFullImage(): Boolean {
        return largerConfig?.automaticLoadFullImage ?: true
    }

    //动画时长
    open fun getDuration(): Long {
        return largerConfig?.duration ?: 300
    }

    //大图的id
    private fun getFullViewId(): Int {
        if (getBaseConfig()?.itemLayout == null) {
            if (!isAudio()) {
                return R.id.image
            } else {
                return largerConfig?.videoLoad?.getVideoFullId() ?: return -1
            }

        }
        return getBaseConfig()?.fullViewId ?: if (!isAudio()) {
            return R.id.image
        } else {
            return largerConfig?.videoLoad?.getVideoFullId() ?: return -1
        }
    }

    //视屏加载id
    private fun getVideoViewId(): Int {
        val videoViewId = largerConfig?.videoLoad?.getVideoViewId() ?: return -1
        if (getBaseConfig()?.itemLayout == null) {
            return videoViewId
        }
        return getBaseConfig()?.videoViewId ?: videoViewId
    }

    //当前的图片index
    private fun getIndex(): Int {
        return getBaseConfig()?.position ?: 0
    }

    //获取缩略图
    abstract fun getThumbnailView(position: Int): ImageView?

    //数据源
    private fun getData(): List<T>? {
        return getBaseConfig()?.data as List<T>?
    }

    //默认的布局
    private fun getItemLayout(): Int {
        return getBaseConfig()?.itemLayout ?: if (!isAudio()) {
            R.layout.item_larger_image
        } else {
            largerConfig?.videoLoad?.getVideoLayoutId() ?: -1
        }
    }

    //判断图片是否有缓存
    abstract fun getImageHasCache(
        itemView: View,
        position: Int,
        data: T?,
        listener: OnCheckImageCacheListener
    )

    //加载图片 对外是可自定义处理
    private fun itemBindViewHolder(isLoadFull: Boolean, itemView: View, position: Int, data: T?) {
        getBaseConfig()?.customItemViewListener?.itemBindViewHolder(
            this,
            itemView,
            position,
            data
        )
        when (val view = itemView.findViewById<View>(getFullViewId())) {
            is ImageView -> {
                if (isLoadFull) {
                    onItemLoadFull(largerConfig?.imageLoad, itemView, position, view, data)
                } else {
                    onItemLoadThumbnails(largerConfig?.imageLoad, itemView, position, view, data)
                }
            }
            is VideoView -> {
                LogUtils.i("itemBindViewHolder is VideoView")
            }
        }
    }


    //加载大图
    override fun reLoadFullImage() {
        val itemView = holderMap[mCurrentIndex]?.itemView
        if (itemView != null) {
            itemBindViewHolder(true, itemView, mCurrentIndex, data?.get(mCurrentIndex))
        }

    }

    //背景颜色
    override fun onDragBackgroundColor(color: Int) {
        parentView.setBackgroundColor(color)
    }

    //开始移动
    override fun onDragStart(image: PhotoView) {
        //移动开始以后不能在滑动了
        setViewPagerEnable(false)
    }

    //停止移动
    override fun onDragStop(image: PhotoView) {
        setViewPagerEnable(true)
    }

    //退出动画
    override fun onDragExit(
        currentScale: Float,
        image: PhotoView,
        holder: ViewPagerAdapter.PhotoViewHolder
    ) {
        dragExitAnimStart(
            currentScale,
            parentView,
            duration,
            image,
            thumbnailView,
            holder
        )
    }

    //还原动画
    override fun onDragResume(
        currentScale: Float,
        image: PhotoView,
        holder: ViewPagerAdapter.PhotoViewHolder
    ) {
        dragResumeAnimStart(
            currentScale,
            parentView,
            duration,
            image,
            thumbnailView,
            holder
        )
    }


    //获取base config
    private fun getBaseConfig(): BaseLargerConfig? {
        if (Larger.listConfig != null) {
            return Larger.listConfig
        } else if (Larger.singleConfig != null) {
            return Larger.singleConfig
        }
        return null
    }

    //是否是视屏模式
    private fun isAudio(): Boolean {
        return Larger.type == FullType.Audio
    }

    //清理资源
    override fun onDestroy() {
        super.onDestroy()
        LogUtils.i("界面销毁 将状态改成 image")
        Larger.clear()
    }

}