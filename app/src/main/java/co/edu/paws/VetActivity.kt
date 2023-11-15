package co.edu.paws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class VetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vet)

        val btnAdd = findViewById<Button>(R.id.btnAdd)

        btnAdd.setOnClickListener{
            val intent = Intent(this, AddVetActivity::class.java)
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
            val id = user.uid
            val db = Firebase.firestore

            db.collection("vets")
                .whereEqualTo("owner_id", id)
                .get()
                .addOnSuccessListener { documents ->

                    val vetsList = mutableListOf<Vet>()
                    val listViewVet = this.findViewById<ListView>(R.id.listViewVet)

                    for (document in documents) {

                        val vet_id = document.id
                        val owner_id = document.getString("owner_id").toString()
                        val name = document.getString("name").toString()
                        val gender = document.getString("gender").toString()
                        val number = document.getString("number").toString()
                        val email = document.getString("email").toString()
                        val address = document.getString("address").toString()


                        if(name != null && gender !=null && id != number && email !=null && address != number && gender !=null && vet_id != number && owner_id != null){
                            val vet = Vet(vet_id, owner_id, name, gender, number, email, address)
                            vetsList.add(vet)
                        }

                        val adapter = AdapterListVet(this,vetsList)
                        listViewVet.adapter = adapter


                        listViewVet.setOnItemClickListener{ parent, view, position, id ->

                            val documentReference = db.collection("vets").document(vetsList[position].vet_id)
                            documentReference
                                .delete()
                                .addOnSuccessListener {
                                    val intent = intent
                                    finish()
                                    startActivity(intent)
                                    Toast.makeText(this, "Vet successfully deleted", Toast.LENGTH_LONG).show()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
                                }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error getting vets.\n$exception", Toast.LENGTH_LONG).show()
                }
        }
    }
}