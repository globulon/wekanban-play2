package persistence

import models.Story
import play.api.db.DB
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import scalaz._
import Scalaz._
/**
 * Date: 10/03/12
 * Time: 21:34
 */

object  StoryMapper {
  val storyParser: RowParser[Option[Story]] = {
    get[Long]("id")~
    get[String]("title")~
    get[String]("body") map {
      case id~title~body => Some(Story(id, title, body))
      case _ => None
    }
  }

  def stored(story: Story): Option[Story] = DB.withConnection { implicit conn =>
    SQL("insert into story(id, title, body) values({id}, {title}, {body})")
      .on('id → story.id, 'title → story.title, 'body → story.body)
        .executeInsert()
    //TODO should send back answer asynchronously
    SQL("select id, title, body from story where id = {id}")
      .on('id → story.id).as(storyParser.singleOpt).flatMap{x: Option[Story] => x}
  }


  def allStories: Option[List[Story]] = DB.withConnection{ implicit connection =>
     SQL("select id, title, body from story").as(storyParser *).sequence
  }
}
