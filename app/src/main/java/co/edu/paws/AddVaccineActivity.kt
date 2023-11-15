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

class AddVaccineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_vaccine)

        val currentPet = intent.getSerializableExtra("currentPet") as Pet

        val textViewName = findViewById<TextView>(R.id.textViewName)
        val btnSaveVaccine = findViewById<Button>(R.id.btnSaveVaccine)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextAmount = findViewById<EditText>(R.id.editTextAmount)
        val editTextDay = findViewById<EditText>(R.id.editTextDay)
        val editTextMonth = findViewById<EditText>(R.id.editTextMonth)
        val editTexYear = findViewById<EditText>(R.id.editTextYear)

        textViewName.text = currentPet.name


        btnSaveVaccine.setOnClickListener{
            val name : String = editTextName.text.toString()
            val day : String = editTextDay.text.toString()
            val month : String = editTextMonth.text.toString()
            val year : String = editTexYear.text.toString()
            val amount : String = editTextAmount.text.toString()

            if(name == "" || day == "" || month == "" || year == "" || amount == ""  ){
                Toast.makeText(this, "Please, fill all fields up", Toast.LENGTH_LONG).show()
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
                            "vaccine_name" to name,
                            "day" to day,
                            "month" to month,
                            "year" to year,
                            "amount" to amount)

                        db.collection("vaccines")
                            .document()
                            .set(new_vaccine)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    baseContext,
                                    "Vaccine successfully saved",
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