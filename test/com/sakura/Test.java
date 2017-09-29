package com.sakura;

import com.hzjs.domain.AlarmPojos;
import com.hzjs.main.DataImport;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Project: alarmcodeimport
 * Package: com.sakura
 * User: Administrator
 * Date: 2017-08-31 13:33
 * Author: Haiyangp
 */
public class Test {

  public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {

    /*int testCoede = 3041;
    String paramString = "";
    AlarmPojos pojos = AlarmPojos._instance();
    pojos.formartPojo(testCoede, paramString);
    List<AlarmPojo> pojoList = pojos.getPojos();
    System.out.println(pojoList.size());

    Connection connection = DBConfig._instance().getConnection();
    if (connection != null) {
      System.out.println("get Connection@!");
    }

    InputStream is = Test.class.getResourceAsStream("E:\\alarmImport.xlsx");
    if (is != null) {
      System.out.println("11111");
    }*/

    new DataImport().getTestCodeFromDB();
    System.out.println(AlarmPojos._instance().getTestCodeMap().size());
  }


}