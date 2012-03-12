package controllers

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import persistence._
import play.api.libs.json.Json
import play.api.mvc.Controller
import models.Story
import support.Aknowledgement._

/**
 * Date: 10/03/12
 * Time: 18:46
 */

object StoryTeller extends Controller {

  val storyForm: Form[Story] = Form(
    mapping (
      "id" → longNumber.verifying(_ > 0L),
      "title" → text.verifying(nonEmpty) ,
      "body" → text.verifying(nonEmpty),
      "userId" → longNumber.verifying(_ == 0L)
    )(Story.apply)(Story.unapply)
  )

  def newStory  = Authenticated { user => request =>
    Ok(views.html.story(storyForm))
  }

  def submit = Authenticated {user => implicit request =>
     storyForm.bindFromRequest.fold(
       errors => BadRequest(views.html.story(errors)),
       story => {
         StoryMapper.stored(story)(user)
         Ok(views.html.stories(StoryMapper.userStories(user)))
       }
     )
  }

  def stories = Authenticated { user => implicit request =>
    Ok(views.html.stories(StoryMapper.userStories(user)))
  }

}
