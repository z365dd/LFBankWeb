package com.adtec.prod.oper.web;

import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.prod.oper.entity.AreaDo;
import com.adtec.prod.oper.entity.BusiTypeDo;
import com.adtec.prod.oper.entity.EntrDemoDO;
import com.adtec.prod.oper.service.EntrDemoService;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.entity.Dict;
import com.adtec.sys.modules.sys.utils.DictUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/prod/oper/entrDemo")
public class EntrDemoController extends BaseController {
    @Autowired
    private EntrDemoService entrDemoService;


    /**
     * 返回查询页面
     */
    @RequiresPermissions("user")
    @RequestMapping(value = {"entrDemoList"})
    public String entrDemoList(HttpServletRequest request, HttpServletResponse response) {
        return "starring/prod/oper/entrDemoList";
    }

    /**
     * 返回新增页面
     */
    @RequiresPermissions("user")
    @RequestMapping(value = {"entrDemoForm"})
    public String entrDemoForm(HttpServletRequest request, HttpServletResponse response) {
        return "starring/prod/oper/entrDemoForm";
    }

  

    /**
     * 列表查询
     */
    @RequiresPermissions("user")
    @RequestMapping(value = {"qry"})
    public void qry(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String ENTR_NO = reqDs.getString("ENTR_NO");
        String ENTR_NAME = reqDs.getString("ENTR_NAME");
        String OPEN_STAT = reqDs.getString("OPEN_STAT");
        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("pageSize");
        EntrDemoDO entrDemoDO = new EntrDemoDO();

        entrDemoDO.setEntrNo(ENTR_NO);
        entrDemoDO.setEntrName(ENTR_NAME);
        entrDemoDO.setOpenStat(OPEN_STAT);

        List<EntrDemoDO> list = entrDemoService.list(entrDemoDO, start, limit);
        int total = entrDemoService.getTotal(entrDemoDO);
        IDataset resDs = DatasetService.getInstace().getDataset(list, EntrDemoDO.class);
        chgDict(resDs, true);
        resDs.setTotalCount(total);

        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
    }

    /**
     * 列表查询
     */
    @RequiresPermissions("user")
    @RequestMapping(value = {"qryEntrList"})
    public void qryEntrList(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String ENTR_NO = reqDs.getString("ENTR_NO");
        String ENTR_NAME = reqDs.getString("ENTR_NAME");
        String OPEN_STAT = reqDs.getString("OPEN_STAT");
        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("pageSize");
        EntrDemoDO entrDemoDO = new EntrDemoDO();

        entrDemoDO.setEntrNo(ENTR_NO);
        entrDemoDO.setEntrName(ENTR_NAME);
        entrDemoDO.setOpenStat(OPEN_STAT);

        List<Dict> listA = new ArrayList<Dict>();

        List<EntrDemoDO> list = entrDemoService.list(entrDemoDO, start, limit);
        for (int i = 0; i < list.size(); i++) {
            //单选下拉框需要添加空选项
            Dict d = new Dict();
            d.setLabel(list.get(i).getEntrName());
            d.setValue(list.get(i).getEntrNo() + "@@" + list.get(i).getEntrName());
            listA.add(d);
        }
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("retCode", "0000");
        m.put("list", listA);
        renderString(response, m);
    }

    /**
     * 列表查询
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = {"qryForMsmall"})
    public void qryForMsmall(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String ENTR_NO = reqDs.getString("ENTR_NO");
        String ENTR_NAME = reqDs.getString("ENTR_NAME");
        String OPEN_STAT = reqDs.getString("OPEN_STAT");
        String entrNature = reqDs.getString("entrNature");
        int start = reqDs.getInt("start");
        int limit = reqDs.getInt("pageSize");
        EntrDemoDO entrDemoDO = new EntrDemoDO();

        entrDemoDO.setEntrNo(ENTR_NO);
        entrDemoDO.setEntrName(ENTR_NAME);
        entrDemoDO.setOpenStat(OPEN_STAT);
        entrDemoDO.setEntrNature(entrNature);

        List<EntrDemoDO> list = entrDemoService.list(entrDemoDO, start, limit);
        int total = entrDemoService.getTotal(entrDemoDO);
        IDataset resDs = DatasetService.getInstace().getDataset(list, EntrDemoDO.class);
        resDs.setTotalCount(total);

        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
    }

    /**
     * 状态修改 after
     */
    @RequiresPermissions("user")
    @RequestMapping(value = {"UpdateStat"})
    public void UpdateStat(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String ENTR_NO = reqDs.getString("ENTR_NO");
        String OPEN_STAT = reqDs.getString("OPEN_STAT");
        EntrDemoDO entrDemoDO = new EntrDemoDO();
        entrDemoDO.setEntrNo(ENTR_NO);
        entrDemoDO.setOpenStat(OPEN_STAT);
        int rs = entrDemoService.UpdateStat(entrDemoDO);
        IDataset resDs = DatasetService.getInstace().getDataset();
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
    }

    /**
     * 提交 after
     *
     * @param request
     * @param response
     */
    @RequiresPermissions("user")
    @RequestMapping(value = {"add"})
    public void add(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String ENTR_NO = reqDs.getString("ENTR_NO");
        String ENTR_NAME = reqDs.getString("ENTR_NAME");
        String ENTR_NATURE = reqDs.getString("ENTR_NATURE");
        String ENTR_CERT_TP = reqDs.getString("ENTR_CERT_TP");
        String LEGA_CERT_TP = reqDs.getString("LEGA_CERT_TP");
        String ENTR_CERT_NO = reqDs.getString("ENTR_CERT_NO");
        String LEGA_CERT_NO = reqDs.getString("LEGA_CERT_NO");
        String LEGA_NAME = reqDs.getString("LEGA_NAME");
        String ENTR_ADDR = reqDs.getString("ENTR_ADDR");
        String COMN_DESC = reqDs.getString("COMN_DESC");
        String ENTR_TEL_NO = reqDs.getString("ENTR_TEL_NO");
        String EMAIL = reqDs.getString("EMAIL");
        String CTCT_PER_NAME = reqDs.getString("CTCT_PER_NAME");
        String CTCT_PHONE_NO = reqDs.getString("CTCT_PHONE_NO");
        String OPEN_STAT = reqDs.getString("OPEN_STAT");
        String MID_RMRK = reqDs.getString("MID_RMRK");
        //short_mark存放业务类别 busiTypeId
        String SHORT_REMARK = reqDs.getString("busiTypeId");
        EntrDemoDO addDo = new EntrDemoDO();
        addDo.setEntrName(ENTR_NAME);
        addDo.setEntrNature(ENTR_NATURE);
        addDo.setEntrCertTp(ENTR_CERT_TP);
        addDo.setLegaCertTp(LEGA_CERT_TP);
        addDo.setEntrCertNo(ENTR_CERT_NO);
        addDo.setLegaCertNo(LEGA_CERT_NO);
        addDo.setEntrAddr(ENTR_ADDR);
        addDo.setComnDesc(COMN_DESC);
        addDo.setEntrTelNo(ENTR_TEL_NO);
        addDo.setEmail(EMAIL);
        addDo.setLegaName(LEGA_NAME);
        addDo.setCtctPerName(CTCT_PER_NAME);
        addDo.setCtctPhoneNo(CTCT_PHONE_NO);
        addDo.setOpenStat(OPEN_STAT);
        addDo.setUrl(reqDs.getString("url"));
        addDo.setMidRmrk(MID_RMRK);
        addDo.setShortRmrk(SHORT_REMARK);
        if ("1".equals(reqDs.getString("OPER_TP"))) {
            addDo.setEntrNo(ENTR_NO);//获取单位编号
            entrDemoService.add(addDo);
        } else if ("2".equals(reqDs.getString("OPER_TP"))) {
            addDo.setEntrNo(ENTR_NO);
            entrDemoService.update(addDo);
        }
        IDataset resDs = DatasetService.getInstace().getDataset();
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");

    }

    /**
     * 获取详细数据 after
     */
    @RequiresPermissions("user")
    @RequestMapping(value = {"getDetail"})
    public void getDetail(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String ENTR_NO = reqDs.getString("ENTR_NO");
        EntrDemoDO DO = new EntrDemoDO();
        DO.setEntrNo(ENTR_NO);
        EntrDemoDO result = entrDemoService.getDetail(DO);
        IDataset resDs = DatasetService.getInstace().getDataset(result, EntrDemoDO.class);
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
    }

    /**
     * 删除记录
     *
     * @param request
     * @param response
     */
    @RequiresPermissions("user")
    @RequestMapping(value = {"delete"})
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String ENTR_NO = reqDs.getString("ENTR_NO");
        EntrDemoDO DO = new EntrDemoDO();
        DO.setEntrNo(ENTR_NO);
        entrDemoService.delete(DO);
        IDataset resDs = DatasetService.getInstace().getDataset();
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
    }

    /**
     * 获取单位编号
     *
     * @param request
     * @param response
     */
    @RequiresPermissions("user")
    @RequestMapping(value = {"getEntrNo"})
    public void getEntrNo(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String ENTR_NO = entrDemoService.getNewEntrNO();
        EntrDemoDO DO = new EntrDemoDO();
        DO.setEntrNo(ENTR_NO);
        IDataset resDs = DatasetService.getInstace().getDataset(DO, EntrDemoDO.class);
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
    }


    /**
     * 列表查询
     */
    @RequiresPermissions("user")
    @RequestMapping(value = {"qryName"})
    public void qryName(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String ENTR_NAME = reqDs.getString("ENTR_NAME");
        EntrDemoDO entrDemoDO = new EntrDemoDO();
        entrDemoDO.setEntrName(ENTR_NAME);
        List<EntrDemoDO> list = entrDemoService.qryName(entrDemoDO);
        IDataset resDs = DatasetService.getInstace().getDataset(list, EntrDemoDO.class);
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
    }

    /**
     * 数字字典转换
     *
     * @param ds
     * @param isAction
     */
    private void chgDict(IDataset ds, boolean isAction) {
        if (null == ds) {
            return;
        }
        //中文描述
//		ds.addColumn("valStr", DatasetColumnType.DS_STRING);

        if (isAction) {
            //新增操作描述列
            ds.addColumn("action", DatasetColumnType.DS_STRING);
            ds.addColumn("statStr", DatasetColumnType.DS_STRING);
        }
        ds.beforeFirst();
        while (ds.hasNext()) {
            ds.next();
            //中文描述
            ds.updateString("statStr", DictUtils.getDictLabel(ds.getString("openStat"), "OPEN_STAT", ""));

            if (isAction) {
                /*添加相关操作按钮*/
                StringBuffer action = new StringBuffer();
                action.append("	<a href=\"JavaScript:void(0);\" onClick=\"Detail('" + ds.getString("entrNo") + "')\" >详情</a>");
                action.append("	<a href=\"JavaScript:void(0);\" onClick=\"Revice('" + ds.getString("entrNo") + "')\" >修改</a>");
                if (ds.getString("openStat").equals("Y")) {
                    action.append("	<a href=\"JavaScript:void(0);\" onClick=\"UpdateStat('" + ds.getString("entrNo") + "','N')\" >关闭</a>");
                } else if (ds.getString("openStat").equals("N")) {
                    action.append("	<a href=\"JavaScript:void(0);\" onClick=\"UpdateStat('" + ds.getString("entrNo") + "','Y')\" >开通</a>");
                }
                action.append("	<a href=\"JavaScript:void(0);\" onClick=\"Delete('" + ds.getString("entrNo") + "')\" >删除</a>");
                ds.updateString("action", action.toString());
            }
        }
    }

    @RequiresPermissions("user")
    @RequestMapping(value = {"area"})
    public void qryArea(HttpServletRequest request, HttpServletResponse response) {
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String parentId = reqDs.getString("parentId");
        List<AreaDo> list = entrDemoService.getEntrDict(parentId);
        IDataset resDs = DatasetService.getInstace().getDataset(list, AreaDo.class);
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
    }
    
    @RequiresPermissions("user")
    @RequestMapping(value = {"getBusiType"})
    public void qryBusiType(HttpServletRequest request, HttpServletResponse response) {

        List<BusiTypeDo>list = entrDemoService.getBusiType();
        IDataset resDs = DatasetService.getInstace().getDataset(list, BusiTypeDo.class);
        setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "交易成功");
    }

}
