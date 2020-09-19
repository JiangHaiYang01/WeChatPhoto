package com.starot.wechat.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.starot.larger.utils.StatusBarTools
import com.starot.wechat.R

open class BaseAct :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarTools.setStatusBar(this)
    }
}