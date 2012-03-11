package models

import play.api.libs.json._

/**
 * Date: 09/03/12
 * Time: 17:07
 */

object DomainMarshallers {

  implicit object StoriesFormat extends Format[List[Story]] {
    def reads(json: JsValue) = null

    def writes(list: List[Story]) = {
      var result = for {
        item ← list
      } yield StoryFormat.writes(item)

      JsArray(result.toSeq)
  }
  }

  implicit object StoryFormat extends Format[Story] {
    def reads(json: JsValue) = Story(
      (json \ "id").as[Long],
      (json \ "title").as[String],
      (json \ "body").as[String]
    )

    def writes(story: Story) = JsObject(Seq(
      "id" → JsNumber(story.id),
      "title" → JsString(story.title),
      "body" → JsString(story.body)
    ))
  }

}
