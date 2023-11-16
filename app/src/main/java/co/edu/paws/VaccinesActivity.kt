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

class VaccinesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vaccines)

        val currentPet = intent.getSerializableExtra("currentPet") as Pet

        val textViewName = findViewById<TextView>(R.id.textViewName)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        textViewName.text = currentPet.name

        btnAdd.setOnClickListener{
            val intent = Intent(this, AddVaccineActivity::class.java)
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

            db.collection("vaccines")
                .whereEqualTo("pet_id", currentPet.pet_id)
                .get()
                .addOnSuccessListener { documents ->

                    val vaccinesList = mutableListOf<Vaccine>()
                    val listViewVaccines = this.findViewById<ListView>(R.id.listViewVaccines)

                    for (document in documents) {

                        val vaccine_id = document.id
                        val pet_id = document.getString("pet_id").toString()
                        val vaccine_name = document.getString("vaccine_name").toString()
                        val day = document.getString("day").toString()
                        val month = document.getString("month").toString()
                        val year = document.getString("year").toString()
                        val amount = document.getString("amount").toString()


                        if(vaccine_id != null && vaccine_name !=null && pet_id != null && amount != null && day != null && year != null && month != null){
                            val vaccine = Vaccine(vaccine_id,pet_id, vaccine_name, day, month, year, amount)
                            vaccinesList.add(vaccine)
                        }

                        val adapter = AdapterListVaccines(this, vaccinesList)
                        listViewVaccines.adapter = adapter
                        listViewVaccines.setOnItemClickListener{ parent, view, position, id ->

                            val documentReference = db.collection("vaccines").document(vaccinesList[position].vaccine_id)
                            documentReference
                                .delete()
                                .addOnSuccessListener {
                                    val intent = intent
                                    finish()
                                    startActivity(intent)
                                    Toast.makeText(this, "Vaccine successfully deleted", Toast.LENGTH_LONG).show()
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