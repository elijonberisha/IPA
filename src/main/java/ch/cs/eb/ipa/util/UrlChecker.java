package ch.cs.eb.ipa.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * author: Elijon Berisha
 * date: 15.03.2022
 * class: UrlChecker.java
 */

public class UrlChecker {
    public boolean isUrl(String url) {
        try {
            (new URL(url)).openStream().close();
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUrlRegex(String url) {
        if (url.matches("((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+(:[0-9]+)?|(?:www.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)")) {
            return true;
        } else {
            return false;
        }
    }
}
