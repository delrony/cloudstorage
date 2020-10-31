package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		this.baseURL = "http://localhost:" + this.port;
	}

	@AfterEach
	public void afterAll() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testUnauthorizedUser() {
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get(baseURL + "/home");

		// User should be redirected to the login page
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testUserSignupLoginAndLogout() {
		String username = "pzastoup";
		String password = "whatabadpassword";
		String messageText = "Hello!";

		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Peter", "Zastoupil", username, password);

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		// Wait until the Home's logout button is loaded
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement marker1 = wait.until(webDriver -> webDriver.findElement(By.cssSelector("div.container")));

		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.logout();

		// Wait until the Login Form's submit button is loaded
		WebElement marker2 = wait.until(webDriver -> webDriver.findElement(By.id("submit-button")));

		driver.get(baseURL + "/home");

		// User should be redirected to the login page
		Assertions.assertEquals("Login", driver.getTitle());
	}
}
