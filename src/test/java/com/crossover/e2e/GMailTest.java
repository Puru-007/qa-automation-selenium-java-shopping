package com.crossover.e2e;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;
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
        wait = new WebDriverWait(driver,20);
    }

    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testSendEmail() {
        driver.get("https://mail.google.com/");

        //Enter User Name read from the properties file
        WebElement userElement = driver.findElement(By.id("identifierId"));
        String userName = properties.getProperty("username");
        userElement.sendKeys(userName);

        //Click next
        driver.findElement(By.id("identifierNext")).click();

        //Enter Password read from the properties file
        By passwordElementIdentifier = By.name("password");
        wait.until(ExpectedConditions.presenceOfElementLocated(passwordElementIdentifier));
        WebElement passwordElement = driver.findElement(passwordElementIdentifier);
        String passwordValue = properties.getProperty("password");
        passwordElement.sendKeys(passwordValue);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("passwordNext")));
        driver.findElement(By.id("passwordNext")).click();

        //Click Compose
        By composeElementIdentifier = By.xpath("//*[@role='button' and (.)='Compose']");
        wait.until(ExpectedConditions.presenceOfElementLocated(composeElementIdentifier));
        driver.findElement(composeElementIdentifier).click();

        By toFieldIdentifier = By.name("to");
        wait.until(ExpectedConditions.presenceOfElementLocated(toFieldIdentifier));
        WebElement txtBoxToField = driver.findElement(toFieldIdentifier);
        txtBoxToField.clear();
        String toUserValue = properties.getProperty("username");
        txtBoxToField.sendKeys(String.format("%s@gmail.com",toUserValue));

        // emailSubject and emailbody to be used in this unit test.
        String emailSubject = properties.getProperty("email.subject");
        String emailBody = properties.getProperty("email.body");

        //Enter subject
        By subjectBoxIdentifier = By.name("subjectbox");
        wait.until(ExpectedConditions.presenceOfElementLocated(subjectBoxIdentifier));
        WebElement subjectBox = driver.findElement(subjectBoxIdentifier);
        subjectBox.clear();
        subjectBox.sendKeys(emailSubject);

        //Enter email body
        driver.findElement(By.xpath("//div[@class='Am Al editable LW-avf']")).clear();
        driver.findElement(By.xpath("//div[@class='Am Al editable LW-avf']")).sendKeys(emailBody);

        //Click More settings
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='J-JN-M-I J-J5-Ji Xv L3 T-I-ax7 T-I']/div[2]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[@class='J-Ph-hFsbo']"))).click();

        //Choose Social label
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='J-LC-Jz' and text()='Social']/div[@class='J-LC-Jo J-J5-Ji']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='J-JK-Jz' and text()='Apply']"))).click();

        //Click Send
        driver.findElement(By.xpath("//*[@role='button' and text()='Send']")).click();

        //Click Social Tab
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='aKz' and text()='Social']"))).click();

        //Thread.sleep(5000);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//table[@class='F cf zt']/tbody/tr[1]/td[3]/span")));

        try{
            driver.findElement(By.xpath("//table[@class='F cf zt']/tbody/tr[1]/td[3]/span")).click();
        }catch (ElementNotVisibleException e){
            driver.findElements(By.xpath("//table[@class='F cf zt']/tbody/tr[1]/td[3]/span")).get(1).click();
        }

        //Open the received email
        try {
            driver.findElement(By.xpath("//table[@class='F cf zt']/tbody/tr[1]")).click();
        }catch (ElementNotVisibleException e){
            driver.findElements(By.xpath("//table[@class='F cf zt']/tbody/tr[1]")).get(1).click();
        }

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
        assertEquals(emailSubject, subjectOfReceivedEmail);

        String bodyOfReceivedEmail = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='a3s aXjCH ']"))).getText();
        assertEquals(emailBody, bodyOfReceivedEmail);
    }
}
