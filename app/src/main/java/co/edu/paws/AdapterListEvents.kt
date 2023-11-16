package co.edu.paws

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class AdapterListEvents (private val mContext: Context, private val listEvents: List<Event>) : ArrayAdapter<Event>(mContext, 0, listEvents) {

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layout = LayoutInflater.from(mContext).inflate(R.layout.event_list, parent, false)

        val event = listEvents[position]
        val textViewTitle = layout.findViewById<TextView>(R.id.textViewTitle)
        val textViewDate = layout.findViewById<TextView>(R.id.textViewDate)
        val textViewHour = layout.findViewById<TextView>(R.id.textViewHour)
        val textViewContact = layout.findViewById<TextView>(R.id.textViewContact)
        val textViewLocation = layout.findViewById<TextView>(R.id.textViewLocation)
        val textViewDescription = layout.findViewById<TextView>(R.id.textViewDescription)

        if (textViewTitle != null && textViewDate != null &&  textViewHour != null &&  textViewContact != null &&  textViewLocation != null && textViewDescription != null) {
            textViewTitle.text = event.title
            textViewDate.text = "${event.day}/${event.month}/${event.year}"
            textViewHour.text = "${event.hour}:${event.minutes}-${event.period}"
            textViewContact.text = event.contact
            textViewLocation.text = "${event.city} - ${event.address}"
            textViewDescription.text = event.description
        }
        return layout
    }

}