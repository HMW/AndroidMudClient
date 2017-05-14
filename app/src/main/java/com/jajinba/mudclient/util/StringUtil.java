package com.jajinba.mudclient.util;


public class StringUtil {
  private static final String ANSI_PREFIX = "[";
  private static final char ANSI_SUFFIX = 'm';

  public static boolean isEmpty(String str) {
    return str == null || str.length() == 0;
  }

  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }

  public static String decodeAnsiColorEncode(String beforeStr) {
    String afterStr = beforeStr;

    while (afterStr.contains(ANSI_PREFIX)) {
      int index = beforeStr.indexOf(ANSI_PREFIX);
      char[] beforeArray = beforeStr.toCharArray();
      for (int i = index + 1; i < beforeArray.length; i++) {
        if (beforeArray[i] == ANSI_SUFFIX) {
          afterStr = beforeStr.substring(i + 1);
        }
      }
    }

    return afterStr;
  }
}
