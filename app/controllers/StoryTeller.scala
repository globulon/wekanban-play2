package controllers

import play.api.mvc.Controller
import play.api.data.Form
import models.Story
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.mvc.Action

/**
 * Date: 10/03/12
 * Time: 18:46
 */

object StoryTeller extends Controller{

  val storyForm: Form[Story] = Form(
    mapping (
      "id" → longNumber,
      "title" → text.verifying(nonEmpty) ,
      "body" → text
    )(Story.apply)(Story.unapply)
  )

  def newStory  = Action {
    Ok(views.html.story(storyForm))
  }

  def submit = Action {implicit request =>
     storyForm.bindFromRequest.fold(
      errors => BadRequest(views.html.story(errors)),
      story => Ok(views.html.viewStory(story))
     )
  }

}
