package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
	
	
    private static Properties props;

    public static void load() {
        if (props != null) return;
        props = new Properties();
        try(FileInputStream fis = new FileInputStream("config/config.properties")){
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key){
        load();
        return props.getProperty(key);
    }

    public static int getInt(String key){
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key){
        return Boolean.parseBoolean(get(key));
    }
    
    
    
}
