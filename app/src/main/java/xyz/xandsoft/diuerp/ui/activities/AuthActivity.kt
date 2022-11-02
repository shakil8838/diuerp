package xyz.xandsoft.diuerp.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import xyz.xandsoft.diuerp.R
import xyz.xandsoft.diuerp.ui.fragments.LoginFragment

class AuthActivity : AppCompatActivity() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var authListener: AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        authListener = AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser != null) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.auth_container, LoginFragment())
                    .commit()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        // Attach the listener
        firebaseAuth.addAuthStateListener(authListener)
    }

    override fun onStop() {
        super.onStop()

        // Remove the listener
        firebaseAuth.removeAuthStateListener(authListener)
    }

}