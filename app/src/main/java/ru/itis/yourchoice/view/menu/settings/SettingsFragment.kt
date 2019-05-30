package ru.itis.yourchoice.view.menu.settings

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.common.collect.Iterables.toArray
import kotlinx.android.synthetic.main.fragment_settings.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.YourChoiceApp
import ru.itis.yourchoice.core.model.City
import ru.itis.yourchoice.core.model.User
import ru.itis.yourchoice.presenter.menu.SettingsPresenter
import javax.inject.Inject

class SettingsFragment : Fragment(), SettingsView {

    @Inject
    lateinit var settingsPresenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        YourChoiceApp.appComponent
            .menuComponent()
            .withActivity(activity = AppCompatActivity())
            .build()
            .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsPresenter.attachView(this)
        settingsPresenter.getCurrentUser()
        tv_change_location.setOnClickListener { settingsPresenter.getCities() }
    }

    override fun setData(user: User) {
        tv_user_name_settings.text = user.name
        tv_contact.text = user.email ?: user.phone
        tv_location.text = user.location
    }

    override fun showChangeLocationDialog(cities: ArrayList<String>) {
        val builder = AlertDialog.Builder(context!!)
            .setTitle("Choose a city")
        builder.setSingleChoiceItems(toArray(cities, String::class.java), -1, DialogInterface.OnClickListener { dialog, item ->
            settingsPresenter.setUsersCity(item)
            dialog.dismiss()
        })
        builder.setPositiveButton("Done", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })
        builder.create()
        builder.show()
    }

    override fun showSuccess(message: String, city: City) {
        tv_location.text = city.name
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        settingsPresenter.detachView()
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}