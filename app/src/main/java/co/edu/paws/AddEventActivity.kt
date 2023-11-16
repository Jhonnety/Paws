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

class AddEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        val editTextTitle = findViewById<TextView>(R.id.editTextTitle)
        val btnSaveEvent = findViewById<Button>(R.id.btnSaveEvent)
        val editTextDay = findViewById<EditText>(R.id.editTextDay)
        val editTextMonth = findViewById<EditText>(R.id.editTextMonth)
        val editTexYear = findViewById<EditText>(R.id.editTextYear)

        val editTextHour = findViewById<EditText>(R.id.editTextHour)
        val editTextMinute = findViewById<EditText>(R.id.editTextMinute)
        val editTextPeriod = findViewById<EditText>(R.id.editTextPeriod)

        val editTextDescription = findViewById<EditText>(R.id.editTextDescription)
        val editTextContact = findViewById<EditText>(R.id.editTextContact)
        val editTextCity = findViewById<EditText>(R.id.editTextCity)
        val editTextAddress = findViewById<EditText>(R.id.editTextAddress)



        btnSaveEvent.setOnClickListener{
            val title : String = editTextTitle.text.toString()
            val day : String = editTextDay.text.toString()
            val month : String = editTextMonth.text.toString()
            val year : String = editTexYear.text.toString()
            val hour : String = editTextHour.text.toString()
            val minutes : String = editTextMinute.text.toString()
            val period : String = editTextPeriod.text.toString()

            val description : String = editTextDescription.text.toString()
            val contact : String = editTextContact.text.toString()
            val city : String = editTextCity.text.toString()
            val address : String = editTextAddress.text.toString()

            if(title == "" || day == "" || month == "" || year == "" || hour == "" || minutes == "" || period == "" || description == "" || contact == "" || city == "" || address == ""){
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
                        val new_event = hashMapOf(
                            "owner_id" to user.uid,
                            "title" to title,
                            "description" to description,
                            "contact" to contact,
                            "city" to city,
                            "address" to address,
                            "day" to day,
                            "month" to month,
                            "year" to year,
                            "hour" to hour,
                            "minutes" to minutes,
                            "period" to period,)

                        db.collection("events")
                            .document()
                            .set(new_event)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    baseContext,
                                    "Event successfully saved",
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