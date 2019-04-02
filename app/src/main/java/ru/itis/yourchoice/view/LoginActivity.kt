package ru.itis.yourchoice.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.login_activity.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.di.component.DaggerActivityComponent
import ru.itis.yourchoice.di.module.PresenterModule
import ru.itis.yourchoice.presenter.LoginActivityPresenter
import javax.inject.Inject

class LoginActivity : MvpAppCompatActivity(), LoginView {

    @Inject
    @InjectPresenter
    lateinit var loginPresenter: LoginActivityPresenter

    lateinit var gso: GoogleSignInOptions

    lateinit var mGoogleSignInClient: GoogleSignInClient

    /*@ProvidePresenter
    fun provideLoginActivityPresenter() = loginPresenter
*/
    override fun onCreate(savedInstanceState: Bundle?) {
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
        injectDependency()
        loginPresenter.init(this)
        google_sign_in_btn.setOnClickListener { loginPresenter.onSignInClick() }
    }

    // TODO
//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = mAuth.currentUser
//        updateUI(currentUser)
//    }


    override fun signInGoogle(mGoogleSignInClient: GoogleSignInClient) {
//        progressBar.setVisibility(View.VISIBLE)
//        loginPresenter.onSignInClick()
        Log.d("MYLOG","IM HERE!!!!")
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun signInGoogle() {
//        progressBar.setVisibility(View.VISIBLE)
//        loginPresenter.onSignInClick()
        Log.d("MYLOG","IM HERE!!!!")
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                loginPresenter.firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .presenterModule(PresenterModule(this))
            .build()
        activityComponent.inject(this)
    }

    companion object {
        internal val RC_SIGN_IN = 228
    }
}
