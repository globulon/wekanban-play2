package controllers

import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import models.User
import persistence.UserMapper
import persistence.UserMapper._
import play.api.Logger

/**
 * Date: 12/03/12
 * Time: 10:27
 */

object AuthKerberos extends Controller {

  val userForm: Form[User] = Form {
    mapping(
      "login" → text,
      "password" → text
    )(User.apply)(User.unapply)
  }

  def login() = Action {
    Ok(views.html.auth.login(userForm))
  }

  def authentify(user: User) = {
    UserMapper.find(user) match {
      case Some(authentifiedUser) => Redirect(routes.StoryTeller.storiesForUser(authentifiedUser.id))
        .withSession("user" -> authentifiedUser.login)
      case None => Unauthorized(views.html.auth.login(userForm))
    }
  }

  def submitLogin() = Action {
    implicit request =>
      userForm.bindFromRequest.fold(
        errors => BadRequest(views.html.auth.login(errors)),
        authentify
      )
  }

  def submitRegistration() = Action {
    implicit request =>
      userForm.bindFromRequest.fold(
        errors => BadRequest(views.html.auth.login(errors)),
        user => {
          stored(user)
          Redirect(routes.StoryTeller.newStory()).withSession("user" -> user.login)
        }
      )
  }

  def register() = Action {
    Logger.info("Want to register")
    Ok(views.html.auth.register(userForm))
  }
}
