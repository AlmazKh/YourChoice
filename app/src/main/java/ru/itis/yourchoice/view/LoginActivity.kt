package ru.itis.yourchoice.view

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.login_activity.*
import ru.itis.yourchoice.R

class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    private val RC_SIGN_IN = 9001
//    private var mGoogleApiClient: GoogleApiClient? = null
//    private var mFirebaseAuth: FirebaseAuth? = null
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.login_activity)
//        init()
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
//            handleSignInResult(result)
//        }
//    }
//
//    private fun init() {
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.web_client_id))
//            .requestEmail()
//            .build()
//        if (mGoogleApiClient == null || mGoogleApiClient?.isConnected == false) {
//            mGoogleApiClient =
//                GoogleApiClient.Builder(this)
//                    .enableAutoManage(this, this)
//                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                    .build()
//
//        }
//
//        google_sign_in_btn.setOnClickListener { v -> signIn() }
//
//    }
//
//    private fun signIn() {
//        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//    }
//
//    private fun handleSignInResult(result: GoogleSignInResult) {
//        Log.d("MYLOG", "handleSignInResult:" + result.status)
//        if (result.isSuccess) {
//            // Signed in successfully, show authenticated UI.
//            val acct = result.signInAccount
//            acct?.let { authWithGoogle(it) }
//        } else {
//            // Signed out, show unauthenticated UI.
//        }
//    }
//
//    private fun authWithGoogle(account: GoogleSignInAccount) {
//        mFirebaseAuth = FirebaseAuth.getInstance()
//        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
//        mFirebaseAuth?.signInWithCredential(credential)?.addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                Toast.makeText(this, "Auth OK", Toast.LENGTH_SHORT).show()
//                //startActivity(Intent(activity, MainActivity::class.java))
//                //finish()
//            } else {
//                Toast.makeText(this, "Auth Error ${task.exception}", Toast.LENGTH_SHORT).show()
//                Log.d("MYLOG", "Auth Error ${task.exception}")
//
//            }
//        }
//    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        mAuth = FirebaseAuth.getInstance()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        google_sign_in_btn.setOnClickListener { v -> SignInGoogle() }


    }

//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = mAuth.currentUser
//        updateUI(currentUser)
//    }


    fun SignInGoogle() {
//        progressBar.setVisibility(View.VISIBLE)
        val signInIntent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("MYLOG", "krasava")
                    // Sign in success, update UI with the signed-in user's information
//                    val user = mAuth.currentUser
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d("MYLOG", "daun")
                    Snackbar.make(login_activity, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
//                    updateUI(null)
                }

                // ...
            }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

//    private fun updateUI(user: FirebaseUser?) {
//        if (user != null) {
//            val name = user!!.getDisplayName()
//            val email = user!!.getEmail()
//            val photo = user!!.getPhotoUrl().toString()
//
//            text.append("Info : \n")
//            text.append(name + "\n")
//            text.append(email)
//            Picasso.with(this@LoginActivity).load(photo).into(image)
//            btn_logout.visibility = View.VISIBLE
//            btn_login.visibility = View.INVISIBLE
//        } else {
//            text.setText("Firebase Login \n")
//            Picasso.with(this@LoginActivity).load(R.drawable.ic_firebase_logo).into(image)
//            btn_logout.visibility = View.INVISIBLE
//            btn_login.visibility = View.VISIBLE
//        }
//    }

    companion object {
        internal val RC_SIGN_IN = 228
    }

}
