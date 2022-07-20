package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesReader {
    public static Logger logger = Logger.getLogger((PropertiesReader.class).toString());
    Properties properties = new Properties();
    public PropertiesReader() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {

            properties.load(input);

        } catch (IOException ex) {
            logger.info("Properties file is not exist!");
            ex.printStackTrace();
        }

    }
    public String getURL(){
       return properties.getProperty("URL");
    }

    public long getDefaultWaitTime(){
        return Long.parseLong(properties.getProperty("DEFAULT_WAITING_TIME"));
    }
}
