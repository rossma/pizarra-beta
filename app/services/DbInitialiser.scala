package services

import javax.inject._

import daos.PhraseDao
import models.Phrase
import play.api.Logger

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Try

/**
  * This class demonstrates how to run code when the
  * application starts and stops. It populates the database with data on
  * application startup and deletes the data on application shutdown
  */
@Singleton
class DbInitialiser @Inject() (phraseDao: PhraseDao) {

  def insert(): Unit = {
    import play.api.libs.concurrent.Execution.Implicits.defaultContext

    Logger.info("Initialising data in database")

    val insertInitialDataFuture = for {
      count <- phraseDao.count() if count == 0
      _ <- phraseDao.insert(InitialData.phrases)
    } yield ()

    Try(Await.result(insertInitialDataFuture, Duration.Inf))
  }

  insert()
}

private[this] object InitialData {
  //  private val sdf = new SimpleDateFormat("yyyy-MM-dd")

  def phrases = Seq(
    Phrase(Some(1L), "Buenos días", "Good morning"),
    Phrase(Some(2L), "Buenas tardes", "Good afternoon"),
    Phrase(Some(3L), "Buenas noches", "Good evening"),
    Phrase(Some(4L), "Hola, me llamo Juan", "Hello, my name is John")
  )
}
