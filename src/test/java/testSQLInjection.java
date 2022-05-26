import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;

import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class testSQLInjection {
    WebDriver driver;
    @BeforeTest
    public  void init(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://juice1001.herokuapp.com/?fbclid=IwAR1XCyrcx6DtnV0QQN-xqU9PNHkxn8uJZrL469HwaLB-G0-OZcHVIT2CKDA#/search");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//*[@id=\"mat-dialog-0\"]/app-welcome-banner/div/div[2]/button[2]")).click();
        driver.findElement(By.xpath("//*[@id=\"navbarAccount\"]/span[1]/span")).click();
        driver.findElement(By.xpath("//*[@id=\"navbarLoginButton\"]")).click();

    }

    @Test
    public void test1() throws InterruptedException, IOException {
        int i = 0;
        FileReader fileReader = new FileReader("C:\\Users\\xuantruong\\code1\\demo_selemium\\src\\test\\java\\payLoadSQL");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = null;
        while ((line = bufferedReader.readLine())!=null){
            String url1 = driver.getCurrentUrl();
            driver.findElement(By.xpath("//*[@id=\"email\"]")).clear();
            driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys(line);
            driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("12344");
            driver.findElement(By.xpath("//*[@id=\"loginButton\"]")).click();
            String url2 = driver.getCurrentUrl();
            driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
            if(!url2.equals(url1)){
                i += 1;
                break;
            }
        }
        Assert.assertEquals(i,0);








    }

    @AfterTest
    public void finishTest()  {
        driver.quit();


    }
}
