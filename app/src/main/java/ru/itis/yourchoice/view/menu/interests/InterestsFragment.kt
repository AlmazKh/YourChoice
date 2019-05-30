package ru.itis.yourchoice.view.menu.interests

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_interests.*
import kotlinx.android.synthetic.main.main_activity.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.YourChoiceApp
import ru.itis.yourchoice.core.model.Interest
import ru.itis.yourchoice.core.model.Subcategory
import ru.itis.yourchoice.presenter.menu.InterestsPresenter
import javax.inject.Inject

class InterestsFragment: Fragment(), InterestsView {

    @Inject
    lateinit var interestsPresenter: InterestsPresenter

    private var filmsAdapter: FilmsAdapter? = null
    private var seriesAdapter: SeriesAdapter? = null
    private var eventsAdapter: EventsAdapter? = null
    private var booksAdapter: BooksAdapter? = null



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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        interestsPresenter.attachView(this)
        activity?.tv_page_title?.setText("Interests")
        rv_films.apply { layoutManager = LinearLayoutManager(rootView.context) }
        rv_series.apply { layoutManager = LinearLayoutManager(rootView.context) }
        rv_events.apply { layoutManager = LinearLayoutManager(rootView.context) }
        rv_books.apply { layoutManager = LinearLayoutManager(rootView.context) }
        initAdapter()
    }

    private fun initAdapter() {
        filmsAdapter = FilmsAdapter()
        seriesAdapter = SeriesAdapter()
        eventsAdapter = EventsAdapter()
        booksAdapter = BooksAdapter()
        rv_films.adapter = filmsAdapter
        rv_series.adapter = seriesAdapter
        rv_events.adapter = eventsAdapter
        rv_books.adapter = booksAdapter
        interestsPresenter.getUsersInterests()
    }

    override fun updateInterestsList(interests: List<Subcategory>) {
        val list1 = mutableListOf<Subcategory>()
        val list2 = mutableListOf<Subcategory>()
        val list3 = mutableListOf<Subcategory>()
        val list4 = mutableListOf<Subcategory>()

        interests.forEach { if (it.categoryId == 1) list1.add(it) }
        interests.forEach { if (it.categoryId == 2) list1.add(it) }
        interests.forEach { if (it.categoryId == 3) list1.add(it) }
        interests.forEach { if (it.categoryId == 4) list1.add(it) }

        filmsAdapter?.submitList(list1)
        seriesAdapter?.submitList(list2)
        eventsAdapter?.submitList(list3)
        booksAdapter?.submitList(list4)
    }

    companion object {
        fun newInstance() = InterestsFragment()
    }
}
