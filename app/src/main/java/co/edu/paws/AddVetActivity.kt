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

class AddVetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_vet)

        val btnSaveVet = findViewById<Button>(R.id.btnSaveVet)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextNumber = findViewById<EditText>(R.id.editTextNumber)
        val editTextAddress = findViewById<EditText>(R.id.editTextAddress)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)

        val genderList = mutableListOf<String>()
        genderList.add("Feminine")
        genderList.add("Masculine")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = this.findViewById<Spinner>(R.id.spinnerGender)
        spinner.adapter = adapter

        btnSaveVet.setOnClickListener{
            val name : String = editTextName.text.toString()
            val genderSelected  = spinner.selectedItem
            val number : String = editTextNumber.text.toString()
            val address : String = editTextAddress.text.toString()
            val email : String = editTextEmail.text.toString()


            if(name == "" || genderSelected == "" || number == "" || address == "" || email == ""){
                Toast.makeText(this, "Please, fill all fields up", Toast.LENGTH_LONG).show()
            }
            else {

                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    val db = Firebase.firestore

                    val new_vet = hashMapOf(
                        "owner_id" to user.uid,
                        "name" to name,
                        "gender" to genderSelected.toString(),
                        "number" to number,
                        "address" to address,
                        "email" to email
                    )

                    db.collection("vets")
                        .document()
                        .set(new_vet)
                        .addOnSuccessListener {
                            Toast.makeText(
                                baseContext,
                                "Vet successfully saved",
                                Toast.LENGTH_SHORT,
                            ).show()
                            val intent = Intent(this, VetActivity::class.java)
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