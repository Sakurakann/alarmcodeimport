package com.hzjs;

import com.hzjs.main.DataImport;
import com.utils.PropertiesLoad;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA. Project: alarmcodeimport Package: com.hzjs User: Administrator Date:
 * 2017-08-31 17:19 Author: Haiyangp
 */
public class ImportConsole {

  static Logger logger = Logger.getLogger(ImportConsole.class);
  private static final String FILEPATH = "E:/alarmImport.xlsx";

  public static void main(String[] args) {

    DataImport dataImport = new DataImport();
    String filePath = null;
    try {
      filePath = new PropertiesLoad("/filePath.properties").getProperty("filePath");
      if ("".equalsIgnoreCase(filePath) || null == filePath) {
        filePath = FILEPATH;
        logger.error("配置的Excel文件地址有误,将使用默认配置;  请确保以下文件存在" + FILEPATH);
      }
    } catch (IOException e) {
      logger.error("读取文件地址配置文件出错,请检查是否存在 filePath.properties");
    }

    if (dataImport.getInit(filePath)) {
      dataImport.write2DB();
    }
  }
}
