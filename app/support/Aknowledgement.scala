package support

import persistence.UserMapper
import play.api.mvc._
import Results._
import models.{AuthentifiedUser, User}

/**
 * Date: 12/03/12
 * Time: 13:50
 */

object Aknowledgement {

  def Authenticated(f: AuthentifiedUser => Request[AnyContent] => Result) =  Action { request =>
      request.session.get("user")
        .flatMap{ data => UserMapper.findByLogin(data)}
          .map { user => f(user)(request)}.getOrElse(Unauthorized)
    }
  }
