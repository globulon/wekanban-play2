package controllers

import play.api.mvc._
import play.api.data.Form
import models.Story
import play.api.data.Form._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

/**
 * Date: 09/03/12
 * Time: 16:40
 */

object Application  extends Controller {

  def index = Action {
    Ok(views.html.index("Welcome to weKanban"))
  }



}
