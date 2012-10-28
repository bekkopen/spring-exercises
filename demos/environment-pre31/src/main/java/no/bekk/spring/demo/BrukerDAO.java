package no.bekk.spring.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import no.bekk.spring.demo.domain.Bruker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class BrukerDAO {
	
	private NamedParameterJdbcTemplate template;

	@Autowired
	public BrukerDAO(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public Bruker lesBruker(int id) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id+"");
		return template.query("select ID, NAVN from bruker where id = :id", params, new ResultSetExtractor<Bruker>() {

			@Override
			public Bruker extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				while (rs.next())
					return new Bruker(rs.getInt("ID"), rs.getString("NAVN"));
				return null;
			}
		});
	}
}
