package xyz.xandsoft.diuerp.repositories.network

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import xyz.xandsoft.diuerp.interfaces.OnAuthCallBack
import xyz.xandsoft.diuerp.repositories.datamodels.UserDataModel

object FirebaseRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val databaseReference = FirebaseDatabase.getInstance().reference

    fun letLogin(onAuthCallBack: OnAuthCallBack, email: String, password: String) {

        onAuthCallBack.onAuthStarted()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) onAuthCallBack.onAuthSuccess()
            }
            .addOnFailureListener { e ->
                onAuthCallBack.onAuthFailed(e.message!!)
            }
    }

    fun letUserRegister(
        onAuthCallBack: OnAuthCallBack,
        userDataModel: UserDataModel,
        password: String
    ) {

        onAuthCallBack.onAuthStarted()

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

    private fun addUserIntoNode(userDataModel: UserDataModel) {
        databaseReference.child("users").child(firebaseAuth.currentUser?.uid!!)
            .setValue(userDataModel)
    }
}