package com.crossover.e2e;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import com.crossover.pages.GmailPage;
import com.crossover.pages.LoginPage;
import junit.framework.TestCase;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class GMailTest extends TestCase {
    private WebDriver driver;
    private Properties properties = new Properties();
    private WebDriverWait wait;

    public void setUp() throws Exception {
        
        properties.load(new FileReader(new File("src/test/resources/test.properties")));
        //Dont Change below line. Set this value in test.properties file incase you need to change it..
        System.setProperty("webdriver.chrome.driver",properties.getProperty("webdriver.chrome.driver") );
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Integer.parseInt(properties.getProperty("timeout")));
    }

    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testSendEmail() {
        driver.get("https://mail.google.com/");

        LoginPage loginPage = new LoginPage(driver, wait, properties);
        loginPage.login();

        GmailPage gmailPage = new GmailPage(driver,wait,properties);
        gmailPage.composeEmail();
        gmailPage.clickSocialTab();
        gmailPage.openReceivedEmail();

        //Verify email came under proper Label i.e. "Social"
        try{
            driver.findElement(By.xpath("//div[@class='T-I J-J5-Ji T-I-Js-Gs mA mw T-I-ax7 L3']")).click();
        }catch (ElementNotVisibleException e) {
            driver.findElements(By.xpath("//div[@class='T-I J-J5-Ji T-I-Js-Gs mA mw T-I-ax7 L3']")).get(1).click();
        }

        String isSocialLabelChecked = driver.findElement(By.xpath("//div[@class='J-LC J-Ks-KO J-LC-JR-Jp']"))
                .getAttribute("aria-checked");
        assertEquals("true", isSocialLabelChecked);

        //Verify the subject and body of the received email
        String subjectOfReceivedEmail = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h2[@class='hP']"))).getText();

        String emailSubject = properties.getProperty("email.subject");
        assertEquals(emailSubject, subjectOfReceivedEmail);

        String bodyOfReceivedEmail = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='a3s aXjCH ']"))).getText();
        String emailBody = properties.getProperty("email.body");
        assertEquals(emailBody, bodyOfReceivedEmail);
    }
}
