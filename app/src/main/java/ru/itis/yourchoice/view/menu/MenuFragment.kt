package ru.itis.yourchoice.view.menu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.menu_fragment.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.YourChoiceApp
import ru.itis.yourchoice.view.menu.interests.InterestsFragment
import ru.itis.yourchoice.view.menu.profile.UserProfileFragment

class MenuFragment : Fragment(), MenuFragmentView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        YourChoiceApp.appComponent
                .menuComponent()
                .withActivity(activity!! as AppCompatActivity)
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.menu_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.tv_page_title?.setText("YC")
        relativeLayout_profile.setOnClickListener { openProfilePage() }
        tv_likes.setOnClickListener { openLikesPage() }
        tv_interests.setOnClickListener { openInterestsPage() }
        tv_settings.setOnClickListener { openSettingspage() }
        tv_help_feedback.setOnClickListener { openHelpAndFeedbackPage() }

    }

    override fun openProfilePage() {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.main_container, UserProfileFragment.newInstance())?.commit()
    }

    override fun openLikesPage() {
        Toast.makeText(context,"likes open", Toast.LENGTH_SHORT).show()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openInterestsPage() {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.main_container, InterestsFragment.newInstance())?.commit()    }

    override fun openSettingspage() {
        Toast.makeText(context,"settings open", Toast.LENGTH_SHORT).show()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openHelpAndFeedbackPage() {
        Toast.makeText(context,"help open", Toast.LENGTH_SHORT).show()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun newInstance() = MenuFragment()
    }
}