package botalibrium;

import org.springframework.boot.SpringApplication;

import java.io.FileNotFoundException;
import java.net.URL;

/**
 * Created by apolyakov on 4/11/2017.
 */
public class ServiceMain {

    private static final String[] REQUIRED_PROPERTIES_FILES = {"application.yaml"};

    public static void main(String[] args) throws FileNotFoundException {
        String classpath = System.getProperty("java.class.path");
        System.out.println(classpath);
        for (String fileName : REQUIRED_PROPERTIES_FILES) {
            URL url = Context.class.getClassLoader().getResource(fileName);
            if (url == null) {
                throw new FileNotFoundException(fileName + " was not found in classpath");
            }
        }
        new SpringApplication(Context.class).run(args);
    }
}
