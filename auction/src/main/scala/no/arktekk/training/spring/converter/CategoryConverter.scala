package no.arktekk.training.spring.converter

import no.arktekk.training.spring.repository.CategoryRepository
import no.arktekk.training.spring.domain.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.core.convert.converter.Converter

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */
class CategoryConverter @Autowired()(repository: CategoryRepository) extends Converter[String,Category]{
  def convert(id: String): Category = repository.find(id.toInt).get
}