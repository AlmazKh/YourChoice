package ru.itis.yourchoice.view.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.menu_fragment.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.YourChoiceApp
import ru.itis.yourchoice.core.model.Post
import ru.itis.yourchoice.view.MainActivity


class PostFragment: Fragment(), PostFragmentView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        YourChoiceApp.appComponent
            .newsFeedComponent()
            .withActivity(activity!! as AppCompatActivity)
            .build()
            .inject(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                (activity as MainActivity).onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)
        activity?.actionBar?.setDisplayShowHomeEnabled(true)
        return inflater.inflate(R.layout.fragment_news, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Post>("post")
        tv_user_name.text = arguments?.getParcelable<Post>("post")?.owner?.name
        tv_post_name.text = arguments?.getParcelable<Post>("post")?.postName
        tv_post_description.text = arguments?.getParcelable<Post>("post")?.description
        tv_category_name.text = arguments?.getParcelable<Post>("post")?.subcategory?.name

        val transformation = RoundedCornersTransformation(20, 1)

        val requestOptions = RequestOptions()
                .centerCrop()
                .transforms(transformation)

        val thumbnail = Glide.with(this)
                .load(R.drawable.image_placeholder)
                .apply(requestOptions)

        Glide.with(this)
                .load(arguments?.getParcelable<Post>("post")?.owner?.photo)
                .apply(requestOptions)
                .thumbnail(thumbnail)
                .into(iv_user_avatar)
    }

    companion object {
        fun newInstance(arguments: Bundle?): PostFragment {
            val fragment = PostFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}