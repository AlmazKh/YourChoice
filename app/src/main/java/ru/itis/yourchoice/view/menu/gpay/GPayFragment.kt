package ru.itis.yourchoice.view.menu.gpay

import android.app.Activity
import android.content.Intent
import android.media.session.MediaSession
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.wallet.*
import org.json.JSONException
import org.json.JSONObject
import ru.itis.yourchoice.BuildConfig
import ru.itis.yourchoice.R
import ru.itis.yourchoice.YourChoiceApp
import org.json.JSONArray
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.wallet.IsReadyToPayRequest
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.gpay_fragment.*
import com.google.android.gms.wallet.AutoResolveHelper
import com.google.android.gms.wallet.PaymentDataRequest

class GPayFragment : Fragment(), GPayView {

    lateinit var paymentsClient: PaymentsClient
    lateinit var gPayRequest: GPayRequest
    private val LOAD_PAYMENT_DATA_REQUEST_CODE = 42;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        YourChoiceApp.appComponent
                .menuComponent()
                .withActivity(activity!! as AppCompatActivity)
                .build()
                .inject(this)

        gPayRequest = GPayRequest()

        paymentsClient = Wallet.getPaymentsClient(
                activity!! as AppCompatActivity,
                Wallet.WalletOptions.Builder()
                        .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                        .build())

        possiblyShowGooglePayButton()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.gpay_fragment, container, false)
    }

    private fun possiblyShowGooglePayButton() {
        val isReadyToPayJson = gPayRequest.getIsReadyToPayRequest(paymentsClient, listener = OnCompleteListener {
            try {
                val result = it.getResult(ApiException::class.java)?:false
                if (result) {
                    btn_gpay.setOnClickListener { view -> requestPayment(view) }
                    btn_gpay.setVisibility(View.VISIBLE)
                }
            } catch (exception: ApiException) {
                // handle developer errors
            }
        })
    }

    fun requestPayment(view: View) {
        val request = gPayRequest.getPaymentDataRequest()
        if (request != null) {
            AutoResolveHelper.resolveTask(
                    paymentsClient.loadPaymentData(request), activity!!, LOAD_PAYMENT_DATA_REQUEST_CODE)
        }
    }


    private val environment: Int
        get() = if (BuildConfig.FLAVOR == FLAVOR_PRODUCTION) {
            WalletConstants.ENVIRONMENT_TEST
        } else {
            WalletConstants.ENVIRONMENT_PRODUCTION
        }


    fun getIsReadyToPayRequest(): JSONObject {
        val isReadyToPayRequest = getBaseRequest()
        isReadyToPayRequest.put(
                "allowedPaymentMethods",
                JSONArray()
                        .put(gPayRequest.getIsReadyToPayRequest()))

        return isReadyToPayRequest
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LOAD_PAYMENT_DATA_REQUEST_CODE ->
                when (resultCode) {
                    Activity.RESULT_OK ->
                        data?.let {
                            val paymentData = PaymentData.getFromIntent(data)
                            val tokenJSON = paymentData?.paymentMethodToken?.token
                            MediaSession.Token.fromString(tokenJSON)?.let { token ->
                                presenter.onGooglePayTokenParsed(token.id)
                            } ?: run {
                                Timber.e(GP_TOKEN_PARSE_FAILED)
                            }
                        }
                    Activity.RESULT_CANCELED -> hideLoading()
                    AutoResolveHelper.RESULT_ERROR -> {
                        data?.let {
                            val status = AutoResolveHelper.getStatusFromIntent(data)
                            GooglePayHelper.handleGooglePayError(status?.statusCode)
                        } ?: run { hideLoading() }
                    }
                    else -> {
                    }
                }
        }
    }

    companion object {
        fun newInstance() = GPayFragment()
    }
}