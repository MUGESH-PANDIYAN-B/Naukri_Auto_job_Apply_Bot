package com.listener;

import com.base.baseSetup;
import com.pageObjectManager.PageObjectManager;
import com.pageObjectModel.NaukriDashBoard;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import static com.pageObjectModel.NaukriDashBoard.botTextField;
import static com.pageObjectModel.NaukriDashBoard.gettingTextOfRatioButton;

public class ApplyJoBNaukriTest extends baseSetup {
    static PageObjectManager pageObjectManager;
    List<WebElement> checkBox;
    int i;
    static String optionsOfChatBot;

    @BeforeTest
    public void setup() throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("D:\\WorkSpace\\Naukri\\src\\main\\resources\\Test.properties");
        prop.load(fis);
        String url = prop.getProperty("url");
        launchBrowser("chrome");
        launchUrl(url);
        pageObjectManager = PageObjectManager.getPageObjectManager();
    }

    @Test(priority = 1)
    public void loginTest() throws InterruptedException {
        pageObjectManager.getLoginPage().enterEmail();
        pageObjectManager.getLoginPage().enterPassword();
        pageObjectManager.getLoginPage().clickLoginButton();
    }

    @Test(priority = 2)
    public static void tearDown() {
        String title = driver.getTitle();
        System.out.println(title);
        PageObjectManager pageObjectManager = PageObjectManager.getPageObjectManager();
        WebElement MUGESH_PANDIYANB = pageObjectManager.getDashBoard().getMUGESH_PANDIYANB();
        verifyElementText(MUGESH_PANDIYANB, "MUGESH PANDIYAN B");
        pageObjectManager.getDashBoard().clickHandpickedForYouTab();
        pageObjectManager.getDashBoard().clickViewAllButton();

    }

    @Test(priority = 3)
    public static void clickCheckBox() {
        pageObjectManager.getDashBoard().clickCheckBox();
    }
    @Test(priority = 4)
    public static void answerBot() throws InterruptedException {
        String botText = pageObjectManager.getDashBoard().getTextOfChatBot();

        try
        {
            String optionsOfChatBot = pageObjectManager.getDashBoard().gettingTextOfRatioButton();
        }
        catch (Exception e)
        {
            System.out.println("No options");
        }
        String answer = pageObjectManager.getGeminiHelper().getAnswerFromGemini(botText, optionsOfChatBot);
        NaukriDashBoard naukriBoard = pageObjectManager.getDashBoard();
        try{
        naukriBoard.clickBotTextField(answer);
        naukriBoard.getclickBotSaveButton();
        System.out.println("Bot Answered: " + answer);}
        catch (Exception e){
            System.out.println("Bot Failed: " + answer);
        }
        Thread.sleep(3000);
        try {
            WebDriverWait wait = new WebDriverWait(baseSetup.driver, Duration.ofSeconds(10));
            boolean isReady = wait.until(driver -> {
                try {
                    return botTextField.isDisplayed() && botTextField.isEnabled();
                } catch (org.openqa.selenium.StaleElementReferenceException | org.openqa.selenium.NoSuchElementException e) {
                   System.out.println("Waiting for text field of bot to be appear");
                    return false;
                }
            });

            if (isReady) {
                answerBot();
            }
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Chatbot conversation completed!");
        }

    }
    @Test(priority = 5)
    static void backRefreshApply() throws InterruptedException {
Thread.sleep(3000);
        driver.navigate().back();
        driver.navigate().refresh();
        try{
            if(NaukriDashBoard.checkBox.getFirst().isDisplayed() && NaukriDashBoard.checkBox.getFirst().isEnabled()) {
                clickCheckBox();
                answerBot();
                backRefreshApply();
            }


        }catch (Exception e){
            System.out.println("No more jobs to apply on HandPicked for you tab");
            try {
                pageObjectManager.getDashBoard().clickJobPreferenceButton1();
            }catch (Exception e1){
                System.out.println("Failed To Click Job Preference");
            }
            }
        try{
            if(NaukriDashBoard.checkBox.getFirst().isDisplayed() && NaukriDashBoard.checkBox.getFirst().isEnabled()) {
                clickCheckBox();
                answerBot();
                backRefreshApply();
            }
        }
        catch (Exception e){
            System.out.println("No more jobs to apply on Job Preference tab");
            try {
                pageObjectManager.getDashBoard().clickYouMightLikeButton();
            } catch (Exception ex) {
                System.out.println("Failed To Click You Might Like Button");
            }
        }
        try {
            if(NaukriDashBoard.checkBox.getFirst().isDisplayed() && NaukriDashBoard.checkBox.getFirst().isEnabled()) {
                clickCheckBox();
                answerBot();
                backRefreshApply();
            }
        }
        catch (Exception e){
            System.out.println("No more jobs to apply on You Might Like tab");
        }
        try{
            ApplyJoBNaukriTest.tearDown();
            ApplyJoBNaukriTest.clickCheckBox();
            ApplyJoBNaukriTest.answerBot();
            ApplyJoBNaukriTest.backRefreshApply();
        } catch (Exception e) {
            System.out.println("All Failed");
        }
    }


}

