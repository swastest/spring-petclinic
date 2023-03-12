package guru.qa;

import guru.qa.domain.Owner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpringBDManager implements IBDManager {
	private DataSource ds = DataSourceMySql.INSTANCE.getDataSource();
	private JdbcTemplate template = new JdbcTemplate(ds);

	@Override
	public int createOwner(Owner owner) {

		return new SimpleJdbcInsert(template)
			.withTableName("owners")
			.usingGeneratedKeyColumns("id")
			.executeAndReturnKey(Map.of(
				"first_name", owner.getFirst_name(),
				"last_name", owner.getLast_name(),
				"address", owner.getAddress(),
				"city", owner.getCity(),
				"telephone", owner.getTelephone()
			)).intValue();
	}

	@Override
	public void deleteOwner(int id) {
		String sqlRow = "DELETE FROM owners WHERE id = ?";
		template.update(sqlRow, id);
	}

	@Override
	public List<Owner> fineByLastName(String lastName) {
		List<Owner> owners = template.query("SELECT * FROM owners", (rs, i) -> Owner.builder()
			.last_name(rs.getString("last_name"))
			.first_name(rs.getString("first_name"))
			.city(rs.getString("city"))
			.telephone(rs.getString("telephone"))
			.address(rs.getString("address")).build());
		return owners.stream().filter(x -> x.getLast_name().equals(lastName)).collect(Collectors.toList());
	}
}
