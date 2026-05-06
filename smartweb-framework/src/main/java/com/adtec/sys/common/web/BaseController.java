package com.adtec.sys.common.web;

import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.ErrInfo;
import com.adtec.framework.exception.ErrMsg;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.CommonDatasets;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.impl.uiengine.util.JsonServiceUtil;
import com.adtec.framework.interfaces.share.DatasetColumnType;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.framework.interfaces.share.IDatasets;
import com.adtec.framework.interfaces.uiengine.JsonService;
import com.adtec.framework.interfaces.uiengine.JsonServiceFactory;
import com.adtec.sys.common.beanvalidator.BeanValidators;
import com.adtec.sys.common.mapper.JsonMapper;
import com.adtec.sys.common.utils.CommonConstants;
import com.adtec.sys.common.utils.Encodes;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.beans.PropertyEditorSupport;
import java.io.*;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;
import java.util.Map.Entry;

/**
 * 控制器支持类
 *
 * @version 2013-3-23
 */
public abstract class BaseController {

    /**
     * 日志对象
     */
    protected final static Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 管理基础路径
     */
    @Value("${adminPath}")
    protected String adminPath;

    /**
     * 前端基础路径
     */
    @Value("${frontPath}")
    protected String frontPath;

    /**
     * 前端URL后缀
     */
    @Value("${urlSuffix}")
    protected String urlSuffix;

    /**
     * 验证Bean实例对象
     */
    @Autowired
    protected Validator validator;

    /**
     * json构造器
     */
    @Autowired
    JsonServiceFactory jsonServiceFactory;

    /**
     * 消息类型：infoinfo
     */
    public static final int MSG_TYPE_INFO = 1;
    /**
     * 消息类型：success
     */
    public static final int MSG_TYPE_SUCCESS = 2;
    /**
     * 消息类型：warning
     */
    public static final int MSG_TYPE_WARNING = 3;
    /**
     * 消息类型：error
     */
    public static final int MSG_TYPE_ERROR = 4;
    /**
     * 消息类型：loading
     */
    public static final int MSG_TYPE_LOADING = 5;


    /**
     * 服务端参数有效性验证
     *
     * @param object 验证的实体对象
     * @param groups 验证组
     * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
     */
    protected boolean beanValidator(Model model, Object object, Class<?>... groups) {
        try {
            BeanValidators.validateWithException(validator, object, groups);
        } catch (ConstraintViolationException ex) {
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "数据验证失败：");
            addMessage(model, list.toArray(new String[]{}));
            return false;
        }
        return true;
    }

    /**
     * 服务端参数有效性验证
     *
     * @param object 验证的实体对象
     * @param groups 验证组
     * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
     */
    protected boolean beanValidator(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
        try {
            BeanValidators.validateWithException(validator, object, groups);
        } catch (ConstraintViolationException ex) {
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "数据验证失败：");
            addMessage(redirectAttributes, list.toArray(new String[]{}));
            return false;
        }
        return true;
    }

    /**
     * 服务端参数有效性验证
     *
     * @param object 验证的实体对象
     * @param groups 验证组，不传入此参数时，同@Valid注解验证
     * @return 验证成功：继续执行；验证失败：抛出异常跳转400页面。
     */
    protected void beanValidator(Object object, Class<?>... groups) {
        BeanValidators.validateWithException(validator, object, groups);
    }

    /**
     * 添加Model消息
     *
     * @param message
     */
    protected void addMessage(Model model, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        model.addAttribute("message", sb.toString());
    }

    /**
     * 添加Flash消息
     *
     * @param message
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        redirectAttributes.addFlashAttribute("message", sb.toString());
    }

    /**
     * 返回错误信息json格式
     *
     * @return
     */
    protected String renderMessage(HttpServletResponse response, String... messages) {
        return renderMessage(response, this.MSG_TYPE_SUCCESS, messages);
    }

    /**
     * 返回错误信息json格式
     *
     * @return
     */
    protected String renderMessage(HttpServletResponse response, int msgType, String... messages) {
        Map messageMap = new HashMap<String, Object>();
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : "");
        }
        messageMap.put("message", sb);
        if (this.MSG_TYPE_ERROR == msgType) {
            messageMap.put("errorNo", SysErr.E_DEFAULT);
            messageMap.put("returnCode", SysErr.E_DEFAULT);
            messageMap.put("errorInfo", "错误信息");
            messageMap.put("msg_type", "error");
        } else if (this.MSG_TYPE_INFO == msgType) {
            messageMap.put("errorNo", SysErr.E_SUCCESS);
            messageMap.put("returnCode", SysErr.E_SUCCESS);
            messageMap.put("errorInfo", "提示信息");
            messageMap.put("msg_type", "info");
        } else if (this.MSG_TYPE_LOADING == msgType) {
            messageMap.put("errorNo", SysErr.E_SUCCESS);
            messageMap.put("returnCode", SysErr.E_SUCCESS);
            messageMap.put("errorInfo", "加载信息");
            messageMap.put("msg_type", "loading");
        } else if (this.MSG_TYPE_SUCCESS == msgType) {
            messageMap.put("errorNo", SysErr.E_SUCCESS);
            messageMap.put("returnCode", SysErr.E_SUCCESS);
            messageMap.put("errorInfo", "成功信息");
            messageMap.put("msg_type", "success");
        } else if (this.MSG_TYPE_WARNING == msgType) {
            messageMap.put("errorNo", SysErr.E_SUCCESS);
            messageMap.put("returnCode", SysErr.E_SUCCESS);
            messageMap.put("errorInfo", "警告信息");
            messageMap.put("msg_type", "warning");
        }
        return renderString(response, JsonMapper.toJsonString(messageMap), "application/json");
    }

    /**
     * 客户端返回JSON字符串
     *
     * @param response
     * @param object
     * @return
     */
    protected String renderString(HttpServletResponse response, Object object) {
        return renderString(response, JsonMapper.toJsonString(object), "application/json");
    }

    /**
     * 客户端返回字符串
     *
     * @param response
     * @param string
     * @return
     */
    protected String renderString(HttpServletResponse response, String string, String type) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0L);
            response.setContentType(type);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
            return string;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 传输对象进行返回到前端界面
     *
     * @param response
     * @param object
     * @param errorNo
     * @param errorMsg
     * @return
     */
    protected String renderDatasetString(HttpServletResponse response, Object object, String errorNo, String errorMsg) {
        String jsonStr = JsonMapper.toJsonString(object);
        return renderDatasetString(response, jsonStr, errorNo, errorMsg);
    }

    /**
     * 客户端返回JSON字符串，以dataset格式返回
     * dataSetResult:[ //转换的第一个结果集 {data:[json list],totalCount : 100},
     * //转换的第二个结果集 {data:[json list],totalCount : 100}, ...... {data:[json
     * list],totalCount : 100} ], //cep返回号 returnCode:, //cep返回的错误号 errorNo:,
     * //cep返回的错误信息 errorInfo: , //辅助字段 ... }<br>
     * 默认控件取dataSetResult数组中的第一条的值作为dataSet装载的数据。<br>
     * 注意：IDataset转化时默认都加入'totalCount"<br>
     *
     * @param response
     * @param object
     * @return
     */
    protected String renderDatasetString(HttpServletResponse response, String jsonStr, String errorNo, String errorMsg) {
        StringBuffer tempBuffer = new StringBuffer();
        tempBuffer.append("{ \"dataSetResult\" : [");
        tempBuffer.append(CommonConstants.CRLF);
        tempBuffer.append("{ \"data\": [").append(jsonStr).append("],");
        tempBuffer.append(" \"dataSetName\":\"result\",");
        tempBuffer.append(" \"totalCount\": 1");
        tempBuffer.append("} ], ");
        appendEventInfo(tempBuffer, errorNo, errorMsg);
        return renderString(response, tempBuffer.toString(), "application/json");
    }

    /**
     * jsonStr必须是正常的json字符串
     *
     * @param response
     * @param jsonStr
     * @param total
     * @param errorNo
     * @param errorMsg
     * @return
     */
    protected String renderDatasetString(HttpServletResponse response, String jsonStr, int total, String errorNo, String errorMsg) {
        StringBuffer tempBuffer = new StringBuffer();
        tempBuffer.append("{ \"dataSetResult\" : [");
        tempBuffer.append(CommonConstants.CRLF);
        tempBuffer.append("{ \"data\": ").append(jsonStr).append(",");
        tempBuffer.append(" \"dataSetName\":\"result\",");
        tempBuffer.append(" \"totalCount\": " + total);
        tempBuffer.append("} ], ");
        appendEventInfo(tempBuffer, errorNo, errorMsg);
        return renderString(response, tempBuffer.toString(), "application/json");
    }

    /**
     * 对于交易成功的可以输入别的成功交易信息，当出现交易失败时自动从BaseException中提取对应的错误码和错误信息
     *
     * @param request
     * @param response
     * @param ds
     * @param errorMsg
     */
    protected void setResponseDataset(HttpServletRequest request, HttpServletResponse response, IDataset ds, String errorMsg) {
        String errorNo = SysErr.E_SUCCESS;
        ErrInfo[] errInfos = BaseException.getErrRegInfo();
        if (null != errInfos && errInfos.length > 0) {
            errorNo = errInfos[0].getErrCode();
            errorMsg = errInfos[0].getErrMsg();
        }
        if (DataUtil.isNullStr(errorMsg)) {
            // 对于交易成功没有设置返回信息时，设置为默认配置的返回信息
            errorMsg = ErrMsg.get(errorNo);
        }
        if (null == ds) {
            ds = DatasetService.getInstace().getDataset();
            ds.setDatasetName("result");
        }
        setResponseDataset(request, response, ds, errorNo, errorMsg);
    }

    protected void setResponseDataset(HttpServletRequest request, HttpServletResponse response, IDataset ds, String errorNo, String errorMsg) {
        if (null == ds) {
            ds = DatasetService.getInstace().getDataset();
            ds.setDatasetName("result");
        }
        if (ds != null && (ds.getDatasetName() == null || "null".equalsIgnoreCase(ds.getDatasetName()) || "".equals(ds.getDatasetName()))) {
            ds.setDatasetName("result");    //默认设置为result
        }
        IDatasets dss = new CommonDatasets();
        dss.putDataset(ds);
        setResponseDataset(request, response, dss, errorNo, errorMsg);
    }

    /**
     * 根据datasets返回对应的json数据格式
     * 约定返回的json结构为如下：<br>
     * { dataSetResult:[ //转换的第一个结果集 {data:[json list],totalCount : 100},
     * //转换的第二个结果集 {data:[json list],totalCount : 100}, ...... {data:[json
     * list],totalCount : 100} ], //cep返回号 returnCode:, //cep返回的错误号 errorNo:,
     * //cep返回的错误信息 errorInfo: , //辅助字段 ... }<br>
     * 默认控件取dataSetResult数组中的第一条的值作为dataSet装载的数据。<br>
     * 注意：IDataset转化时默认都加入'totalCount"<br>
     *
     * @param response
     * @param dss
     * @throws IOException
     */
    protected void setResponseDataset(HttpServletRequest request, HttpServletResponse response, IDatasets dss, String errorNo, String errorMsg) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0L);
        if(null == request){
            throw new BaseException(SysErr.E_NULL_POINTER, "请求为空");
        }
        String json = "";
        StringBuffer tempBuffer = new StringBuffer();
        tempBuffer.append("{ \"dataSetResult\" : [");
        tempBuffer.append(CommonConstants.CRLF);
        try {
            // 是否配置了添加叶子属性标识
            boolean hasLeafAttribute = (request
                    .getParameter("hasLeafAttribute") != null) ? Boolean
                    .valueOf(request.getParameter("hasLeafAttribute")) : false;
            // dataset的数据类型
            String dataType = request.getParameter("_type");
            // 树使用的参数
            String _mapping = request.getParameter("mapping");
            // 表示IDataset是否转换为map
            boolean isMap = (request.getParameter("isMap") != null) ? Boolean
                    .valueOf(request.getParameter("isMap")) : false;
            // 树使用的参数
            String rootId = request.getParameter("_rootId");
            if (!DataUtil.isNullStr(rootId)) {
                rootId = request.getParameter("node");
            }
            // 如果返回OK
            if (SysErr.E_SUCCESS.equals(errorNo)) {
                IDatasets result = dss;
                if (result == null) {
                    logger.error("返回结果数据空!");
                } else {
                    if (dataType != null && dataType.equals("treeOutput")) {
                        result2Tree(result, dataType, tempBuffer,
                                _mapping, rootId, hasLeafAttribute);
                    } else {
                        result2List(result, dataType, tempBuffer,
                                isMap);
                    }
                }
            } else {
                IDatasets result = dss;
                if (result == null) {
                    logger.error("返回结果数据空!");
                } else {
                    result2List(result, dataType, tempBuffer, isMap);
                }

            }
            tempBuffer.append(CommonConstants.CRLF);
            tempBuffer.append("],");
            tempBuffer.append(CommonConstants.CRLF);
            appendEventInfo(tempBuffer, errorNo, errorMsg);
            json = tempBuffer.toString();
        } catch (Exception e) {
            logger.error(SysErr.E_DEFAULT, e);
            String message = "{\"dataSetResult\":[]," + "\"returnCode\" : -1,"
                    + "\"errorInfo\" :'" + e.getMessage() + "'}";
            try {
                response.getWriter().write(message);
            } catch (IOException e1) {
                System.out.println("出现异常");
            }

        }
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
                System.out.println("出现异常");
            }
    }

    /**
     * 追加IEvent中的信息(错误号/错误信息等)
     *
     * @param tempBuffer
     * @param IEvent
     */
    private void appendEventInfo(StringBuffer tempBuffer, String errorNo,
                                 String errorMsg) {
        tempBuffer.append("\"errorNo\" : ");
        tempBuffer.append(escapingString(errorNo));
        tempBuffer.append(",");
        tempBuffer.append(CommonConstants.CRLF);
        tempBuffer.append("\"errorInfo\" : ");
        tempBuffer.append(escapingString(errorMsg));
        tempBuffer.append(",");
        tempBuffer.append(CommonConstants.CRLF);
        tempBuffer.append("\"returnCode\" : ");
        tempBuffer.append(escapingString(errorNo));
        tempBuffer.append(",");
        tempBuffer.append(CommonConstants.CRLF);
        tempBuffer.append("\"msg_type\" : ");
        if (SysErr.E_SUCCESS.equals(errorNo)) {
            tempBuffer.append(escapingString("success"));
        } else {
            tempBuffer.append(escapingString("error"));
        }
        tempBuffer.append(",");
        tempBuffer.append(CommonConstants.CRLF);
        tempBuffer.append("\"message\" : ");
        tempBuffer.append(escapingString(errorMsg));
        tempBuffer.append(CommonConstants.CRLF);
        tempBuffer.append("}");

    }

    /**
     * 将结果转换为json字符串
     *
     * @param map
     * @param dataType
     * @param object
     * @param isMap
     * @return
     * @throws Exception
     */
    public String result2JsonStr(Object result, String dataType, String mapping, boolean isMap) throws Exception {
        String json = "";
        if (jsonServiceFactory != null) {
            JsonService jsonService = jsonServiceFactory.getJsonService();
            if (dataType != null && dataType.equals("treeOutput")) {
                json = jsonService.parseTree(result, mapping);
            } else {
                if (!isMap) {
                    json = jsonService.parse(result);
                } else {
                    json = jsonService.parseDataset((IDataset) result, isMap);
                }

            }
        } else {
            logger.error(ErrMsg.get(SysErr.E_JSON_RESULT));
            throw new BaseException(SysErr.E_JSON_RESULT);
        }
        return json;
    }

    /**
     * 将结果集中的dataSet转换为普通json
     *
     * @param result
     * @param dataType
     * @param tempBuffer
     * @param isMap
     * @throws Exception
     */
    public void result2List(IDatasets result, String dataType,
                            StringBuffer tempBuffer, boolean isMap) throws Exception {

        long sTime = System.nanoTime();
        int datasetCount = result.getDatasetCount();

        for (int i = 0; i < datasetCount; i++) {
            IDataset ds = result.getDataset(i);
            String dsJson = "";
            if (!isMap) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("totalCount", ds.getTotalCount());
                map.put("data", ds.getRowCount() <= 0 ? "[]" : ds);
                map.put("dataSetName", ds.getDatasetName());
                dsJson = result2JsonStr(map, dataType, null, isMap);
            } else {
                dsJson = result2JsonStr(ds, dataType, null, isMap);
            }
            if (i > 0) {
                tempBuffer.append(",");
                tempBuffer.append(CommonConstants.CRLF);
            }
            tempBuffer.append(dsJson);
        }
        long eTime = System.nanoTime();
        logger.debug("转换json用时：" + (eTime - sTime));

    }

    /**
     * dataSet转换为树json
     *
     * @param result
     * @param dataType
     * @param tempBuffer
     * @param mapping
     * @param root
     * @throws Exception
     */
    public void result2Tree(IDatasets result, String dataType,
                            StringBuffer tempBuffer, String mapping, String root)
            throws Exception {
        long sTime = System.nanoTime();

        String pidColName = null;
        String idColName = null;
        Map fieldNameMapping = JsonServiceUtil.setFieldMapping(mapping);

        Set<Entry> set = fieldNameMapping.entrySet();
        for (Entry e : set) {
            if (e.getValue().equals("pid")) {
                pidColName = (String) e.getKey();
            } else if (e.getValue().equals("id")) {
                idColName = (String) e.getKey();
            }

        }
        int datasetCount = result.getDatasetCount();

        if (datasetCount >= 1) {
            IDataset ds = result.getDataset(0);
            Map<String, Object> treeMap = this.bivariateTableToTree(ds, root,
                    pidColName, idColName);
            List treeList = new ArrayList();
            if (treeMap != null) {
                treeList = (List) treeMap
                        .get(CommonConstants.TREE_NODE_ATTRIBUTE_CHILDREN);
            }
            long eTime = System.nanoTime();
            logger.debug("转换树的总耗时：" + (eTime - sTime));

            long ssTime = System.nanoTime();
            String dsJson = this.result2JsonStr(treeList, dataType, mapping, false);
            dsJson = "{ \"data\" : " + dsJson + ",\r\n \"dataSetName\" : \"" + ds.getDatasetName() + "\"\n}";

            tempBuffer.append(dsJson);
            long eeTime = System.nanoTime();
            logger.debug("转json的总耗时：" + (eTime - eeTime));
        }
    }

    private void result2Tree(IDatasets result, String dataType,
                             StringBuffer tempBuffer, String _mapping, String rootId,
                             boolean hasLeafAttribute) {
        // TODO Auto-generated method stub

    }

    /**
     * 转换树
     *
     * @param ds
     * @param root
     * @param pidColName
     * @param idColName
     * @return
     */
    public Map<String, Object> bivariateTableToTree(IDataset ds, String root,
                                                    String pidColName, String idColName) {
        if (root == null)
            throw new BaseException(SysErr.E_IN_NULL, "root");
        if (pidColName == null)
            throw new BaseException(SysErr.E_IN_NULL, "pidColName");
        if (idColName == null)
            throw new BaseException(SysErr.E_IN_NULL, "idColName");
        List<Map> list = new ArrayList<Map>();
        Map<String, Object> rootNode = new HashMap<String, Object>();
        rootNode.put(idColName, root);
        list.add(rootNode);

        Map<String, Integer> id_IndexMap = new HashMap<String, Integer>();
        id_IndexMap.put(root, 0);

        int columnCount = ds.getColumnCount();
        // first ergod ,get all nodes in list , 设置id_index map
        ds.beforeFirst();
        int cnt = 1;
        while (ds.hasNext()) {
            ds.next();
            Map<String, Object> map = new HashMap<String, Object>();
            for (int col = 1; col <= columnCount; col++) {
                String columnName = ds.getColumnName(col);
                Object value = null;
                char columnType = ds.getColumnType(col);
                switch (columnType) {
                    case DatasetColumnType.DS_INT:
                        value = ds.getLong(columnName);
                        break;
                    case DatasetColumnType.DS_DOUBLE:
                        value = ds.getDouble(columnName);
                        break;
                    case DatasetColumnType.DS_LONG:
                        value = ds.getLong(columnName);
                        break;
                    case DatasetColumnType.DS_STRING:
                        value = ds.getString(columnName);
                        break;
                    case DatasetColumnType.DS_STRING_ARRAY:
                        value = ds.getStringArray(columnName);
                        break;
                    case DatasetColumnType.DS_BYTE_ARRAY:
                        value = ds.getByteArray(columnName);
                        break;
                    default:
                        value = ds.getString(columnName);
                        break;
                }

                map.put(columnName, value);
                if (columnName.equals(idColName)) {
                    id_IndexMap.put("" + value, cnt++);
                }
            }
            list.add(map);
        }
        try {
            for (int index = 1; index < list.size(); index++) {
                Map<String, Object> node = list.get(index);
                String pidValue = node.get(pidColName).toString();
                ////?????修改20110212
                if (!((node.get(idColName)).toString().equals(root))) {
                    Integer pIndex = id_IndexMap.get(pidValue);
                    if (pIndex == null) {
                        continue;
                    }
                    Map<String, Object> pNode = list.get(pIndex);
                    if (!pNode
                            .containsKey(CommonConstants.TREE_NODE_ATTRIBUTE_CHILDREN)) {
                        pNode.put(CommonConstants.TREE_NODE_ATTRIBUTE_CHILDREN,
                                new ArrayList());
                    }
                    ArrayList childrenList = (ArrayList) pNode
                            .get(CommonConstants.TREE_NODE_ATTRIBUTE_CHILDREN);
                    childrenList.add(node);

                }
            }
            int rootIndex = id_IndexMap.get(root);
            Map<String, Object> rootMap = list.get(rootIndex);
            return rootMap;
        } catch (Exception e) {
            throw new BaseException(SysErr.E_TABLE_TO_TREE, e);
        }
    }

    /**
     * 二维集合转换树
     *
     * @param ds
     * @param root
     * @param pidColName
     * @param idColName
     * @return
     */
    public Map<String, Object> bivariateTableToTree(IDataset ds, String root,
                                                    String pidColName, String idColName, boolean hasLeafAttribute) {
        if (root == null)
            throw new BaseException(SysErr.E_IN_NULL, "root");
        if (pidColName == null)
            throw new BaseException(SysErr.E_IN_NULL, "pidColName");
        if (idColName == null)
            throw new BaseException(SysErr.E_IN_NULL, "idColName");
        List<Map> list = new ArrayList<Map>();
        Map<String, Object> rootNode = new HashMap<String, Object>();
        rootNode.put(idColName, root);
        list.add(rootNode);

        Map<String, Integer> id_IndexMap = new HashMap<String, Integer>();
        id_IndexMap.put(root, 0);

        int columnCount = ds.getColumnCount();
        // first ergod ,get all nodes in list , 设置id_index map
        ds.beforeFirst();
        int cnt = 1;
        while (ds.hasNext()) {
            ds.next();
            Map<String, Object> map = new HashMap<String, Object>();
            for (int col = 1; col <= columnCount; col++) {
                String columnName = ds.getColumnName(col);
                Object value = null;
                char columnType = ds.getColumnType(col);
                switch (columnType) {
                    case DatasetColumnType.DS_INT:
                        value = ds.getLong(columnName);
                        break;
                    case DatasetColumnType.DS_DOUBLE:
                        value = ds.getDouble(columnName);
                        break;
                    case DatasetColumnType.DS_LONG:
                        value = ds.getLong(columnName);
                        break;
                    case DatasetColumnType.DS_STRING:
                        value = ds.getString(columnName);
                        break;
                    case DatasetColumnType.DS_STRING_ARRAY:
                        value = ds.getStringArray(columnName);
                        break;
                    case DatasetColumnType.DS_BYTE_ARRAY:
                        value = ds.getByteArray(columnName);
                        break;
                    default:
                        value = ds.getString(columnName);
                        break;
                }

                map.put(columnName, value);
                if (columnName.equals(idColName)) {
                    id_IndexMap.put("" + value, cnt++);
                }
            }
            list.add(map);
        }
        try {
            for (int index = 1; index < list.size(); index++) {
                Map<String, Object> node = list.get(index);
                String pidValue = node.get(pidColName).toString();
                ////?????修改20110212
                if (!((node.get(idColName).toString()).equals(root))) {
                    Integer pIndex = id_IndexMap.get(pidValue);
                    if (pIndex == null) {
                        continue;
                    }
                    Map<String, Object> pNode = list.get(pIndex);
                    if (!pNode
                            .containsKey(CommonConstants.TREE_NODE_ATTRIBUTE_CHILDREN)) {
                        pNode.put(CommonConstants.TREE_NODE_ATTRIBUTE_CHILDREN,
                                new ArrayList());
                    }
                    ArrayList childrenList = (ArrayList) pNode
                            .get(CommonConstants.TREE_NODE_ATTRIBUTE_CHILDREN);
                    childrenList.add(node);

                }
            }
            if (hasLeafAttribute) {
                //添加叶子属性 20110302
                addLeafAttribute(list);
            }
            //获得根
            int rootIndex = id_IndexMap.get(root);
            Map<String, Object> rootMap = list.get(rootIndex);
            return rootMap;
        } catch (Exception e) {
            throw new BaseException(SysErr.E_TABLE_TO_TREE, e);
        }
    }

    private void addLeafAttribute(List<Map> list) {
        //add leaf attribute
        for (int index = 1; index < list.size(); index++) {
            Map<String, Object> node = list.get(index);
            if (node.containsKey(CommonConstants.TREE_NODE_ATTRIBUTE_CHILDREN)) {
                List childs = (List) node.get(CommonConstants.TREE_NODE_ATTRIBUTE_CHILDREN);
                if (childs.size() > 0) {
                    continue;
                } else {
                    node.put("leaf", true);
                }
            } else {
                node.put("leaf", true);
            }

        }
    }

    /**
     * 转义字符串中的特殊字符
     *
     * @param obj
     * @return
     */
    private String escapingString(String obj) {
        if (obj == null) {
            return null;
        } else {
            CharacterIterator it = new StringCharacterIterator(obj.trim());
            StringBuilder build = new StringBuilder();
            build.append("\"");
            for (char c = it.first(); c != CharacterIterator.DONE; c = it
                    .next()) {
                if (c == '"') {
                    build.append("\\\"");
                } else if (c == '\\') {
                    build.append("\\\\");
                } else if (c == '/') {
                    build.append("\\/");
                } else if (c == '\b') {
                    build.append("\\b");
                } else if (c == '\f') {
                    build.append("\\f");
                } else if (c == '\n') {
                    build.append("\\n");
                } else if (c == '\r') {
                    build.append("\\r");
                } else if (c == '\t') {
                    build.append("\\t");
                } else {
                    build.append(c);
                }
            }
            build.append("\"");
            return build.toString();
        }
    }

    /**
     * 参数绑定异常
     */
    @ExceptionHandler({BindException.class, ConstraintViolationException.class, ValidationException.class})
    public String bindException() {
        return "error/400";
    }

    /**
     * 授权登录异常
     */
    @ExceptionHandler({AuthenticationException.class})
    public String authenticationException() {
        return "error/403";
    }

    /**
     * 初始化数据绑定
     * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
     * 2. 将字段中Date类型转换为String类型
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtil.parseDate(text));
            }
//			@Override
//			public String getAsText() {
//				Object value = getValue();
//				return value != null ? DateUtil.formatDateTime((Date)value) : "";
//			}
        });
    }


    /**
     * filePath 相对web-content/export
     * 路径分隔符请使用File.pathSeparator
     * 可以有多层目录    0.xls  file1/1.xls   file1/file2/2.xls
     *
     * @param response
     * @param filePath
     * @throws IOException
     * @throws InvalidFormatException
     */
    protected String downExcelFile(HttpServletResponse response, String filePath) throws Exception {
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        InputStream ins = null;

        filePath = ParamUtil.getConfig("web.path") + "/export/" + filePath;

        int index = filePath.lastIndexOf(".");
        String suffix = "";
        if (index != -1) {
            suffix = filePath.substring(index + 1);
        }
        String contentType = "";

        if ("doc".equals(suffix) || "docx".equals(suffix)) {
            contentType = "application/msword;charset=UTF-8";
        } else if ("xls".equals(suffix) || "xlsx".equals(suffix)) {
            contentType = "application/x-xls;charset=UTF-8";
        } else if ("ppt".equals(suffix) || "pptx".equals(suffix)) {
            contentType = "application/x-ppt;charset=UTF-8";
        } else if ("pdf".equals(suffix)) {
            contentType = "application/pdf";
        } else if ("xml".equals(suffix)) {
            contentType = "text/xml";
        } else {
            contentType = "application/octet-stream";   //没有后缀或其他后缀
        }

        try {
            ins = new FileInputStream(new File(filePath));
            response.setContentType(contentType);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + Encodes.urlEncode(DateUtil.getDate("yyMMddhhmmss") + "_" + filePath.substring(filePath.lastIndexOf("/") + 1)));
            out = new BufferedOutputStream(response.getOutputStream());
            in = new BufferedInputStream(ins);
            byte[] buffer = new byte[4096];
            int n = -1;
            while ((n = in.read(buffer, 0, 4096)) > -1) {
                out.write(buffer, 0, n);
            }
            out.flush();
            response.flushBuffer();
            return null;
        } finally {
            if (ins != null) {
                ins.close();
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }

    }

    protected String downExcelFile(HttpServletResponse response, String filePath, String fileName) throws Exception {

        if (fileName == null) {
            downExcelFile(response, filePath);
            return null;
        }

        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        InputStream ins = null;

        filePath = ParamUtil.getConfig("web.path") + "/export/" + filePath;

        int index = filePath.lastIndexOf(".");
        String suffix = "";
        if (index != -1) {
            suffix = filePath.substring(index + 1);
        }
        String contentType = "";

        if ("doc".equals(suffix) || "docx".equals(suffix)) {
            contentType = "application/msword;charset=UTF-8";
        } else if ("xls".equals(suffix) || "xlsx".equals(suffix)) {
            contentType = "application/x-xls;charset=UTF-8";
        } else if ("ppt".equals(suffix) || "pptx".equals(suffix)) {
            contentType = "application/x-ppt;charset=UTF-8";
        } else if ("pdf".equals(suffix)) {
            contentType = "application/pdf";
        } else if ("xml".equals(suffix)) {
            contentType = "text/xml";
        } else {
            contentType = "application/octet-stream";   //没有后缀或其他后缀
        }

        try {
            ins = new FileInputStream(new File(filePath));
            response.setContentType(contentType);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + Encodes.urlEncode(fileName));
            out = new BufferedOutputStream(response.getOutputStream());
            in = new BufferedInputStream(ins);
            byte[] buffer = new byte[4096];
            int n = -1;
            while ((n = in.read(buffer, 0, 4096)) > -1) {
                out.write(buffer, 0, n);
            }
            out.flush();
            response.flushBuffer();
            return null;
        } finally {
            if (ins != null) {
                ins.close();
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    public void setViewModel(HttpServletRequest request, Object viewModel) {
    	request.setAttribute("viewModel", viewModel);
	}

}
