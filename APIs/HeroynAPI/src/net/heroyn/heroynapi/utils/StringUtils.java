package net.heroyn.heroynapi.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils
{
  public static List<String> convertStringArrayToList(String[] array)
  {
    List<String> list = new ArrayList<>();
    String[] arrayOfString = array;int j = array.length;
    for (int i = 0; i < j; i++)
    {
      String value = arrayOfString[i];
      if (!value.isEmpty())
      {
        list.add(value);
        list.add("");
      }
    }
    return list;
  }
}
