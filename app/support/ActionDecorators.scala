package support

import play.api.mvc._
import play.api.Logger

/**
 * Date: 12/03/12
 * Time: 12:21
 */

object ActionDecorators {

  def trace[A](f: Request[AnyContent] => Result) = {
    Action {
      request =>
        request.headers.toMap.foreach {
          entry => Logger.info(entry._1.toString() + " : " + entry._2)
        }
        f(request)

    }
  }


  def onSteroid[A](bd: BodyParser[A])(g: Request[A] => Unit)(f: Request[A] => Result) = {
    Action(bd) {
      request =>
        g(request)
        f(request)
    }
  }

}
