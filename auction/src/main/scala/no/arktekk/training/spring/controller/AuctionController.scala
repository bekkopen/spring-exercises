package no.arktekk.training.spring.controller

import org.springframework.web.bind.annotation.RequestMethod._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.web.bind.support.SessionStatus
import no.arktekk.training.spring.form._
import no.arktekk.training.spring.domain._
import org.springframework.web.bind.annotation._
import org.springframework.stereotype.Controller
import no.arktekk.training.spring.controller.ModelAndViews._
import no.arktekk.training.spring.repository._
import no.arktekk.training.spring.form.Transformations._

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */
@Controller
@SessionAttributes(Array("newAuction"))
class AuctionController(auctionRepository: AuctionRepository, categoryRepository: CategoryRepository, labelRepository: LabelRepository) {

  @RequestMapping(Array("/forms/auction"))
  def prepareForm = newAuction

  @RequestMapping(Array("/auctions/{auctionId}"))
  def showDetails(@PathVariable auctionId: String) = auctionDetails(auctionRepository.findById(auctionId))


  @RequestMapping(value = Array("/forms/auction"), method = Array(POST))
  def createAuction(@ModelAttribute("newAuction") auction: AuctionForm, bindingResult: BindingResult, status: SessionStatus) = {
    if (bindingResult.hasErrors) {
      newAuction
    } else {
      auctionRepository.newAuction(auction)
      status.setComplete()
      redirectToFrontPage
    }
  }

  @RequestMapping(value = Array("/forms/auction/album"), method = Array(POST))
  def addAlbum(@ModelAttribute("newAuction") auction: AuctionForm, @ModelAttribute("newAlbum") album: AlbumForm, bindingResult: BindingResult) = {
    if (!bindingResult.hasErrors) {
      auction.albums :+ album
    }
    newAuction
  }

  @ModelAttribute("newAuction") def auctionForm: AuctionForm = new AuctionForm

  @ModelAttribute("newAlbum") def album: AlbumForm = new AlbumForm

  @ModelAttribute("categories") def categories: Array[Category] = categoryRepository.list.toArray

  @ModelAttribute("labels") def labels: Array[Label] = labelRepository.list.toArray

}