package com.hzjs.domain;

/**
 * Created with IntelliJ IDEA. Project: alarmcodeimport Package: com.hzjs.domain User: Administrator
 * Date: 2017-08-31 11:10 Author: Haiyangp
 */
public class AlarmPojo {

  /**
   * 测试类型编号
   */
  private int testCode;

  /**
   * 测试结果指标参数
   */
  private String testParam;

  /**
   * 指标参数对应的告警值
   */
  private int paramValue;

  /**
   * 参数描述
   */
  private String paramName;


  public AlarmPojo() {
  }

  public AlarmPojo(int testCode) {
    this.testCode = testCode;
  }

  public AlarmPojo(int testCode, String testParam, int paramValue, String paramName) {
    this.testCode = testCode;
    this.testParam = testParam.trim().toUpperCase();
    this.paramValue = paramValue;
    this.paramName = paramName.trim();
  }

  public int getTestCode() {
    return testCode;
  }

  public void setTestCode(int testCode) {
    this.testCode = testCode;
  }

  public String getTestParam() {
    return testParam;
  }

  public void setTestParam(String testParam) {
    this.testParam = testParam;
  }

  public int getParamValue() {
    return paramValue;
  }

  public void setParamValue(int paramValue) {
    this.paramValue = paramValue;
  }

  public String getParamName() {
    return paramName;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  @Override
  public String toString() {
    return "AlarmPojo{" +
        "testCode=" + testCode +
        ", testParam='" + testParam + '\'' +
        ", paramValue=" + paramValue +
        ", paramName='" + paramName + '\'' +
        '}';
  }
}
