package ru.itis.yourchoice.view

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.view.addpost.AddPostFragment
import ru.itis.yourchoice.view.news.NewsFeedFragment
import ru.itis.yourchoice.view.menu.MenuFragment
import ru.itis.yourchoice.view.news.PostFragment

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_news -> {
                fragmentTransaction.replace(R.id.main_container, NewsFeedFragment.newInstance()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_addpost -> {
                fragmentTransaction.replace(R.id.main_container, AddPostFragment.newInstance()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_menu -> {
                fragmentTransaction.replace(R.id.main_container, MenuFragment.newInstance()).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        init()
    }

    private fun init() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false);
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    fun navigateTo(fragment: String, arguments: Bundle?) {
        val transaction =
                supportFragmentManager.beginTransaction()
        when (fragment) {
            PostFragment.toString() -> {
                transaction.replace(
                        R.id.main_container,
                        PostFragment.newInstance(arguments)
                )
            }
        }
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
