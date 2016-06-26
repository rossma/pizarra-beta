package models

import org.joda.time.DateTime

case class Phrase(id: Option[Long] = None, spanish: String, english: String, createdAt: DateTime = DateTime.now())
