package co.edu.paws

import java.io.Serializable

class Pet(val pet_id : String,
          val owner :String,
          val name : String,
          val type : String,
          val day : String,
          val month : String,
          val year : String,
          val breed : String,
          val features : String,
          val weigth : String) : Serializable

