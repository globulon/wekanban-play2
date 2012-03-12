package models

/**
 * Date:s12String
 *Option[AbstractTypeB]me: 11:25
 */



case class User(login: String, password: String)
final case class AuthentifiedUser(id: Long, override val login: String, override val password: String)
  extends User (login, password)

