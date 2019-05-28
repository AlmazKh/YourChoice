package ru.itis.yourchoice.view.menu.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_user_profile.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.YourChoiceApp
import ru.itis.yourchoice.core.model.Post
import ru.itis.yourchoice.presenter.menu.UserProfilePresenter
import javax.inject.Inject

class UserProfileFragment: Fragment(), UserProfileView {

    @Inject
    lateinit var userProfilePresenter: UserProfilePresenter

    private var profilePostsAdapter: ProfilePostsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        YourChoiceApp.appComponent
                .menuComponent()
                .withActivity(activity = AppCompatActivity())
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userProfilePresenter.attachView(this)
        rv_posts.apply {
            layoutManager = LinearLayoutManager(rootView.context)
        }
        initAdapter()
    }

    private fun initAdapter() {
        profilePostsAdapter = ProfilePostsAdapter {
            userProfilePresenter.onPostClick(it)
        }
        rv_posts.adapter = profilePostsAdapter
        userProfilePresenter.updateUserProfilePosts()
    }

    override fun updateListView(list: List<Post>) {
        profilePostsAdapter?.submitList(list)
    }

    override fun navigateToDetails(post: Post) {
        //post fragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        userProfilePresenter.detachView()
    }

    companion object {
        fun newInstance() = UserProfileFragment()
    }
}
