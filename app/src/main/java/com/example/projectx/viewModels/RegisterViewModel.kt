package com.example.projectx.viewModels

import androidx.lifecycle.ViewModel
import com.example.projectx.data.User
import com.example.projectx.util.CheckVauleEmail
import com.example.projectx.util.CheckVaulePassword
import com.example.projectx.util.Constants
import com.example.projectx.util.RegisterCheck
import com.example.projectx.util.RegisterCheckStates
import com.example.projectx.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel(){

    private val _register = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val register : Flow<Resource<User>> = _register

    private val _check = Channel<RegisterCheckStates>()
    val check = _check.receiveAsFlow()

    fun createAccountWithEmailAndPass(user: User, password: String){

        if (CheckVauleBefor(user, password)){
            runBlocking {
                _register.emit(Resource.Loading())
            }

            firebaseAuth.createUserWithEmailAndPassword(user.email, password)
                .addOnSuccessListener {
                    it.user?.let{
                        saveUserInfo(it.uid, user)
                        //_register.value = Resource.Success(it)
                    }
                }
                .addOnFailureListener{
                    _register.value = Resource.Error(it.message.toString())
                }
        }
        else {
            val registerCheckStates = RegisterCheckStates(CheckVauleEmail(user.email), CheckVaulePassword(password))
            runBlocking {
                _check.send(registerCheckStates)
            }
        }
    }

    private fun saveUserInfo(userUid: String, user: User) {
        db.collection(Constants.USER_COLECTION)
            .document(userUid)
            .set(user)
            .addOnSuccessListener {
                _register.value = Resource.Success(user)
            }
            .addOnFailureListener{
                _register.value = Resource.Error(it.message.toString())
            }
    }

    private fun CheckVauleBefor(user: User, password: String) : Boolean {
        val emailCheck = CheckVauleEmail(user.email)
        val passwordCheck = CheckVaulePassword(password)
        val SuccesRegister =
            emailCheck is RegisterCheck.Succes && passwordCheck is RegisterCheck.Succes
        return SuccesRegister
    }
}