package controllers

import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import models.User
import persistence.UserMapper

/**
 * Date: 12/03/12
 * Time: 10:27
 */

object AuthKerberos extends Controller{

  val userForm: Form[User] = Form{
    mapping(
      "login" → text ,
      "password" → text
    )(User.apply)(User.unapply)
  }

  def login() = Action {
    Ok(views.html.auth.login(userForm))
  }

  def submitLogin() = Action {
    Redirect(routes.StoryTeller.stories())
  }

  def submitRegistration() = Action { implicit request =>
    userForm.bindFromRequest.fold (
      errors => BadRequest(views.html.auth.login(errors)),
      user => {
        UserMapper.store(user)
        Redirect(routes.StoryTeller.newStory())
      }
    )
  }

  def register() = Action {
    Ok(views.html.auth.register(userForm))
  }
}
