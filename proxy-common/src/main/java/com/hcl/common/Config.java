package com.hcl.common;

import com.sun.org.apache.xml.internal.security.Init;
import org.apache.commons.lang3.StringUtils;

import javax.swing.text.ViewFactory;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * hechenglong
 * 2019/9/24
 */
public class Config {

    private static final String DEFAULT_CONFIG = "config.properties";

    private Properties configuration = new Properties() ;

    private static Map<String,Config> instances = new HashMap<String,Config>();

    private void init(String fileName){
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
        try {
            configuration.load(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Config(){
        init(DEFAULT_CONFIG);
    }

    public Config(String fileName){
        init(fileName);
    }

    public static Config getInstance(){
        return getInstance(DEFAULT_CONFIG);
    }

    public static Config getInstance(String fileName){
        Config config = instances.get(fileName);
        if(config == null){
            synchronized (instances){
                config = instances.get(fileName);
                if(config == null){
                    config = getInstance(fileName);
                    instances.put(fileName,config);
                }
            }
        }
        return config;
    }

    public String getStringValue(String key){
        return this.configuration.getProperty(key);
    }

    public String getStringValueWithDefault(String key,String defaultValue){
        String value = this.configuration.getProperty(key);
        if(StringUtils.isEmpty(value)){
            return defaultValue;
        }
        return value;
    }


}
