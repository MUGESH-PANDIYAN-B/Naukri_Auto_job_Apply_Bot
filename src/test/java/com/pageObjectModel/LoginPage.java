package com.pageObjectModel;

import com.base.baseSetup;
import com.interfaceElement.LoginPageInterFace;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.security.PublicKey;

public class LoginPage implements LoginPageInterFace {
    public LoginPage(){
        PageFactory.initElements(baseSetup.driver, this);
    }
    @FindBy(xpath = "//input[@id=\"usernameField\"]")
    protected WebElement eMailField;
    public void enterEmail(){
        eMailField.sendKeys("mugeshpandiyanb@gmail.com");
    }
    @FindBy(xpath = "//input[@id=\"passwordField\"]")
    protected WebElement passwordField;
public void enterPassword(){
    passwordField.sendKeys("Lastleaf+");
}
    @FindBy(xpath = "//button[@class=\"waves-effect waves-light btn-large btn-block btn-bold blue-btn textTransform\"]")
    protected WebElement loginButton;
    public void clickLoginButton(){
        loginButton.click();
}
@FindBy(xpath ="//span[contains(text(), 'Login')]")
    public WebElement jopPreferenceButton;
    public void clickJopPreferenceButton() {
        jopPreferenceButton.click();
    }
}
