package persistence
import models.User
import play.api.db.DB
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import scalaz._
import Scalaz._
import play.api.data.Form

/**
 * Date: 12/03/12
 * Time: 11:52
 */

object UserMapper {

  val userMapper =
  get[String]("login")~
  get[String]("password") map {
    case login~password => Some(User(login, password))
    case _ => None
  }

  def store(user: User)= DB.withConnection{ implicit cnx =>
    SQL("insert into user (login, password) values ({login}, {password})")
      .on('login → user.login, 'password → user.password)
        .executeInsert()
  }


}
