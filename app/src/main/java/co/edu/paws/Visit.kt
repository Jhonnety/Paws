package co.edu.paws

import java.io.Serializable

class Visit (val visit_id : String,
             val pet_id :String,
             val reason : String,
             val day : String,
             val month : String,
             val year : String,
             val weight : String,
             val medication : String,
             val note : String) : Serializable