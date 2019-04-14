package ru.itis.yourchoice.view.login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.login_with_phone_dialog.*
import kotlinx.android.synthetic.main.login_with_phone_dialog.view.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.di.component.DaggerActivityComponent
import ru.itis.yourchoice.di.module.PresenterModule
import ru.itis.yourchoice.presenter.login.LoginWithPhoneDialogPresenter
import javax.inject.Inject


class LoginWithPhoneDialog : MvpAppCompatDialogFragment(), LoginWithPhoneView {

    @Inject
    @InjectPresenter
    lateinit var loginPresenter: LoginWithPhoneDialogPresenter

    @ProvidePresenter
    fun provideLoginWithPhoneDialogPresenter() = loginPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        injectDependency()
        dialog.setTitle("Sign in with phone")
        Log.d("MYLOG", "onCreateView")
        val v = inflater.inflate(R.layout.login_with_phone_dialog, null)
        v.get_verification_code_btn.setOnClickListener{sendVerificationCode()}
        v.sign_in_btn.setOnClickListener{verifySignInCode()}
        return v
    }

    override fun sendVerificationCode() {
        loginPresenter.sendVerificationCode(phone_et.toString())
    }

    override fun verifySignInCode() {
        loginPresenter.verifySignInCode(verification_code_et.toString())
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .presenterModule(PresenterModule())
            .build()
        activityComponent.inject(this)
    }

    companion object {
        fun newInstance() = LoginWithPhoneDialog()
    }

}