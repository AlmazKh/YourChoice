package ru.itis.yourchoice.view.addpost

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import com.arellomobile.mvp.MvpAppCompatFragment
import ru.itis.yourchoice.R
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.add_post_fragment.*
import kotlinx.android.synthetic.main.main_activity.*
import ru.itis.yourchoice.di.component.DaggerActivityComponent
import ru.itis.yourchoice.di.module.PresenterModule
import ru.itis.yourchoice.presenter.addpost.AddPostPresenter
import ru.itis.yourchoice.view.MainActivity
import javax.inject.Inject


class AddPostFragment : MvpAppCompatFragment(), AddPostView {

//    @Inject
    @InjectPresenter
    lateinit var addPostPresenter: AddPostPresenter

//    @ProvidePresenter
//    fun provideAddPostPresenter() = addPostPresenter

    var list_of_items = arrayOf("Item 1", "Item 2", "Item 3")

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        injectDependency()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.add_post_fragment, null)
        setHasOptionsMenu(true)



        return v;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, list_of_items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        select_category_spinner.setAdapter(adapter)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        (activity as MainActivity).toolbar.inflateMenu(R.menu.addpost_toolbar)
        inflater?.inflate(R.menu.addpost_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
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