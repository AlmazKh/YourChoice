package ru.itis.yourchoice.view.menu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.menu_fragment.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.YourChoiceApp
import ru.itis.yourchoice.core.model.User
import ru.itis.yourchoice.presenter.menu.MenuPresenter
import ru.itis.yourchoice.view.menu.help.HelpFragment
import ru.itis.yourchoice.view.menu.interests.InterestsFragment
import ru.itis.yourchoice.view.menu.likes.LikesFragment
import ru.itis.yourchoice.view.menu.profile.UserProfileFragment
import ru.itis.yourchoice.view.menu.settings.SettingsFragment
import javax.inject.Inject

class MenuFragment : Fragment(), MenuFragmentView {

    @Inject
    lateinit var menuPresenter: MenuPresenter

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
        menuPresenter.attachView(this)
        activity?.tv_page_title?.setText("YC")
        menuPresenter.getCurrentUser()
        relativeLayout_profile.setOnClickListener { openProfilePage() }
        tv_likes.setOnClickListener { openLikesPage() }
        tv_interests.setOnClickListener { openInterestsPage() }
        tv_settings.setOnClickListener { openSettingsPage() }
        tv_help_feedback.setOnClickListener { openHelpAndFeedbackPage() }

    }

    override fun updateUserInfo(user: User) {
        tv_user_name_menu.text = user.name
        val transformation = RoundedCornersTransformation(20, 1)

        val requestOptions = RequestOptions()
                .centerCrop()
                .transforms(transformation)

        val thumbnail = Glide.with(this)
                .load(R.drawable.image_placeholder)
                .apply(requestOptions)

        Glide.with(this)
                .load(user.photo)
                .apply(requestOptions)
                .thumbnail(thumbnail)
                .into(iv_user_avatar_menu)
   }

    override fun openProfilePage() {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.main_container, UserProfileFragment.newInstance())?.commit()
    }

    override fun openLikesPage() {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.main_container, LikesFragment.newInstance())?.commit()
    }

    override fun openInterestsPage() {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.main_container, InterestsFragment.newInstance())?.commit()
    }

    override fun openSettingsPage() {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.main_container, SettingsFragment.newInstance())?.commit()
    }

    override fun openHelpAndFeedbackPage() {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.main_container, HelpFragment.newInstance())?.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        menuPresenter.detachView()
    }

    companion object {
        fun newInstance() = MenuFragment()
    }
}