package co.edu.paws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class AddPetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pet)

        val petList = mutableListOf<String>()
        val btnSavePet = findViewById<Button>(R.id.btnSavePet)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextDay = findViewById<EditText>(R.id.editTextDay)
        val editTextMonth = findViewById<EditText>(R.id.editTextMonth)
        val editTexYear = findViewById<EditText>(R.id.editTextYear)
        val editTexBreed = findViewById<EditText>(R.id.editTextBreed)
        val editTexFeatures = findViewById<EditText>(R.id.editTextFeatures)
        val editTexWeigth = findViewById<EditText>(R.id.editTextWeigth)


        petList.add("Cat")
        petList.add("Dog")
        petList.add("Frog")
        petList.add("Cow")
        petList.add("Rat")
        petList.add("Horse")
        petList.add("Pig")

        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, petList)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = this.findViewById<Spinner>(R.id.spinnerPet)
        spinner.adapter = adaptador

        btnSavePet.setOnClickListener{
            val name : String = editTextName.text.toString()
            val petSelected  = spinner.selectedItem
            val day : String = editTextDay.text.toString()
            val month : String = editTextMonth.text.toString()
            val year : String = editTexYear.text.toString()
            val breed : String = editTexBreed.text.toString()
            val features : String = editTexFeatures.text.toString()
            val weigth : String = editTexWeigth.text.toString()

            if(name == "" || day == "" || month == "" || year == "" || breed == "" || features == "" || weigth == "" ){
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

                    val new_pet = hashMapOf(
                        "owner" to user.uid,
                        "name" to name,
                        "type" to petSelected.toString(),
                        "day" to day,
                        "month" to month,
                        "year" to year,
                        "breed" to breed,
                        "features" to features,
                        "weigth" to weigth,
                    )

                    db.collection("pets")
                        .document()
                        .set(new_pet)
                        .addOnSuccessListener {
                            Toast.makeText(
                                baseContext,
                                "Pet successfully saved",
                                Toast.LENGTH_SHORT,
                            ).show()
                            val intent = Intent(this, MainActivity::class.java)
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