package ru.itis.yourchoice.presenter.login

import android.content.Intent
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.itis.yourchoice.core.interactors.LoginInteractor
import ru.itis.yourchoice.view.login.LoginView
import javax.inject.Inject

@InjectViewState
class LoginActivityPresenter
@Inject constructor(
    private val loginInteractor: LoginInteractor,
    private val firebaseAuth: FirebaseAuth
) : MvpPresenter<LoginView>(), GoogleApiClient.OnConnectionFailedListener {

    fun onGoogleSignInClick() {
        viewState.signInGoogle()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("MYLOG", "connection failed")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("MYLOG", acct.photoUrl.toString())
        loginInteractor.loginGoogle(acct)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loginInteractor.addUserToDb(acct.displayName, acct.email, null)
                viewState.updateUI()
                viewState.signInSuccess()
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
                // login_btn_background Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                account?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                // login_btn_background Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    fun checkAuthUser() {
        val currentUser = firebaseAuth.currentUser
        currentUser?.let { viewState.updateUI() }
    }

    fun onPhoneSignInClick() {
        viewState.signInPhone()
    }

    companion object {
        internal val RC_SIGN_IN = 228
    }
}
