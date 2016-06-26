package daos

import java.sql.Timestamp
import java.util.Date
import javax.inject.Inject

import models.Phrase
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import scala.concurrent.Future

class PhraseDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val Phrases = TableQuery[PhrasesTable]

  def all(): Future[Seq[Phrase]] = db.run(Phrases.result)

  def insert(phrase: Phrase): Future[Unit] = db.run(Phrases += phrase).map { _ => () }

  def insert(phrases: Seq[Phrase]): Future[Unit] = db.run(this.Phrases ++= phrases).map(_ => ())

  def findById(id: Long): Future[Option[Phrase]] = db.run(Phrases.filter(_.id === id).result.headOption)

  def update(id: Long, phrase: Phrase): Future[Unit] = {
    val phraseToUpdate: Phrase = phrase.copy(Some(id))
    db.run(Phrases.filter(_.id === id).update(phraseToUpdate)).map(_ => ())
  }

  def delete(id: Long): Future[Unit] =
    db.run(Phrases.filter(_.id === id).delete).map(_ => ())

  def count(): Future[Int] = {
    // this should be changed to db.run(Phrases.length.result) when https://github.com/slick/slick/issues/1237 is fixed
    db.run(Phrases.map(_.id).length.result)
  }

  private class PhrasesTable(tag: Tag) extends Table[Phrase](tag, "PHRASE") {
    //implicit val dateColumnType = MappedColumnType.base[Date, Long](d => d.getTime, d => new Date(d))
    implicit def dateTime = MappedColumnType.base[DateTime, Timestamp] (
      dt => new Timestamp(dt.getMillis), ts => new DateTime(ts.getTime)
    )

    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def spanish = column[String]("SPANISH")
    def english = column[String]("ENGLISH")
    def createdAt = column[DateTime]("CREATED_AT")

    def * = (id.?, spanish, english, createdAt) <> (Phrase.tupled, Phrase.unapply _)
  }

}