package com.example.wallpaper.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.wallpaper.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        if (firebaseAuth.currentUser == null) {

            tv_register.text = "Creating New Accaunt"

            firebaseAuth.signInAnonymously().addOnCompleteListener {

                if (it.isSuccessful) {

                    tv_register.text = "Accaunt Created , Logging in"

                    navController!!.navigate(R.id.action_registerFragment_to_homeFragment)

                } else {

                    tv_register.text = "No Internet Connection!"

                }

            }

        } else {
            navController!!.navigate(R.id.action_registerFragment_to_homeFragment)

        }

    }


}
