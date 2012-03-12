package persistence

import play.api.db.DB
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import scalaz._
import Scalaz._
import models.{AuthentifiedUser, Story}

/**
 * Date: 10/03/12
 * Time: 21:34
 */

object  StoryMapper {

  val storyParser: RowParser[Option[Story]] = {
    get[Long]("id")~
    get[String]("title")~
    get[String]("body")~
    get[Long]("userId") map {
      case id~title~body~userId => Some(Story(id, title, body, userId))
      case _ => None
    }
  }

  def stored(story: Story)(user: AuthentifiedUser) = DB.withConnection { implicit conx =>
    SQL("insert into story(id, title, body, usr_id) values({id}, {title}, {body}, {userId})")
      .on('id → story.id, 'title → story.title, 'body → story.body, 'userId → user.id)
      .executeInsert()
  }

  def userStories(implicit user: AuthentifiedUser) = DB.withConnection{ implicit connection =>
    SQL("select id, title, body, usr_id from story, user where user.id = story.usr_id and usr_id = {id}")
      .on('id → user.id)
        .as(storyParser *).sequence
  }

}
