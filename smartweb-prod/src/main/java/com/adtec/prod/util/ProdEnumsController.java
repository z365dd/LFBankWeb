package com.adtec.prod.util;

import com.adtec.prod.oper.entity.ProdAttrDO;
import com.adtec.prod.oper.service.ProdAttrService;
import com.adtec.prod.oper.service.TPipSvcService;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 下拉列表枚举
 */
@Controller
@RequestMapping(value = "${adminPath}/prod/enums")
public class ProdEnumsController extends BaseController {
    @Autowired
    private ProdAttrService prodAttrService;
    @Autowired
    private TPipSvcService tPipSvcService;

    private final static Logger logGER = LoggerFactory.getLogger(ProdEnumsController.class);

//    @RequestMapping(value = "selectData")
//    public void getSelectJson(HttpServletRequest request, HttpServletResponse response) {
//        IDataset reqDs = DatasetService.getInstace().getDataset(request);
//        String type = reqDs.getString("type");
//        String noEmpty = reqDs.getString("noEmpty");
//        List<Map<String, Object>> maps;
//        maps = ProdEnums.getSelect(type, noEmpty);
//
//        Map<String, Object> m = new HashMap<>();
//
//        m.put("retCode", "0000");
//        m.put("list", maps);
//
//        renderString(response, m);
//    }

//    @RequestMapping(value = "selectDataAll")
//    public void selectDataAll(HttpServletRequest request, HttpServletResponse response) {
//        IDataset reqDs = DatasetService.getInstace().getDataset(request);
//        String type = reqDs.getString("type");
//        String noEmpty = reqDs.getString("noEmpty");
//        List<Map<String, Object>> maps;
//        maps = ProdEnums.getSelectAll(type, noEmpty);
//
//        Map<String, Object> m = new HashMap<>();
//
//        m.put("retCode", "0000");
//        m.put("list", maps);
//
//        renderString(response, m);
//    }

    @RequestMapping(value = "selectCompData")
    public void selectCompData(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String type = reqDs.getString("type");
        String noEmpty = reqDs.getString("noEmpty");
        List<Map<String, Object>> maps;
        maps = getSelect(type, noEmpty);

        Map<String, Object> m = new HashMap<>();

        m.put("retCode", "0000");
        m.put("list", maps);

        renderString(response, m);
    }

    public List<Map<String, Object>> getSelect(String type, String noEmpty) {
        List<Map<String, Object>> selects = new ArrayList<>();
        if (!"Y".equals(noEmpty)) {
            Map<String, Object> emptyMap = new HashMap<>(2);
            emptyMap.put("label", "--请选择--");
            emptyMap.put("value", "");
            selects.add(emptyMap);
        }
        List<ProdAttrDO> list = prodAttrService.qry(new ProdAttrDO(),0,0);
        for (ProdAttrDO prodAttrDO : list) {
            Map<String, Object> map = new HashMap<>(2);
            map.put("label", prodAttrDO.getCOMP_NAME());
            map.put("value", prodAttrDO.getCOMP_NO());
            selects.add(map);
        }
        return selects;
    }

}
