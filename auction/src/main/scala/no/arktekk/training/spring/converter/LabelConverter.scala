package no.arktekk.training.spring.converter

import no.arktekk.training.spring.repository.LabelRepository
import no.arktekk.training.spring.domain.Label
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */
class LabelConverter(repository: LabelRepository) extends Converter[String, Label] {
  def convert(id: String): Label = repository.find(id.toInt).get
}