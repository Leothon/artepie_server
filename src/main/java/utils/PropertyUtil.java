package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {


    private static Properties payProperties;

    private PropertyUtil() {
    }

    public static Properties getInstance() {
        if (payProperties == null) {
            payProperties = new Properties();
            InputStream in = null;
            try {
                in = PropertyUtil.class.getResourceAsStream("/pay.properties");
                payProperties.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return payProperties;
    }
}
