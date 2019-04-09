package ru.itis.yourchoice.view

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*
import ru.itis.yourchoice.R

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_news -> {
                fragmentTransaction.replace(R.id.main_container, NewsFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_addpost -> {
                fragmentTransaction.replace(R.id.main_container, AddPostFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_menu -> {
                fragmentTransaction.replace(R.id.main_container, MenuFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
