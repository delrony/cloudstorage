package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    // TODO Break this Class in several other Modal classes

    @FindBy(css="#logout-button")
    private WebElement logoutButton;

    @FindBy(css="#nav-notes-tab")
    private WebElement navNotesTabLink;

    @FindBy(css="#show-note")
    private WebElement showNoteButton;

    @FindBy(css="#note-title")
    private WebElement noteTitleText;

    @FindBy(css="#note-description")
    private WebElement noteDescriptionText;

    @FindBy(css="a.btn-delete-note")
    private WebElement noteDeleteButton;

    @FindBy(css="button.btn-edit-note")
    private WebElement noteEditButton;

    @FindBy(css="#userTable tbody")
    private WebElement noteTbody;

    @FindBy(css="#nav-credentials-tab")
    private WebElement navCredentialsTabLink;

    @FindBy(css="#show-credential")
    private WebElement showCredentialsButton;

    @FindBy(css="#credential-url")
    private WebElement credentialUrlText;

    @FindBy(css="#credential-username")
    private WebElement credentialUsernameText;

    @FindBy(css="#credential-password")
    private WebElement credentialPasswordText;

    @FindBy(css="a.btn-delete-credential")
    private WebElement credentialDeleteButton;

    @FindBy(css="button.btn-edit-credential")
    private WebElement credentialEditButton;

    @FindBy(css="#credentialTable tbody")
    private WebElement credentialTbody;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        logoutButton.click();
    }

    /**
     * Notes
     */

    public void addNewNote(String title, String description, Boolean checkIfExists) throws InterruptedException {
        navNotesTabLink.click();

        Thread.sleep(2000);

        if (checkIfExists) {
            if (!noteTbody.getText().equals("")) {
                this.deleteNote();
            }
        }

        Thread.sleep(2000);

        showNoteButton.click();

        Thread.sleep(2000);

        noteTitleText.sendKeys(title);
        noteDescriptionText.sendKeys(description);
        noteTitleText.submit();
    }

    public void deleteNote() {
        noteDeleteButton.click();
    }

    public void editNote(String title, String description) throws InterruptedException {
        noteEditButton.click();

        Thread.sleep(2000);

        noteTitleText.sendKeys(title);
        noteDescriptionText.sendKeys(description);
        noteTitleText.submit();
    }

    /**
     * Credentials
     */

    public void addNewCredentials(String url, String username, String password) throws InterruptedException {
        navCredentialsTabLink.click();

        Thread.sleep(2000);

        if (!credentialTbody.getText().equals("")) {
            this.deleteCredential();
        }

        Thread.sleep(2000);

        showCredentialsButton.click();

        Thread.sleep(2000);

        credentialUrlText.sendKeys(url);
        credentialUsernameText.sendKeys(username);
        credentialPasswordText.sendKeys(password);
        credentialUrlText.submit();
    }

    public void deleteCredential() {
        credentialDeleteButton.click();
    }

    public void showCredentialModal() throws InterruptedException {
        credentialEditButton.click();

        Thread.sleep(2000);
    }

    public String getCredentialPassword() {
        return credentialPasswordText.getAttribute("value");
    }

    public void editCredential(String url, String username, String password) throws InterruptedException {
        credentialUrlText.clear();
        credentialUrlText.sendKeys(url);

        credentialUsernameText.clear();
        credentialUsernameText.sendKeys(username);

        credentialPasswordText.clear();
        credentialPasswordText.sendKeys(password);

        credentialUrlText.submit();
    }
}
