package co.edu.paws

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class DogInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dog_info)
    }

    override fun onResume() {
        super.onResume()

        val dog = intent.getSerializableExtra("dog") as Dog

        val imageView = findViewById<ImageView>(R.id.imageView)
        val textViewBreed = findViewById<TextView>(R.id.textViewBreed)
        val textViewDescription = findViewById<TextView>(R.id.textViewDescription)
        val textViewSize = findViewById<TextView>(R.id.textViewSize)
        val textViewHeight = findViewById<TextView>(R.id.textViewHeight)
        val textViewLifeExpectancy = findViewById<TextView>(R.id.textViewLifeExpectancy)
        val textViewHairType = findViewById<TextView>(R.id.textViewHairType)
        val textViewCharacter = findViewById<TextView>(R.id.textViewCharacter)
        val textViewIdealFor = findViewById<TextView>(R.id.textViewIdealFor)

        DownloadImageTask(imageView).execute(dog.url)
        textViewBreed.text = dog.breed
        textViewDescription.text = dog.description
        textViewSize.text = dog.size
        textViewHeight.text = "${dog.height} cm"
        textViewLifeExpectancy.text = "${dog.life_expectancy} years"
        textViewHairType.text = dog.hair_type
        textViewCharacter.text = dog.character
        textViewIdealFor.text = dog.ideal_for
    }

    private class DownloadImageTask(private val imageView: ImageView) :
        AsyncTask<String, Void, Bitmap?>() {

        override fun doInBackground(vararg params: String): Bitmap? {
            val imageUrl = params[0]

            try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                val input: InputStream = connection.inputStream
                return BitmapFactory.decodeStream(input)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(result: Bitmap?) {
            if (result != null) {
                // Establece el Bitmap en el ImageView
                imageView.setImageBitmap(result)
            }
        }
    }

}