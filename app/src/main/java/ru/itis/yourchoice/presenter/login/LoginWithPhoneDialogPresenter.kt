package ru.itis.yourchoice.presenter.login

import android.content.ContentValues
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import ru.itis.yourchoice.core.interactors.LoginInteractor
import ru.itis.yourchoice.view.login.LoginView
import ru.itis.yourchoice.view.login.LoginWithPhoneView
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val PHONE_NUMBER_LENGHT = 10

@InjectViewState
class LoginWithPhoneDialogPresenter
@Inject constructor(
    private val loginInteractor: LoginInteractor
) : MvpPresenter<LoginWithPhoneView>() {

    private lateinit var storedVerificationId: String
    private lateinit var storedPhoneNumber: String

    fun sendVerificationCode(phoneNumber: String, userName: String) {
        if (phoneNumber.isEmpty()) {
            return
        }
        if (phoneNumber.length < PHONE_NUMBER_LENGHT) {
            return
        }
        loginInteractor.sendVerificationCode(phoneNumber)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onSuccess = {
                viewState.codeIsSending()
                storedVerificationId = it
                storedPhoneNumber = phoneNumber
            }, onComplete = {
                viewState.updateUI()
                viewState.signInSuccess()
                Log.d("MYLOG", "presenter sendCode ${loginInteractor.getCurrentUser()}")
            }, onError = {})

        Log.d("MYLOG", "presenter sendCode")
    }

    fun verifySignInCode(verificationCode: String, userName: String) {
        loginInteractor.loginPhone(storedVerificationId, verificationCode)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loginInteractor.addUserToDb(userName, null, storedPhoneNumber)
                viewState.updateUI()
                viewState.signInSuccess()
            }, {
                viewState.showError(it.message ?: "Login error")
                it.printStackTrace()
            })
    }

    fun getCurrentUser(): FirebaseUser? = loginInteractor.getCurrentUser()
}
