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

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        logoutButton.click();
    }

    public void addNewNote(String title, String description, Boolean checkIfExists) throws InterruptedException {
        navNotesTabLink.click();

        // Wait until the Home page is loaded
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
}
