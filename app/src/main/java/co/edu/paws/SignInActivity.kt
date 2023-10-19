package co.edu.paws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val btnSignUp = this.findViewById<Button>(R.id.btnSignUp)
        val btnSignIn = this.findViewById<Button>(R.id.btnSignIn)

        btnSignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        btnSignIn.setOnClickListener{
            signIn()
        }
    }


    fun signIn(){
        val editTextEmail = this.findViewById<EditText>(R.id.editTextEmail)
        val editTextPassword = this.findViewById<EditText>(R.id.editTextPassword)
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()

        if(email.trim() != "" && password != "" ){

            val auth = FirebaseAuth.getInstance()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val user = FirebaseAuth.getInstance().currentUser

                        if(user != null){
                            if (user.isEmailVerified) {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(baseContext,"Log in successfully",Toast.LENGTH_SHORT,).show()
                            }
                            else {
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
                        }

                    } else {
                        Toast.makeText(baseContext,"Authentication failed2.",Toast.LENGTH_SHORT,).show()
                    }
                }







        }else{
            Toast.makeText(this, "Please fill out the fields", Toast.LENGTH_LONG).show()
        }
    }
}