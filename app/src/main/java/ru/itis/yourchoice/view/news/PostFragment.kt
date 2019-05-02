package ru.itis.yourchoice.view.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.itis.yourchoice.R
import ru.itis.yourchoice.YourChoiceApp


class PostFragment: Fragment(), PostFragmentView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        YourChoiceApp.appComponent
            .newsFeedComponent()
            .withActivity(activity!! as AppCompatActivity)
            .build()
            .inject(this)

        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        activity?.actionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, null)
    }

    companion object {
        fun newInstance() = PostFragment()
    }
}