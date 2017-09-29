package com.hzjs.main;

import com.hzjs.domain.AlarmPojo;
import com.hzjs.domain.AlarmPojos;
import com.utils.DBConfig;
import com.utils.ExcelImport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA. Project: alarmcodeimport Package: com.hzjs.main User: Administrator
 * Date: 2017-08-31 15:40 Author: Haiyangp
 */
public class DataImport {

  Logger logger = Logger.getLogger(DateFormat.class);
  Map<Integer, Integer> testCodeMap = AlarmPojos._instance().getTestCodeMap();

  /**
   * 从数据库中获取已经定义的测试类型的集合
   */
  public void getTestCodeFromDB() {

    Connection connection = null;
    try {
      connection = DBConfig._instance().getConnection();
    } catch (IOException e) {
      logger.error("读取数据库配置文件出错", e);
    } catch (ClassNotFoundException e) {
      logger.error("oracle驱动类未找到", e);
    } catch (SQLException e) {
      logger.error("获取oracle连接失败", e);
    }
    if (connection != null) {
      String sql = "SELECT TESTCODE FROM S_ALARM_CODEPARAM GROUP BY TESTCODE";
      try {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          int testCode = resultSet.getInt(1);
          testCodeMap.put(testCode, testCode);
        }
        //关闭资源
        DBConfig.closeResources(connection, preparedStatement, resultSet);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 将新添加的失败节点定义添加到数据库中
   *
   * @return true:添加成功 false:入库失败/没有需要入库的数据
   */
  public boolean write2DB() {
    boolean flag;
    Connection connection = null;
    try {
      connection = DBConfig._instance().getConnection();
    } catch (IOException e) {
      flag = false;
      logger.error("读取数据库配置文件出错", e);
    } catch (ClassNotFoundException e) {
      flag = false;
      logger.error("oracle驱动类未找到", e);
    } catch (SQLException e) {
      flag = false;
      logger.error("获取oracle连接失败", e);
    }

    if (connection != null) {
      String sql = "INSERT INTO S_ALARM_CODEPARAM(TESTCODE,TESTPARAM,PARAMVALUE,PARAMNAME,ISTDATE,UPTDATE) VALUES(?,?,?,?,sysdate,sysdate)";

      PreparedStatement preparedStatement;
      try {
        preparedStatement = connection.prepareStatement(sql);

        List<AlarmPojo> pojoList = AlarmPojos._instance().getPojos();
        if (pojoList.size() < 1) {
          logger.warn("===========================");
          logger.warn("===========================");
          logger.warn("没有新的测试类型告警节点可以导入");
          logger.warn("===========================");
          logger.warn("===========================");
          logger.warn("");
          return false;
        }
        int count = 0;
        for (AlarmPojo alarmPojo : pojoList) {
          int i = 1;
          preparedStatement.setInt(i++, alarmPojo.getTestCode());
          preparedStatement.setString(i++, alarmPojo.getTestParam());
          preparedStatement.setInt(i++, alarmPojo.getParamValue());
          preparedStatement.setString(i++, alarmPojo.getParamName());

          preparedStatement.addBatch();
          count++;

          //批处理设置 500提交一次
          if (count % 500 == 0) {
            preparedStatement.executeBatch();
          }
        }
        if (count % 500 > 0) {
          preparedStatement.executeBatch();
        }
        preparedStatement.executeBatch();

        //关闭资源
        DBConfig.closeResources(connection, preparedStatement);

        flag = true;
        logger.warn("====================");
        logger.warn("====================");
        logger.warn("测试类型告警节点导入成功");
        logger.warn("====================");
        logger.warn("====================");
        logger.warn("");
      } catch (SQLException e) {
        flag = false;
        logger.error("oracle插入失败", e);
      }

    } else {
      flag = false;
      logger.error("没有数据库连接");
    }
    return flag;
  }

  /**
   * 读取需要入库的失败节点excel 格式:两列 col1:testCode col2:paramString [失败字段=值=定义;]...
   */
  public boolean getInit(String filePath) {

    ExcelImport excelImport = new ExcelImport();
    InputStream inputStream = null;

    try {
      inputStream = new FileInputStream(new File(filePath));
    } catch (FileNotFoundException e) {
      logger.error("读取文件失败, 文件路径: " + filePath, e);
      return false;
    }

    List<List<Object>> bankList = null;
    String fileName = filePath.substring(filePath.lastIndexOf("/"));

    try {
      bankList = excelImport.getBankListByExcel(inputStream, fileName);
    } catch (Exception e) {
      logger.error("解析excel失败, 文件路径: " + filePath, e);
      return false;
    }

    getTestCodeFromDB();

    if (bankList != null) {
      for (List<Object> list : bankList) {
        int testCode = 0;
        String paramString = "";

        if (list.size() == 2) {
          testCode = Integer.parseInt(String.valueOf(list.get(0)));
          paramString = String.valueOf(list.get(1));
        } else if (list.size() == 1) {
          testCode = Integer.parseInt(String.valueOf(list.get(0)));
          logger.warn("  测试类型: " + testCode + " 没有失败节点定义,不做导入处理");
          continue;
        } else {
          logger.error("解析错误,excel列数不正确");
          continue;
        }
        if (testCodeMap.containsKey(testCode)) {
          logger.info("该测试类型已经定义过,不再重复定义:" + testCode);
          continue;
        } else {
          AlarmPojos._instance().formartPojo(testCode, paramString);
        }
      }
      return true;
    } else {
      logger.warn("excel中没有数据");
      return false;
    }
  }
}
