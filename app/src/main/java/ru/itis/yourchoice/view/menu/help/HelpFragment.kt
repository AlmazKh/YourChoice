package ru.itis.yourchoice.view.menu.help

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_help_and_feeedback.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.YourChoiceApp
import ru.itis.yourchoice.presenter.menu.HelpPresenter
import javax.inject.Inject

class HelpFragment : Fragment(), HelpView {

    @Inject
    lateinit var helpPresenter: HelpPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        YourChoiceApp.appComponent
                .menuComponent()
                .withActivity(activity!! as AppCompatActivity)
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_help_and_feeedback, container, false)
        setHasOptionsMenu(true)
        return v;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        helpPresenter.attachView(this)
//        activity?.tv_page_title?.setText(R.string.title_feedback)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.feedback_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.feedback_approve -> {
                if (checkFields()) {
                    showFieldsFillError()
                } else {
                    helpPresenter.sendMessage(et_feedback.text.toString())
                }
                return true
            }
            R.id.feedback_delete -> {
                cleanFields()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun checkFields(): Boolean =
            et_feedback.text.toString().trim().equals("")

    override fun cleanFields() {
        et_feedback.setText("")
    }

    override fun showSuccess() {
        cleanFields()
        Toast.makeText(context, "Thanks for your feedback", Toast.LENGTH_LONG).show()
    }

    override fun showError(errorText: String) =
            Toast.makeText(context, errorText, Toast.LENGTH_LONG).show()

    override fun showFieldsFillError() =
            Toast.makeText(context, R.string.err_fill_all_fields, Toast.LENGTH_SHORT).show()

    override fun onDestroyView() {
        super.onDestroyView()
        helpPresenter.detachView()
    }

    companion object {
        fun newInstance() = HelpFragment()
    }
}
