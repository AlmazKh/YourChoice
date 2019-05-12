package ru.itis.yourchoice.view.menu.likes

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_likes.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.YourChoiceApp
import ru.itis.yourchoice.core.model.Post
import ru.itis.yourchoice.presenter.menu.LikesPresenter
import javax.inject.Inject

class LikesFragment: Fragment(), LikesView {

    @Inject
    lateinit var likesPresenter: LikesPresenter

    private var likesListAdapter: LikesListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        YourChoiceApp.appComponent
                .menuComponent()
                .withActivity(activity = AppCompatActivity())
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_likes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        likesPresenter.attachView(this)
        rv_posts.apply {
            layoutManager = LinearLayoutManager(rootView.context)
        }
        initAdapter()
    }

    private fun initAdapter() {
        likesListAdapter = LikesListAdapter() {
            likesPresenter.onPostClick(it)
        }
        rv_posts.adapter = likesListAdapter
        likesPresenter.updateUserProfilePosts()
    }

    override fun updateListView(list: List<Post>) {
        likesListAdapter?.submitList(list)
    }

    override fun navigateToDetails(post: Post) {
        //post fragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        likesPresenter.detachView()
    }

    companion object {
        fun newInstance() = LikesFragment()
    }
}