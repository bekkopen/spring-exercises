package no.arktekk.training.spring.mapper

import java.sql.ResultSet
import org.springframework.jdbc.core.RowMapper
import no.arktekk.training.spring.domain.Category

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */

class CategoryMapper extends RowMapper[Category] {
  def mapRow(resultSet: ResultSet, i: Int) =
    new Category(resultSet.getInt("id"), resultSet.getString("name"))
}