package com.example.marvelbrowser.app.base

interface BaseView<T> {
    var presenter: BasePresenter<T>
}