package no.arktekk.training.spring.controller

import org.springframework.beans.factory.annotation.Autowired
import no.arktekk.training.spring.repository.AuctionRepository
import org.springframework.web.bind.annotation.RequestMapping
import no.arktekk.training.spring.controller.ModelAndViews._
import no.arktekk.training.spring.form.Transformations._
import org.springframework.stereotype.Controller

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */
@Controller
class FrontPageController(auctionRepository: AuctionRepository) {
  @RequestMapping(Array("/"))
  def auctionList = frontPage(auctionRepository.listAllRunningAuctions)
}