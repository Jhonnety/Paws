package co.edu.paws

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class AdapterListDogs (private val mContext: Context, private val listDogs: List<Dog>) : ArrayAdapter<Dog>(mContext, 0, listDogs) {
    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layout = LayoutInflater.from(mContext).inflate(R.layout.dog_list, parent, false)

        val dog = listDogs[position]
        val textViewBreed = layout.findViewById<TextView>(R.id.textViewBreed)
        val textViewDescription = layout.findViewById<TextView>(R.id.textViewDescription)
        val imageView = layout.findViewById<ImageView>(R.id.imageView)

        if (textViewBreed != null && textViewDescription != null &&  imageView != null ) {
            textViewBreed.text = dog.breed
            textViewDescription.text = dog.description
            DownloadImageTask(imageView).execute(dog.url)
        }
        return layout
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