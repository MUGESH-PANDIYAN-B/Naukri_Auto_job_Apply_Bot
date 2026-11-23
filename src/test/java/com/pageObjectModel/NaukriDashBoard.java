package com.pageObjectModel;

import com.base.baseSetup;
import com.pageObjectManager.PageObjectManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class NaukriDashBoard {
    public Object getTextOfChatBot;

    public NaukriDashBoard(){
        PageFactory.initElements(baseSetup.driver, this);
    }
    static List <String> textOfRatioButton;
    public int selectedCount;
    List <WebElement> checkBoxList;
    public int i;
    int j;
    String checkedBoxAttributes;
    @FindBy(xpath = "//div[@title=\"MUGESH PANDIYAN B\"]")
    private WebElement MUGESH_PANDIYANB;
public WebElement getMUGESH_PANDIYANB(){
    return MUGESH_PANDIYANB;
}
@FindBy(xpath="//a[@class=\"nI-gNb-header__logo nI-gNb-company-logo\"]")
    private WebElement logo;
public void clickLogo(){
    logo.click();
}
@FindBy(xpath = "//div[@class=\"tab-wrapper\"]")
    private List <WebElement> tabWrapper;
@FindBy(xpath = "//div[@class=\"ni-citem\"]")
private List<WebElement> niCitem;
public void clickTabWrapper(){
    List <WebElement> tabWrapperList=new ArrayList<>(tabWrapper);
    int tabWrapperSize=tabWrapperList.size();
    System.out.println(tabWrapperSize);
    for(WebElement element:tabWrapperList){

       String tabWrapperAttributes= element.getAttribute("class");
       Boolean interActionCheck=element.isDisplayed()&& element.isEnabled();
       System.out.println("Wrapper are: "+interActionCheck);
       System.out.println(tabWrapperAttributes);
        element.click();
        List<WebElement> niCitemList=new ArrayList<>(niCitem);
        System.out.println(niCitemList.size());


    }

}
@FindBy(xpath = "//button[contains(text(), 'Preferences')]")
    private WebElement jobPreferenceButton1;
public void clickJobPreferenceButton1() {
    jobPreferenceButton1.click();
}
    @FindBy(xpath ="//div[contains(text(), 'Preferences')]")
    public WebElement jopPreferenceButton;
    public void clickJopPreferenceButton() {
        jopPreferenceButton.click();
    }
    @FindBy(xpath = "//span[contains(text(),'View all')]")
    public WebElement viewAllButton;
    public void clickViewAllButton() {
        viewAllButton.click();

    }
    @FindBy(xpath = "//i[@class=\"dspIB naukicon naukicon-ot-checkbox\"]")
    public static List <WebElement> checkBox;
    public List<WebElement> clickCheckBox() {
        List <WebElement> checkBoxList=new ArrayList<>(checkBox);
        int checkBoxSize=checkBoxList.size();
        System.out.println(checkBoxSize);
        for(i=0;i<checkBoxSize;i++){
            String checkBoxAttributes= checkBoxList.get(i).getAttribute("class");
            WebElement checkBoxBoolean=checkBoxList.get(i);
            Boolean interActionCheck=checkBoxBoolean.isDisplayed()&& checkBoxBoolean.isEnabled();
            System.out.println("Wrapper are: "+interActionCheck);
            checkBoxBoolean.click();

            try {
                WebDriverWait wait = new WebDriverWait(baseSetup.driver, Duration.ofSeconds(10));
                boolean isReady = wait.until(driver -> {
                    try {
                        String checkedBoxAttributes=checkBoxBoolean.getAttribute("class");
                        if(checkBoxBoolean.isDisplayed() && checkBoxBoolean.isEnabled()&&!checkedBoxAttributes.contains(checkBoxAttributes)){
                           selectedCount++;
                            return true;
                        }
                    } catch (org.openqa.selenium.StaleElementReferenceException | org.openqa.selenium.NoSuchElementException e) {

                    }
                    return false;

                });

            } catch (org.openqa.selenium.TimeoutException e) {
                System.out.println("selectCount"+selectedCount);
                System.out.println("Interaction check for the checkbox is completed and failed to click");
                clickApplyButton();
                System.out.println("Apply Button is Clicked");
                break;
            }

        }
        try {
            if(i==checkBoxSize) {
                clickApplyButton();
                System.out.println("Loop were Ended cause it reached the loop size");
            }
        }catch (Exception e){
            System.out.println(("In Loop Exception"));
            clickJobPreferenceButton1();
            clickCheckBox();
        }
        System.out.println(selectedCount);
        return checkBoxList;
    }
    @FindBy(xpath = "//div[@class=\"tab-wrapper\"]//div[contains(text(),'Handpicked for you')]")
    public WebElement profileTab;
    public void clickHandpickedForYouTab() {
        profileTab.click();
    }
    @FindBy(xpath = "//button[contains(text(), 'You might like')]")
    public WebElement youMightLikeButton;
    public void clickYouMightLikeButton() {
        youMightLikeButton.click();
    }
    @FindBy(xpath = "//button[@class=\"multi-apply-button typ-16Bold \"]")
    public static WebElement multiApplyButton;
    public static void clickApplyButton() {
        multiApplyButton.click();
    }
@FindBy(xpath = "//div[@class=\"botMsg msg \"]//span")
private static List<WebElement> botMsg;
    public static String getTextOfChatBot(){
        WebDriverWait wait=new WebDriverWait(baseSetup.driver, Duration.ofSeconds(10));
        List<WebElement> waitAllElement=wait.until(ExpectedConditions.visibilityOfAllElements(botMsg));
       wait.until(driver -> !botMsg.getLast().getText().trim().isEmpty());
        String getText=waitAllElement.getLast().getText();
        System.out.println(getText);
        return getText;

    }
    @FindBy(xpath = "//div[@data-placeholder=\"Type message here...\"]")
public static WebElement botTextField;
    public void clickBotTextField(String answer) {
        botTextField.sendKeys(answer);
    }
    @FindBy(xpath = "//div[@class=\"sendMsg\"]")
    private WebElement clickBotSaveButton;
    public void getclickBotSaveButton() {
        clickBotSaveButton.click();
    }
    @FindBy(xpath = "//label[@class=\"ssrc__label\"]")
    public static List <WebElement> ratioButtonLabel;
public static String gettingTextOfRatioButton() {
    List<String> textOfRadioButton = new ArrayList<>();

    try {
        for(WebElement element:ratioButtonLabel){
            if (element.isDisplayed()&&element.isEnabled())
            {
                String textOFElement=element.getText().trim();

                    if(!textOFElement.isEmpty()) {
                        textOfRadioButton.add(textOFElement);
                    }


            }
        }

    } catch (Exception e) {
        System.out.println("Ratio Button Failed");
        return null;
    }
    String singleString=String.join(",",textOfRatioButton);
    return singleString;
}

}
