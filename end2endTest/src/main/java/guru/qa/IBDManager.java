package guru.qa;

import guru.qa.domain.Owner;

import java.util.List;

public interface IBDManager {
	int createOwner(Owner owner);

	void deleteOwner(int id);

	List<Owner> fineByLastName(String lastName);
}
