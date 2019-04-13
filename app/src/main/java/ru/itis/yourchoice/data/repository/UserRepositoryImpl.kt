package ru.itis.yourchoice.data.repository

import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Completable
import ru.itis.yourchoice.core.interfaces.UserRepository

class UserRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
): UserRepository {

    override fun login(): Completable {
        Completable.create {

        }
    }
}