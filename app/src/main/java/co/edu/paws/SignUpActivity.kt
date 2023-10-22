package co.edu.paws


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore


class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val btnSignIn = this.findViewById<Button>(R.id.btnSignIn)
        val btnSignUp = this.findViewById<Button>(R.id.btnSignUp)
        val intent = Intent(this, MainActivity::class.java)

        btnSignIn.setOnClickListener{
            startActivity(intent)
        }
        btnSignUp.setOnClickListener{
            signUp()
        }

    }

    fun signUp (){
        val auth = FirebaseAuth.getInstance()

        val editTextName = this.findViewById<EditText>(R.id.editTextName)
        val editTextEmail = this.findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = this.findViewById<EditText>(R.id.editTextPassword)
        val editTextConfirmPassword = this.findViewById<EditText>(R.id.editTextConfirmPassword)

        val name = editTextName.text.toString()
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()
        val confirmPassword = editTextConfirmPassword.text.toString()

        if(name != "" && email != ""  && password != "" && confirmPassword != ""){
            if(email.contains("@") && email.contains(".")){
                if(password.length >= 8){
                    if (password == confirmPassword){

                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    val user = FirebaseAuth.getInstance().currentUser



                                    if (user != null) {
                                        val db = Firebase.firestore
                                        val new_user = hashMapOf(
                                            "id" to user.uid,
                                            "name" to name,
                                            "email" to user.email
                                        )

                                        db.collection("users")
                                            .document(user.uid + "")
                                            .set(new_user)
                                            .addOnSuccessListener {

                                                user.let {
                                                    user.sendEmailVerification()
                                                        .addOnCompleteListener { task ->
                                                            if (task.isSuccessful) {
                                                                val intent = Intent(this, SignInActivity::class.java)
                                                                startActivity(intent)
                                                                Toast.makeText(baseContext,"We have sent a verification email to your inbox. Please confirm your email address to sign in.",Toast.LENGTH_SHORT,).show()
                                                            } else {
                                                                Toast.makeText(baseContext,"Authentication failed.",Toast.LENGTH_SHORT,).show()
                                                            }
                                                        }
                                                }

                                            }
                                            .addOnFailureListener { e ->
                                                Toast.makeText(baseContext,e.toString(),Toast.LENGTH_SHORT,).show()
                                            }
                                    }
                                }
                            }
                            .addOnFailureListener { e ->
                                if(e.toString() == "com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account."){
                                    Toast.makeText(baseContext, "The email is already associated with an account.", Toast.LENGTH_SHORT,).show()
                                }
                                else{
                                    Toast.makeText(baseContext, e.toString(), Toast.LENGTH_SHORT,).show()
                                }
                            }
                    }else{
                        Toast.makeText(this, "The two passwords must be the same", Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(this, "The password length must be at least 8 characters.", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "The email is not valid", Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this, "All the fields are required", Toast.LENGTH_LONG).show()
        }
    }
}