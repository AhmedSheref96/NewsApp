package com.el3sas.newsapp.helpers

import timber.log.Timber

const val myTag = "*************"
class MyDebugTree : Timber.DebugTree() {

    override fun d(message: String?, vararg args: Any?) {
        val mMsg = "$myTag $message"
        super.d(mMsg, *args)
    }
}
