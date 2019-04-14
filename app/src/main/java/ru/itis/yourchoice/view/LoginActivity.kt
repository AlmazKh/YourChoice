package ru.itis.yourchoice.view

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.login_activity.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.di.component.DaggerActivityComponent
import ru.itis.yourchoice.di.module.PresenterModule
import ru.itis.yourchoice.presenter.LoginActivityPresenter
import ru.itis.yourchoice.presenter.LoginActivityPresenter.Companion.RC_SIGN_IN
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
        google_sign_in_btn.setOnClickListener { loginPresenter.onSignInClick() }
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(errorText: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
