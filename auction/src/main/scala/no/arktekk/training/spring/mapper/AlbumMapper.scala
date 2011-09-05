package no.arktekk.training.spring.mapper

import no.arktekk.training.spring.domain.Album
import java.sql.ResultSet
import org.springframework.jdbc.core.RowMapper
import no.arktekk.training.spring.repository._

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */

class AlbumMapper(val categoryRepository: CategoryRepository, val labelRepository: LabelRepository) extends RowMapper[Album] {

  def mapRow(resultSet: ResultSet, rowNum: Int) =
    new Album(resultSet.getString("id"), resultSet.getString("title"), resultSet.getString("artist"), categoryRepository.find(resultSet.getInt("categoryId")).get, labelRepository.find(resultSet.getInt("labelId")).get)

}