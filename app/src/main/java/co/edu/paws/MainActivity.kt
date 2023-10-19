package co.edu.paws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSignOut = findViewById<Button>(R.id.buttonSignOut)

        buttonSignOut.setOnClickListener(){
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            Toast.makeText(this,"Sign out successfully", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        val user = FirebaseAuth.getInstance().currentUser

        if(user == null){
            val intent = Intent(this, SignInActivity::class.java)
            Toast.makeText(this,"Please sign in", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }
    }

}