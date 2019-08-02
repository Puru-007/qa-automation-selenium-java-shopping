package com.crossover.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Properties;

public class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Properties properties;

    public BasePage(WebDriver driver, WebDriverWait wait, Properties properties) {
        this.driver = driver;
        this.wait = wait;
        this.properties = properties;
    }
}
