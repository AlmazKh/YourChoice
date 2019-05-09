package ru.itis.yourchoice.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import io.reactivex.Single
import ru.itis.yourchoice.core.interfaces.InterestRepository
import ru.itis.yourchoice.core.model.Interest
import javax.inject.Inject

private const val OWNER_ID = "owner_id"
private const val SUBCATEGORY_ID = "subcategory_id"
private const val INTERESTS = "interests"

class InterestRepositoryImpl
@Inject constructor(
        private val firebaseAuth: FirebaseAuth,
        private val db: FirebaseFirestore
) : InterestRepository {

    override fun getUsersInterests(): Single<List<Interest>> {
        return Single.create { emitter ->
            db.collection(INTERESTS)
                    .whereEqualTo(OWNER_ID, firebaseAuth.currentUser?.uid)
                    .get()
                    .addOnSuccessListener { documents ->
                        val list = documents.map {
                            mapDocumetToInterest(it)
                        }
                        emitter.onSuccess(list)
                    }
                    .addOnFailureListener { exception ->
                        emitter.onError(exception)
                    }
        }
    }

    private fun mapDocumetToInterest(documentSnapshot: QueryDocumentSnapshot): Interest =
            Interest(
                    documentSnapshot.get(SUBCATEGORY_ID).toString().toInt(),
                    documentSnapshot.get(OWNER_ID).toString()
            )

}