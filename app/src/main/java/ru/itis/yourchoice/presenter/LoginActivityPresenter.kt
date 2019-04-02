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
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.login_activity.*
import ru.itis.yourchoice.R
import ru.itis.yourchoice.view.LoginView
import javax.inject.Inject


@InjectViewState
class LoginActivityPresenter
@Inject constructor(): MvpPresenter<LoginView>(), GoogleApiClient.OnConnectionFailedListener {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    fun init(context: Context) {
        mAuth = FirebaseAuth.getInstance()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    fun onSignInClick() {
//        viewState.signInGoogle(mGoogleSignInClient)
        viewState.signInGoogle()
        //mGoogleSignInClient.let { viewState.signInGoogle(it) }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("MYLOG", "connection failed")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("MYLOG", "krasava")
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

//    fun checkRequestCode(requestCode: Int, resultCode: Int, data: Intent?) {
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                val account = task.getResult(ApiException::class.java)
//                firebaseAuthWithGoogle(account!!)
//            } catch (e: ApiException) {
//                // Google Sign In failed, update UI appropriately
//                // ...
//            }
//        }
//    }
//
//    companion object {
//        internal val RC_SIGN_IN = 228
//    }

}