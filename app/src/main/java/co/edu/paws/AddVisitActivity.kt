package co.edu.paws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class AddVisitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_visit)

        val currentPet = intent.getSerializableExtra("currentPet") as Pet


        val textViewName = findViewById<TextView>(R.id.textViewName)
        val btnSaveVisit = findViewById<Button>(R.id.btnSaveVisit)

        val editTextReason = findViewById<EditText>(R.id.editTextReason)
        val editTextWeight = findViewById<EditText>(R.id.editTextWeight)
        val editTextDay = findViewById<EditText>(R.id.editTextDay)
        val editTextMonth = findViewById<EditText>(R.id.editTextMonth)
        val editTexYear = findViewById<EditText>(R.id.editTextYear)
        val editTextMedication = findViewById<EditText>(R.id.editTextMedication)
        val editTextNote = findViewById<EditText>(R.id.editTextNote)

        textViewName.text = currentPet.name


        btnSaveVisit.setOnClickListener{
            val reason : String = editTextReason.text.toString()
            val weight : String = editTextWeight.text.toString()
            val day : String = editTextDay.text.toString()
            val month : String = editTextMonth.text.toString()
            val year : String = editTexYear.text.toString()
            val medication : String = editTextMedication.text.toString()
            val note : String = editTextNote.text.toString()

            if(reason == "" || day == "" || month == "" || year == ""){
                Toast.makeText(this, "Please, fill all required fields up", Toast.LENGTH_LONG).show()
            }
            else{
                if(day.length <2 || month.length < 2 || year.length < 4){
                    Toast.makeText(this, "Please, check the date", Toast.LENGTH_LONG).show()
                }
                else{
                    val user = FirebaseAuth.getInstance().currentUser
                    if(user != null) {
                        val db = Firebase.firestore
                        val new_vaccine = hashMapOf(
                            "pet_id" to currentPet.pet_id,
                            "reason" to reason,
                            "weight" to weight,
                            "day" to day,
                            "month" to month,
                            "year" to year,
                            "medication" to medication,
                            "note" to note)

                        db.collection("visits")
                            .document()
                            .set(new_vaccine)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    baseContext,
                                    "Visit successfully saved",
                                    Toast.LENGTH_SHORT,
                                ).show()
                                val intent = Intent(this, VaccinesActivity::class.java)
                                startActivity(intent)
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(baseContext, e.toString(), Toast.LENGTH_SHORT,).show()
                            }

                    }
                }
            }
        }

    }
}