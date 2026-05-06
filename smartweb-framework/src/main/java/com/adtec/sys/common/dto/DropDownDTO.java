package com.adtec.sys.common.dto;

import java.util.List;

/**
 * @author Leize
 * @date 2020/10/12 15:36
 */
public class DropDownDTO {

    private String retCode;
    private List<DropDownOptionDTO> list;

    public DropDownDTO(List<DropDownOptionDTO> list) {
        this.retCode = "0000";
        this.list = list;
    }

    public DropDownDTO(String retCode, List<DropDownOptionDTO> list) {
        this.retCode = retCode;
        this.list = list;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public List<DropDownOptionDTO> getList() {
        return list;
    }

    public void setList(List<DropDownOptionDTO> list) {
        this.list = list;
    }
}
