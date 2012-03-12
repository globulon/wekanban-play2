package controllers

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import persistence._
import play.api.libs.json.Json
import play.api.mvc.Controller
import models.Story
import support.Aknowledgement._
import play.api.Logger

/**
 * Date: 10/03/12
 * Time: 18:46
 */

object StoryTeller extends Controller {

  val storyForm: Form[Story] = Form(
    mapping (
      "id" → longNumber.verifying(_ > 0L),
      "title" → text.verifying(nonEmpty) ,
      "body" → text.verifying(nonEmpty)
    ) ((id,title, body) => Story(id, title, body,0L))
      ((story) => Some((story.id, story.title, story.body)))
  )

  def newStory  = Authenticated { user => request =>
    Logger.info("new story for user " + user)
    Ok(views.html.story(storyForm))
  }

  def submit = Authenticated {user => implicit request =>
     storyForm.bindFromRequest.fold(
       errors => {
         Logger.info("errors")
         BadRequest(views.html.story(errors))
       },
       story => {
         StoryMapper.stored(story)(user)
         Logger.info("stored:" + story + " for user " + user)
         Ok(views.html.stories(StoryMapper.userStories(user)))
       }
     )
  }

  def stories = Authenticated { user => implicit request =>
    Logger.info("working with user " + user)
    Ok(views.html.stories(StoryMapper.userStories(user)))
  }

}
