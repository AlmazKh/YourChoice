package ru.itis.yourchoice.view.login

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.login_with_phone_dialog.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.YourChoiceApp
import ru.itis.yourchoice.presenter.login.LoginWithPhoneDialogPresenter
import ru.itis.yourchoice.view.MainActivity
import javax.inject.Inject

class LoginWithPhoneDialog : DialogFragment(), LoginWithPhoneView {

    @Inject
    lateinit var loginPresenter: LoginWithPhoneDialogPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        YourChoiceApp.appComponent
                .authComponent()
                .withActivity(activity!! as AppCompatActivity)
                .build()
                .inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_with_phone_dialog, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginPresenter.attachView(this)
        btn_get_verification_code.setOnClickListener { sendVerificationCode() }
        btn_sign_in.setOnClickListener { verifySignInCode() }
    }

    override fun sendVerificationCode() {
        loginPresenter.sendVerificationCode(et_phone.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loginPresenter.detachView()
    }

    override fun verifySignInCode() {
        loginPresenter.verifySignInCode(et_verification_code.text.toString(), et_user_name.text.toString(), et_phone.text.toString())
    }

    override fun signInSuccess() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(activity, R.string.login_success, Toast.LENGTH_LONG).show();
    }

    override fun codeIsSending() {
        Toast.makeText(activity, R.string.verify_code_sending, Toast.LENGTH_LONG).show()
    }

    override fun showError(errorText: String) {
        Log.d("MYLOG", errorText)
        Toast.makeText(activity, errorText, Toast.LENGTH_LONG).show()
    }

    override fun showErrorPhoneNumberFormat() {
        Toast.makeText(activity, R.string.err_phone_format, Toast.LENGTH_LONG).show()
    }

    override fun showErrorVerificationCode() {
        Toast.makeText(activity, R.string.err_verify_code, Toast.LENGTH_LONG).show()
    }

    override fun showStoredAndWritedPhonesNumberMismatch() {
        Toast.makeText(activity, R.string.err_phones_mismatch, Toast.LENGTH_LONG).show()
    }

    companion object {
        fun newInstance() = LoginWithPhoneDialog()
    }
}
