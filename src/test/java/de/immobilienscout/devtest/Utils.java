package de.immobilienscout.devtest;

import java.util.StringJoiner;


public class Utils {

    private Utils() {}

    public static String link(String href) {
        return new StringJoiner("").add("<a href=\"")
                .add(href )
                .add("\"> Link...</a>").toString();
    }
}
