package ru.itis.yourchoice.presenter.news

import android.util.Log
import io.reactivex.rxkotlin.subscribeBy
import ru.itis.yourchoice.core.interactors.NewsFeedInteractor
import ru.itis.yourchoice.core.model.Post
import ru.itis.yourchoice.presenter.base.BasePresenter
import ru.itis.yourchoice.view.news.NewsFeedView
import javax.inject.Inject

class NewsFeedPresenter(
private val newsFeedInteractor: NewsFeedInteractor
) : BasePresenter<NewsFeedView>() {
    fun updateNewsFeed() = newsFeedInteractor
        .getPostsFromDb()
        .doOnSubscribe { view?.showProgress() }
        .doAfterTerminate { view?.hideProgress() }
        .subscribe({
            Log.d("MYLOG", "presenter suc " + it.toString())
            view?.updateListView(it)
        }, {
            Log.d("MYLOG", "presen error " + it.toString())
            updateNewsFeedFromCache()
        })

    fun updateNewsFeedFromCache() {

    }

    fun onNewsClick(post: Post) = view?.navigateToDetails(post)
}
