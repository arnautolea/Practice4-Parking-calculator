package com.Parking;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ParkingCalculatorN {
	
	private static WebDriver driver;

	
	@DataProvider(name = "ParkingTestDataN")
	
	public static Object[][] Provider() {
		 
        return new Object[][] {
        	{ "Test1N - Invalid Parking", "Long-Term Garage Parking", "0:00", "AM", "3/1/2019", "0:00", "AM", "1/1/2019", "", "ERROR! YOUR EXIT DATE OR TIME IS BEFORE YOUR ENTRY DATE OR TIME"},
        };
	}
   @Test(dataProvider = "ParkingTestDataN")
     
    public void test
(String TestCaseName, String Lot, String startT, String startAmPm, String startD, String endT, String endAmPm, String endD, String Cost, String Time)
 throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.get("http://adam.goucher.ca/parkcalc/index.php");

        Assert.assertEquals(driver.getTitle(), "Parking Calculator");


        Select chooseALotSelect = new Select(driver.findElement(By.xpath("//tr[td[contains(text(),'Choose a Lot')]]//select[@id='Lot']")));
        chooseALotSelect.selectByVisibleText(Lot);

        WebElement startTime = driver.findElement(By.xpath("//tr[td[contains(text(),'Choose Entry Date and Time')]]//input[@name='EntryTime']"));
        List<WebElement> startDateAmPm = driver.findElements(By.xpath("//tr[td[contains(text(),'Choose Entry Date and Time')]]//input[@name='EntryTimeAMPM']"));
        WebElement startDate = driver.findElement(By.xpath("//tr[td[contains(text(),'Choose Entry Date and Time')]]//input[@name='EntryDate']"));

        startTime.clear();
        startTime.sendKeys(startT);
        selectRadioValue(startDateAmPm, startAmPm);
        startDate.clear();
        startDate.sendKeys(startD);

        WebElement endTime = driver.findElement(By.xpath("//tr[td[contains(text(),'Choose Leaving Date and Time')]]//input[@name='ExitTime']"));
        List<WebElement> endDateAmPm = driver.findElements(By.xpath("//tr[td[contains(text(),'Choose Leaving Date and Time')]]//input[@name='ExitTimeAMPM']"));
        WebElement endDate = driver.findElement(By.xpath("//tr[td[contains(text(),'Choose Leaving Date and Time')]]//input[@name='ExitDate']"));

        endTime.clear();
        endTime.sendKeys(endT);
        selectRadioValue(endDateAmPm, endAmPm);
        endDate.clear();
        endDate.sendKeys(endD);


        WebElement submitBtn = driver.findElement(By.xpath("//input[@name='Submit' and @value='Calculate']"));

        submitBtn.click();

        Thread.sleep(1000);

       WebElement calculatedTime = driver.findElement(By.xpath("//tr[contains(.,'COST')]/td//b[contains(text(),'ERROR')]"));

        Assert.assertEquals(calculatedTime.getText().trim(), Time);
        
        driver.quit();
    }

    public void selectRadioValue(List<WebElement> list, String selectValue){
        for(WebElement elem:list){
            String paramValue = elem.getAttribute("value");
            if (StringUtils.equals(selectValue,paramValue)){
                elem.click();
                return;
            }
        }
    }
}