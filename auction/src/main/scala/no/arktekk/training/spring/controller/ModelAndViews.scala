package no.arktekk.training.spring.controller

import no.arktekk.training.spring.form.AuctionForm
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.view.RedirectView

/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */

object ModelAndViews {
  def frontPage(auctions: Array[AuctionForm]) = new ModelAndView("frontpage").addObject("auctions", auctions)

  def auctionDetails(auction: AuctionForm) = new ModelAndView("auction/details").addObject("auction", auction)

  def newAuction = new ModelAndView("/auction/new")

  def redirectToFrontPage = new RedirectView("/")
}