package guru.qa;

import guru.qa.domain.Owner;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BDManager implements IBDManager {
	private DataSource ds = DataSourceMySql.INSTANCE.getDataSource();

	@Override
	public int createOwner(Owner owner) {
		String rowSql = "INSERT INTO owners (first_name, last_name, address, city, telephone) \n" +
			"VALUES (?, ?, ?, ?, ?)";
		try (Connection connection = ds.getConnection();
			 PreparedStatement ps = connection.prepareStatement(rowSql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, owner.getFirst_name());
			ps.setString(2, owner.getLast_name());
			ps.setString(3, owner.getAddress());
			ps.setString(4, owner.getCity());
			ps.setString(5, owner.getTelephone());
			ps.executeUpdate();

			ResultSet resultSet = ps.getGeneratedKeys();
			while (resultSet.next()) {
				return resultSet.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -3;
	}

	@Override
	public void deleteOwner(int id) {
		String rowSql = "DELETE FROM owners WHERE id = ?";
		try (Connection connection = ds.getConnection();
			 PreparedStatement ps = connection.prepareStatement(rowSql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Owner> fineByLastName(String lastName) {
		List<Owner> ownerList = new ArrayList<>();
		String rowSql = "SELECT * FROM owners WHERE last_name = ?";
		try (Connection connection = ds.getConnection();
			 PreparedStatement ps = connection.prepareStatement(rowSql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, lastName);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				ownerList.add(Owner.builder()
					.last_name(resultSet.getString("last_name"))
					.first_name(resultSet.getString("first_name"))
					.address(resultSet.getString("address"))
					.city(resultSet.getString("city"))
					.telephone(resultSet.getString("telephone"))
					.build());
			}
			return ownerList;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
