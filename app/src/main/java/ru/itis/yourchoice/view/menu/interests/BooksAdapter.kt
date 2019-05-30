package ru.itis.yourchoice.view.menu.interests

import android.support.v7.recyclerview.extensions.ListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_interest_item.view.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.core.model.Subcategory

class BooksAdapter : ListAdapter<Subcategory, SubcategoryViewHolder>(SubcategoryDiffCallback()) {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SubcategoryViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return SubcategoryViewHolder(inflater.inflate(R.layout.fragment_interest_item, p0, false))
    }

    override fun onBindViewHolder(p0: SubcategoryViewHolder, p1: Int) {
        p0.itemView.chb_interest.setOnClickListener {
            p0.itemView.chb_interest.isChecked
        }
        p0.bind(getItem(p1))
    }
}