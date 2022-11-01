package xyz.xandsoft.diuerp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import xyz.xandsoft.diuerp.R
import xyz.xandsoft.diuerp.interfaces.OnAuthCallBack
import xyz.xandsoft.diuerp.repositories.datamodels.UserDataModel
import xyz.xandsoft.diuerp.repositories.network.FirebaseRepository
import xyz.xandsoft.diuerp.utils.hideProgressbar
import xyz.xandsoft.diuerp.utils.showProgressbar
import xyz.xandsoft.diuerp.utils.showShortToast

class RegisterFragment : Fragment() {

    private lateinit var fullNameET: EditText
    private lateinit var emailET: EditText
    private lateinit var phoneET: EditText
    private lateinit var addressET: EditText
    private lateinit var passwordET: EditText

    private lateinit var progressBar: ProgressBar

    private lateinit var signupBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        signupBtn.setOnClickListener {
            val fullName = fullNameET.text.toString().trim()
            val email = emailET.text.toString().trim()
            val phone = phoneET.text.toString().trim()
            val address = addressET.text.toString().trim()
            val password = passwordET.text.toString().trim()

            if (fullName.isNullOrEmpty() || email.isNullOrEmpty() || phone.isNullOrEmpty() ||
                address.isNullOrEmpty() || password.isNullOrEmpty()
            ) {
                activity?.showShortToast("All fields are required")
            } else {
                val userDataModel = UserDataModel(fullName, email, address, phone)
                lifecycleScope.launch {
                    FirebaseRepository.letUserRegister(object : OnAuthCallBack {
                        override fun onAuthStarted() {
                            progressBar.showProgressbar()
                        }

                        override fun onAuthSuccess() {
                            progressBar.hideProgressbar()
                        }

                        override fun onAuthFailed(messge: String) {
                            progressBar.hideProgressbar()
                            activity?.showShortToast(messge)
                        }
                    }, userDataModel, password)
                }
            }
        }
    }

    private fun init() {

        fullNameET = activity?.findViewById(R.id.signup_full_name)!!
        emailET = activity?.findViewById(R.id.signup_email)!!
        phoneET = activity?.findViewById(R.id.signup_phone)!!
        addressET = activity?.findViewById(R.id.signup_address)!!
        passwordET = activity?.findViewById(R.id.signup_pass)!!

        progressBar = activity?.findViewById(R.id.signup_progressbar)!!

        signupBtn = activity?.findViewById(R.id.signup_btn)!!
    }
}