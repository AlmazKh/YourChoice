package ru.itis.yourchoice.view.menu.interests

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.itis.yourchoice.R
import ru.itis.yourchoice.YourChoiceApp

class InterestsFragment: Fragment(), InterestsView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        YourChoiceApp.appComponent
                .menuComponent()
                .withActivity(activity!! as AppCompatActivity)
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_interests, container, false)
    }
    companion object {
        fun newInstance() = InterestsFragment()
    }
}