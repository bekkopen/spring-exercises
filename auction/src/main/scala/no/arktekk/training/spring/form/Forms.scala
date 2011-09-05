package no.arktekk.training.spring.form

import reflect.BeanProperty
import org.springframework.format.annotation.NumberFormat.Style._
import org.joda.time.DateTime
import org.springframework.format.annotation.{DateTimeFormat, NumberFormat}
import no.arktekk.training.spring.domain._

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */

class AlbumForm {
  @BeanProperty var id: String = ""
  @BeanProperty var title: String = ""
  @BeanProperty var artist: String = ""
  @BeanProperty var category: Category = null
  @BeanProperty var label: Label = null
}

class AuctionForm {
  final val DATE_PATTERN = "dd.MM.yyyy"

  @BeanProperty var id: String = null

  @NumberFormat(style = NUMBER)
  @BeanProperty var minimumPrice: Double = 0d

  @DateTimeFormat(pattern = DATE_PATTERN)
  @BeanProperty var startDate: DateTime = null

  @DateTimeFormat(pattern = DATE_PATTERN)
  @BeanProperty var expiresDate: DateTime = null

  @BeanProperty var albums: Array[AlbumForm] = Array()
  @BeanProperty var description: String = ""
}

object Transformations {
  implicit def albumFormToAlbum(af:AlbumForm) : Album = new Album(af.id,af.title,af.artist,af.category,af.label)
  implicit def albumFormsToAlbums(afs:Array[AlbumForm]) : List[Album] = afs.map(albumFormToAlbum(_)).toList

  implicit def albumToAlbumForm(a:Album) : AlbumForm = new AlbumForm(){
    id = a.id
    title = a.title
    artist = a.artist
    category = a.category
    label = a.label
  }
  implicit def albumsToAlbumForms(as : List[Album]) : Array[AlbumForm] = as.map(albumToAlbumForm(_)).toArray


  implicit def auctionFormToAuction(af: AuctionForm): Auction = new Auction(af.id,af.minimumPrice,af.description,af.startDate,af.expiresDate,af.albums)
  implicit def auctionFormsToAuctions(afs : Array[AuctionForm]) : List[Auction] = afs.map(auctionFormToAuction(_))

  implicit def auctionToAuctionForm(a:Auction) : AuctionForm = new AuctionForm(){
    id = a.id
    minimumPrice = a.minimumPrice
    description = a.description
    startDate = a.starts
    expiresDate = a.expires
    albums = a.albums
  }
  implicit def auctionsToAuctionForms(as:List[Auction]) : Array[AuctionForm] = as.map(auctionToAuctionForm(_)).toArray


}