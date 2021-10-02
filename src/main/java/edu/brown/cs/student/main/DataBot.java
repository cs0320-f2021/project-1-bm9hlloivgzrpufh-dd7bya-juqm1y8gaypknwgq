package edu.brown.cs.student.main;

import java.lang.reflect.Field;

public class DataBot {

  public Field[] getFields(Object object) {
    Class<?> objectClass = object.getClass();
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

  public String insert(Object object){
    Field[] fields = getFields(object);
    int numFields = fields.length;
    String sqlStatement = "INSERT INTO " + getType(object) + " VALUES (";
    int counter = 0;
    while (counter < (numFields - 1)){
      sqlStatement = sqlStatement + "?, ";
      counter = counter + 1;
    }
    sqlStatement = sqlStatement + "?);";
    return sqlStatement;
  }

  public String delete(Object object){
    String[] fields = convertFields(getFields(object));
    int numFields = fields.length;
    String sqlStatement = "DELETE FROM " + getType(object) + " WHERE ";
    int counter = 0;
    while (counter < (numFields - 1)){
      sqlStatement = sqlStatement + fields[counter] + " = ? AND ";
      counter = counter + 1;
    }
    sqlStatement = sqlStatement + fields[numFields - 1] + " = ?";
    return sqlStatement;
  }

  void select(){

  }
  void update(){

  }
  void rawQuery(){

  }

}