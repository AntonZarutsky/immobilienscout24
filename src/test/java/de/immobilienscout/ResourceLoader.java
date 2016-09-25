package de.immobilienscout;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;


public final class ResourceLoader {

    public static String resource(String name){
        try(InputStream inputStream = ResourceLoader.class.getClassLoader().getResourceAsStream(name)){
            return IOUtils.toString(inputStream, Charset.defaultCharset());
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
