package ru.itis.yourchoice.view.menu.interests

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_interest_item.*
import ru.itis.yourchoice.core.interactors.InterestsInteractor
import ru.itis.yourchoice.core.model.Subcategory
import javax.inject.Inject

class SubcategoryViewHolder(
        override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    @Inject
    lateinit var interestsInteractor: InterestsInteractor

    fun bind(subcategory: Subcategory) {
        tv_subcategory_name.text = subcategory.name
        interestsInteractor.checkInterest(subcategory.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    chb_interest.isChecked = it
                }, {
                    it.printStackTrace()
                })

        chb_interest.setOnClickListener {
            interestsInteractor.changeInterestState(chb_interest.isChecked, subcategory.id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Toast.makeText(containerView.context, "Successfully changed", Toast.LENGTH_SHORT).show()
                    }, {
                        it.printStackTrace()
                    })
        }
    }
}
