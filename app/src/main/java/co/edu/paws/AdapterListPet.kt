package co.edu.paws

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class AdapterListPet(private val mContext: Context, private val listPets: List<Pet>) : ArrayAdapter<Pet>(mContext, 0, listPets) {
    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false)

        val pet = listPets[position]
        val textViewName = layout.findViewById<TextView>(R.id.textViewName)
        val textViewType = layout.findViewById<TextView>(R.id.textViewType)
        val textViewWeigth = layout.findViewById<TextView>(R.id.textViewWeigth)
        val imageView = layout.findViewById<ImageView>(R.id.imageView)


        if (textViewName != null && textViewType != null &&  textViewWeigth != null &&  imageView != null ) {
            textViewName.text = pet.name
            textViewType.text = pet.type
            textViewWeigth.text = "${pet.weigth}  Kg"

            if (textViewType.text == "Cat") {
                imageView.setImageResource(R.drawable.cat_icon)
            } else if (textViewType.text == "Dog") {
                imageView.setImageResource(R.drawable.dog_icon)
            } else if (textViewType.text == "Frog") {
                imageView.setImageResource(R.drawable.frog_icon)
            } else if (textViewType.text == "Cow") {
                imageView.setImageResource(R.drawable.cow_icon)
            } else if (textViewType.text == "Rat") {
                imageView.setImageResource(R.drawable.rat_icon)
            } else if (textViewType.text == "Horse") {
                imageView.setImageResource(R.drawable.horse_icon)
            } else if (textViewType.text == "Pig") {
                imageView.setImageResource(R.drawable.pig_icon)
            }

        }


        return layout
    }
}
