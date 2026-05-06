package com.adtec.sys.modules.sys.utils;

import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import org.apache.axis.utils.StringUtils;

public class PwdCheck {

    //非shift键盘字符表
    private final static char[][] CHAR_TABLE1 = new char[][]{
            {'1','2','3','4','5','6','7','8','9','0','-','=','\0'},
            {'q','w','e','r','t','y','u','i','o','p','[',']','\\'},
            {'a','s','d','f','g','h','j','k','l',';','\'','\0','\0'},
            {'z','x','c','v','b','n','m',',','.','/','\0','\0','\0'}};

    //shift键盘字符表
    private final static char[][] CHAR_TABLE2 = new char[][]{
            {'!','@','#','$','%','^','&','*','(',')','_','+','\0'},
            {'q','w','e','r','t','y','u','i','o','p','{','}','|'},
            {'a','s','d','f','g','h','j','k','l',':','"','\0','\0'},
            {'z','x','c','v','b','n','m','<','>','?','\0','\0','\0'}};
    /**
     * 是否包含3个及以上相同或字段连续字符
     * @param password
     * @return
     */
    public static boolean hasContinuousChar(String password){
        char[] chars = password.toCharArray();
        if (chars.length < 8) {
            throw new BaseException(SysErr.E_MESSAGE, "密码长度至少为8位");
        }
        for (int i = 0; i < chars.length - 2; i++) {
            int n1 = chars[i];
            int n2 = chars[i + 1];
            int n3 = chars[i + 2];
            if(n1 == n2 && n1 == n3){
                return true;
            }
            //判断连续字符 正序+倒序
            if((n1 + 1 == n2 && n1 +2 == n3) || (n1 -1 == n2 && n1 -2 == n3)){
                return true;
            }
        }
        return false;
    }


    public static boolean hasKeyBoardContinuousChar(String password){
        if(StringUtils.isEmpty(password)){
            return false;
        }
        char[] lowCaseChars = password.toLowerCase().toCharArray();

        int charsLen = lowCaseChars.length;
        if (charsLen < 8) {
            throw new BaseException(SysErr.E_MESSAGE, "密码长度至少为8位");
        }
        //记录位置数据
        int[] rowPosition = new int[charsLen];
        int[] colPosition = new int[charsLen];
        for (int i = 0; i < charsLen; i++) {
            char chLower = lowCaseChars[i];
            colPosition[i] = -1;
            //检索表1
            for (int rowIndex1 = 0; rowIndex1 < 4; rowIndex1++) {
                for (int colIndex1 = 0; colIndex1 < 13; colIndex1++) {
                    if(chLower == CHAR_TABLE1[rowIndex1][colIndex1]){
                        rowPosition[i] = rowIndex1;
                        colPosition[i] = colIndex1;
                    }
                }
            }
            //表1中没找到 去表2中寻找
            if(colPosition[i] >= 0){
                continue;
            }
            //检索表1
            for (int rowIndex2 = 0; rowIndex2 < 4; rowIndex2++) {
                for (int colIndex2 = 0; colIndex2 < 13; colIndex2++) {
                    if(chLower == CHAR_TABLE2[rowIndex2][colIndex2]){
                        rowPosition[i] = rowIndex2;
                        colPosition[i] = colIndex2;
                    }
                }
            }
        }
        //匹配坐标连线
        for (int j = 1; j <= charsLen - 2; j++) {
            //同一行
            if(rowPosition[j - 1] == rowPosition[j] && rowPosition[j] == rowPosition[j + 1]){
                //键盘连续正向(asd)或者反向连续(dsa)
                if((colPosition[j -1] + 1 == colPosition[j] && colPosition[j] + 1 == colPosition[j + 1]) ||
                        (colPosition[j + 1] + 1 == colPosition[j] && colPosition[j] + 1 ==colPosition[j - 1])){
                    return true;
                }
            }
            //同一列
//            if(colPosition[j - 1] == colPosition[j] && colPosition[j] == colPosition[j + 1]){
//                //键盘正向(qaz)或者反向连续(zaq)
//                if((rowPosition[j - 1] + 1 == rowPosition[j] && rowPosition[j] + 1 == rowPosition[j + 1]) ||
//                        (rowPosition[j + 1] + 1 == rowPosition[j] && rowPosition[j] + 1 ==rowPosition[j - 1])){
//                    return true;
//                }
//            }
        }
        return false;
    }

}
