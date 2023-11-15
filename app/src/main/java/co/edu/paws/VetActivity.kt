package co.edu.paws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class VetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vet)

        val btnAdd = findViewById<Button>(R.id.btnAdd)

        btnAdd.setOnClickListener{
            val intent = Intent(this, AddVetActivity::class.java)
            startActivity(intent)
        }
    }
}