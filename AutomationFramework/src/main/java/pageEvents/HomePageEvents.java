package main.java.pageEvents;

import main.java.pageObjects.HomePageElements;
import main.java.utils.ElementFetch;

import javax.xml.xpath.XPath;

public class HomePageEvents {

    public void clickOnSignInButton(){
        ElementFetch elementFetch = new ElementFetch();
        elementFetch.getWebElement("XPath", HomePageElements.signInButton).click();

    }
}
