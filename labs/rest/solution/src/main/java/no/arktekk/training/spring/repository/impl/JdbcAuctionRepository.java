package no.arktekk.training.spring.repository.impl;

import no.arktekk.training.spring.domain.Auction;
import no.arktekk.training.spring.mapper.AuctionMapper;
import no.arktekk.training.spring.repository.AuctionRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static no.arktekk.training.spring.util.DatabaseUtils.no_NO;
import static no.arktekk.training.spring.util.DatabaseUtils.timeStampFormatter;


/**
 * @author <a href="mailto:kaare.nilsen@arktekk.no">Kaare Nilsen</a>
 */
@Repository
public class JdbcAuctionRepository implements AuctionRepository {
    private final JdbcTemplate template;

    @Autowired
    public JdbcAuctionRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public List<Auction> listAllRunningAuctions() {
        DateTime now = new DateTime();
        return template.query(
                "select * from Auctions where ? between starts and expires",
                new AuctionMapper(),
                timeStampFormatter.print(now.toDate(), no_NO));
    }

    public Auction findById(String auctionId) {
        List<Auction> list = template.query("select * from Auctions where id = ?", new AuctionMapper(), auctionId);
		return list != null && list.size() > 0 ? list.get(0) : null;
    }

    public void newAuction(Auction auction) {
        template.update("insert into Auctions values(?,?,?,?,?)",
                auction.getId(),
                auction.getMinimumPrice(),
                timeStampFormatter.print(auction.getStarts().toDate(), no_NO),
                timeStampFormatter.print(auction.getExpires().toDate(), no_NO),
                auction.getDescription());
    }
}
