package xyz.xandsoft.diuerp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xyz.xandsoft.diuerp.R
import xyz.xandsoft.diuerp.interfaces.OnAuthCallBack
import xyz.xandsoft.diuerp.repositories.network.FirebaseRepository
import xyz.xandsoft.diuerp.utils.hideProgressbar
import xyz.xandsoft.diuerp.utils.showProgressbar
import xyz.xandsoft.diuerp.utils.showShortToast

class LoginFragment : Fragment() {

    private val TAG = "LoginFragment"

    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText

    private lateinit var loginProgressBar: ProgressBar

    private lateinit var loginBtn: Button
    private lateinit var registerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // initializing
        init(view)

        loginBtn.setOnClickListener {
            val email = loginEmail.text.toString().trim()
            val password = loginPassword.text.toString().trim()

            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                activity?.showShortToast("Both fields are required")
            } else {
                letUserLogin(email, password)
            }
        }

        registerBtn.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.auth_container, RegisterFragment())
                ?.addToBackStack("")
                ?.commit()
        }
    }

    private fun init(view: View) {

        loginEmail = view.findViewById(R.id.login_email)
        loginPassword = view.findViewById(R.id.login_password)

        loginProgressBar = view.findViewById(R.id.login_progressbar)

        loginBtn = view.findViewById(R.id.login_btn)
        registerBtn = view.findViewById(R.id.account_create_btn)
    }

    private fun letUserLogin(email: String, password: String) {
        lifecycleScope.launch {
            FirebaseRepository.letLogin(object : OnAuthCallBack {
                override fun onAuthStarted() {
                    loginProgressBar.showProgressbar()
                }

                override fun onAuthSuccess() {
                    loginProgressBar.hideProgressbar()
                }

                override fun onAuthFailed(messge: String) {
                    loginProgressBar.hideProgressbar()
                    activity?.showShortToast(messge)
                }
            }, email, password)
        }

    }
}