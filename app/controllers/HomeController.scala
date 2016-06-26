package controllers

import javax.inject._

import daos.PhraseDao
import models.Phrase
import org.joda.time.DateTime
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms.{ignored, jodaDate, longNumber, mapping, nonEmptyText, optional}
import play.api.libs.json._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(phraseDao: PhraseDao) extends Controller {

  implicit val phraseWrites = Json.writes[Phrase]

  //implicit val residentReads = Json.reads[Resident]

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    Ok(views.html.index())
  }

  //case class Resident(name: String, age: Int, role: Option[String])
  case class Location(lat: Double, long: Double)

  case class Resident(name: String, age: Int, role: Option[String])

  case class Place(name: String, location: Location, residents: Seq[Resident])

//  def places = Action {
//    //val json = Json.toJson(Resident.list)
//    /*implicit val locationWrites = new Writes[Location] {
//      def writes(location: Location) = Json.obj(
//        "lat" -> location.lat,
//        "long" -> location.long
//      )
//    }
//
//    implicit val residentWrites = new Writes[Resident] {
//      def writes(resident: Resident) = Json.obj(
//        "name" -> resident.name,
//        "age" -> resident.age,
//        "role" -> resident.role
//      )
//    }
//
//    implicit val placeWrites = new Writes[Place] {
//      def writes(place: Place) = Json.obj(
//        "name" -> place.name,
//        "location" -> place.location,
//        "residents" -> place.residents)
//    }*/
//
//    implicit val locationWrites = Json.writes[Location]
//
//    implicit val residentWrites = Json.writes[Resident]
//
//    implicit val placeWrites = Json.writes[Place]
//
//    val place = Place(
//      "Watership Down",
//      Location(51.235685, -1.309197),
//      Seq(
//        Resident("Fiver", 4, None),
//        Resident("Bigwig", 6, Some("Owsla"))
//      )
//    )
//
//    val json = Json.toJson(place)
//    Ok(json)
//  }

  def phrases = Action.async { implicit request =>
    phraseDao.all().map { case (phrases) =>
      val json = Json.toJson(phrases)
      Ok(json)
    }
  }

  /** Describe the phrase form (used in both edit and create screens).*/
  val phraseForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "spanish" -> nonEmptyText,
      "english" -> nonEmptyText,
      "createdAt" -> ignored[DateTime](DateTime.now()))(Phrase.apply)(Phrase.unapply))

   def addPhrase = Action.async { implicit request =>
     Logger.debug("In addPhrase")

     val phrase: Phrase = phraseForm.bindFromRequest.get
     phraseDao.insert(phrase).map(_ => Ok("success"))

   }

    def edit(id: Long) = Action.async { implicit request =>
      val phrase = phraseDao.findById(id)

      phrase.map { case (ph) =>
        ph match {
          case Some(p) => {
            val json = Json.toJson(p)
            Ok(json)
          }
          case None => Ok("error")
        }
      }
    }

  //
//  def editPhrase(id: Long) = Action.async { implicit request =>
//    phraseForm.bindFromRequest.fold(
//      formWithErrors => Future.successful(BadRequest(html.phrase.edit(webJarAssets, requireJS, id, formWithErrors))),
//      phrase => {
//        for {
//          _ <- phraseDao.update(id, phrase)
//        } yield Home.flashing("success" -> "Phrase has been updated")
//      })
//  }
//
//  def deletePhrase(id: Long) = Action.async { implicit rs =>
//    for {
//      _ <- phraseDao.delete(id)
//    } yield Home.flashing("success" -> "Phrase has been deleted")
//  }

    def editPhrase(id: Long) = Action.async { implicit request =>
      Logger.debug("In addPhrase")

      val phrase: Phrase = phraseForm.bindFromRequest.get
      phraseDao.update(id, phrase).map(_ => Ok("success"))

    }

    def deletePhrase(id: Long) = Action.async { implicit rs =>
      for {
        _ <- phraseDao.delete(id)
      } yield Ok("success")
    }

}
