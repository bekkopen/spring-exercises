package no.arktekk.training.spring.repository

import org.springframework.beans.factory.annotation.Autowired
import javax.sql.DataSource
import no.arktekk.training.spring.mapper.LabelMapper
import org.springframework.jdbc.core.JdbcTemplate
import no.arktekk.training.spring.util.Cache
import no.arktekk.training.spring.domain.Label
import collection.JavaConverters._
import org.springframework.stereotype.Repository

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */
class LabelRepository (dataSource: DataSource) extends CrudRepository[Int,Label] {
  val labelMapper = new LabelMapper
  val template = new JdbcTemplate(dataSource)
  val cache = new Cache[Int,Label]


  def find(id: Int) = cache.get(id) {
    template.queryForObject("select * from Labels where id=?", labelMapper, new Integer(id))
  }

  def list = template.query("select * from Labels", labelMapper).asScala.toList

  def addNew(category: Label) {
    template.update("insert into Labels values(?,?)", new Integer(category.id), category.name)
  }

  def update(category: Label) {
    template.update("update Labels set name=? where id=?", category.name, new Integer(category.id))
    cache.evict(category.id)
  }

  def delete(category: Label) {
    template.update("delete from Labels where id=?", new Integer(category.id))
    cache.evict(category.id)
  }

}