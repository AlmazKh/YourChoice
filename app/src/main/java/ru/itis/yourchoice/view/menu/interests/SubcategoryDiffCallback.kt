package ru.itis.yourchoice.view.menu.interests

import android.support.v7.util.DiffUtil
import ru.itis.yourchoice.core.model.Subcategory

class SubcategoryDiffCallback : DiffUtil.ItemCallback<Subcategory>() {

    override fun areItemsTheSame(oldItem: Subcategory, newItem: Subcategory): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Subcategory, newItem: Subcategory): Boolean = oldItem == newItem
}
