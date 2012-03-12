package controllers

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import persistence._
import play.api.libs.json.Json
import play.api.mvc.{Controller, Action}
import models.{DomainMarshallers, Story}
import DomainMarshallers._

/**
 * Date: 10/03/12
 * Time: 18:46
 */

object StoryTeller extends Controller {

  val storyForm: Form[Story] = Form(
    mapping (
      "id" → longNumber.verifying(_ > 0L),
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
       story => Ok(views.html.viewStory(StoryMapper.stored(story)))
     )
  }

  def stories() = Action { implicit request =>
    request.headers.get(ACCEPT) match {
      case Some("application/json") => Ok(Json.toJson(StoryMapper.allStories.getOrElse(List())))
      case _ => Ok(views.html.stories(StoryMapper.allStories))
    }
  }
                                               1
  def allStories() = Action(parse.json) { implicit request =>
     Ok(Json.toJson(StoryMapper.allStories))
  }

}
