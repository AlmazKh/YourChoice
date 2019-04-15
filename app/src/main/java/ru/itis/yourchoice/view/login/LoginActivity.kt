package ru.itis.yourchoice.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.auth.api.signin.*
import kotlinx.android.synthetic.main.login_activity.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.di.component.DaggerActivityComponent
import ru.itis.yourchoice.di.module.PresenterModule
import ru.itis.yourchoice.presenter.login.LoginActivityPresenter
import ru.itis.yourchoice.presenter.login.LoginActivityPresenter.Companion.RC_SIGN_IN
import ru.itis.yourchoice.view.MainActivity
import javax.inject.Inject

class LoginActivity : MvpAppCompatActivity(), LoginView {

    @Inject
    @InjectPresenter
    lateinit var loginPresenter: LoginActivityPresenter

    lateinit var gso: GoogleSignInOptions

    lateinit var mGoogleSignInClient: GoogleSignInClient

    @ProvidePresenter
    fun provideLoginActivityPresenter() = loginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependency()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        init()

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun init() {
        google_sign_in_btn.setOnClickListener { loginPresenter.onGoogleSignInClick() }
        phone_sign_in_btn.setOnClickListener { loginPresenter.onPhoneSignInClick() }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        loginPresenter.checkAuthUser()
    }

    override fun signInGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun signInPhone() {
        LoginWithPhoneDialog.newInstance().show(supportFragmentManager, "loginWithPhoneDialog")    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loginPresenter.onGoogleIntentResult(requestCode, data)
    }

    override fun updateUI() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .presenterModule(PresenterModule())
            .build()
        activityComponent.inject(this)
    }

    override fun signInSuccess() {
        Toast.makeText(getApplicationContext(),
            "Login successful", Toast.LENGTH_LONG).show();
    }

    override fun showError(errorText: String) {
        Log.d("MYLOG", errorText)
        Toast.makeText(getApplicationContext(),
            errorText, Toast.LENGTH_LONG).show();
    }
}
