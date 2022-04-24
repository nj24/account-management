package com.project.account.management;

import com.project.account.management.api.model.CreateAccountRequest;
import com.project.account.management.api.model.CreateAccountResponse;
import com.project.account.management.api.model.TransferBalanceRequest;
import com.project.account.management.api.model.UpdateBalanceRequest;
import com.project.account.management.domain.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

	public static final UUID accountRef = UUID.randomUUID();

	@Autowired
	private TestRestTemplate restTemplate;
	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	void contextLoads() {
	}

	@org.junit.Test
	public void testGetAllAccounts() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/accounts", HttpMethod.GET, entity,
				String.class);
		assertThat(response.getBody()).isNotNull();
	}

	@org.junit.Test
	public void testGetAccountById() {
		Account account = restTemplate.getForObject(getRootUrl() + "/accounts/" + accountRef, Account.class);
		assertThat(account).isNotNull();
	}

	@org.junit.Test
	public void testCreateAccount() {
		CreateAccountRequest account = new CreateAccountRequest("Test Account",BigDecimal.valueOf(5000));

		ResponseEntity<CreateAccountResponse> postResponse = restTemplate.postForEntity(getRootUrl() + "/accounts", account, CreateAccountResponse.class);
		assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		CreateAccountResponse responseBody = postResponse.getBody();

		assertThat(responseBody).isNotNull();
		Account updatedAccount = restTemplate.getForObject(getRootUrl() + "/accounts/" + responseBody.accountRef(), Account.class);
		assertThat(updatedAccount.getBalance().doubleValue()).isEqualTo(500);

		restTemplate.delete(getRootUrl() + "/accounts/3");
	}

	@org.junit.Test
	public void testAccountUpdate() {

		UpdateBalanceRequest updateAccountRequest = new UpdateBalanceRequest(accountRef,BigDecimal.valueOf(-100));
		restTemplate.put(getRootUrl() + "/accounts/" + accountRef, updateAccountRequest);

		Account updatedAccount = restTemplate.getForObject(getRootUrl() + "/accounts/" + 1, Account.class);
		assertThat(updatedAccount.getBalance().doubleValue()).isEqualTo(900);

		updateAccountRequest = new UpdateBalanceRequest(accountRef,BigDecimal.valueOf(100));
		restTemplate.put(getRootUrl() + "/accounts/" + 1, updateAccountRequest);

		updatedAccount = restTemplate.getForObject(getRootUrl() + "/accounts/" + 1, Account.class);
		assertThat(updatedAccount.getBalance().doubleValue()).isEqualTo(1000);
	}

	@org.junit.Test
	public void testAccountTransfer() {

		UUID destinationAccount = UUID.fromString("a484a182-b2ad-11ec-b909-0242ac120002");
		UUID sourceAccount = UUID.fromString("b5682b68-a18b-4eea-9920-22df26f9d28f");

		TransferBalanceRequest transferAccountRequest = new TransferBalanceRequest(accountRef, destinationAccount, BigDecimal.valueOf(100));
		restTemplate.put(getRootUrl() + "/accounts/transfer", transferAccountRequest);

		Account account_1L = restTemplate.getForObject(getRootUrl() + "/accounts/" + accountRef, Account.class);
		Account account_2L = restTemplate.getForObject(getRootUrl() + "/accounts/" + destinationAccount, Account.class);
		assertThat(account_1L.getBalance().doubleValue()).isEqualTo(900);
		assertThat(account_2L.getBalance().doubleValue()).isEqualTo(2100);

		transferAccountRequest = new TransferBalanceRequest(accountRef, destinationAccount, BigDecimal.valueOf(100));
		restTemplate.put(getRootUrl() + "/accounts/transfer", transferAccountRequest);

		account_1L = restTemplate.getForObject(getRootUrl() + "/accounts/" + accountRef, Account.class);
		account_2L = restTemplate.getForObject(getRootUrl() + "/accounts/" + destinationAccount, Account.class);
		assertThat(account_1L.getBalance().doubleValue()).isEqualTo(1000);
		assertThat(account_2L.getBalance().doubleValue()).isEqualTo(2000);

	}
}
