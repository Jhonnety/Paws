package co.edu.paws

import java.io.Serializable

class Event (val event_id : String,
             val owner_id :String,
             val title : String,
             val description : String,
             val contact : String,
             val city : String,
             val address : String,
             val day : String,
             val month : String,
             val year : String,
             val hour : String,
             val minutes : String,
             val period : String,): Serializable
