package ru.itis.yourchoice.view.login

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.login_with_phone_dialog.*
import kotlinx.android.synthetic.main.login_with_phone_dialog.view.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.di.component.DaggerActivityComponent
import ru.itis.yourchoice.di.module.PresenterModule
import ru.itis.yourchoice.presenter.login.LoginWithPhoneDialogPresenter
import ru.itis.yourchoice.view.MainActivity
import javax.inject.Inject


class LoginWithPhoneDialog : MvpAppCompatDialogFragment(), LoginWithPhoneView {

    @Inject
    @InjectPresenter
    lateinit var loginPresenter: LoginWithPhoneDialogPresenter

    @ProvidePresenter
    fun provideLoginWithPhoneDialogPresenter() = loginPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.setTitle("Sign in with phone")
        val v = inflater.inflate(R.layout.login_with_phone_dialog, null)
        v.get_verification_code_btn.setOnClickListener{sendVerificationCode()}
        v.sign_in_btn.setOnClickListener{verifySignInCode()}
        return v
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        injectDependency()
    }

    override fun sendVerificationCode() {
        loginPresenter.sendVerificationCode(phone_et.text.toString())

    }

    override fun verifySignInCode() {
        loginPresenter.verifySignInCode(verification_code_et.text.toString(), user_name_et.text.toString())
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .presenterModule(PresenterModule())
            .build()
        activityComponent.inject(this)
    }

    override fun updateUI() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun signInSuccess() {
        Toast.makeText(activity,
            "Login successful", Toast.LENGTH_LONG).show();
    }

    override fun codeIsSending() {
        Toast.makeText(activity,
            "Wait, your code is sending", Toast.LENGTH_LONG).show()
    }

    override fun showError(errorText: String) {
        Log.d("MYLOG", errorText)
        Toast.makeText(activity,
            errorText, Toast.LENGTH_LONG).show();
    }

    companion object {
        fun newInstance() = LoginWithPhoneDialog()
    }
}
