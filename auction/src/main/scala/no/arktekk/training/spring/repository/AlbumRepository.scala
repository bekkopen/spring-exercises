package no.arktekk.training.spring.repository

import no.arktekk.training.spring.domain.Album
import org.springframework.beans.factory.annotation.Autowired
import javax.sql.DataSource
import org.springframework.jdbc.core.JdbcTemplate
import no.arktekk.training.spring.mapper.AlbumMapper
import org.springframework.stereotype.Repository
import collection.JavaConverters._

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */
class AlbumRepository (dataSource: DataSource, categoryRepository: CategoryRepository, labelRepository: LabelRepository) {
  val template = new JdbcTemplate(dataSource)
  val albumMapper = new AlbumMapper(categoryRepository,labelRepository)

  def findById(id: String) = template.queryForObject("select * from Albums WHERE id = ?", albumMapper, id)

  def listForAuction(auctionId: String) = template.query("select * from Albums where auctionId = ?", albumMapper, auctionId).asScala.toList

  def storeForAuction(auctionId: String, albums: List[Album]) {
    albums.foreach(album =>
      template.update("insert into Albums values(?,?,?,?,?,?)", album.id, auctionId, album.title, album.artist, new Integer(album.category.id), new Integer(album.label.id)))
  }

}