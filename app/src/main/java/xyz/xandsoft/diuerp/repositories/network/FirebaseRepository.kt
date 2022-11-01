package xyz.xandsoft.diuerp.repositories.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import xyz.xandsoft.diuerp.interfaces.OnAuthCallBack
import xyz.xandsoft.diuerp.repositories.datamodels.UserDataModel

object FirebaseRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference = FirebaseDatabase.getInstance().reference

    suspend fun letLogin(onAuthCallBack: OnAuthCallBack, email: String, password: String) {

        onAuthCallBack.onAuthStarted()

        withContext(Dispatchers.IO) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) onAuthCallBack.onAuthSuccess()
                }
                .addOnFailureListener { e ->
                    onAuthCallBack.onAuthFailed(e.message!!)
                }
        }
    }

    suspend fun letUserRegister(
        onAuthCallBack: OnAuthCallBack,
        userDataModel: UserDataModel,
        password: String
    ) {

        onAuthCallBack.onAuthStarted()

        withContext(Dispatchers.IO) {
            firebaseAuth.createUserWithEmailAndPassword(userDataModel.userEmail!!, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        onAuthCallBack.onAuthSuccess()
                        addUserIntoNode(userDataModel)
                    }
                }
                .addOnFailureListener {
                    onAuthCallBack.onAuthFailed(it.message!!)
                }
        }
    }

    private fun addUserIntoNode(userDataModel: UserDataModel) {
        databaseReference.child("users").child(firebaseAuth.currentUser?.uid!!)
            .setValue(userDataModel)
    }
}