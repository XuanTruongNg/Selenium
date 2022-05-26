import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;

import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class XSS_test {
    WebDriver driver;

    @BeforeTest
    public void init() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://juice1001.herokuapp.com/?fbclid=IwAR1XCyrcx6DtnV0QQN-xqU9PNHkxn8uJZrL469HwaLB-G0-OZcHVIT2CKDA#/search");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//*[@id=\"mat-dialog-0\"]/app-welcome-banner/div/div[2]/button[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"navbarAccount\"]/span[1]/span")).click();
        driver.findElement(By.xpath("//*[@id=\"navbarLoginButton\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("truong@gmail.com");
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("truong@gmail.com");
        driver.findElement(By.xpath("//*[@id=\"loginButton\"]")).click();
        driver.manage().timeouts().implicitlyWait(300,TimeUnit.SECONDS);




    }

    @Test
    public void test1() throws InterruptedException, IOException {

        driver.findElement(By.xpath("//*[@id=\"navbarAccount\"]/span[1]/span")).click();

        try {
            driver.findElement(By.xpath("//*[@id=\"mat-menu-panel-0\"]/div/button[1]")).click();

        } catch (StaleElementReferenceException e) {
            driver.findElement(By.xpath("//*[@id=\"mat-menu-panel-0\"]/div/button[1]")).click();

        }
        FileReader fileReader = new FileReader("C:\\Users\\xuantruong\\code1\\demo_selemium\\src\\test\\java\\payloadXSS.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            driver.findElement(By.xpath("//*[@id=\"username\"]")).clear();
            driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys(line);
            driver.findElement(By.xpath("//*[@id=\"submit\"]/span")).click();
            driver.manage().timeouts().implicitlyWait(300,TimeUnit.SECONDS);
            try {
                Alert alert = driver.switchTo().alert();
                System.out.println("loi");
            } catch (NoAlertPresentException e) {
                System.out.println("không có thông báo");
            }
        }


    }


    @AfterTest
    public void finishTest() {


    }
}
