package ru.itis.yourchoice.data.repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single
import ru.itis.yourchoice.core.interfaces.InterestRepository
import ru.itis.yourchoice.core.model.Interest
import javax.inject.Inject

private const val INTERESTS = "interests"

class InterestRepositoryImpl
@Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
): InterestRepository {

    override fun getUsersInterests(): Single<List<Interest>> {
        return Single.create { emitter ->
            db.collection(INTERESTS)
                .whereEqualTo("owner_id", firebaseAuth.currentUser?.uid)
                .get()
                .addOnSuccessListener { documents ->
                    val list: ArrayList<Interest> = ArrayList()
                    for (document in documents) {
                        list.add(document.toObject(Interest::class.java))
                        Log.d("MYLOG", "${document.id} => ${document.data}")
                        Log.d("MYLOG", "${list[0]}")
                    }
                    Log.d("MYLOG", "Success getInterests $list")
                    emitter.onSuccess(list)
                }
                .addOnFailureListener { exception ->
                    emitter.onError(exception)
                    Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                }
        }
    }
}