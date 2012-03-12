package controllers

import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import models.User
/**
 * Date: 12/03/12
 * Time: 10:27
 */

object AuthKerberos extends Controller{

  val userForm = Form{
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
}
