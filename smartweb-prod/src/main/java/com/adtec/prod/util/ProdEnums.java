package com.adtec.prod.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 下拉列表枚举
 */
public enum ProdEnums {

    KEY_TP("KEY_TP", new String[]{"01__产品KV属性","02__技术KV属性","04__界面属性"}),
    VAL_TP("VAL_TP", new String[]{"01__String","02__Double","03__Float","04__Long","05__Int"}),
    ENTER_TP("ENTER_TP", new String[]{"01__输入框（text）","02__下拉框（select）","03__多选框（checkbox）"}),
    ENTR_NATURE("ENTR_NATURE", new String[]{"1__国有企业","2__国有控股企业","3__外资企业","4__合资企业","5__私营企业","6__事业单位","7__国家行政机关","8__政府"}),
    FLG("FLG", new String[]{"Y__开通","N__关闭"}),
    STAT("STAT",new String[]{"01__开通","02__关闭"}),
    ;

    private final String type;
    private final String[] list;

    ProdEnums(String type, String[] list) {
        this.type = type;
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public String[] getList() {
        return list;
    }

    public static ProdEnums codeOf(String code) {
        for (ProdEnums dropDownEnums : values()) {
            if (dropDownEnums.getType().equals(code)) {
                return dropDownEnums;
            }
        }
        throw new RuntimeException("没有找到对应的枚举");
    }

    /**
     * 获取下拉列表数据
     *
     * @param type 类型
     * @return 下拉列表数据
     */
//    public static List<Map<String, Object>> getSelect(String type) {
//        List<Map<String, Object>> selects = new ArrayList<>();
//        Map<String, Object> emptyMap = new HashMap<>(2);
//        emptyMap.put("label", "--请选择--");
//        emptyMap.put("value", "");
//        selects.add(emptyMap);
//        String[] list = codeOf(type).getList();
//        for (String str : list) {
//            String[] arr = str.split("__");
//            Map<String, Object> map = new HashMap<>(2);
//            map.put("label", arr[1]);
//            map.put("value", arr[0]);
//            selects.add(map);
//        }
//        return selects;
//    }

//    public static List<Map<String, Object>> getSelect(String type, String noEmpty) {
//        List<Map<String, Object>> selects = new ArrayList<>();
//        if (!"Y".equals(noEmpty)) {
//            Map<String, Object> emptyMap = new HashMap<>(2);
//            emptyMap.put("label", "--请选择--");
//            emptyMap.put("value", "");
//            selects.add(emptyMap);
//        }
//        String[] list = codeOf(type).getList();
//        for (String str : list) {
//            String[] arr = str.split("__");
//            Map<String, Object> map = new HashMap<>(2);
//            map.put("label", arr[1]);
//            map.put("value", arr[0]);
//            selects.add(map);
//        }
//        return selects;
//    }

//    public static List<Map<String, Object>> getSelectAll(String type, String noEmpty) {
//        List<Map<String, Object>> selects = new ArrayList<>();
//        if (!"Y".equals(noEmpty)) {
//            Map<String, Object> emptyMap = new HashMap<>(2);
//            emptyMap.put("label", "全部");
//            emptyMap.put("value", "");
//            selects.add(emptyMap);
//        }
//        String[] list = codeOf(type).getList();
//        for (String str : list) {
//            String[] arr = str.split("__");
//            Map<String, Object> map = new HashMap<>(2);
//            map.put("label", arr[1]);
//            map.put("value", arr[0]);
//            selects.add(map);
//        }
//        return selects;
//    }
}
