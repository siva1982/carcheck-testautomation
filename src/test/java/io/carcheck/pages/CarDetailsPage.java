package io.carcheck.pages;

import io.carcheck.modal.Car;
import io.cucumber.java8.Ca;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sound.midi.Soundbank;
import java.io.ByteArrayInputStream;
import java.sql.Time;
import java.time.Duration;

@Component
public class CarDetailsPage extends Page {


    @FindBy(xpath = "//dt[contains(text(),'Registration')]/parent::dl/dd")
    WebElement txtRegistration;

    @FindBy(xpath = "//dt[contains(text(),'Make')]/parent::dl/dd")
    WebElement txtMake;

    @FindBy(xpath = "//dt[contains(text(),'Model')]/parent::dl/dd")
    WebElement txtModel;

    @FindBy(xpath = "//dt[contains(text(),'Colour')]/parent::dl/dd")
    WebElement txtColor;

    @FindBy(xpath = "//dt[contains(text(),'Year')]/parent::dl/dd")
    WebElement txtYear;

    @FindBy(xpath = "//a[contains(text(), 'Try Again')]")
    WebElement btnTryAgain;

    public void openURL(String url) {
        driver.navigate().to(url);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //Wait to click the cloudflare dialogue box link otherwise the data is not getting visible
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[text()='Skip, with limited data']")))).click();
    }

    /**
     * While generating report if the vehice details not found, then it will attach the evidence
     * to the report
     * @return car details
     */
    public Car getCarDetails() {
        if (!vehicleFound()) {
            Allure.addAttachment("Not found", new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
            return null;
        }

        return new Car(txtRegistration.getText(),
                txtMake.getText(),
                txtModel.getText(),
                txtColor.getText(),
                txtYear.getText());
    }

    /**
     * Method to check whether the vehicle is found or not
     * @return
     */
    private boolean vehicleFound() {
        boolean found = true;
        for (int count = 0; count < 10; count++) {
            try {
                btnTryAgain.getText();
                found = false;
                break;
            } catch (StaleElementReferenceException ex) {
            } catch (NoSuchElementException e) {
                break;
            }
        }
        return found;
    }
}
