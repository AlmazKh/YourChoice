package ru.itis.yourchoice.presenter

import android.content.Context
import android.content.Intent
import android.support.design.widget.Snackbar
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.login_activity.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.core.interactors.LoginInteractor
import ru.itis.yourchoice.view.LoginActivity
import ru.itis.yourchoice.view.LoginView
import javax.inject.Inject

@InjectViewState
class LoginActivityPresenter
@Inject constructor(
    private val loginInteractor: LoginInteractor,
    private val firebaseAuth: FirebaseAuth
) : MvpPresenter<LoginView>(), GoogleApiClient.OnConnectionFailedListener {

    fun onSignInClick() {
        viewState.signInGoogle()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("MYLOG", "connection failed")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        loginInteractor.login(acct)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.updateUI()
            }, {
                viewState.showError(it.message ?: "Login error")
                it.printStackTrace()
            })
    }

    fun onGoogleIntentResult(requestCode: Int, data: Intent?) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                account?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    fun checkAuthUser() {
        val currentUser = firebaseAuth.currentUser
        currentUser?.let{ viewState.updateUI()}
    }

    companion object {
        internal val RC_SIGN_IN = 228
    }
}
