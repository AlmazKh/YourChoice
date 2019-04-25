package ru.itis.yourchoice.view.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {

    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        inject()
        setupView()
    }

    protected abstract fun inject()

    protected abstract fun setupView()
}