package ru.itis.yourchoice.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import io.reactivex.Completable
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

    override fun checkInterest(subcategoryId: Int): Single<Boolean> {
        return Single.create { emitter ->
            db.collection(INTERESTS)
                    .whereEqualTo(OWNER_ID, firebaseAuth.currentUser?.uid)
                    .whereEqualTo(SUBCATEGORY_ID, subcategoryId)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (documents == null) {
                            emitter.onSuccess(false)
                        } else {
                            emitter.onSuccess(true)
                        }
                    }
                    .addOnFailureListener { exception ->
                        emitter.onError(exception)
                    }
        }
    }

    override fun addInterest(subcategoryId: Int): Completable {
        val interestMap = HashMap<String, Any?>()
        interestMap[SUBCATEGORY_ID] = subcategoryId
        interestMap[OWNER_ID] = firebaseAuth.currentUser?.uid
        return Completable.create { emitter ->
            db.collection(INTERESTS)
                    .add(interestMap)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            emitter.onComplete()
                        } else {
                            emitter.onError(task.exception ?: Exception(""))
                        }
                    }
        }
    }

    override fun getDocumentId(subcategoryId: Int): Single<String> {
        return Single.create { emitter ->
            db.collection(INTERESTS)
                    .whereEqualTo(OWNER_ID, firebaseAuth.currentUser?.uid)
                    .whereEqualTo(SUBCATEGORY_ID, subcategoryId)
                    .get()
                    .addOnSuccessListener { documents ->
                        documents.forEach { emitter.onSuccess(it.id) }
                    }
        }
    }

    override fun deleteInterest(docId: String): Completable {
       return Completable.create { emitter ->
           db.collection(INTERESTS).document(docId)
                   .delete()
                   .addOnSuccessListener { emitter.onComplete() }
       }
    }

    private fun mapDocumetToInterest(documentSnapshot: QueryDocumentSnapshot): Interest =
            Interest(
                    documentSnapshot.get(SUBCATEGORY_ID).toString().toInt(),
                    documentSnapshot.get(OWNER_ID).toString()
            )

}