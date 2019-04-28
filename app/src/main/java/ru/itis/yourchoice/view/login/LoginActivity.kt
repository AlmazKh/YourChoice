package ru.itis.yourchoice.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.login_activity.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.YourChoiceApp
import ru.itis.yourchoice.presenter.login.LoginActivityPresenter
import ru.itis.yourchoice.presenter.login.LoginActivityPresenter.Companion.RC_SIGN_IN
import ru.itis.yourchoice.view.MainActivity
import ru.itis.yourchoice.view.base.BaseActivity
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginView {

    @Inject
    lateinit var loginPresenter: LoginActivityPresenter

    override val layoutId: Int
        get() = R.layout.login_activity

    override fun inject() {
        YourChoiceApp.appComponent
            .authComponent()
            .withActivity(this)
            .build()
            .inject(this)
    }

    override fun setupView() {
        loginPresenter.attachView(this)
        btn_google_sign_in.setOnClickListener { loginPresenter.onGoogleSignInClick(this) }
        btn_phone_sign_in.setOnClickListener { loginPresenter.onPhoneSignInClick() }
    }

    public override fun onStart() {
        super.onStart()
        loginPresenter.checkAuthUser()
    }

    override fun onDestroy() {
        super.onDestroy()
        loginPresenter.detachView()
    }

    override fun showLoginWithPhoneDialog() {
        LoginWithPhoneDialog.newInstance().show(supportFragmentManager, "loginWithPhoneDialog")
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginPresenter.onGoogleIntentResult(requestCode, data)
    }

    override fun signInSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(getApplicationContext(),R.string.login_success, Toast.LENGTH_LONG).show();
    }

    override fun showError(errorText: String) {
        Log.d("MYLOG", errorText)
        Toast.makeText(getApplicationContext(),
            errorText, Toast.LENGTH_LONG).show();
    }
}
