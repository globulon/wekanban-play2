package controllers

import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import models.User
import persistence.UserMapper._
import play.api.Logger
import persistence.{StoryMapper, UserMapper}

/**
 * Date: 12/03/12
 * Time: 10:27
 */

object AuthKerberos extends Controller {

  val userForm: Form[User] = Form {
    mapping(
      "login" → text.verifying(nonEmpty).verifying(_.length < 17) ,
      "password" → text.verifying(nonEmpty).verifying(_.length < 17)
    )(User.apply)(User.unapply)
  }

  def login() = Action {
    Ok(views.html.auth.login(userForm))
  }

  def authentify(user: User) = {
    Logger.info("found: " + UserMapper.find(user))
    UserMapper.find(user) match {
      case Some(List(authentifiedUser)) => Ok(views.html.stories(StoryMapper.userStories(authentifiedUser)))
        .withSession("user" -> authentifiedUser.login)
      case _ => Unauthorized(views.html.auth.register(userForm))
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
