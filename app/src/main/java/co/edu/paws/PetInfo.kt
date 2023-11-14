package co.edu.paws

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

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


        textViewBirthdate.text = "${pet.day}/${pet.month}/${pet.year}"
        textViewBreed.text = pet.breed
        textViewFeatures.text = pet.features
        textViewWeigth.text = pet.weigth
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
    }
}