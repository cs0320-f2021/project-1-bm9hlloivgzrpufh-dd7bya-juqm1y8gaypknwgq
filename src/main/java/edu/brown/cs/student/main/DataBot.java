package edu.brown.cs.student.main;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataBot {
  private static Connection conn;

  public static void loadDb(String path) throws ClassNotFoundException, SQLException {
    ResultSet rs = null;
    try {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:" + path;
      conn = DriverManager.getConnection(urlToDB);
      Statement stat = conn.createStatement();
      stat.executeUpdate("PRAGMA foreign_keys=ON;");
      String[] tablenames = {"users", "rent", "reviews"};
      DatabaseMetaData dmd = conn.getMetaData();
      for (String name : tablenames) {
        rs = dmd.getTables(null, null, name, null);
        if (!rs.next()) {
          System.out.println("database invalid");
        }
      }
      rs.close();
    } catch (ClassNotFoundException | SQLException e) {
      System.out.println("database not found");
    }
  }

  public static Connection getConnection() {
    return conn;
  }

  public Class<?> getClass(String str) throws ClassNotFoundException {
    Class<?> myClass = Class.forName("edu.brown.cs.student.main." + str);
    return myClass;
  }

  public Field[] getFields(Object obj) throws ClassNotFoundException {
    Field[] fieldNames = obj.getClass().getDeclaredFields();
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

  public Object[] getValues(Object object, Field[] fieldNames) throws IllegalAccessException {
    Object[] values = new Object[fieldNames.length];
    int counter = 0;
    for (Field field : fieldNames) {
      field.setAccessible(true);
      Object x = field.get(object); //turn parameter into an object so it can be stored in array
      values[counter] = x;
      counter++;
    }
    return values;
  }


  public String getType(Object object) { //gets name of object type, a.k.a. the table name
    return object.getClass().getSimpleName();
  }

  public void insert(Object obj)
      throws ClassNotFoundException, SQLException, IllegalAccessException {
    Field[] fields = getFields(obj);
    int numFields = fields.length;
    String sqlStatement = "INSERT INTO " + getType(obj) + " VALUES (";
    int counter = 0;
    while (counter < (numFields - 1)) {
      sqlStatement = sqlStatement + "?, ";
      counter = counter + 1;
    }
    sqlStatement = sqlStatement + "?);";

    PreparedStatement prep;
    prep = conn.prepareStatement(sqlStatement);
    int count = 1;
    for (Object entry : getValues(obj, getFields(obj))) {
      if (entry instanceof Integer) {
        prep.setInt(count, (Integer) entry);
        count = count + 1;
      } else if (entry instanceof String) {
        prep.setString(count, (String) entry);
        count = count + 1;
      }
    }
    prep.addBatch();
    prep.executeBatch();
  }

  public void delete(Object obj) throws ClassNotFoundException, SQLException,
      IllegalAccessException {
    String[] fields = convertFields(getFields(obj));
    int numFields = fields.length;
    String sqlStatement = "DELETE FROM " + getType(obj) + " WHERE ";
    int counter = 0;
    while (counter < (numFields - 1)) {
      sqlStatement = sqlStatement + fields[counter] + " = ? AND ";
      counter = counter + 1;
    }
    sqlStatement = sqlStatement + fields[numFields - 1] + " = ?";
    PreparedStatement prep = conn.prepareStatement(sqlStatement);
    int count = 1;
    for (Object entry : getValues(obj, getFields(obj))) {
      if (entry instanceof Integer) {
        prep.setInt(count, (Integer) entry);
        count = count + 1;
      } else if (entry instanceof String) {
        prep.setString(count, (String) entry);
        count = count + 1;
      }
    }
    prep.executeUpdate();
  }

  public List<Object> select(String u_class, List<String> conditions)
      throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException,
      InstantiationException, IllegalAccessException {
    Class<?> clas = getClass(u_class);
    List<Field> classFields = Arrays.asList(clas.getDeclaredFields());
    for (Field field : classFields) {
      field.setAccessible(true);
    }
    List<Object> objList = new ArrayList<>();
    String sqlStatement = "SELECT * FROM " + u_class + " WHERE ";
    for (int i = 0; i < conditions.size(); i += 2) {
      sqlStatement += conditions.get(i) + " = " + conditions.get(i + 1);
    }
    PreparedStatement ps = conn.prepareStatement(sqlStatement);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      Object obj = clas.getConstructor().newInstance();
      for (Field field : classFields) {
        String fieldName = field.getName();
        Object value;
        if (field.getType().isAssignableFrom(int.class)) {
          value = rs.getInt(fieldName);
        } else {
          value = rs.getString(fieldName);
        }
        field.set(obj, value);

      }
      objList.add(obj);
    }
    return objList;
  }

  void update(Object obj, String tableName, String field, String val)
      throws SQLException, ClassNotFoundException, IllegalAccessException {

    String sqlStatement = "UPDATE " + tableName + " SET " + field + " = " + val +
        " WHERE ";
    Map<String, String> mapFields = new HashMap<>();
    Field[] fields = getFields(obj);
    for (Field f: fields){
      f.setAccessible(true);
      mapFields.put(f.getName(), f.get(obj).toString());
    }
    Set<String> keySet = mapFields.keySet();
    List<String> mainList = new ArrayList<String>();
    mainList.addAll(keySet);
    for (int i =0; i<mainList.size(); i++) {
      sqlStatement += mainList.get(i) + " = " + "'" + mapFields.get(mainList.get(i)) + "'";
      if (i < mainList.size() - 1) {
        sqlStatement += " and ";
      }
    }
//    System.out.println(sqlStatement);
    PreparedStatement ps = conn.prepareStatement(sqlStatement);
    ps.executeUpdate();
    }

  void rawQuery(String rawStatement) throws SQLException {
    System.out.println(rawStatement);
    PreparedStatement ps = conn.prepareStatement(rawStatement);
    String[] raw = rawStatement.trim().split(" ");
    int type;
    if (raw[0].equals("SELECT")) {
      ResultSet set = ps.executeQuery();
      ResultSetMetaData rsmd = set.getMetaData();
      int columnCount = rsmd.getColumnCount();
//      if (set == null){
//        System.out.println("no such row");
//      }
      while(set.next()){
        String result = "";
        for (int i = 1; i<columnCount+1; i++){
          type= rsmd.getColumnType(i);
          if (type == Types.INTEGER) {
            int intVal = set.getInt(i);
            result += intVal + " ";
          }
          else {
            String stringVal = set.getString(i);
            result += stringVal + " ";
          }
        }
        System.out.println(result);
      }
    }
    else {
      ps.executeUpdate();
    }
  }

}