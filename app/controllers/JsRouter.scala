package controllers

import play.api.mvc._
import play.api.routing._

class JsRouter extends Controller {

  def javascriptRoutes = Action { implicit request =>
    import routes.javascript._
    Ok(
      JavaScriptReverseRouter("jsRoutes")(
        HomeController.phrases,
        HomeController.addPhrase,
        HomeController.edit,
        HomeController.editPhrase,
        HomeController.deletePhrase
      )
    ).as("text/javascript")
  }

}
