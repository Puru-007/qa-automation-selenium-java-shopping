package com.shopping.e2e;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import junit.framework.TestCase;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ShoppingTest extends TestCase {
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
    public void testMyShoppingSite() throws InterruptedException {
        driver.get("http://localhost:3000/");


        driver.findElement(By.xpath("//button[@class='Product_buttons__btn__2s4i7 Product_buttons__add__1u5wV']")).click();
        driver.findElement(By.xpath("//button[@class='Product_buttons__btn__2s4i7 Product_buttons__add__1u5wV']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@class='Product_buttons__btn__2s4i7 Product_buttons__add__1u5wV']")).click();
        driver.findElement(By.xpath("//button[@class='Product_buttons__btn__2s4i7 Product_buttons__add__1u5wV']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@class='Product_buttons__btn__2s4i7 Product_buttons__add__1u5wV']")).click();
        driver.findElement(By.xpath("//button[@class='Product_buttons__btn__2s4i7 Product_buttons__add__1u5wV']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@class='Product_buttons__btn__2s4i7 Product_buttons__add__1u5wV']")).click();
        driver.findElement(By.xpath("//button[@class='Product_buttons__btn__2s4i7 Product_buttons__add__1u5wV']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@class='Product_buttons__btn__2s4i7 Product_buttons__add__1u5wV']")).click();
        driver.findElement(By.xpath("//button[@class='Product_buttons__btn__2s4i7 Product_buttons__add__1u5wV']")).click();

        //Scenario 1 - Check if the items are correctly added to the cart
        String counter = driver.findElement(By.xpath("//div[@class='Navbar_cart__counter__3Tvmc']")).getText().toString();
        assertEquals("10", counter);

        Thread.sleep(4000);

        //Scenario 2 - Able to go to the cart
        driver.findElement(By.xpath("//div[@class='Navbar_navbar__cart__3aCcm']")).click();

        //Scenario 3 - Check if the added items are correct
        String itemName = driver.findElement(By.xpath("//p[@class='CartItem_details__title__Tiwsf']")).getText().toString();
        assertEquals("Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops", itemName);

        Thread.sleep(4000);

        //Scenario 4 - Check if the amount calculation is done right
        String amount = driver.findElement(By.xpath("//div[@class='myPrice']")).getText().toString();
        assertEquals("$ 1099.5", amount);

        Thread.sleep(4000);

        //Scenario 5 - Check if items can be cancelled
        driver.findElement(By.xpath("//button[@class='CartItem_actions__deleteItemBtn__3WzBc']")).click();
        Thread.sleep(3000);

        //Scenario 6 - Check after cancellation the cart is empty and price is 0
        amount = driver.findElement(By.xpath("//div[@class='myPrice']")).getText().toString();
        assertEquals("$ 0", amount);

        //Scenario 5 - Go back to the homepage
        driver.findElement(By.xpath("//h2[@class='Navbar_navbar__logo__aP_Pp']")).click();
        Thread.sleep(5000);
    }
}
