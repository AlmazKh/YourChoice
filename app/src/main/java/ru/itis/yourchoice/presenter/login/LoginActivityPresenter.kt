package ru.itis.yourchoice.presenter.login

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.itis.yourchoice.core.interactors.LoginInteractor
import ru.itis.yourchoice.presenter.base.BasePresenter
import ru.itis.yourchoice.view.login.LoginActivity
import ru.itis.yourchoice.view.login.LoginView

class LoginActivityPresenter(
    private val loginInteractor: LoginInteractor,
    private val googleSignInClient: GoogleSignInClient
) : BasePresenter<LoginView>(), GoogleApiClient.OnConnectionFailedListener {

    fun onGoogleSignInClick(activity: LoginActivity) {
        val signInIntent = googleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("MYLOG", "connection failed")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("MYLOG", acct.photoUrl.toString())
        disposables.add(
            loginInteractor.loginWithGoogle(acct)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.signInSuccess()
                }, {
                    view?.showError(it.message ?: "Login error")
                    it.printStackTrace()
                })
        )
    }

    fun onGoogleIntentResult(requestCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let { firebaseAuthWithGoogle(it) }
            } catch (e: ApiException) {
                // login_btn_background Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    fun checkAuthUser() {
        if(loginInteractor.checkAuthUser()) {
            view?.signInSuccess()
        }
    }

    fun onPhoneSignInClick() {
        view?.showLoginWithPhoneDialog()
    }

    companion object {
        internal val RC_SIGN_IN = 228
    }
}
