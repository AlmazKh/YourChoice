package ru.itis.yourchoice.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import ru.itis.yourchoice.core.interfaces.HelpRepository
import javax.inject.Inject

private const val MESSAGE = "message"
private const val OWNER_ID = "owner_id"
private const val HELP_AND_FEEDBACK = "help&feedback"

class HelpRepositoryImpl
@Inject constructor(
        private val firebaseAuth: FirebaseAuth,
        private val db: FirebaseFirestore
) : HelpRepository {

    override fun sendFeedback(message: String): Completable {
        val helpMap = HashMap<String, Any?>()
        helpMap[MESSAGE] = message
        helpMap[OWNER_ID] = firebaseAuth.currentUser?.uid
        return Completable.create { emitter ->
            db.collection(HELP_AND_FEEDBACK)
                    .add(helpMap)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            emitter.onComplete()
                        } else {
                            emitter.onError(task.exception ?: Exception(""))
                        }
                    }
        }
    }
}
