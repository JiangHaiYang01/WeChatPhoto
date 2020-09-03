package com.starot.wechat.utils

import java.util.ArrayList

open class Urls {
    fun getTargetButtonTarget(): List<String> {
        val list: MutableList<String> =
            ArrayList()
        list.add("https://cdn.nlark.com/yuque/0/2020/jpeg/252337/1592643441654-assets/web-upload/771e09b0-aaf9-4308-bae0-cd5b3cb98817.jpeg")
        list.add("https://cdn.nlark.com/yuque/0/2020/jpeg/252337/1592643441557-assets/web-upload/94ed7774-2bed-4dbe-be54-080c2f8939a1.jpeg")
        list.add("https://cdn.nlark.com/yuque/0/2020/jpeg/252337/1592643441632-assets/web-upload/84d01b3f-7f28-4125-b3c7-8e5b5b15c0cb.jpeg")

        return list
    }

    fun getTargetButtonSmall(): List<String> {
        val list: MutableList<String> =
            ArrayList()
        list.add("https://cdn.nlark.com/yuque/0/2020/jpeg/252337/1592643585276-assets/web-upload/6e2520de-544d-45b6-9892-ff228d14e175.jpeg")
        list.add("https://cdn.nlark.com/yuque/0/2020/jpeg/252337/1592643585330-assets/web-upload/85629ce4-bacf-4ab4-af6f-5a0a67931b68.jpeg")
        list.add("https://cdn.nlark.com/yuque/0/2020/jpeg/252337/1592643585344-assets/web-upload/3b178609-8aef-48f3-9ebc-41537149f13b.jpeg")

        return list
    }
}