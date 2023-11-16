package co.edu.paws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class BreedsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breeds)

        val btnDogs = findViewById<Button>(R.id.btnDogs)

        btnDogs.setOnClickListener{
            val intent = Intent(this,DogsActivity::class.java)
            startActivity(intent)
        }
    }
}