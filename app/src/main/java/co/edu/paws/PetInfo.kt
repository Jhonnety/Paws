package co.edu.paws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class PetInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_info)

        val pet = intent.getSerializableExtra("pet") as Pet

        val textViewBirthdate = findViewById<TextView>(R.id.textViewBirthdate)
        val textViewBreed = findViewById<TextView>(R.id.textViewBreed)
        val textViewFeatures = findViewById<TextView>(R.id.textViewFeatures)
        val textViewWeigth = findViewById<TextView>(R.id.textViewWeigth)
        val textViewName = findViewById<TextView>(R.id.textViewName)
        val imageViewType = findViewById<ImageView>(R.id.imageViewType)

        val btnVaccines = findViewById<Button>(R.id.btnVaccines)
        val btnVisits = findViewById<Button>(R.id.btnVisits)
        val btnDetele = findViewById<Button>(R.id.btnDetele)

        textViewBirthdate.text = "${pet.day}/${pet.month}/${pet.year}"
        textViewBreed.text = pet.breed
        textViewFeatures.text = pet.features
        textViewWeigth.text = "${pet.weigth} Kg"
        textViewName.text = pet.name

        if (pet.type == "Cat") {
            imageViewType.setImageResource(R.drawable.cat_icon)
        } else if (pet.type == "Dog") {
            imageViewType.setImageResource(R.drawable.dog_icon)
        } else if (pet.type == "Frog") {
            imageViewType.setImageResource(R.drawable.frog_icon)
        } else if (pet.type == "Cow") {
            imageViewType.setImageResource(R.drawable.cow_icon)
        } else if (pet.type == "Rat") {
            imageViewType.setImageResource(R.drawable.rat_icon)
        } else if (pet.type == "Horse") {
            imageViewType.setImageResource(R.drawable.horse_icon)
        } else if (pet.type == "Pig") {
            imageViewType.setImageResource(R.drawable.pig_icon)
        }

        btnVaccines.setOnClickListener{
            val intent = Intent(this, VaccinesActivity::class.java)
            intent.putExtra("currentPet", pet)
            startActivity(intent)
        }

        btnVisits.setOnClickListener{
            val intent = Intent(this, VisitsActivity::class.java)
            intent.putExtra("currentPet", pet)
            startActivity(intent)
        }

        btnDetele.setOnClickListener{
            val currentPet = intent.getSerializableExtra("pet") as Pet
            val db = Firebase.firestore

            val documentReference = db.collection("pets").document(currentPet.pet_id)
            documentReference
                .delete()
                .addOnSuccessListener {
                    val intent = Intent(this, MainActivity::class.java)
                    Toast.makeText(this, "Pet successfully deleted", Toast.LENGTH_LONG).show()
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
                }
        }
    }

    override fun onResume() {
        super.onResume()

        val textViewLastVaccine = findViewById<TextView>(R.id.textViewLastVaccine)
        val textViewLastVisit = findViewById<TextView>(R.id.textViewLastVisit)
        val currentPet = intent.getSerializableExtra("pet") as Pet
        val db = Firebase.firestore


        db.collection("vaccines")
            .whereEqualTo("pet_id", currentPet.pet_id)
            .get()
            .addOnSuccessListener { documents ->
                var last_day = -1
                var last_month = -1
                var last_year = -1

                for (document in documents) {
                    val day = document.getString("day").toString().toInt()
                    val month = document.getString("month").toString().toInt()
                    val year = document.getString("year").toString().toInt()

                    if(year >= last_year ){
                        if(month >= last_month ){
                            if(day >= last_day ){
                                last_day = day
                                last_month = month
                                last_year = year
                            }
                        }
                    }
                }
                if(last_day != -1){
                    textViewLastVaccine.text = "${last_day}/${last_month}/${last_year}"
                }
                else{
                    textViewLastVaccine.text = "N/A"
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error getting last vaccine.\n$exception", Toast.LENGTH_LONG).show()
            }
        db.collection("visits")
            .whereEqualTo("pet_id", currentPet.pet_id)
            .get()
            .addOnSuccessListener { documents ->
                var last_day = -1
                var last_month = -1
                var last_year = -1

                for (document in documents) {
                    val day = document.getString("day").toString().toInt()
                    val month = document.getString("month").toString().toInt()
                    val year = document.getString("year").toString().toInt()

                    if(year >= last_year ){
                        if(month >= last_month ){
                            if(day >= last_day ){
                                last_day = day
                                last_month = month
                                last_year = year
                            }
                        }
                    }
                }
                if(last_day != -1){
                    textViewLastVisit.text = "${last_day}/${last_month}/${last_year}"
                }
                else{
                    textViewLastVisit.text = "N/A"
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error getting last visit.\n$exception", Toast.LENGTH_LONG).show()
            }
    }
}