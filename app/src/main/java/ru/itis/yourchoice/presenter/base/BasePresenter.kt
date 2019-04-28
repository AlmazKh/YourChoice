package ru.itis.yourchoice.presenter.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T> {

    var disposables = CompositeDisposable()

    var view: T? = null
        private set

    fun attachView(view: T) {
        this.view = view
    }

    fun detachView() {
        view = null
        disposables.clear()
    }

    val isViewAttached: Boolean
        get() = view != null
}