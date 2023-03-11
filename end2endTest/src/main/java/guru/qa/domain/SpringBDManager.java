package guru.qa.domain;

import guru.qa.DataSourceMySql;
import guru.qa.IBDManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class SpringBDManager implements IBDManager {
	private DataSource ds = DataSourceMySql.INSTANCE.getDataSource();
	private JdbcTemplate template = new JdbcTemplate(ds);

	@Override
	public int createOwner(Owner owner) {

		return new SimpleJdbcInsert(template)
			.withTableName("owners")
			.usingGeneratedKeyColumns("id")
			.execute(Map.of(
				"first_name", owner.getFirst_name(),
				"last_name", owner.last_name,
				"address", owner.address,
				"city", owner.city,
				"telephone", owner.telephone
				));
	}

	@Override
	public void deleteOwner(int id) {

	}

	@Override
	public List<Owner> fineByLastName(String lastName) {
		return null;
	}
}
