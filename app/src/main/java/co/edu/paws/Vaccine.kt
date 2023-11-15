package co.edu.paws

import java.io.Serializable
class Vaccine (val vaccine_id : String,
               val pet_id :String,
               val vaccine_name : String,
               val day : String,
               val month : String,
               val year : String,
               val amount : String) : Serializable