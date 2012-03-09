package controllers

import play.api.mvc.Controller
import play.api.mvc._
import play.api.mvc.Action._

/**
 * Date: 09/03/12
 * Time: 16:40
 */

object Application   extends Controller {

  def index = Action {
    Ok(views.html.index("Welcome to weKanban"))
  }

}
