package ru.itis.yourchoice.view.addpost

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.add_post_fragment.*
import kotlinx.android.synthetic.main.main_activity.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.YourChoiceApp
import ru.itis.yourchoice.presenter.addpost.AddPostPresenter
import javax.inject.Inject

class AddPostFragment : Fragment(), AddPostView {

    @Inject
    lateinit var addPostPresenter: AddPostPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        YourChoiceApp.appComponent
                .appPostComponent()
                .withActivity(activity!! as AppCompatActivity)
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.add_post_fragment, container, false)
        setHasOptionsMenu(true)
        return v;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addPostPresenter.attachView(this)
        activity?.tv_page_title?.setText(R.string.title_addpost)
        addPostPresenter.getMainCategories()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.addpost_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.addpost_approve -> {
                if (checkFields()) {
                    showFieldsFillError()
                } else {
                    addPostPresenter.addPost(
                            select_subcategory_spinner.selectedItem.toString(),
                            et_post_name.text.toString(),
                            et_description.text.toString()
                    )
                }
                return true
            }
            R.id.addpost_delet -> {
                cleanFields()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun checkFields(): Boolean =
            et_post_name.text.toString().trim().equals("") || et_description.text.toString().trim().equals("")

    override fun cleanFields() {
        et_post_name.setText("")
        et_description.setText("")
    }

    override fun updateUIwithMainCategories(categories: List<String>) {
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        select_category_spinner.setAdapter(adapter)
        select_category_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                addPostPresenter.getSubcategories(parent?.getItemAtPosition(position).toString())
            }
        }
    }

    override fun updateUIwithSubcategories(categories: List<String>) {
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        select_subcategory_spinner.setAdapter(adapter)
    }

    override fun postAddedSuccessful() {
        cleanFields()
        Toast.makeText(context,
                R.string.post_add_success, Toast.LENGTH_LONG).show()
    }

    override fun showError(errorText: String) =
            Toast.makeText(context,
                    errorText, Toast.LENGTH_LONG).show()

    override fun showFieldsFillError() =
            Toast.makeText(context,
                    R.string.err_fill_all_fields, Toast.LENGTH_SHORT).show()


    override fun onDestroyView() {
        super.onDestroyView()
        addPostPresenter.detachView()
    }

    companion object {
        fun newInstance() = AddPostFragment()
    }
}
