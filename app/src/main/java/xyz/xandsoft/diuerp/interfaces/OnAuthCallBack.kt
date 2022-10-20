package xyz.xandsoft.diuerp.interfaces

interface OnAuthCallBack {

    fun onAuthStarted()
    fun onAuthSuccess()
    fun onAuthFailed(messge: String)
}