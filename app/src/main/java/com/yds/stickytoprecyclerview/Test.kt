package com.yds.stickytoprecyclerview

class Test {

    var mCallBack: ((str: String) -> Unit)? = null
    fun setCallback(myCallBack: ((str: String) -> Unit)) {
        this.mCallBack = myCallBack
    }

    interface ICallback {
        fun onSuccess(msg: String)

        fun onFail(msg: String)
    }



}