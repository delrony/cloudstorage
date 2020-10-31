package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
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
	// TODO Refactor code here as much as possible

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private WebDriverWait wait;
	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		this.baseURL = "http://localhost:" + this.port;

		this.wait = new WebDriverWait(driver, 5);
	}

	@AfterEach
	public void afterAll() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	public void signupAndLogin() throws InterruptedException {
		String username = "pzastoup";
		String password = "whatabadpassword";

		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Peter", "Zastoupil", username, password);

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		// Wait until the Home page is loaded
		Thread.sleep(2000);
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
	public void testUserSignupLoginAndLogout() throws InterruptedException {
		signupAndLogin();

		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.logout();

		// Wait until the Login Form's submit button is loaded
		wait.until(webDriver -> webDriver.findElement(By.id("submit-button")));

		driver.get(baseURL + "/home");

		// User should be redirected to the login page
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testAddNewNote() throws InterruptedException {
		String title = "TODO";
		String description = "New Task";

		signupAndLogin();

		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);

		homePage.addNewNote(title, description, true);

		Thread.sleep(2000);

		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertTrue(driver.findElement(By.id("nav-notes")).getAttribute("class").contains("show"));
		Assertions.assertEquals("TODO", driver.findElement(By.cssSelector("#userTable tbody th")).getText());
		Assertions.assertEquals("New Task", driver.findElement(By.cssSelector("#userTable tbody td:nth-child(3)")).getText());
	}

	@Test
	public void testDeleteNote() throws InterruptedException {
		String title = "TODO";
		String description = "New Task";

		signupAndLogin();

		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);

		homePage.addNewNote(title, description, true);
		Thread.sleep(2000);

		homePage.deleteNote();

		Thread.sleep(2000);

		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertTrue(driver.findElement(By.id("nav-notes")).getAttribute("class").contains("show"));
		// We deleted the only element, so the table content should be empty
		Assertions.assertEquals("", driver.findElement(By.cssSelector("#userTable tbody")).getText());
	}

	@Test
	public void testEditNote() throws InterruptedException {
		String title = "TODO";
		String description = "New Task";

		signupAndLogin();

		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);

		homePage.addNewNote(title, description, true);
		Thread.sleep(2000);

		homePage.editNote("_changed", "_changed");

		Thread.sleep(2000);

		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertTrue(driver.findElement(By.id("nav-notes")).getAttribute("class").contains("show"));
		Assertions.assertEquals("TODO_changed", driver.findElement(By.cssSelector("#userTable tbody th")).getText());
		Assertions.assertEquals("New Task_changed", driver.findElement(By.cssSelector("#userTable tbody td:nth-child(3)")).getText());
	}
}
