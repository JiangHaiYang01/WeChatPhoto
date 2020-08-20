package com.starot.larger.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.lang.reflect.Field
import java.lang.reflect.Method


class MyMutableLiveData<T> : MutableLiveData<T>() {
    //目的：使得在observe被调用的时候，能够保证 if (observer.mLastVersion >= mVersion) （livedata源码里面的）成立
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, observer)
        try {
            hook(observer)
        } catch (t: Throwable) {

        }
    }

    /**要修改observer.mLastVersion的值那么思考：（逆向思维）
     * mLastVersion-》observer-》iterator.next().getValue()-》mObservers
     * 反射使用的时候，正好相反
     *
     * mObservers-》函数（iterator.next().getValue()）-》observer-》mLastVersion
     * 通过hook，将observer.mLastVersion = mVersion
     * @param observer
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun hook(observer: Observer<in T>) {
        val liveDataClass = LiveData::class.java
        val fieldObservers: Field = liveDataClass.getDeclaredField("mObservers")
        fieldObservers.isAccessible = true
        val mObservers = fieldObservers.get(this)
        val mObserversClass: Class<*> = mObservers.javaClass
        val method: Method = mObserversClass.getDeclaredMethod("get", Any::class.java)
        method.isAccessible = true
        val entry = method.invoke(mObservers, observer)
        val observerWrapper = (entry as Map.Entry<*, *>).value!!
        val mObserver: Class<*> = observerWrapper.javaClass.superclass //observer
        val mLastVersion: Field = mObserver.getDeclaredField("mLastVersion")
        mLastVersion.isAccessible = true
        val mVersion: Field = liveDataClass.getDeclaredField("mVersion")
        mVersion.isAccessible = true
        val mVersionObject = mVersion.get(this)
        mLastVersion.set(observerWrapper, mVersionObject)
    }
}