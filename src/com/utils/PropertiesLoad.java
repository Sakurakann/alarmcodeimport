package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Project: alarmcodeimport
 * Package: com.utils
 * User: Administrator
 * Date: 2017-08-31 10:38
 * Author: Haiyangp
 */
public class PropertiesLoad {

  protected Properties properties = new Properties();

  public PropertiesLoad(String filePath) throws IOException {
    InputStream is = this.getClass().getResourceAsStream(filePath);
    this.properties.load(is);
    is.close();
  }

  public PropertiesLoad(File file) throws IOException {
    InputStream is = new FileInputStream(file);
    this.properties.load(is);
    is.close();
  }

  public String getProperty(String key) {
    if (this.properties.getProperty(key) == null) {
      return null;
    }
    return this.properties.getProperty(key);
  }

}