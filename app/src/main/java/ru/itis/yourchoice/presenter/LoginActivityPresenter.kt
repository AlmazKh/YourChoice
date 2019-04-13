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
import ru.itis.yourchoice.view.LoginView
import javax.inject.Inject

@InjectViewState
class LoginActivityPresenter
@Inject constructor(
    private val loginInteractor: LoginInteractor
) : MvpPresenter<LoginView>(), GoogleApiClient.OnConnectionFailedListener {

    fun onSignInClick() {
        viewState.signInGoogle()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("MYLOG", "connection failed")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        loginInteractor.login()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                viewState.showError(it.message ?: "")
                it.printStackTrace()
            })
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("MYLOG", "krasava")
                    viewState.updateUI()
                    // Sign in success, update UI with the signed-in user's information
//                    val user = mAuth.currentUser
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("MYLOG", "fail")
//                    updateUI(null)
                }

                // ...
            }
    }

    fun checkAuthUser() {
        val currentUser = mAuth.currentUser
        currentUser?.let{ viewState.updateUI()}
    }
}
