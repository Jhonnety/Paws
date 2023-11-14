package co.edu.paws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSignOut = findViewById<Button>(R.id.buttonSignOut)
        val buttonNewPet = findViewById<Button>(R.id.buttonNewPet)

        buttonSignOut.setOnClickListener(){
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            Toast.makeText(this,"Sign out successfully", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }

        buttonNewPet.setOnClickListener(){
            val intent = Intent(this, AddPetActivity::class.java)
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
        else{
            val textViewName = findViewById<TextView>(R.id.textViewName)
            val id = user.uid

            val db = Firebase.firestore

            db.collection("users")
                .document(id)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val name = document.getString("name")
                        textViewName.setText(name)
                    } else {
                        Toast.makeText(this, "Document not found", Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error getting the user.\n$exception", Toast.LENGTH_LONG).show()
                }

            db.collection("pets")
                .whereEqualTo("owner", id)
                .get()
                .addOnSuccessListener { documents ->

                    val petList = mutableListOf<Pet>()
                    val listViewPet = this.findViewById<ListView>(R.id.listViewPet)

                    for (document in documents) {

                        val name = document.getString("name").toString()
                        val type = document.getString("type").toString()
                        val weigth = document.getString("weigth").toString()
                        val idPet = document.id


                        if(name != null && type !=null && id != null){
                            val pet = Pet(idPet ,name, type, weigth)
                            petList.add(pet)
                        }

                        val adapter = AdapterListPet(this,petList)
                        listViewPet.adapter = adapter
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error getting pets.\n$exception", Toast.LENGTH_LONG).show()
                }
        }
    }

}