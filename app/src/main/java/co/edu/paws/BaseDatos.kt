package co.edu.paws

class BaseDatos {
    companion object{

        data class User(val name: String, val email: String, val password: String)

        var users = arrayOf<User>()
    }
}