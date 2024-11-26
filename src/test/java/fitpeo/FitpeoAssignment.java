package fitpeo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FitpeoAssignment {

	public static void main(String[] args) {

		// Initialize WebDriver (ChromeDriver in this case)
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        
        // Set implicit wait time for elements to load
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // WebDriverWait for explicit waits on specific elements
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // JavaScript Executor for scrolling functionality
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Step 1: Open FitPeo Homepage
            driver.get("https://www.fitpeo.com/");
            System.out.println("Navigated to FitPeo Homepage.");

            // Step 2: Click to navigate to the Revenue Calculator page
            WebElement revenueCalculatorLink = driver.findElement(By.xpath("//div[contains(text(),'Revenue Calculator')]"));
            revenueCalculatorLink.click();
            System.out.println("Navigated to Revenue Calculator Page.");

            // Scroll to the slider section
            WebElement slider = driver.findElement(By.xpath("//input[@type='range']"));
            js.executeScript("arguments[0].scrollIntoView();", slider);

            // Adjust slider to 820
            int currentValue = Integer.parseInt(slider.getAttribute("value"));
            int clicksToAdjust = 820 - currentValue;
            for (int i = 0; i < clicksToAdjust; i++) {
                slider.sendKeys(Keys.ARROW_RIGHT);
            }

            // Verify the updated slider value
            Thread.sleep(3000); // Waiting for See the Updated value on Page
            String sliderValue = slider.getAttribute("value");
            System.out.println("Slider value is set to: " + sliderValue);

            // Step 3: Validate that the text field matches the slider value
            WebElement textField = driver.findElement(By.xpath("//input[@type='number']"));
            if (textField.getAttribute("value").equals(sliderValue)) {
                System.out.println("Text Field updated according to Slider.");
            } else {
                System.out.println("Text Field failed to update.");
            }

            // Step 4: Update the Text Field to 560
            driver.navigate().refresh(); // Refresh the page to reset elements
            //Re Assigning WebElements to avoid StaleElementException 
            textField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='number']")));
            slider = driver.findElement(By.xpath("//input[@type='range']"));

            // Adjust the value of text field to 560
            int newSliderValue = Integer.parseInt(slider.getAttribute("value"));
            int adjustTo560 = 560 - newSliderValue;
            for (int i = 0; i < adjustTo560; i++) {
                textField.sendKeys(Keys.ARROW_UP);
            }
            System.out.println("Text value is set to: " + textField.getAttribute("value"));
            Thread.sleep(3000); // Waiting for See the Updated value on Page
            
            // Validate if the slider value is in sync with the text field
            if (textField.getAttribute("value").equals(slider.getAttribute("value"))) {
                System.out.println("Slider updated according to the Text field.");
            } else {
                System.out.println("Slider failed to update.");
            }

            // Step 5: Re-adjust slider to 820 To Get Total Recurring Reimbursement Value as showing an assignment document
            int finalAdjust = 820 - Integer.parseInt(slider.getAttribute("value"));
            for (int i = 0; i < finalAdjust; i++) {
                slider.sendKeys(Keys.ARROW_RIGHT);
            }

            // Step 6: Select CPT Codes
            WebElement cpt99091Checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='57']")));
            WebElement cpt99453Checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='19.19']")));
            WebElement cpt99454Checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='63']")));
            WebElement cpt99474Checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='15']")));

            // Select the checkboxes
            cpt99091Checkbox.click();
            cpt99453Checkbox.click();
            cpt99454Checkbox.click();
            cpt99474Checkbox.click();
            System.out.println("CPT Codes selected.");

            // Step 7: Validate the Total Recurring Reimbursement
            WebElement totalReimbursementHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(),'Total Recurring')]/p")));
            String totalReimbursementValue = totalReimbursementHeader.getText();

            if ("$110700".equals(totalReimbursementValue)) {
                System.out.println("Total recurring reimbursement value is correct: " + totalReimbursementValue);
            } else {
                System.out.println("Total recurring reimbursement value is incorrect: " + totalReimbursementValue);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } 
        finally {
            // Close the browser after the test
            driver.quit();
            System.out.println("Browser closed.");
        }
			 

	}

}
