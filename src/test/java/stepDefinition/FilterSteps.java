package stepDefinition;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FilterSteps {

	WebDriver driver;

    @BeforeStep
    public void beforeStep() {
        System.out.println("Executing step...");
    }

    @AfterStep
    public void afterStep() {
        System.out.println("Step executed.");
    }

    @Before
    public void setup() {
        driver = new ChromeDriver();
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I log in to SauceDemo with username {string} and password {string}")
    public void i_log_in_to_saucedemo_with_username_and_password(String username, String password) throws InterruptedException {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(3000);
    }

    @When("I apply the filter {string}")
    public void i_apply_the_filter(String filterOption) throws InterruptedException {
        WebElement filterDropdown = driver.findElement(By.className("product_sort_container"));
        filterDropdown.sendKeys(filterOption);
        Thread.sleep(2000);
    }

    @Then("I should see products sorted according to {string}")
    public void i_should_see_products_sorted_according_to(String expectedFilter) {
        List<WebElement> productPrices = driver.findElements(By.className("inventory_item_price"));

        if (expectedFilter.equals("Price (low to high)")) {
            double previousPrice = 0.0;
            for (WebElement priceElement : productPrices) {
                double currentPrice = Double.parseDouble(priceElement.getText().replace("$", ""));
                Assert.assertTrue(currentPrice >= previousPrice, "Products are not sorted correctly!");
                previousPrice = currentPrice;
            }
        } else if (expectedFilter.equals("Price (high to low)")) {
            double previousPrice = Double.MAX_VALUE;
            for (WebElement priceElement : productPrices) {
                double currentPrice = Double.parseDouble(priceElement.getText().replace("$", ""));
                Assert.assertTrue(currentPrice <= previousPrice, "Products are not sorted correctly!");
                previousPrice = currentPrice;
            }
        } else {
            Assert.fail("Invalid filter option provided.");
        }
    }
	
}
