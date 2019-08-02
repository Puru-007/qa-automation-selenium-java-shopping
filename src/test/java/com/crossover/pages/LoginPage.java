package com.crossover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Properties;

public class LoginPage extends BasePage{

    public LoginPage(WebDriver driver, WebDriverWait wait, Properties properties) {
        super(driver, wait, properties);
    }

    public void login() {
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
    }
}
