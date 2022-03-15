package ch.cs.eb.ipa.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
}
