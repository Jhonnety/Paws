package co.edu.paws

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class AdapterListVisits (private val mContext: Context, private val listVisits: List<Visit>) : ArrayAdapter<Visit>(mContext, 0, listVisits) {

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layout = LayoutInflater.from(mContext).inflate(R.layout.visit_list, parent, false)

        val visit = listVisits[position]
        val textViewReason = layout.findViewById<TextView>(R.id.textViewReason)
        val textViewDate = layout.findViewById<TextView>(R.id.textViewDate)
        val textViewWeight = layout.findViewById<TextView>(R.id.textViewWeight)
        val textViewMedication = layout.findViewById<TextView>(R.id.textViewMedication)
        val textViewNote = layout.findViewById<TextView>(R.id.textViewNote)

        if (textViewReason != null && textViewDate != null &&  textViewWeight != null &&  textViewMedication != null &&  textViewNote != null) {
            textViewReason.text = visit.reason
            textViewDate.text = "${visit.day}/${visit.month}/${visit.year}"
            textViewWeight.text = "${visit.weight}  Kg"
            textViewMedication.text = visit.medication
            textViewNote.text = visit.note
        }
        return layout
    }
}