package co.edu.paws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class DogsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dogs)
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

            db.collection("dogs")
                .get()
                .addOnSuccessListener { documents ->

                    val dogsList = mutableListOf<Dog>()
                    val listViewDogs = this.findViewById<ListView>(R.id.listViewDogs)

                    for (document in documents) {

                        val breed_id = document.id
                        val breed = document.getString("breed").toString()
                        val description = document.getString("description").toString()
                        val url = document.getString("url").toString()
                        val size = document.getString("size").toString()
                        val weight = document.getString("weight").toString()
                        val height = document.getString("height").toString()
                        val life_expectancy = document.getString("life_expectancy").toString()
                        val hair_type = document.getString("hair_type").toString()
                        val character = document.getString("Character").toString()
                        val ideal_for = document.getString("ideal_for").toString()


                        val dog = Dog(breed_id, breed, description, url,  size, weight, height, life_expectancy,hair_type, character, ideal_for)
                        dogsList.add(dog)


                        val adapter = AdapterListDogs(this, dogsList)
                        listViewDogs.adapter = adapter
                        listViewDogs.setOnItemClickListener{ parent, view, position, id ->

                            val intent = Intent(this, DogInfo::class.java)
                            intent.putExtra("dog",dogsList[position])
                            startActivity(intent)
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error getting pets.\n$exception", Toast.LENGTH_LONG).show()
                }
        }

    }
}