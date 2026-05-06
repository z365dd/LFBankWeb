/**
 * 功能说明: 字符串工具类, 继承org.apache.commons.lang3.StringUtils类  <br>
 * 系统版本: v1.0 <br>
 * 开发人员: <br>
 * 开发时间: <br>
 * 审核人员:   <br>
 * 相关文档:   <br>
 * 修改记录:   <br>
 * 修改日期      修改人员                     修改说明  <br>
 * ========	   ======  ============================================  <br>
 *
 */
package com.adtec.framework.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Date;
import java.text.BreakIterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.sys.common.utils.Encodes;
import com.google.common.collect.Lists;

public class StringUtil extends org.apache.commons.lang3.StringUtils{
	private static final Logger log = LoggerFactory.getLogger(StringUtil.class);
	 // Constants used by escapeHTMLTags
    private static final char[] QUOTE_ENCODE = "&quot;".toCharArray();
    private static final char[] AMP_ENCODE = "&amp;".toCharArray();
    private static final char[] LT_ENCODE = "&lt;".toCharArray();
    private static final char[] GT_ENCODE = "&gt;".toCharArray();
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final char SEPARATOR = '_';
    private static final String CHARSET_NAME = "UTF-8";
   

   /**
     * 此方法将给出的字符串source使用delim划分为单词数组。
     * @param source 需要进行划分的原字符串
     * @param delim 单词的分隔字符串
     * @return 划分以后的数组，如果source为null的时候返回以source为唯一元素的数组，
     *         如果delim为null则使用逗号作为分隔字符串。
     * @since  0.1
     */
    public static String[] split(String source, String delim) {
      String[] wordLists;
      if (source == null) {
        wordLists = new String[1];
        wordLists[0] = source;
        return wordLists;
      }
      if (delim == null) {
        delim = ",";
      }
      StringTokenizer st = new StringTokenizer(source, delim);
      int total = st.countTokens();
      wordLists = new String[total];
      for (int i = 0; i < total; i++) {
        wordLists[i] = st.nextToken();
      }
      return wordLists;
    }

    /**
     * 此方法将给出的字符串source使用delim划分为单词数组。
     * @param source 需要进行划分的原字符串
     * @param delim 单词的分隔字符
     * @return 划分以后的数组，如果source为null的时候返回以source为唯一元素的数组。
     * @since  0.2
     */
    public static String[] split(String source, char delim) {
      return split(source, String.valueOf(delim));
    }

    /**
     * 此方法将给出的字符串source使用逗号划分为单词数组。
     * @param source 需要进行划分的原字符串
     * @return 划分以后的数组，如果source为null的时候返回以source为唯一元素的数组。
     */
    public static String[] split(String source) {
      return split(source, ",");
    }

    /**
     * 循环打印字符串数组。
     * 字符串数组的各元素间以指定字符分隔，如果字符串中已经包含指定字符则在字符串的两端加上双引号。
     * @param strings 字符串数组
     * @param delim 分隔符
     * @param out 打印到的输出流
     */
    public static void printStrings(String[] strings, String delim,
                                    OutputStream out) {
      try {
        if (strings != null) {
          int length = strings.length - 1;
          for (int i = 0; i < length; i++) {
            if (strings[i] != null) {
              if (strings[i].indexOf(delim) > -1) {
                out.write( ("\"" + strings[i] + "\"" + delim).getBytes());
              }
              else {
                out.write( (strings[i] + delim).getBytes());
              }
            }
            else {
              out.write("null".getBytes());
            }
          }
          if (strings[length] != null) {
            if (strings[length].indexOf(delim) > -1) {
              out.write( ("\"" + strings[length] + "\"").getBytes());
            }
            else {
              out.write(strings[length].getBytes());
            }
          }
          else {
            out.write("null".getBytes());
          }
        }
        else {
          out.write("null".getBytes());
        }
        out.write(LINE_SEPARATOR.getBytes());
      }
      catch (IOException e) {
          System.out.println("操作失败");

      }
    }

    /**
     * 循环打印字符串数组到标准输出。
     * 字符串数组的各元素间以指定字符分隔，如果字符串中已经包含指定字符则在字符串的两端加上双引号。
     * @param strings 字符串数组
     * @param delim 分隔符
     * @since  0.4
     */
    public static void printStrings(String[] strings, String delim) {
      printStrings(strings, delim, System.out);
    }

    /**
     * 循环打印字符串数组。
     * 字符串数组的各元素间以逗号分隔，如果字符串中已经包含逗号则在字符串的两端加上双引号。
     * @param strings 字符串数组
     * @param out 打印到的输出流
     * @since  0.2
     */
    public static void printStrings(String[] strings, OutputStream out) {
      printStrings(strings, ",", out);
    }

    /**
     * 循环打印字符串数组到系统标准输出流System.out。
     * 字符串数组的各元素间以逗号分隔，如果字符串中已经包含逗号则在字符串的两端加上双引号。
     * @param strings 字符串数组
     * @since  0.2
     */
    public static void printStrings(String[] strings) {
      printStrings(strings, ",", System.out);
    }

    /**
     * 将字符串中的变量使用values数组中的内容进行替换。
     * 替换的过程是不进行嵌套的，即如果替换的内容中包含变量表达式时不会替换。
     * @param prefix 变量前缀字符串
     * @param source 带参数的原字符串
     * @param values 替换用的字符串数组
     * @return 替换后的字符串。
     *         如果前缀为null则使用“%”作为前缀；
     *         如果source或者values为null或者values的长度为0则返回source；
     *         如果values的长度大于参数的个数，多余的值将被忽略；
     *         如果values的长度小于参数的个数，则后面的所有参数都使用最后一个值进行替换。
     * @since  0.2
     */
    public static String getReplaceString(String prefix, String source,
                                          String[] values) {
      String result = source;
      if (source == null || values == null || values.length < 1) {
        return source;
      }
      if (prefix == null) {
        prefix = "%";
      }

      for (int i = 0; i < values.length; i++) {
        String argument = prefix + Integer.toString(i + 1);
        int index = result.indexOf(argument);
        if (index != -1) {
          String temp = result.substring(0, index);
          if (i < values.length) {
            temp += values[i];
          }
          else {
            temp += values[values.length - 1];
          }
          temp += result.substring(index + 2);
          result = temp;
        }
      }
      return result;
    }

    /**
     * 将字符串中的变量（以“%”为前导后接数字）使用values数组中的内容进行替换。
     * 替换的过程是不进行嵌套的，即如果替换的内容中包含变量表达式时不会替换。
     * @param source 带参数的原字符串
     * @param values 替换用的字符串数组
     * @return 替换后的字符串
     * @since  0.1
     */
    public static String getReplaceString(String source, String[] values) {
      return getReplaceString("%", source, values);
    }

    /**
     * 字符串数组中是否包含指定的字符串。
     * @param strings 字符串数组
     * @param string 字符串
     * @param caseSensitive 是否大小写敏感
     * @return 包含时返回true，否则返回false
     * @since  0.4
     */
    public static boolean contains(String[] strings, String string,
                                   boolean caseSensitive) {
      for (int i = 0; i < strings.length; i++) {
        if (caseSensitive == true) {
          if (strings[i].equals(string)) {
            return true;
          }
        }
        else {
          if (strings[i].equalsIgnoreCase(string)) {
            return true;
          }
        }
      }
      return false;
    }

    /**
     * 字符串数组中是否包含指定的字符串。大小写敏感。
     * @param strings 字符串数组
     * @param string 字符串
     * @return 包含时返回true，否则返回false
     * @since  0.4
     */
    public static boolean contains(String[] strings, String string) {
      return contains(strings, string, true);
    }

    /**
     * 不区分大小写判定字符串数组中是否包含指定的字符串。
     * @param strings 字符串数组
     * @param string 字符串
     * @return 包含时返回true，否则返回false
     * @since  0.4
     */
    public static boolean containsIgnoreCase(String[] strings, String string) {
      return contains(strings, string, false);
    }

    /**
     * 将字符串数组使用指定的分隔符合并成一个字符串。
     * @param array 字符串数组
     * @param delim 分隔符，为null的时候使用""作为分隔符（即没有分隔符）
     * @return 合并后的字符串
     * @since  0.4
     */
    public static String combineStringArray(String[] array, String delim) {
      int length = array.length - 1;
      if (delim == null) {
        delim = "";
      }
      StringBuffer result = new StringBuffer(length * 8);
      for (int i = 0; i < length; i++) {
        result.append(array[i]);
        result.append(delim);
      }
      result.append(array[length]);
      return result.toString();
    }

    /**
     * 在字符串中用新的字符串替换旧的字符串
     * @param line String  整条字符串
     * @param oldString String  旧字符串
     * @param newString String  新的字符串
     * @return String  替换后的整条字符串
     */
    public static final String replace( String line, String oldString, String newString )
    {
        if (line == null) {
            return null;
        }
        int i=0;
        if ( ( i=line.indexOf( oldString, i ) ) >= 0 ) {
            char [] line2 = line.toCharArray();
            char [] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while( ( i=line.indexOf( oldString, i ) ) > 0 ) {
                buf.append(line2, j, i-j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            return buf.toString();
        }
        return line;
    }

    /**
     * 字符串替换，忽略大小写
     *
     * @param line 要搜索的字符串
     * @param oldString 源字符串
     * @param newString 新字符串
     *
     * @return a String 替换后的字符串
     */
    public static final String replaceIgnoreCase(String line, String oldString,
            String newString)
    {
        if (line == null) {
            return null;
        }
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i=0;
        if ((i=lcLine.indexOf(lcOldString, i)) >= 0) {
            char [] line2 = line.toCharArray();
            char [] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i=lcLine.indexOf(lcOldString, i)) > 0) {
                buf.append(line2, j, i-j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            return buf.toString();
        }
        return line;
    }
    /******************************************************************************)
      * 从一个字符串中根据分隔符截取第iIndex项的值
      * @param sSource 原字符串
      * @param iIndex 第几项，从1开始
      * @param chSplit 分割符，单个字符
      * @return String 截取的字符串
      * @throws HsException
      */
     public static String GetItemfromStr(String sSource, int iIndex, char chSplit) {
       try {
         String Result = "";
         int iI = 1;
         int iJ = 0;
         while (iJ <= sSource.length() - 1) {
           if (sSource.charAt(iJ) == chSplit) iI++;
           if (iI > iIndex)break;
           if ( (iI == iIndex) && (sSource.charAt(iJ) != chSplit))
             Result = Result + sSource.charAt(iJ);
           iJ++;
         }
         return Result;
       }
       catch (Exception e) {
    	   e.getStackTrace();
        return null;
       }
     }

     /******************************************************************************)
      * 将给定字符串按左边或右边填充，直到长度达到预定值
      * @param sSource 原字符串
      * @param intMaxLength 最大长度
      * @param cFill 填充字符
      * @param blRL 左右 true：左 false: 右
      * @return String 结果字符串
      */
     public static String FillStrByChar(String sSource, int intMaxLength,
                                        char cFill, boolean blRL) {
       if (blRL) {
         while (sSource.length() < intMaxLength) {
           sSource = sSource + cFill;
         }
         return sSource;
       }
       else {
         while (sSource.length() < intMaxLength) {
           sSource = cFill + sSource;
         }
         return sSource;
       } //if
     }

     /******************************************************************************)
      * 从一个字符串获得包含子字符串的个数
      * @param sSource 原字符串
      * @param sItem 子字符串
      * @return int 个数，异常返回-1
      */
     public static int GetItemCountsInStr(String sSource, String sItem) {
       try {
         String s = sSource;
         int i = 0, index = 0;
         while (index >= 0) {
           index = s.indexOf(sItem);
           if (index >= 0) {
             i++;
             s = StringRemove(s,index,sItem.length());
           }
         }
         return i;
       }
       catch (Exception E) {
         return -1;
       }
     }

     /******************************************************************************)
      * 从一个字符串中删除一个子串
      * @param sSource 原字符串
      * @param index 起始删除的位置
      * @param RemoveLen 删除的长度
      * @return String 结果字符串，异常返回""
      * @throws HsException
      */
     public static String StringRemove(String sSource,int index,int RemoveLen)
     {
       try
       {
         String sTmp = sSource;
         String s = sTmp.substring(0, index) +
             sTmp.substring(index + RemoveLen, sTmp.length());
         return s;
       }
       catch(Exception E)
       {
    	   E.getStackTrace();
         return null;
       }
  }
    /**
     * Replaces all instances of oldString with newString in line with the
     * added feature that matches of newString in oldString ignore case.
     * The count paramater is set to the number of replaces performed.
     *
     * @param line the String to search to perform replacements on
     * @param oldString the String that should be replaced by newString
     * @param newString the String that will replace all instances of oldString
     * @param count a value that will be updated with the number of replaces
     *      performed.
     *
     * @return a String will all instances of oldString replaced by newString
     */
    public static final String replaceIgnoreCase(String line, String oldString,
            String newString, int [] count)
    {
        if (line == null) {
            return null;
        }
        String lcLine = line.toLowerCase();
        String lcOldString = oldString.toLowerCase();
        int i=0;
        if ((i=lcLine.indexOf(lcOldString, i)) >= 0) {
            int counter = 0;
            char [] line2 = line.toCharArray();
            char [] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i=lcLine.indexOf(lcOldString, i)) > 0) {
                counter++;
                buf.append(line2, j, i-j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        }
        return line;
    }

   /**
    * Replaces all instances of oldString with newString in line.
    * The count Integer is updated with number of replaces.
    *
    * @param line the String to search to perform replacements on
    * @param oldString the String that should be replaced by newString
    * @param newString the String that will replace all instances of oldString
    *
    * @return a String will all instances of oldString replaced by newString
    */
    public static final String replace(String line, String oldString,
            String newString, int[] count)
    {
        if (line == null) {
            return null;
        }
        int i=0;
        if ((i=line.indexOf(oldString, i)) >= 0) {
            int counter = 0;
            counter++;
            char [] line2 = line.toCharArray();
            char [] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i=line.indexOf(oldString, i)) > 0) {
                counter++;
                buf.append(line2, j, i-j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length-j);
            count[0] = counter;
            return buf.toString();
        }
        return line;
    }

    /**
     * This method takes a string which may contain HTML tags (ie, &lt;b&gt;,
     * &lt;table&gt;, etc) and converts the '&lt'' and '&gt;' characters to
     * their HTML escape sequences.
     *
     * @param in the text to be converted.
     * @return the input string with the characters '&lt;' and '&gt;' replaced
     *  with their HTML escape sequences.
     */
    public static final String escapeHTMLTags(String in) {
        if (in == null) {
            return null;
        }
        char ch;
        int i=0;
        int last=0;
        char[] input = in.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int)(len*1.3));
        for (; i < len; i++) {
            ch = input[i];
            if (ch > '>') {
                continue;
            } else if (ch == '<') {
                if (i > last) {
                    out.append(input, last, i - last);
                }
                last = i + 1;
                out.append(LT_ENCODE);
            } else if (ch == '>') {
                if (i > last) {
                    out.append(input, last, i - last);
                }
                last = i + 1;
                out.append(GT_ENCODE);
            }
        }
        if (last == 0) {
            return in;
        }
        if (i > last) {
            out.append(input, last, i - last);
        }
        return out.toString();
    }

    /**
     * Used by the hash method.
     */
//    private static MessageDigest digest = null;
//
//    public synchronized static final String hash(String data) {
//        if (digest == null) {
//            try {
//                digest = MessageDigest.getInstance("MD5");
//            }
//            catch (NoSuchAlgorithmException nsae) {
//                System.err.println("转换 MD5 失败. ") ;
//                nsae.printStackTrace();
//            }
//        }
//        // Now, compute hash.
//        digest.update(data.getBytes());
//        return encodeHex(digest.digest());
//    }

    /**
     * Turns an array of bytes into a String representing each byte as an
     * unsigned hex number.
     * <p>
     * Method by Santeri Paavolainen, Helsinki Finland 1996<br>
     * (c) Santeri Paavolainen, Helsinki Finland 1996<br>
     * Distributed under LGPL.
     *
     * @param bytes an array of bytes to convert to a hex-string
     * @return generated hex string
     */
    public static final String encodeHex(byte[] bytes) {
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        int i;

        for (i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }

    /**
     * Turns a hex encoded string into a byte array. It is specifically meant
     * to "reverse" the toHex(byte[]) method.
     *
     * @param hex a hex encoded String to transform into a byte array.
     * @return a byte array representing the hex String[
     */
    public static final byte[] decodeHex(String hex) {
        char [] chars = hex.toCharArray();
        byte[] bytes = new byte[chars.length/2];
        int byteCount = 0;
        for (int i=0; i<chars.length; i+=2) {
            byte newByte = 0x00;
            newByte |= hexCharToByte(chars[i]);
            newByte <<= 4;
            newByte |= hexCharToByte(chars[i+1]);
            bytes[byteCount] = newByte;
            byteCount++;
        }
        return bytes;
    }

    /**
     * Returns the the byte value of a hexadecmical char (0-f). It's assumed
     * that the hexidecimal chars are lower case as appropriate.
     *
     * @param ch a hexedicmal character (0-f)
     * @return the byte value of the character (0x00-0x0F)
     */
    private static final byte hexCharToByte(char ch) {
        switch(ch) {
            case '0': return 0x00;
            case '1': return 0x01;
            case '2': return 0x02;
            case '3': return 0x03;
            case '4': return 0x04;
            case '5': return 0x05;
            case '6': return 0x06;
            case '7': return 0x07;
            case '8': return 0x08;
            case '9': return 0x09;
            case 'a': return 0x0A;
            case 'b': return 0x0B;
            case 'c': return 0x0C;
            case 'd': return 0x0D;
            case 'e': return 0x0E;
            case 'f': return 0x0F;
        }
        return 0x00;
    }

    //*********************************************************************
    //* Base64 - a simple base64 encoder and decoder.
    //*
    //*     Copyright (c) 1999, Bob Withers -
    //*
    //* This code may be freely used for any purpose, either personal
    //* or commercial, provided the authors copyright notice remains
    //* intact.
    //*********************************************************************

    /**
     * Encodes a String as a base64 String.
     *
     * @param data a String to encode.
     * @return a base64 encoded String.
     */
    public static String encodeBase64(String data) {
        return encodeBase64(data.getBytes());
    }

    /**
     * Encodes a byte array into a base64 String.
     *
     * @param data a byte array to encode.
     * @return a base64 encode String.
     */
    public static String encodeBase64(byte[] data) {
        int c;
        int len = data.length;
        StringBuffer ret = new StringBuffer(((len / 3) + 1) * 4);
        for (int i = 0; i < len; ++i) {
            c = (data[i] >> 2) & 0x3f;
            ret.append(cvt.charAt(c));
            c = (data[i] << 4) & 0x3f;
            if (++i < len)
                c |= (data[i] >> 4) & 0x0f;

            ret.append(cvt.charAt(c));
            if (i < len) {
                c = (data[i] << 2) & 0x3f;
                if (++i < len)
                    c |= (data[i] >> 6) & 0x03;

                ret.append(cvt.charAt(c));
            }
            else {
                ++i;
                ret.append((char) fillchar);
            }

            if (i < len) {
                c = data[i] & 0x3f;
                ret.append(cvt.charAt(c));
            }
            else {
                ret.append((char) fillchar);
            }
        }
        return ret.toString();
    }

    /**
     * Decodes a base64 String.
     *
     * @param data a base64 encoded String to decode.
     * @return the decoded String.
     */
    public static String decodeBase64(String data) {
        return decodeBase64(data.getBytes());
    }

    /**
     * Decodes a base64 aray of bytes.
     *
     * @param data a base64 encode byte array to decode.
     * @return the decoded String.
     */
    public static String decodeBase64(byte[] data) {
        int c, c1;
        int len = data.length;
        StringBuffer ret = new StringBuffer((len * 3) / 4);
        for (int i = 0; i < len; ++i) {
            c = cvt.indexOf(data[i]);
            ++i;
            c1 = cvt.indexOf(data[i]);
            c = ((c << 2) | ((c1 >> 4) & 0x3));
            ret.append((char) c);
            if (++i < len) {
                c = data[i];
                if (fillchar == c)
                    break;

                c = cvt.indexOf((char) c);
                c1 = ((c1 << 4) & 0xf0) | ((c >> 2) & 0xf);
                ret.append((char) c1);
            }

            if (++i < len) {
                c1 = data[i];
                if (fillchar == c1)
                    break;

                c1 = cvt.indexOf((char) c1);
                c = ((c << 6) & 0xc0) | c1;
                ret.append((char) c);
            }
        }
        return ret.toString();
    }

    private static final int fillchar = '=';
    private static final String cvt = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "abcdefghijklmnopqrstuvwxyz"
                                    + "0123456789+/";

    /**
     * Converts a line of text into an array of lower case words using a
     * BreakIterator.wordInstance(). <p>
     *
     * This method is under the Jive Open Source Software License and was
     * written by Mark Imbriaco.
     *
     * @param text a String of text to convert into an array of words
     * @return text broken up into an array of words.
     */
    public static final String [] toLowerCaseWordArray(String text) {
        if (text == null || text.length() == 0) {
                return new String[0];
        }

        ArrayList wordList = new ArrayList();
        BreakIterator boundary = BreakIterator.getWordInstance();
        boundary.setText(text);
        int start = 0;

        for (int end = boundary.next(); end != BreakIterator.DONE;
                start = end, end = boundary.next())
        {
            String tmp = text.substring(start,end).trim();
            // Remove characters that are not needed.
            tmp = replace(tmp, "+", "");
            tmp = replace(tmp, "/", "");
            tmp = replace(tmp, "\\", "");
            tmp = replace(tmp, "#", "");
            tmp = replace(tmp, "*", "");
            tmp = replace(tmp, ")", "");
            tmp = replace(tmp, "(", "");
            tmp = replace(tmp, "&", "");
            if (tmp.length() > 0) {
                wordList.add(tmp);
            }
        }
        return (String[]) wordList.toArray(new String[wordList.size()]);
    }

    /**
     * Pseudo-random number generator object for use with randomString().
     * The Random class is not considered to be cryptographically secure, so
     * only use these random Strings for low to medium security applications.
     */
    private static SecureRandom randGen = new SecureRandom();

    /**
     * Array of numbers and letters of mixed case. Numbers appear in the list
     * twice so that there is a more equal chance that a number will be picked.
     * We can use the array to get a random number or letter by picking a random
     * array index.
     */
    private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" +
                    "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

    /**
     * Returns a random String of numbers and letters (lower and upper case)
     * of the specified length. The method uses the Random class that is
     * built-in to Java which is suitable for low to medium grade security uses.
     * This means that the output is only pseudo random, i.e., each number is
     * mathematically generated so is not truly random.<p>
     *
     * The specified length must be at least one. If not, the method will return
     * null.
     *
     * @param length the desired length of the random String to return.
     * @return a random String of numbers and letters of the specified length.
     */
    public static final String randomString(int length) {
        if (length < 1) {
            return null;
        }
        // Create a char buffer to put random letters and numbers in.
        char [] randBuffer = new char[length];
        for (int i=0; i<randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }

   /**
    * Intelligently chops a String at a word boundary (whitespace) that occurs
    * at the specified index in the argument or before. However, if there is a
    * newline character before <code>length</code>, the String will be chopped
    * there. If no newline or whitespace is found in <code>string</code> up to
    * the index <code>length</code>, the String will chopped at <code>length</code>.
    * <p>
    * For example, chopAtWord("This is a nice String", 10) will return
    * "This is a" which is the first word boundary less than or equal to 10
    * characters into the original String.
    *
    * @param string the String to chop.
    * @param length the index in <code>string</code> to start looking for a
    *       whitespace boundary at.
    * @return a substring of <code>string</code> whose length is less than or
    *       equal to <code>length</code>, and that is chopped at whitespace.
    */
    public static final String chopAtWord(String string, int length) {
        if (string == null) {
            return string;
        }

        char [] charArray = string.toCharArray();
        int sLength = string.length();
        if (length < sLength) {
            sLength = length;
        }

        // First check if there is a newline character before length; if so,
        // chop word there.
        for (int i=0; i<sLength-1; i++) {
            // Windows
            if (charArray[i] == '\r' && charArray[i+1] == '\n') {
                return string.substring(0, i+1);
            }
            // Unix
            else if (charArray[i] == '\n') {
                return string.substring(0, i);
            }
        }
        // Also check boundary case of Unix newline
        if (charArray[sLength-1] == '\n') {
            return string.substring(0, sLength-1);
        }

        // Done checking for newline, now see if the total string is less than
        // the specified chop point.
        if (string.length() < length) {
            return string;
        }

        // No newline, so chop at the first whitespace.
        for (int i = length-1; i > 0; i--) {
            if (charArray[i] == ' ') {
                return string.substring(0, i).trim();
            }
        }

        // Did not find word boundary so return original String chopped at
        // specified length.
        return string.substring(0, length);
    }

    // Create a regular expression engine that is used by the highlightWords
    // method below.
   // private static Perl5Util perl5Util = new Perl5Util();

    /**
     * Highlights words in a string. Words matching ignores case. The actual
     * higlighting method is specified with the start and end higlight tags.
     * Those might be beginning and ending HTML bold tags, or anything else.<p>
     *
     * This method is under the Jive Open Source Software License and was
     * written by Mark Imbriaco.
     *
     * @param string the String to highlight words in.
     * @param words an array of words that should be highlighted in the string.
     * @param startHighlight the tag that should be inserted to start highlighting.
     * @param endHighlight the tag that should be inserted to end highlighting.
     * @return a new String with the specified words highlighted.
     */
   /*
    public static final String highlightWords(String string, String[] words,
        String startHighlight, String endHighlight)
    {
        if (string == null || words == null ||
                startHighlight == null || endHighlight == null)
        {
            return null;
        }

        StringBuffer regexp = new StringBuffer();

        // Iterate through each word and generate a word list for the regexp.
        for (int x=0; x<words.length; x++)
        {
            // Excape "|" and "/"  to keep us out of trouble in our regexp.
            words[x] = perl5Util.substitute("s#([\\|\\/\\.])#\\\\$1#g", words[x]);
            if (regexp.length() > 0)
            {
                regexp.append("|");
            }
            regexp.append(words[x]);
        }

        // Escape the regular expression delimiter ("/").
        startHighlight = perl5Util.substitute("s#\\/#\\\\/#g", startHighlight);
        endHighlight = perl5Util.substitute("s#\\/#\\\\/#g", endHighlight);

        // Build the regular expression. insert() the first part.
        regexp.insert(0, "s/\\b(");
        // The word list is here already, so just append the rest.
        regexp.append(")\\b/");
        regexp.append(startHighlight);
        regexp.append("$1");
        regexp.append(endHighlight);
        regexp.append("/igm");

        // Do the actual substitution via a simple regular expression.
        return perl5Util.substitute(regexp.toString(), string);
    }

    /**
     * Escapes all necessary characters in the String so that it can be used
     * in an XML doc.
     *
     * @param string the string to escape.
     * @return the string with appropriate characters escaped.
     */
    public static final String escapeForXML(String string) {
        if (string == null) {
            return null;
        }
        char ch;
        int i=0;
        int last=0;
        char[] input = string.toCharArray();
        int len = input.length;
        StringBuffer out = new StringBuffer((int)(len*1.3));
        for (; i < len; i++) {
            ch = input[i];
            if (ch > '>') {
                continue;
            } else if (ch == '<') {
                if (i > last) {
                    out.append(input, last, i - last);
                }
                last = i + 1;
                out.append(LT_ENCODE);
            } else if (ch == '&') {
                if (i > last) {
                    out.append(input, last, i - last);
                }
                last = i + 1;
                out.append(AMP_ENCODE);
            } else if (ch == '"') {
                if (i > last) {
                    out.append(input, last, i - last);
                }
                last = i + 1;
                out.append(QUOTE_ENCODE);
            }
        }
        if (last == 0) {
            return string;
        }
        if (i > last) {
            out.append(input, last, i - last);
        }
        return out.toString();
    }

    /**
     * Unescapes the String by converting XML escape sequences back into normal
     * characters.
     *
     * @param string the string to unescape.
     * @return the string with appropriate characters unescaped.
     */
    public static final String unescapeFromXML(String string) {
        string = replace(string, "&lt;", "<");
        string = replace(string, "&gt;", ">");
        string = replace(string, "&quot;", "\"");
        return replace(string, "&amp;", "&");
    }

    private static final char[] zeroArray = "0000000000000000".toCharArray();

    /**
     * Pads the supplied String with 0's to the specified length and returns
     * the result as a new String. For example, if the initial String is
     * "9999" and the desired length is 8, the result would be "00009999".
     * This type of padding is useful for creating numerical values that need
     * to be stored and sorted as character data. Note: the current
     * implementation of this method allows for a maximum <tt>length</tt> of
     * 16.
     *
     * @param string the original String to pad.
     * @param length the desired length of the new padded String.
     * @return a new String padded with the required number of 0's.
     */
     public static final String zeroPadString(String string, int length) {
        if (string == null || string.length() > length) {
            return string;
        }
        StringBuffer buf = new StringBuffer(length);
        buf.append(zeroArray, 0, length-string.length()).append(string);
        return buf.toString();
     }

     public static String nbspPadString(String string,int length) {
       if (string == null || string.toCharArray().length>length) {
            return string;
       }
       int len=string.length();
       StringBuffer buf = new StringBuffer(string);
       for (int i=0;i<length-len;i++) {
         buf.append("&nbsp;");
       }
       return buf.toString();
     }

     public static String nbspPadStringmid(String string,int length) {
       StringBuffer buf = new StringBuffer();
       if (string == null || string.equals("")) {
         for (int i=0;i<length;i++) {
           buf.append("&nbsp;");
         }
         return buf.toString();
       }
       int len=string.getBytes().length;
       if (len>=length) {
         return string;
       } else {
         int leftlen=(length-len)/2;
         int rightlen=length-len-leftlen;
         for (int i=0;i<leftlen;i++) {
           buf.append("&nbsp;");
         }
         buf.append(string);
         for (int i=0;i<rightlen;i++) {
           buf.append("&nbsp;");
         }
         return buf.toString();
       }
     }

     /**
      * Formats a Date as a fifteen character long String made up of the Date's
      * padded millisecond value.
      *
      * @return a Date encoded as a String.
      */
     public static final String dateToMillis(Date date) {
        return zeroPadString(Long.toString(date.getTime()), 15);
     }
     /**
      * remove the \r or \n
      */
     public static String removeEnterChar(String sour)
     {

        for(int i=0;sour!=null&&i<sour.length();i++)
        {
            char c=sour.charAt(i);

          if(c=='\r'||c=='\n')
          {
              sour=sour.replace(c,' ');
          }


        }
        return sour.trim();
     }
     
//=====================================================

 	/**
 	 * Tokenize the given String into a String array via a StringTokenizer.
 	 * Trims tokens and omits empty tokens.
 	 * <p>The given delimiters string is supposed to consist of any number of
 	 * delimiter characters. Each of those characters can be used to separate
 	 * tokens. A delimiter is always a single character; for multi-character
 	 * delimiters, consider using <code>delimitedListToStringArray</code>
 	 * @param str the String to tokenize
 	 * @param delimiters the delimiter characters, assembled as String
 	 * (each of those characters is individually considered as delimiter).
 	 * @return an array of the tokens
 	 * @see java.util.StringTokenizer
 	 * @see java.lang.String#trim()
 	 * @see #delimitedListToStringArray
 	 */
 	public static String[] tokenizeToStringArray(String str, String delimiters) {
 		return tokenizeToStringArray(str, delimiters, true, true);
 	}

 	/**
 	 * Tokenize the given String into a String array via a StringTokenizer.
 	 * <p>The given delimiters string is supposed to consist of any number of
 	 * delimiter characters. Each of those characters can be used to separate
 	 * tokens. A delimiter is always a single character; for multi-character
 	 * delimiters, consider using <code>delimitedListToStringArray</code>
 	 * @param str the String to tokenize
 	 * @param delimiters the delimiter characters, assembled as String
 	 * (each of those characters is individually considered as delimiter)
 	 * @param trimTokens trim the tokens via String's <code>trim</code>
 	 * @param ignoreEmptyTokens omit empty tokens from the result array
 	 * (only applies to tokens that are empty after trimming; StringTokenizer
 	 * will not consider subsequent delimiters as token in the first place).
 	 * @return an array of the tokens (<code>null</code> if the input String
 	 * was <code>null</code>)
 	 * @see java.util.StringTokenizer
 	 * @see java.lang.String#trim()
 	 * @see #delimitedListToStringArray
 	 */
 	public static String[] tokenizeToStringArray(
 			String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

 		if (str == null) {
 			return null;
 		}
 		StringTokenizer st = new StringTokenizer(str, delimiters);
 		List tokens = new ArrayList();
 		while (st.hasMoreTokens()) {
 			String token = st.nextToken();
 			if (trimTokens) {
 				token = token.trim();
 			}
 			if (!ignoreEmptyTokens || token.length() > 0) {
 				tokens.add(token);
 			}
 		}
 		return toStringArray(tokens);
 	}

	/**
	 * Copy the given Collection into a String array.
	 * The Collection must contain String elements only.
	 * @param collection the Collection to copy
	 * @return the String array (<code>null</code> if the passed-in
	 * Collection was <code>null</code>)
	 */
	public static String[] toStringArray(Collection collection) {
		if (collection == null) {
			return null;
		}
		return (String[]) collection.toArray(new String[collection.size()]);
	}
     

	/**
	 * Trim leading and trailing whitespace from the given String.
	 * @param str the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
			buf.deleteCharAt(0);
		}
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * Trim <i>all</i> whitespace from the given String:
	 * leading, trailing, and inbetween characters.
	 * @param str the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimAllWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		int index = 0;
		while (buf.length() > index) {
			if (Character.isWhitespace(buf.charAt(index))) {
				buf.deleteCharAt(index);
			}
			else {
				index++;
			}
		}
		return buf.toString();
	}

	/**
	 * Trim leading whitespace from the given String.
	 * @param str the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimLeadingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
			buf.deleteCharAt(0);
		}
		return buf.toString();
	}

	/**
	 * Trim trailing whitespace from the given String.
	 * @param str the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimTrailingWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * Trim all occurences of the supplied leading character from the given String.
	 * @param str the String to check
	 * @param leadingCharacter the leading character to be trimmed
	 * @return the trimmed String
	 */
	public static String trimLeadingCharacter(String str, char leadingCharacter) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && buf.charAt(0) == leadingCharacter) {
			buf.deleteCharAt(0);
		}
		return buf.toString();
	}

	/**
	 * Trim all occurences of the supplied trailing character from the given String.
	 * @param str the String to check
	 * @param trailingCharacter the trailing character to be trimmed
	 * @return the trimmed String
	 */
	public static String trimTrailingCharacter(String str, char trailingCharacter) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && buf.charAt(buf.length() - 1) == trailingCharacter) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * Check that the given CharSequence is neither <code>null</code> nor of length 0.
	 * Note: Will return <code>true</code> for a CharSequence that purely consists of whitespace.
	 * <p><pre>
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * @param str the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not null and has length
	 * @see #hasText(String)
	 */
	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}
	
    // Empty checks
    //-----------------------------------------------------------------------
    /**
     * <p>Checks if a String is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the String.
     * That functionality is available in isBlank().</p>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
    
    /*20200321 add by chenyl for 迁移原来sys.common.utils.StringUtils的方法*/
    /**
     * 转换为字节数组
     * @param str
     * @return
     */
    public static byte[] getBytes(String str){
    	if (str != null){
    		try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
    	}else{
    		return null;
    	}
    }
    
    /**
     * 转换为字节数组
     * @param bytes	需要转换的字节数组
     * @return
     */
    public static String toString(byte[] bytes){
    	try {
    		return replaceCode(new String(bytes, CHARSET_NAME));
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
    }
    
    /**
     * 替换字符串中的八进制数据
     * @param str
     * @return
     */
	@SuppressWarnings("finally")
	public static String replaceCode(String str){
    	try {
    		//替换字符串中用八进制表示的换行，回车，tab的符号
			str = str.replace("\\015", "\r").replace("\\012", "\n").replace("\\011", "\t");
			//对字符串中形如\xxx(x是数字)的八进制数据进行转换，3个为一组替换成中文
            String regex = "(\\\\[0-7]{3}){3}";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(str);
            while(m.find()){
            	//八进制数据转化为中文
            	str = str.replace(m.group(), codeToChinese(m.group()));
            }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return str;
		}
    }
	
	/**
	 * 把匹配的八进制数据转化为中文
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
    public static String codeToChinese(String str) throws UnsupportedEncodingException {
		String code = str;
		// 1.获取八进制数据
		String[] split = code.substring(1).split("\\\\");
		StringBuffer s16 = new StringBuffer();
		// 2.把八进制转换成格式为:%+十六进制
		for (String s : split) {
			s16.append("%" + Integer.toHexString(Integer.valueOf(s, 8)).toString().toUpperCase());
		}
		// 3.解码
		return URLDecoder.decode(s16.toString(), "UTF-8");
	}
    
    /**
     * 是否包含字符串
     * @param str 验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inString(String str, String... strs){
    	if (str != null){
        	for (String s : strs){
        		if (str.equals(trim(s))){
        			return true;
        		}
        	}
    	}
    	return false;
    }
    
	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {
		if (isBlank(html)){
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}
	
	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * @param html
	 * @return
	 */
	public static String replaceMobileHtml(String html){
		if (html == null){
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}
	
	/**
	 * txt 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * @param txt
	 * @return
	 */
	public static String toHtml(String txt){
		if (txt == null){
			return "";
		}
		return replace(replace(Encodes.escapeHtml(txt), "\n", "<br/>"), "\t", "&nbsp; &nbsp; ");
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String abbr2(String param, int length) {
		if (param == null) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		int n = 0;
		char temp;
		boolean isCode = false; // 是不是HTML代码
		boolean isHTML = false; // 是不是HTML特殊字符,如&nbsp;
		for (int i = 0; i < param.length(); i++) {
			temp = param.charAt(i);
			if (temp == '<') {
				isCode = true;
			} else if (temp == '&') {
				isHTML = true;
			} else if (temp == '>' && isCode) {
				n = n - 1;
				isCode = false;
			} else if (temp == ';' && isHTML) {
				isHTML = false;
			}
			try {
				if (!isCode && !isHTML) {
					n += String.valueOf(temp).getBytes("GBK").length;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (n <= length - 3) {
				result.append(temp);
			} else {
				result.append("...");
				break;
			}
		}
		// 取出截取字符串中的HTML标记
		String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)",
				"$1$2");
		// 去掉不需要结素标记的HTML标记
		temp_result = temp_result
				.replaceAll(
						"</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
						"");
		// 去掉成对的HTML标记
		temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>",
				"$2");
		// 用正则表达式取出标记
		Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
		Matcher m = p.matcher(temp_result);
		List<String> endHTML = Lists.newArrayList();
		while (m.find()) {
			endHTML.add(m.group(1));
		}
		// 补全不成对的HTML标记
		for (int i = endHTML.size() - 1; i >= 0; i--) {
			result.append("</");
			result.append(endHTML.get(i));
			result.append(">");
		}
		return result.toString();
	}
	
	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val){
		if (val == null){
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val){
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val){
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val){
		return toLong(val).intValue();
	}
	
	/**
	 * 获得i18n字符串
	 */
	public static String getMessage(String code, Object[] args) {
		LocaleResolver localLocaleResolver = (LocaleResolver) SpringContextHolder.getBean(LocaleResolver.class);
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		Locale localLocale = localLocaleResolver.resolveLocale(request);
		return SpringContextHolder.getApplicationContext().getMessage(code, args, localLocale);
	}
	
	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		String remoteAddr = request.getHeader("X-Real-IP");
        if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("X-Forwarded-For");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("Proxy-Client-IP");
        }else if (isNotBlank(remoteAddr)) {
        	remoteAddr = request.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}

	/**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    
    /**
	 * 驼峰命名法工具
	 * @return
	 * 		toCamelCase("hello_world") == "helloWorld" 
	 * 		toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 * 		toUnderScoreCase("helloWorld") = "hello_world"
	 */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }
    
    /**
     * 如果不为空，则设置值
     * @param target	需要设置的主非常
     * @param source	设置的值
     * @return			重新设置值之后的字符串
     */
    public static void setValueIfNotBlank(String target, String source) {
		if (isNotBlank(source)){
			target = source;
		}
	}
 
    /**
     * 转换为JS获取对象值，生成三目运算返回结果
     * @param objectString 对象串
     *   例如：row.user.id
     *   返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
     */
    public static String jsGetVal(String objectString){
    	StringBuilder result = new StringBuilder();
    	StringBuilder val = new StringBuilder();
    	String[] vals = split(objectString, ".");
    	for (int i=0; i<vals.length; i++){
    		val.append("." + vals[i]);
    		result.append("!"+(val.substring(1))+"?'':");
    	}
    	result.append(val.substring(1));
    	return result.toString();
    }
    
    /**
     * 初始化搜索树的校验功能
     * @param validators
     * @return
     */
    public static String gernValidators(String validators){
    	String str = "";
    	if(null!=validators){
    		String[] _split = validators.split("#");
    		str += "data-bv-" + _split[0] + "=\"true\" ";
    		for(int i=1;i<_split.length;i++){
    			String[] _split1 = _split[i].split("=");
    			str += "data-bv-" + _split[0] + "-" +_split1[0] + "=\"" + _split1[1] + "\" ";
    		}
    	}
    	return str;
    }
    /**
     * 		校验字符串  是否以splitStr为间隔符,并且以字符串以startStr开头,suffix结尾
     *      lg: String str = '111",'2222"    validateStringisReg(str,",","'","\"");
     *      符合规则，返回true;不符合规则，返回false
     * @param str0
     * @param splitStr
     * @param startStr
     * @param suffix
     * @return
     */
    public static boolean validateStringisReg(String str0,String splitStr,String startStr,String suffix){
    	String [] str = str0.split(splitStr);
    	if(str.length > 0){
    		String temp = null;
    		for(int i = 0 ; i < str.length;i++){
    			temp = str[i];
    			if(temp.startsWith(startStr) && temp.endsWith(suffix)){
    				continue;
    			}else{
    				return false;
    			}
    		}
    		return true;
    	}else{
    		return false;
    	}
    	
    }
    
    /*20200321 add by chenyl for 迁移framework.common.share.util.StringUtil的方法*/
	public static int toInt(String str) {
		if (str == null || str.length() == 0) {
			return -1;
		}
		int result = 1;
		char ch;
		int size = str.length();
		for (int i = 0; i < size; ++i) {
			ch = str.charAt(i);
			if (ch >= '0' && ch <= '9') {
				if (i == 0) {
					result = ch - '0';
				} else {
					result = result * 10 + (ch - '0');
				}
			} else {
				return -1;
			}
		}
		return result;
	}

	public static int toInt(String str, int def) {
		if (str == null || str.length() == 0) {
			return def;
		}
		int result = 1;
		char ch;
		int size = str.length();
		for (int i = 0; i < size; ++i) {
			ch = str.charAt(i);
			if (ch >= '0' && ch <= '9') {
				if (i == 0) {
					result = ch - '0';
				} else {
					result = result * 10 + (ch - '0');
				}
			} else {
				return def;
			}
		}
		return result;
	}

	public static int toIntWithException(String str, int def) throws Exception {
		if (str == null || str.length() == 0) {
			throw new Exception();
		}
		int result = 1;
		char ch;
		int size = str.length();
		for (int i = 0; i < size; ++i) {
			ch = str.charAt(i);
			if (ch >= '0' && ch <= '9') {
				if (i == 0) {
					result = ch - '0';
				} else {
					result = result * 10 + (ch - '0');
				}
			} else {
				throw new Exception();
			}
		}
		return result;
	}

	// 增加此方法，用于替代String比较的equals方法
	public static final boolean equals(String oper1, String oper2) {
		if (oper1 == oper2) {
			return true;
		} else if (oper1 == null || oper2 == null) {
			return false;
		} else {
			if (oper1.length() != oper2.length()) {
				return false;
			} else {
				return oper1.compareTo(oper2) == 0;
			}
		}
	}
	/**
	 * 20181013 chenyl
	 * 获取字符串中某个字符串的的数量
	 * @param parent
	 * @param child
	 * @return
	 */
	public static final int getStrNumFormParent(String parent, String child) {
		int fromIndex = 0;
		int count = 0;
		while (true) {
			int index = parent.indexOf(child, fromIndex);
			if (-1 != index) {
				fromIndex = index + 1;
				count++;
			} else {
				break;
			}
		}
		return count;
	}
	
	/*20200321 add by chenyl for 迁移framework.impl.bizkernel.runtime.util.StringUtil的方法*/
	/**
	 * 去除空格,如果源字符串是null则返回空字符串 ""，不为null则返回去除该字符串两边空格的字符串
	 * @param str 源字符串
	 * @return 返回，去掉字符串前后空格的字符串
	 */
	public static String  parseNullAndTrim(String str){    	
    	if(str==null){
    		return "";
    	}    	
    	return str.trim();
    	
    }
	
	/**
	 * 根据错误或异常的超类throwable，得到错误信息堆栈信息
	 * @param e 错误或者异常类
	 * @return 返回异常的堆栈信息
	 */
	public static String parseStackTrace(Throwable e){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(out));
		String errorMessage = out.toString();
		try {
			out.close();
		} catch (IOException exception) {
			log.error("获得异常堆栈信息出错: "+exception.getMessage());
		}
		return errorMessage;
	}
	
	/**
	 * 把传入的类名称加上"java.lang.",并去掉范型符号<>所包含的东西
	 * @param shortName 类名
	 * @return 加上完整路径，或者去掉范型类型的类名
	 */
	public static String parseClassNameForLangPackage(String shortName){
		String longName=shortName;
		if(shortName!=null&&shortName.indexOf(".")==-1&&shortName.length()>0&&Character.isUpperCase(shortName.charAt(0))){
			longName="java.lang."+shortName;
		}
		if(longName!=null){
			int i=longName.indexOf("<");
			if(i!=-1){
				longName=longName.substring(0, i);
			}
		}
		return longName;
	}
	
	/**
	 * 字符串不为空，如果为空则抛出BaseException异常
	 * @param value 需要判断的字符串
	 * @param message 错误消息
	 * @throws BaseException 如果字符串为空会抛出运行期异常
	 */
	public static void notBlank(String value,String message) {
		if(null == value || value.trim().length() < 1) {
			throw new BaseException(SysErr.E_DEFAULT, message);
		}
	}
	
	/**
	 * 判断字符串是否不为空，如果不为空，则返回true，为空返回false
	 * @param value 需要判断的字符串
	 * @return 如果不为空，则返回true，为空返回false
	 */
	public static boolean isNotBlank(String value) {
		if(null != value && value.trim().length() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 格式化时间
	 * @param time IAdapter生成时间
	 * @param format 格式化串
	 * @return
	 */
	public static String formatModifiTime(long time,String format) {
		String formatDate = null;
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date(time);
		formatDate = dateFormat.format(date);
		return formatDate;
	}
}

