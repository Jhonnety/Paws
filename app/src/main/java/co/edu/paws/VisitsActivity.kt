package co.edu.paws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class VisitsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visits)

        val currentPet = intent.getSerializableExtra("currentPet") as Pet

        val textViewName = findViewById<TextView>(R.id.textViewName)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        textViewName.text = currentPet.name

        btnAdd.setOnClickListener{
            val intent = Intent(this, AddVisitActivity::class.java)
            intent.putExtra("currentPet", currentPet)
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
            val currentPet = intent.getSerializableExtra("currentPet") as Pet
            val db = Firebase.firestore

            db.collection("visits")
                .whereEqualTo("pet_id", currentPet.pet_id)
                .get()
                .addOnSuccessListener { documents ->

                    val visitsList = mutableListOf<Visit>()
                    val listViewVisits = this.findViewById<ListView>(R.id.listViewVisits)

                    for (document in documents) {

                        val visit_id = document.id
                        val pet_id = document.getString("pet_id").toString()
                        val reason = document.getString("reason").toString()
                        val day = document.getString("day").toString()
                        val month = document.getString("month").toString()
                        val year = document.getString("year").toString()
                        val weight = document.getString("weight").toString()
                        val medication = document.getString("medication").toString()
                        val note = document.getString("note").toString()


                        val visit = Visit(visit_id,pet_id, reason, day, month, year, weight, medication, note)
                        visitsList.add(visit)


                        val adapter = AdapterListVisits(this, visitsList)
                        listViewVisits.adapter = adapter
                        listViewVisits.setOnItemClickListener{ parent, view, position, id ->

                            val documentReference = db.collection("visits").document(visitsList[position].visit_id)
                            documentReference
                                .delete()
                                .addOnSuccessListener {
                                    val intent = intent
                                    finish()
                                    startActivity(intent)
                                    Toast.makeText(this, "Visit successfully deleted", Toast.LENGTH_LONG).show()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
                                }
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error getting pets.\n$exception", Toast.LENGTH_LONG).show()
                }
        }
    }

}