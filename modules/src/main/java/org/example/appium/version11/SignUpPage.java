package io.github.mfaisalkhatri.android.pages.wdio;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;

import static io.github.mfaisalkhatri.drivers.AndroidDriverManager.getDriver;

/**
 * @author Faisal Khatri
 * @since 2/25/2023
 **/
//@EAT({"modules/testcases/jdkAll/Android/appium/appium-java-examples/src/test/java#10.9.9"})
public class SignUpPage {


    private WebElement signUpLink() {
        return getDriver().findElement(AppiumBy.accessibilityId("button-sign-up-container"));
    }

    private WebElement emailField() {
        return getDriver().findElement(AppiumBy.accessibilityId("input-email"));
    }

    private WebElement passwordField() {
        return getDriver().findElement(AppiumBy.accessibilityId("input-password"));
    }

    private WebElement confirmPasswordField() {
        return getDriver().findElement(AppiumBy.accessibilityId("input-repeat-password"));
    }

    private WebElement signUpBtn() {
        return getDriver().findElement(AppiumBy.accessibilityId("button-SIGN UP"));
    }

    public String getSuccessMessageTitle() {
        try {
            String s = getDriver().findElement(AppiumBy.id("android:id/alertTitle")).getText();
            return s;
        } catch(Exception e) {
            return null;
        }
    }

    public String getSuccessMessage() {
        return getDriver().findElement(AppiumBy.id("android:id/message")).getText();
    }

    private WebElement okBtn() {
        return getDriver().findElement(AppiumBy.id("android:id/button1"));
    }

    private void openSignUpForm() {
        signUpLink().click();
    }

    public void signUp(String email, String password) {
        HomePage homePage = new HomePage();
        homePage.openMenu("Login");
        openSignUpForm();
        emailField().sendKeys(email);
        passwordField().sendKeys(password);
        confirmPasswordField().sendKeys(password);
        signUpBtn().click();
    }
    
    public void signUpError(String email, String password, String password2) {
        HomePage homePage = new HomePage();
        homePage.openMenu("Login");
        openSignUpForm();
        emailField().sendKeys(email);
        passwordField().sendKeys(password);
        confirmPasswordField().sendKeys(password2);
        signUpBtn().click();
    }

    public void closeSuccessMessage() {
        okBtn().click();
    }
    
}
