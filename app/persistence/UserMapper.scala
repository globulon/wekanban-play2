package persistence
import play.api.db.DB
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import scalaz._
import Scalaz._
import play.api.data.Form
import models.{AuthentifiedUser, User}

/**
 * Date: 12/03/12
 * Time: 11:52
 */

object UserMapper {

  val userMapper =
  get[Long]("id")~
  get[String]("login")~
  get[String]("password") map {
    case id~login~password => Some(AuthentifiedUser(id, login, password))
    case _ => None
  }

  def stored(user: User)= DB.withConnection{ implicit cnx =>
    SQL("insert into user (login, password) values ({login}, {password})")
    .on('login → user.login, 'password → user.password)
    .executeInsert()
  }

  def find(user: User) = DB.withConnection{ implicit cnx =>
    SQL("select id, login, password from user where login = {name} and password = {password}")
      .on('name -> user.login, 'password -> user.password)
      .as[Option[AuthentifiedUser]](userMapper.single)
  }

  def findByLogin(name: String) = DB.withConnection{ implicit cnx =>
    SQL("select id, login, password from user where login = {name}")
      .on('name -> name)
      .as[Option[AuthentifiedUser]](userMapper.single)
  }


}
