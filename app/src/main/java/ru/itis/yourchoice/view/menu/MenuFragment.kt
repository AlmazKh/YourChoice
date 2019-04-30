package ru.itis.yourchoice.view.menu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.menu_fragment.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.YourChoiceApp
import ru.itis.yourchoice.view.NewsFragment

class MenuFragment : Fragment(), MenuFragmentView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        YourChoiceApp.appComponent
                .menuComponent()
                .withActivity(activity!! as AppCompatActivity)
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.menu_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.tv_page_title?.setText("YC")
        relativeLayout_profile.setOnClickListener { openProfilePage() }
        tv_interests.setOnClickListener { openInterests() }
        liner_layout_menu.setOnClickListener (View.OnClickListener {
            when(it.id) {
                R.id.tv_likes -> {
                    //likes page
                    Toast.makeText(context,"likes 1", Toast.LENGTH_SHORT).show()
                }
                R.id.tv_interests -> {
                    Toast.makeText(context,"interests 1", Toast.LENGTH_SHORT).show()
                }
            }
        })

        const_layout_menu.setOnClickListener { item ->
            when(item.id) {
                R.id.tv_likes -> {
                    //likes page
                    Toast.makeText(context,"likes 2", Toast.LENGTH_SHORT).show()

                }
                R.id.tv_interests -> {
                    Toast.makeText(context,"interests 2", Toast.LENGTH_SHORT).show()

                }
                R.id.tv_settings -> {
                    Toast.makeText(context,"settings 2", Toast.LENGTH_SHORT).show()

                }
            }
        }

    }

    override fun openProfilePage() {
        Toast.makeText(context,"profile open", Toast.LENGTH_SHORT).show()

    }
    fun openInterests() {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.main_container, InterestsFragment.newInstance())?.commit()
    }


    companion object {
        fun newInstance() = MenuFragment()
    }
}