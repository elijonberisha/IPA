package ch.cs.eb.ipa.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * author: Elijon Berisha
 * date: 15.03.2022
 * class: UrlChecker.java
 */

// CLASS IS USED TO VALIDATE THE USER INPUTS IN FORM OF A URL
public class UrlChecker {

    // CHECKS IF URL CAN BE OPENED; MAKES REQUEST TO URL
    public boolean isUrl(String url) {
        try {
            // REQUEST IS MADE; IF ABLE TO CONNECT -> TRUE | OTHERWISE -> FALSE
            (new URL(url)).openStream().close();
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // CHECKS IF URL MATCHES A URL REGEX PATTERN; NO REQUEST IS MADE TO URL
    public boolean isUrlRegex(String url) {
        // IF URL MATCHES REGEX -> TRUE | OTHERWISE -> FALSE
        if (url.matches("((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+(:[0-9]+)?|(?:www.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)")) {
            return true;
        } else {
            return false;
        }
    }
}
