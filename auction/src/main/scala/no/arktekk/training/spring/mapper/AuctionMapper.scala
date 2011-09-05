package no.arktekk.training.spring.mapper

import org.joda.time.DateTime
import no.arktekk.training.spring.domain.Auction
import java.sql.ResultSet
import org.springframework.jdbc.core.RowMapper

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */

class AuctionMapper extends RowMapper[Auction] {
  def mapRow(resultSet: ResultSet, rowNum: Int) =
    new Auction(resultSet.getString("id"), resultSet.getDouble("minimumPrice"), resultSet.getString("description"), new DateTime(resultSet.getDate("starts")), new DateTime(resultSet.getDate("expires")), List())
}