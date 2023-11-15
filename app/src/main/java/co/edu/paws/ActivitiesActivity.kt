package co.edu.paws

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivitiesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activities)

        val btnVets = findViewById<Button>(R.id.btnVets)


        btnVets.setOnClickListener{
                val intent = Intent(this, VetActivity::class.java)
                startActivity(intent)
        }

    }
}