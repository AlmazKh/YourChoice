package ru.itis.yourchoice.core.interfaces

import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.itis.yourchoice.core.model.Category
import ru.itis.yourchoice.core.model.Post
import ru.itis.yourchoice.core.model.User

interface UserRepository {
    fun loginWithGoogle(acct: GoogleSignInAccount): Completable
    fun loginWithPhone(storedVerificationId: String, verificationCode: String, userName: String, phone: String): Completable
    fun sendVerificationCode(phoneNumber: String): Maybe<String>
    fun addUserToDb(name: String?, email: String?, phone: String?, photo: String?)
    fun searchUserInDb(email: String?, phone: String?): Single<Boolean>
    fun getUserFromDbById(id: String): Single<User>
    fun updatePostsListWithUserName(posts: List<Post>): Single<List<Post>>
    fun getCurrentUser(): Single<FirebaseUser?>
    fun checkAuthUser(): Single<Boolean>
}
