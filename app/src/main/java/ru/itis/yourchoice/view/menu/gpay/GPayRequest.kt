package ru.itis.yourchoice.view.menu.gpay

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentDataRequest
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.WalletConstants
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class GPayRequest {

    private fun getBaseRequest(): JSONObject = JSONObject()
            .put("apiVersion", 2)
            .put("apiVersionMinor", 0)

    private fun createTokenizationParameters(): JSONObject {
        val tokenizationSpecification = JSONObject()
        tokenizationSpecification.put("type", "PAYMENT_GATEWAY")
        tokenizationSpecification.put(
                "parameters",
                JSONObject()
                        .put("gateway", "example")
                        .put("gatewayMerchantId", "exampleGatewayMerchantId"))

        return tokenizationSpecification
    }

    private fun getAllowedCardAuthMethods(): JSONArray {
        return JSONArray()
                .put("PAN_ONLY")
                .put("CRYPTOGRAM_3DS")
    }

    private fun getAllowedCardNetworks(): JSONArray {
        return JSONArray()
                .put("AMEX")
                .put("DISCOVER")
                .put("INTERAC")
                .put("JCB")
                .put("MASTERCARD")
                .put("VISA")
    }


    private fun getBaseCardPaymentMethod(): JSONObject {
        val cardPaymentMethod = JSONObject()
        cardPaymentMethod.put("type", "CARD")
        cardPaymentMethod.put(
                "parameters",
                JSONObject()
                        .put("allowedAuthMethods", getAllowedCardAuthMethods())
                        .put("allowedCardNetworks", getAllowedCardNetworks()))

        return cardPaymentMethod
    }

    private fun getCardPaymentMethod(): JSONObject {
        val cardPaymentMethod = getBaseCardPaymentMethod()
        cardPaymentMethod.put("tokenizationSpecification", createTokenizationParameters())

        return cardPaymentMethod
    }

    private fun getTransactionInfo(): JSONObject =
            JSONObject().apply {
                put("totalPrice", "12.34") // не забудьте про Locale
                put("totalPriceStatus", "FINAL")
                put("currencyCode", "USD")
            }

    private val getMerchantInfo: JSONObject
        @Throws(JSONException::class)
        get() = JSONObject().put("merchantName", "merchant_name")

    fun getIsReadyToPayRequest(paymentsClient: PaymentsClient, listener: OnCompleteListener<Boolean>) {
        val isReadyToPayRequest = getBaseRequest()
        isReadyToPayRequest.put(
                "allowedPaymentMethods",
                JSONArray()
                        .put(WalletConstants.PAYMENT_METHOD_CARD)
                        .put(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD))

        val task = paymentsClient.isReadyToPay(IsReadyToPayRequest.fromJson(isReadyToPayRequest.toString()))
        task.addOnCompleteListener(listener)

    }

    fun getPaymentDataRequest(): PaymentDataRequest? {
            val paymentDataRequest = getBaseRequest()
            paymentDataRequest.put(
                    "allowedPaymentMethods",
                    JSONArray()
                            .put(JSONArray()
                                    .put(WalletConstants.PAYMENT_METHOD_CARD)
                                    .put(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD)))
            paymentDataRequest.put("transactionInfo", getTransactionInfo())
            paymentDataRequest.put("merchantInfo", getMerchantInfo)
            paymentDataRequest.put("getAllowedCardNetworks", getAllowedCardNetworks()) // тут не уверен
            paymentDataRequest.put("tokenizationSpecification", createTokenizationParameters())

            return PaymentDataRequest.fromJson(paymentDataRequest.toString())
    }

}
