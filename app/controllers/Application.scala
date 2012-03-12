package controllers

import play.api.mvc._

/**
 * Date: 09/03/12
 * Time: 16:40
 */

object Application  extends Controller {

  def index = Action {
    Redirect(routes.AuthKerberos.login())
  }
}
