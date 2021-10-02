package edu.brown.cs.student.main;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBot {
  private static Connection conn;
  public static void loadDb(String argument) throws ClassNotFoundException, SQLException {
    ResultSet rs = null;
    try{
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:" + argument;
    Connection conn = DriverManager.getConnection(urlToDB);
    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys=ON;");
    String[] tablenames = {"user", "rent", "review"};
    DatabaseMetaData dmd = conn.getMetaData();
    for (String name : tablenames) {
      rs = dmd.getTables(null, null, name, null);
      if (!rs.next()) {
        System.out.println("database invalid");
      }
    }
    rs.close();}
   catch (ClassNotFoundException | SQLException e) {
    System.out.println("database not found");
  }
}

  public Class<?> getClass(String str) throws ClassNotFoundException {
    Class<?> myClass = Class.forName("edu.brown.cs.student.main."+str);
    return myClass;
  }
  public Field[] getFields(Class<?> objectClass) throws ClassNotFoundException {
    Field[] fieldNames= objectClass.getDeclaredFields();
    return fieldNames;
  }

  public String[] convertFields(Field[] fieldNames) {
    String[] fieldNamesString = new String[fieldNames.length];
    int counter = 0;
    for (Field field : fieldNames) {
      fieldNamesString[counter] = field.getName();
      counter++;
    }
    return fieldNamesString;
  }

  public String[] getValues(Object object, Field[] fieldNames) throws IllegalAccessException {
    String[] values = new String[fieldNames.length];
    int counter = 0;
    for (Field field : fieldNames) {
      values[counter] = field.get(object).toString();
      counter++;
    }
    return values;
  }


  public String getType(Object object) { //gets name of object type, a.k.a. the table name
    return object.getClass().getSimpleName();
  }

  public String insert(String str) throws ClassNotFoundException {
    Field[] fields = getFields(getClass(str));
    int numFields = fields.length;
    String sqlStatement = "INSERT INTO " + str + " VALUES (";
    int counter = 0;
    while (counter < (numFields - 1)){
      sqlStatement = sqlStatement + "?, ";
      counter = counter + 1;
    }
    sqlStatement = sqlStatement + "?);";

    return sqlStatement;
  }

  public String delete(String str) throws ClassNotFoundException {
    String[] fields = convertFields(getFields(getClass(str)));
    int numFields = fields.length;
    String sqlStatement = "DELETE FROM " + str + " WHERE ";
    int counter = 0;
    while (counter < (numFields - 1)){
      sqlStatement = sqlStatement + fields[counter] + " = ? AND ";
      counter = counter + 1;
    }
    sqlStatement = sqlStatement + fields[numFields - 1] + " = ?";
    return sqlStatement;
  }

  public List<?> select(String u_class, List<String> conditions)
      throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
      InstantiationException, IllegalAccessException {
    Class<?> clas = getClass(u_class);
    List<Field> classFields = Arrays.asList(getFields(clas));
    for(Field field: classFields) {
      field.setAccessible(true);
    }
    List<Object> objList = new ArrayList<>();
    String sqlStatement = "SELECT * FROM " + u_class + " WHERE ";
    for(int i=0; i<conditions.size(); i+=2){
      sqlStatement += conditions.get(i) + " = " + conditions.get(i+1);
    }
    PreparedStatement ps = conn.prepareStatement(sqlStatement);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      Object obj = clas.getConstructor().newInstance();
      for (Field field:classFields) {
        String fieldName = field.getName();
          String value = rs.getString(fieldName);
          field.set(obj, field.getType().getConstructor(String.class).newInstance(value));
      }
      objList.add(obj);
    }
    return objList;
  }
  void update(String u_class, String field, String update) {
    
  }
  void rawQuery(){

  }

}