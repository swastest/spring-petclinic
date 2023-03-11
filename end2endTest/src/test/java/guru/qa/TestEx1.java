package guru.qa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import guru.qa.domain.Owner;
import guru.qa.domain.SpringBDManager;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.$;

public class TestEx1 {

	//private IBDManager manager = new BDManager();
	private IBDManager manager = new SpringBDManager();
	int idOwner;

	@BeforeEach
	void addOwner(){
		Owner owner = Owner.builder()
			.telephone("33333")
			.address("Kzn")
			.city("Kzn")
			.first_name("firstname")
			.last_name("spring")
			.build();
		idOwner = manager.createOwner(owner);
	}

	@AfterEach
	void clearTestData() {
    manager.deleteOwner(idOwner);
	}
	@RepeatedTest(2)
	void findOwner(){
		Selenide.open("http://localhost:8080/owners/find");
		$("#lastName").setValue("spring");
		$("[type=submit]").click();
		$("table.table-striped").shouldHave(Condition.text("spring"));
	}
}
