package support

import models.User
import persistence.UserMapper
import play.api.mvc._
import Results._
/**
 * Date: 12/03/12
 * Time: 13:50
 */

object Aknowledgment {
//  def Authenticated(f: User => Request[AnyContent] => Result) = {
//    Action { request =>
//      request.session.get("user").flatMap{ data =>
//        UserMapper.findByLogin(data)
//      }.map { user =>
//        f(user)(request)
//      }.getOrElse(Unauthorized)
//    }
//  }


  def Authenticated(f: Request[AnyContent] => Result) = {
    Action { request =>
      request.session.get("user").flatMap{ data => UserMapper.findByLogin(data)}.map { _ =>
        f(request)
      }.getOrElse(Unauthorized)
    }
  }
}
