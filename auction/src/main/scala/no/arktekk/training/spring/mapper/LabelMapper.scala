package no.arktekk.training.spring.mapper

import java.sql.ResultSet
import org.springframework.jdbc.core.RowMapper
import no.arktekk.training.spring.domain.Label

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */

class LabelMapper extends RowMapper[Label] {
  def mapRow(resultSet: ResultSet, i: Int) = new Label(resultSet.getInt("id"), resultSet.getString("name"))
}