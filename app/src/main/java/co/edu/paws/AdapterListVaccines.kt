package co.edu.paws

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
class AdapterListVaccines (private val mContext: Context, private val listVaccines: List<Vaccine>) : ArrayAdapter<Vaccine>(mContext, 0, listVaccines) {

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layout = LayoutInflater.from(mContext).inflate(R.layout.vaccine_list, parent, false)

        val vaccine = listVaccines[position]
        val textViewName = layout.findViewById<TextView>(R.id.textViewName)
        val textViewDate = layout.findViewById<TextView>(R.id.textViewDate)
        val textViewAmount = layout.findViewById<TextView>(R.id.textViewAmount)


        if (textViewName != null && textViewDate != null &&  textViewAmount != null) {
            textViewName.text = vaccine.vaccine_name
            textViewDate.text = "${vaccine.day}/${vaccine.month}/${vaccine.year}"
            textViewAmount.text = "${vaccine.amount}  mL"
        }
        return layout
    }
}