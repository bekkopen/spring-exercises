package no.arktekk.training.spring.repository

import no.arktekk.training.spring.mapper.CategoryMapper
import org.springframework.jdbc.core.JdbcTemplate
import no.arktekk.training.spring.domain.Category
import javax.sql.DataSource
import org.springframework.beans.factory.annotation.Autowired
import no.arktekk.training.spring.util.Cache
import collection.JavaConverters._
import org.springframework.stereotype.Repository

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */
class CategoryRepository(dataSource: DataSource) extends CrudRepository[Int,Category] {
  val categoryMapper = new CategoryMapper
  val template = new JdbcTemplate(dataSource)
  val cache = new Cache[Int,Category]


  def find(id: Int) = cache.get(id){
    template.queryForObject("select * from Categories where id=?", categoryMapper, new Integer(id))
  }

  def list = template.query("select * from Categories", categoryMapper).asScala.toList

  def addNew(category: Category) {
    template.update("insert into Categories values(?,?)", new Integer(category.id), category.name)
  }

  def update(category: Category) {
    template.update("update Categories set name=? where id=?", category.name, new Integer(category.id))
    cache.evict(category.id)
  }

  def delete(category: Category) {
    template.update("delete from Categories where id=?", new Integer(category.id))
    cache.evict(category.id)
  }

}