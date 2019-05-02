package ru.itis.yourchoice.view.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.news_feed_fragment.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.YourChoiceApp
import ru.itis.yourchoice.core.model.Post
import ru.itis.yourchoice.presenter.news.NewsFeedPresenter
import javax.inject.Inject

class NewsFeedFragment : Fragment(), NewsFeedView {

    @Inject
    lateinit var newsFeedPresenter: NewsFeedPresenter

    private var newsFeedAdapter: NewsFeedAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        YourChoiceApp.appComponent
            .newsFeedComponent()
            .withActivity(activity!! as AppCompatActivity)
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.news_feed_fragment, container, false)
        setHasOptionsMenu(true)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsFeedPresenter.attachView(this)
        activity?.tv_page_title?.setText("")
        swipeContainer.setOnRefreshListener {
            refreshNewsFeed()
        }
        rv_news_feed.apply {
            layoutManager = LinearLayoutManager(rootView.context)
        }
        initAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.news_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initAdapter() {
        newsFeedAdapter = NewsFeedAdapter {
            newsFeedPresenter.onNewsClick(it)
        }
        rv_news_feed.adapter = newsFeedAdapter
        newsFeedPresenter.updateNewsFeed()
    }

    override fun updateListView(list: MutableList<Post>) {
        newsFeedAdapter?.submitList(list)
    }

    override fun addItemsToListView(list: List<Post>) {
        newsFeedAdapter?.submitList(list)    }

    override fun showProgress() {
        swipeContainer.isRefreshing = true
    }

    override fun hideProgress() {
        swipeContainer.isRefreshing = false
    }

    override fun navigateToDetails(post: Post) {
        // TODO replace fragment with one news
    }

    private fun refreshNewsFeed() {
        newsFeedPresenter.updateNewsFeed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        newsFeedPresenter.detachView()
    }

    companion object {
        fun newInstance() = NewsFeedFragment()
    }
}
