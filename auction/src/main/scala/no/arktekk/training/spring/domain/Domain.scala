package no.arktekk.training.spring.domain

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */

import org.joda.time.DateTime
import reflect.BeanProperty

case class Album(id: String, title: String, artist: String, category: Category, label: Label)

case class Auction(id: String, minimumPrice: Double, description: String, starts: DateTime, expires: DateTime, albums: List[Album]) {
  def withAlbums(as: List[Album]): Auction = new Auction(id, minimumPrice, description, starts, expires, as)
}

case class ValueList(@BeanProperty id: Int, @BeanProperty name: String)

case class Label(override val id: Int, override val name: String) extends ValueList(id, name){
  override def toString = name
}

case class Category(override val id: Int, override val name: String) extends ValueList(id, name){
  override def toString = name
}







