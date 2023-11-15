package co.edu.paws

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class AdapterListVet (private val mContext: Context, private val listVets: List<Vet>) : ArrayAdapter<Vet>(mContext, 0, listVets) {

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vet_list, parent, false)

        val vet = listVets[position]
        val textViewName = layout.findViewById<TextView>(R.id.textViewName)
        val textViewEmail = layout.findViewById<TextView>(R.id.textViewEmail)
        val textViewAddress = layout.findViewById<TextView>(R.id.textViewAddress)
        val textViewPhone = layout.findViewById<TextView>(R.id.textViewPhone)
        val imageView = layout.findViewById<ImageView>(R.id.imageView)


        if (textViewName != null && textViewEmail != null &&  textViewAddress != null  && textViewPhone != null &&  imageView != null ) {
            textViewName.text = vet.name
            textViewEmail.text = vet.email
            textViewAddress.text = vet.address
            textViewPhone.text = vet.number

            if (vet.gender == "Masculine") {
                imageView.setImageResource(R.drawable.man)
            } else{
                imageView.setImageResource(R.drawable.woman)
            }
        }
        return layout
    }
}