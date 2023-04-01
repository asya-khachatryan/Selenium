import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.locators.RelativeLocator;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SeleniumTest {
    public static WebDriver driver;
    public static String baseUrl = "https://www.inecobank.am/hy/Individual";


    @BeforeAll
    public static void initWebDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @BeforeEach
    public void navigateToBaseUrl(){
        driver.get(baseUrl);
    }

    @Test
    public void navigationToBusinessPage() {
        WebElement productsLink = driver.findElement(By.xpath("//a[text()='Բիզնես']"));
        productsLink.click();
        String expectedUrl = "https://www.inecobank.am/hy/Business";
        assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    @Test
    public void wrongSignUp() {
        SoftAssertions softAssert = new SoftAssertions();
        WebElement signUpLink = driver.findElement(By.xpath("//span[text()='Գրանցվել']"));
        By singInButtonLocator = RelativeLocator.with(By.className("btn__link")).near(signUpLink);
        driver.findElement(singInButtonLocator).click();
        String expectedUrl = "https://www.inecobank.am/hy/Individual/register";
        softAssert.assertThat(driver.getCurrentUrl()).isEqualTo(expectedUrl);
        driver.findElement(By.id("phone")).sendKeys("99123456");
        driver.findElement(By.id("email")).sendKeys("name.surname@example.com");
        driver.findElement(By.cssSelector("#root > div > main > div.container.container--main > div > div:nth-child(2) > div > form > div.formSubmitButton > button > span")).click();
        String url = "https://www.inecobank.am/hy/Business/register/confirm/phone";
        softAssert.assertThat(driver.getCurrentUrl()).isNotEqualTo(url);
        softAssert.assertAll();
    }

    @AfterAll
    public static void quit() {
        driver.quit();
    }

}
