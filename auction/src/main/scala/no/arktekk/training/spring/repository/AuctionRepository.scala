package no.arktekk.training.spring.repository

import org.joda.time.DateTime
import no.arktekk.training.spring.mapper.AuctionMapper
import no.arktekk.training.spring.util.DatabaseUtils._
import javax.sql.DataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import collection.JavaConverters._
import org.springframework.stereotype.Repository
import java.util.List
import no.arktekk.training.spring.domain.{Album, Auction}


/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */
class AuctionRepository (dataSource: DataSource, albumRepository: AlbumRepository) {
  val template = new JdbcTemplate(dataSource)

  def listAllRunningAuctions = template.query("select * from Auctions where ? between starts and expires", new AuctionMapper, timeStampFormatter.print(new DateTime().toDate, no_NO)).asScala.toList

  def findById(id: String) = {
    val auction = template.queryForObject("select * from Auctions where id = ?", new AuctionMapper, id)
    auction.withAlbums(albumRepository.listForAuction(auction.id))
  }

  def newAuction(a: Auction) {
    template.update("insert into Auctions values(?,?,?,?,?)", a.id, a.minimumPrice.asInstanceOf[java.lang.Double], timeStampFormatter.print(a.starts.toDate, no_NO), timeStampFormatter.print(a.expires.toDate, no_NO), a.description)
  }
}