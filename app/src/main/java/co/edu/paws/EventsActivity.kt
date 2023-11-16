package co.edu.paws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import java.time.LocalDate

class EventsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        val btnAdd = findViewById<Button>(R.id.btnAdd)

        btnAdd.setOnClickListener{
            val intent = Intent(this, AddEventActivity::class.java)
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
            val db = Firebase.firestore

            db.collection("events")
                .get()
                .addOnSuccessListener { documents ->

                    val eventsList = mutableListOf<Event>()
                    val listViewEvents = this.findViewById<ListView>(R.id.listViewEvents)

                    val actual_date = LocalDate.now()

                    val actual_year = actual_date.year
                    val actual_month = actual_date.monthValue
                    val actual_day = actual_date.dayOfMonth

                    for (document in documents) {

                        val event_id = document.id
                        val owner_id = document.getString("owner_id").toString()
                        val title = document.getString("title").toString()
                        val description = document.getString("description").toString()
                        val contact = document.getString("contact").toString()
                        val city = document.getString("city").toString()
                        val address = document.getString("address").toString()
                        val day = document.getString("day").toString()
                        val month = document.getString("month").toString()
                        val year = document.getString("year").toString()
                        val hour = document.getString("hour").toString()
                        val minutes = document.getString("minutes").toString()
                        val period = document.getString("period").toString()



                        if(event_id != null && owner_id != null && title !=null && description !=null && contact !=null && city !=null && day !=null && address !=null && month !=null && year !=null && hour !=null && minutes !=null && period !=null  ){

                        if (year.toInt() < actual_year || (year.toInt() == actual_year && month.toInt() < actual_month) || (year.toInt() == actual_year && month.toInt() == actual_month && day.toInt() < actual_day)) {
                                val documentReference = db.collection("events").document(event_id)
                                documentReference
                                    .delete()
                                    .addOnSuccessListener {
                                    }
                                    .addOnFailureListener { e ->
                                    }
                        }else{
                            val event = Event(event_id, owner_id, title, description, contact, city, address, day, month, year, hour, minutes, period)
                            eventsList.add(event)
                        }
                    }

                    }
                    val adapter = AdapterListEvents(this, eventsList)
                    listViewEvents.adapter = adapter
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error getting events.\n$exception", Toast.LENGTH_LONG).show()
                }
        }
    }
}