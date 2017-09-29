package com.hzjs.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA. Project: alarmcodeimport Package: com.hzjs.domain User: Administrator
 * Date: 2017-08-31 12:02 Author: Haiyangp
 */
public class AlarmPojos {

  Logger logger = Logger.getLogger(AlarmPojos.class);

  /**
   * 存放可以入库的alarmPojo类
   */
  private List<AlarmPojo> pojos = new ArrayList<AlarmPojo>(20);

  /**
   * 存放数据库中已经存在的失败节点定义的测试类型编号 用于与excel中的数据进行对比,没有定义过的将会入库
   */
  private Map<Integer, Integer> testCodeMap = new HashMap<Integer, Integer>(20);

  private AlarmPojos() {
  }

  private static AlarmPojos alarmPojos = new AlarmPojos();

  /**
   * 获取AlarmPojos的单实例
   *
   * @return alarmPojos AlarmPojos的单实例
   */
  public static AlarmPojos _instance() {
    return alarmPojos;
  }

  /**
   * 将excel传入的数据解析成AlarmPojo,将失败字段和值以及定义解析出来
   *
   * @param testCode 测试类型
   * @param paramString 格式: [失败字段=值=定义;]...
   */
  public void addPojo(int testCode, String paramString) {
    formartPojo(testCode, paramString);
  }

  public void formartPojo(int testCode, String paramString) {
    String[] params = paramString.split(";");
    if (params.length > 0 && "" != params[0]) {
      for (String param : params) {
        String[] paramList = param.split("=");
        if (paramList.length == 3) {
          int k = 0;
          AlarmPojo alarmPojo = new AlarmPojo(testCode, paramList[k++],
              Integer.parseInt(paramList[k++]), paramList[k++]);
          if (pojos == null) {
            pojos = new ArrayList<AlarmPojo>(20);
          }
          pojos.add(alarmPojo);
        } else {
          logger.error("  测试类型: " + testCode + " 中高级定义,需要手动添加 " + param);
        }
      }
    } else {
      logger.warn("  测试类型: " + testCode + " 没有定义");
    }


  }

  public List<AlarmPojo> getPojos() {
    return pojos;
  }

  public Map<Integer, Integer> getTestCodeMap() {
    return testCodeMap;
  }


}
