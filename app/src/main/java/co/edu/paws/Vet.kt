package co.edu.paws

import java.io.Serializable

class Vet (val vet_id : String,
           val owner_id :String,
           val name :String,
           val gender : String,
           val number : String,
           val email : String,
           val address : String) : Serializable