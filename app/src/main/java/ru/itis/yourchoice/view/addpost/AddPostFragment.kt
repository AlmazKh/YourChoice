package ru.itis.yourchoice.view.addpost

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.arellomobile.mvp.MvpAppCompatFragment
import ru.itis.yourchoice.R
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.add_post_fragment.*
import kotlinx.android.synthetic.main.main_activity.*
import ru.itis.yourchoice.di.component.DaggerActivityComponent
import ru.itis.yourchoice.di.module.PresenterModule
import ru.itis.yourchoice.presenter.addpost.AddPostPresenter
import javax.inject.Inject

class AddPostFragment : MvpAppCompatFragment(), AddPostView {

    @Inject
    @InjectPresenter
    lateinit var addPostPresenter: AddPostPresenter

    @ProvidePresenter
    fun provideAddPostPresenter() = addPostPresenter

//    var categories = arrayListOf("Films", "Series", "Events", "Books")

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        injectDependency()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.add_post_fragment, container, false)
        setHasOptionsMenu(true)
        return v;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.tv_page_title?.setText(R.string.title_addpost)
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, addPostPresenter.getMainCategories())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        select_category_spinner.setAdapter(adapter)
        select_category_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                addPostPresenter.getCategories(parent?.getItemAtPosition(position))
            }

        }
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.addpost_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            R.id.addpost_approve -> {
                addPostPresenter.addPost(
                        select_subcategory_spinner.selectedItem.toString(),
                        et_description.text.toString()
                )
                return true
            }
            R.id.addpost_delet -> {
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun updateUI(categories: List<String>) {
        Log.d("MYLOG", "updateUI with categories")
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        select_subcategory_spinner.setAdapter(adapter)
    }


    override fun getCategories(mainCategory: Int) {
        TODO("not implemented") //ge
    }

    override fun addPost(mainCategory: Int, category: String, description: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .presenterModule(PresenterModule())
            .build()
        activityComponent.inject(this)
    }

    companion object {
        fun newInstance() = AddPostFragment()
    }
}