package com.adtec.framework.ide.action;

import com.adtec.framework.common.util.ClassUtil;
import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.ide.abstractutil.AbstractCstLoadingUtil;
import com.adtec.framework.ide.abstractutil.AbstractCstNameChangeUtil;
import com.adtec.framework.ide.abstractutil.AbstractJsLoadingUtil;
import com.adtec.framework.ide.abstractutil.AbstractJsNameChangeUtil;
import com.adtec.framework.ide.po.form;
import com.adtec.framework.ide.service.StructsNameService;
import com.adtec.framework.ide.utilImpl.CstLoadingUtilImpl;
import com.adtec.framework.ide.utilImpl.CstNameChangeUtilImpl;
import com.adtec.framework.ide.utilImpl.JsLoadingUtilImpl;
import com.adtec.framework.ide.utilImpl.JsNameChangeUtilImpl;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.web.Servlets;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源导航处理器
 */
@Controller
@RequestMapping(value = "b_ide/ide")
public class ResourceNavController extends IdeBaseController {
    private AbstractJsNameChangeUtil jsChange = new JsNameChangeUtilImpl();
    private AbstractCstNameChangeUtil cstChange = new CstNameChangeUtilImpl();
    public final static String VIEW_START = "<!-- view start -->";
    public final static String VIEW_END = "<!-- view end -->";

    // JS工具类
    private AbstractJsLoadingUtil jsUtil = new JsLoadingUtilImpl();
    // CST工具类
    private AbstractCstLoadingUtil cstUtil = new CstLoadingUtilImpl();

    //	@Autowired
    private StructsNameService structsNameService;

    private static FileFilter filter = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            String name = pathname.getName();
            return (pathname.isFile() && name.substring(name.indexOf(".") + 1).equals("jsp"))
                    || (pathname.isDirectory() && !name.equals(".svn"));
        }
    };

    /**
     * IDE获取所有JSP页面列表
     *
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "getAllJspName")
    public void getAllJspName(HttpServletRequest request, HttpServletResponse response) {
        try {
            String jspPath = ParamUtil.getJspPath();
            jspPath = FileUtil.path(jspPath);
            File file = new File(jspPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            // File file=new File("F://aa");
            String filePath = FileUtil.path(file.toString());
            String root = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
            File[] list = file.listFiles(filter);
            StringBuffer str = new StringBuffer("{name:'" + root + "',open:'true',children:[");
            ShowAllJspNameFile(list, str);
            str.append("]}");
            this.returnResultJson(response, str.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ShowAllJspNameFile(File[] list, StringBuffer str) {
        if (null != list && list.length > 0) {
            for (File e : list) {
                String filePath = FileUtil.path(e.toString());
                String aa = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
                if (e.isDirectory()) {
                    str.append("{name:'").append(aa).append("',open:'true',").append("children:[");
                    ShowAllJspNameFile(e.listFiles(filter), str);
                    str.append("]},");
                } else if (e.isFile()) {
                    str.append("{name:'").append(aa).append("'},");
                }
            }
        } else {
            str.append("[");
        }
        str.deleteCharAt(str.length() - 1);
    }

    @ResponseBody
    @RequestMapping(value = "addJspName")
    public void addJspName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 转换请求参数
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String path = reqDs.getString("path"); // 文件路径
        String name = reqDs.getString("name"); // 文件名称
        String jspPath = ParamUtil.getJspPath();
        String path1 = "";
        String msg = "";
        String filePath = FileUtil.path(path.toString());
        if (filePath.contains("/")) {
            path1 = filePath.substring(filePath.toString().indexOf("/"), filePath.length());
        }
        File file = new File(FileUtil.path(jspPath + path1 + "/" + name));
        if (!file.exists()) {
            if (FileUtil.createFile(file.getPath())) {
                msg = "SUCCESS";
            } else {
                msg = "Error";
            }
        }
        this.returnResultJson(response, msg);
    }

    @ResponseBody
    @RequestMapping(value = "addCstName")
    public void addCstName(HttpServletRequest request, HttpServletResponse response) {
        // 转换请求参数
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String path = reqDs.getString("path"); // 文件路径
        String name = reqDs.getString("name"); // 文件名称
        String jspPath = ParamUtil.getJspPath();
        String path1 = "";
        String msg = "";
        String filePath = FileUtil.path(path.toString());
        if (filePath.contains("/")) {
            path1 = filePath.substring(filePath.toString().indexOf("/"), filePath.length());
        }
        File file = new File(FileUtil.path(jspPath + path1 + "/" + name));
        if (!file.exists()) {
            if (FileUtil.createFile(file.getPath())) {
                msg = "SUCCESS";
            } else {
                msg = "Error";
            }
        }
        this.returnResultJson(response, msg);
    }

    @ResponseBody
    @RequestMapping(value = "addJScrptName")
    public void addJScrptName(HttpServletRequest request, HttpServletResponse response) {
        // 转换请求参数
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String path = reqDs.getString("path"); // 文件路径
        String name = reqDs.getString("name"); // 文件名称
        String jsPath = ParamUtil.getJsPath();
        String path1 = "";
        String msg = "";
        String filePath = FileUtil.path(path.toString());
        if (filePath.contains("/")) {
            path1 = filePath.substring(filePath.toString().indexOf("/"), filePath.length());
        }
        File file = new File(FileUtil.path(jsPath + path1 + "/" + name));
        if (!file.exists()) {
            if (FileUtil.createFile(file.getPath())) {
                msg = "SUCCESS";
            } else {
                msg = "Error";
            }
        }
        this.returnResultJson(response, msg);
    }

    @ResponseBody
    @RequestMapping(value = "removeJspName")
    public void removeJspName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 转换请求参数
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String path = reqDs.getString("path"); // 文件路径
        String msg = "";
        String contextPath = request.getRealPath(ParamUtil.getJspPath().split(ParamUtil.getConfig("web.path"))[1]);
        String filePath = FileUtil.path(path.toString());
        String path1 = filePath.substring(filePath.indexOf("/"), filePath.length());
        String jspPath = ParamUtil.getJspPath();
        File file = new File(FileUtil.path(jspPath + path1));
        File filemetadata = new File(FileUtil.path(contextPath + path1));
        log.info("jspPath=" + file.getPath());
        log.info("jspMetadataPath=" + filemetadata.getPath());
        if (file.exists()) {
            file.delete();
            msg = "SUCCESS";
        } else {
            msg = "ERROR";
        }
        if (filemetadata.exists()) {
            filemetadata.delete();
            msg = "SUCCESS";
        } else {
            msg = "ERROR";
        }
        this.returnResultJson(response, msg);
    }

    /**
     * 删除JS
     */
    @ResponseBody
    @RequestMapping(value = "removeJScript")
    public void removeJScript(HttpServletRequest request, HttpServletResponse response) {
        // 转换请求参数
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String path = reqDs.getString("path"); // 文件路径
        String msg = "";
        String contextPath = request.getRealPath(ParamUtil.getJsPath().split(ParamUtil.getConfig("web.path"))[1]);
        String filePath = FileUtil.path(path.toString());
        String path1 = filePath.substring(filePath.indexOf("/"), filePath.length());
        String jsPath = ParamUtil.getJsPath();
        File file = new File(FileUtil.path(jsPath + path1));
        File filemetadata = new File(FileUtil.path(contextPath + path1));
        log.info("jsPath=" + file.getPath());
        log.info("jsMetadataPath=" + filemetadata.getPath());
        if (file.exists()) {
            file.delete();
            msg = "SUCCESS";
        } else {
            msg = "ERROR";
        }
        if (filemetadata.exists()) {
            filemetadata.delete();
            msg = "SUCCESS";
        } else {
            msg = "ERROR";
        }
        this.returnResultJson(response, msg);
    }

    /**
     * 删除客户化文件cst
     */
    @ResponseBody
    @RequestMapping(value = "removeCst")
    public void removeCst(HttpServletRequest request, HttpServletResponse response) {
        // 转换请求参数
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String path = reqDs.getString("path"); // 文件路径
        String msg = "";
        String contextPath = request.getRealPath("jsp");
        String filePath = FileUtil.path(path.toString());
        String path1 = filePath.substring(filePath.indexOf("/"), filePath.length());
        String jspPath = ParamUtil.getJspPath();
        File file = new File(FileUtil.path(jspPath + path1));
        File filemetadata = new File(FileUtil.path(contextPath + path1));
        log.info("cstPath=" + file.getPath());
        log.info("cstMetadataPath=" + filemetadata.getPath());
        if (file.exists()) {
            file.delete();
            msg = "SUCCESS";
        } else {
            msg = "ERROR";
        }
        if (filemetadata.exists()) {
            filemetadata.delete();
            msg = "SUCCESS";
        } else {
            msg = "ERROR";
        }
        this.returnResultJson(response, msg);
    }

    @ResponseBody
    @RequestMapping(value = "renameJspName")
    public void renameJspName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 转换请求参数
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String path = reqDs.getString("path"); // 文件路径
        String name = reqDs.getString("name"); // 文件名称
        String msg = "";
        path = FileUtil.path(path.toString());
        name = FileUtil.path(name.toString());
        String path1 = path.substring(path.toString().indexOf("/"), path.toString().length());
        String path2 = name.substring(name.toString().indexOf("/"), name.toString().length());
        String jspPath = ParamUtil.getJspPath();
        File file = new File(jspPath + path1);
        File f = new File(jspPath + path2);

        if (file.isDirectory()) {
            if (path2.contains(".jsp")) {
                file.delete();
                try {
                    f.createNewFile();
                    msg = "SUCCESS";
                } catch (IOException e) {
                    msg = "Error";
                }
            } else {
                file.renameTo(f);
            }
        } else if (file.isFile()) {
            if (path2.endsWith(".jsp")) {
                file.renameTo(f);

            } else {
                file.delete();
                f.mkdir();
            }
        } else {
            msg = "Error";
        }
        this.returnResultJson(response, msg);
    }

    /**
     * JS重名命
     */
    @ResponseBody
    @RequestMapping(value = "renameJScriptName")
    public void renameJScriptName(HttpServletRequest request, HttpServletResponse response) {
        // 转换请求参数
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String path = reqDs.getString("path"); // 文件路径
        String name = reqDs.getString("name"); // 文件名称
        String msg = "";
        path = FileUtil.path(path.toString());
        name = FileUtil.path(name.toString());
        // 原来文件
        String path1 = path.substring(path.toString().indexOf("/"), path.toString().length());
        // 新文件
        String path2 = name.substring(name.toString().indexOf("/"), name.toString().length());

        String jsPath = FileUtil.path(ParamUtil.getJsPath());
        String jspPath = FileUtil.path(ParamUtil.getJspPath());
        String oldPathUrl = path1;
        String newPathUrl = path2;
        File oldFile = new File(jsPath + path1);

        File newFile = new File(jsPath + path2);
        try {
            if (oldFile.exists()) {
                if (oldFile.isDirectory()) {
                    if (path2.contains(".js")) {
                        oldFile.delete();
                        newFile.createNewFile();
                        msg = "SUCCESS";
                    } else {
                        oldFile.renameTo(newFile);
                    }
                } else if (oldFile.isFile()) {
                    if (path2.endsWith(".js")) {
                        oldFile.renameTo(newFile);
                    } else {
                        oldFile.delete();
                        newFile.mkdir();
                    }
                    Map<String, File> flagFile = jsChange.JsNameChangeUtil(oldPathUrl, newPathUrl, jspPath);
                    int i = flagFile.size();
                    if (i == 2) {
                        File tmpFile = flagFile.get("tmpFile");
                        File nwFile = flagFile.get("newFile");
                        nwFile.delete();
                        tmpFile.renameTo(nwFile);
                    } else {
                        throw new Exception(newPathUrl + "，该JSP中引用的JS为重写！");
                    }
                } else {
                    msg = "Error";
                }
            } else {
                newFile.createNewFile();
                msg = "SUCCESS";
            }

        } catch (IOException e) {
            msg = "Error";
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.returnResultJson(response, msg);

    }

    /**
     * 客户化文件cst重名命
     */
    @ResponseBody
    @RequestMapping(value = "renameCstName")
    public void renameCstName(HttpServletRequest request, HttpServletResponse response) {
        // 转换请求参数
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String path = reqDs.getString("path"); // 文件路径
        String name = reqDs.getString("name"); // 文件名称
        String msg = "";
        path = FileUtil.path(path.toString());
        name = FileUtil.path(name.toString());
        // 原来文件
        String path1 = path.substring(path.toString().indexOf("/"), path.toString().length());
        // 新文件
        String path2 = name.substring(name.toString().indexOf("/"), name.toString().length());

        String jspPath = FileUtil.path(ParamUtil.getJspPath());
        String oldPathUrl = path1;
        String newPathUrl = path2;

        File oldFile = new File(jspPath + path1);

        File newFile = new File(jspPath + path2);
        try {
            if (oldFile.exists()) {
                if (oldFile.isDirectory()) {
                    if (path2.contains(".cst")) {
                        oldFile.delete();
                        newFile.createNewFile();
                        msg = "SUCCESS";
                    } else {
                        oldFile.renameTo(newFile);
                    }
                } else if (oldFile.isFile()) {
                    if (path2.endsWith(".cst")) {
                        oldFile.renameTo(newFile);
                    } else {
                        oldFile.delete();
                        newFile.mkdir();
                    }

                    Map<String, File> flagFile = cstChange.CstNameChangeUtil(oldPathUrl, newPathUrl, jspPath);
                    int i = flagFile.size();
                    if (i == 2) {
                        File tmpFile = flagFile.get("tmpFile");
                        File nwFile = flagFile.get("newFile");
                        nwFile.delete();
                        tmpFile.renameTo(nwFile);
                    } else {
                        throw new Exception(newPathUrl + "，该JSP中引用的客户化代码为重写！");
                    }

                } else {
                    msg = "Error";
                }
            } else {
                newFile.createNewFile();
                msg = "SUCCESS";
            }

        } catch (IOException e) {
            msg = "Error";
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.returnResultJson(response, msg);

    }

    @ResponseBody
    @RequestMapping(value = "jspGet")
    public void jspGet(HttpServletRequest request, HttpServletResponse response) {
        // 转换请求参数
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String path = reqDs.getString("path"); // 文件路径
        try {
            String jspPath = ParamUtil.getJspPath();
            String fileName = jspPath + path;
            String jspFile = readFileByLines(fileName);
            HashMap<String, String> m = new HashMap<String, String>();
            m.put("jspPath", fileName.split(ParamUtil.getWebPath().substring(ParamUtil.getWebPath().lastIndexOf("/") + 1))[1]);
            m.put("jspFile", jspFile);
            this.returnResultJson(response, m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取后台Xml中action
     */
    @ResponseBody
    @RequestMapping(value = "getActionName")
    public void getActionName(HttpServletRequest request, HttpServletResponse response) {
        // 转换请求参数
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String xmlName = reqDs.getString("xmlName"); // 文件路径
        List<form> list = null;
        try {
            list = structsNameService.getActionName(xmlName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.setMsg("获取失败!请确定输入路径无误!");
        }
        this.returnResultJson(response, list, this.getMsg());
    }

    /**
     * 获取po类下面的变量名
     */
    @ResponseBody
    @RequestMapping(value = "getPoName")
    public void getPoName(HttpServletRequest request, HttpServletResponse response) {
        // 转换请求参数
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String path = reqDs.getString("path"); // 文件路径
        String value = "";
        List<form> list = new ArrayList<form>();
        Class cls = null;
        try {
            try {
                cls = Class.forName(path);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                this.setMsg("没有找到[" + path + "]类，请在form属性栏中重新设置[poSrc]");
            }
            // 20170709 mod by chenyl for 修改成通过封装的反正工具类获取
            Field[] fields = ClassUtil.getAccessibleFields(cls.newInstance());// 根据Class对象获得属性
            // 私有的也可以获得
            for (Field f : fields) {
                value = f.getName();
                form d = new form();
                d.setValue(value);
                d.setText(value);
                list.add(d);
            }
            if (list.isEmpty()) {
                this.setMsg("name为空，请在form属性栏中重新设置[poSrc]");
            }
            // this.setMsg("加载路径成功");
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.setMsg("加载路径失败！请在form属性栏中重新设置[poSrc]");
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.returnResultJson(response, list, this.getMsg());
    }

    /**
     * 预览生成的JSP文件
     */
    @ResponseBody
    @RequestMapping(value = "ideSave")
    public void ideSave(HttpServletRequest request, HttpServletResponse response) {
        // 转换请求参数
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String jspfile = reqDs.getString("jspfile"); // JSP文件内容
        String path = reqDs.getString("path"); // 文件路径
        try {
            fileUploads(jspfile, path);
        } catch (IOException e) {
            log.debug(e.getMessage());
        }
    }

    /**
     * 保存生成的JSP文件
     *
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "ideJspSave")
    public void ideJspSave(HttpServletRequest request, HttpServletResponse response) {
        // 转换请求参数
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String jspfile = reqDs.getString("jspfile"); // JSP文件内容
        String path = reqDs.getString("path"); // 文件路径
        String msg = "";
        try {
            jspFileUploads(jspfile, path);
            msg = "SUCCESS";
        } catch (BaseException e) {
            log.error(e.getMessage());
            msg = e.getMessage();
        }
        this.returnResultJson(response, msg);
    }

    /**##---------------CST文件处理----------------##**/
    /**
     * 获取CST文件内容
     */
    @ResponseBody
    @RequestMapping(value = "getCst")
    public void getCst(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("Method is getCst");
            // 转换请求参数
            IDataset reqDs = DatasetService.getInstace().getDataset(request);
            String path = reqDs.getString("path"); // 文件路径
            if (path == null || "".equals(path)) {
                log.error("path 为 空 !");
            }
            String cstContent = cstUtil.getCstLoading(path);
            if (cstContent != null) {
                this.getMap().put("cstContent", cstContent);
                this.getMap().put("msg", "SCUESS");
                this.returnMap2Json(response, getMap());
            } else {
                throw new Exception("getJsLoading() 返回异常！");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.getMap().put("msg", "ERROR");
            this.returnMap2Json(response, getMap());
        }
    }

    /**
     * 设置CST文件内容
     */
    @ResponseBody
    @RequestMapping(value = "setCst")
    public void setCst(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("Method is setCst");
            // 转换请求参数
            IDataset reqDs = DatasetService.getInstace().getDataset(request);
            String path = reqDs.getString("path"); // 文件路径
            String cstContent = reqDs.getString("cstContent"); // 脚本内容
            log.debug("cstContent=" + cstContent);
            boolean flag = cstUtil.setCstLoading(cstContent, path);
            if (flag == true) {
                this.getMap().put("msg", "success");
                this.returnMap2Json(response, getMap());
            } else {
                throw new Exception("保存客户化代码异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getMap().put("msg", "ERROR");
            this.returnMap2Json(response, getMap());
        }
    }

    /**##---------------JavaScript文件处理----------------##**/
    /**
     * 编辑JavaScript
     */
    @ResponseBody
    @RequestMapping(value = "setJScript")
    public void setJScript(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("Method is setJScript");
            // 转换请求参数
            IDataset reqDs = DatasetService.getInstace().getDataset(request);
            String path = reqDs.getString("path"); // 文件路径
            String jsContent = reqDs.getString("jsContent"); // 脚本内容

            boolean flag = jsUtil.setJsLoading(jsContent, path);
            // jsFileUploads(jsContent, path);
            if (flag == true) {
                this.getMap().put("msg", "success");
                this.returnMap2Json(response, getMap());
            } else {
                throw new Exception("保存JS异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.getMap().put("msg", "ERROR");
            this.returnMap2Json(response, getMap());
        }
    }

    /**
     * 获取js文件内容
     */
    @ResponseBody
    @RequestMapping(value = "getJScript")
    public void getJScript(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("Method is getJScript");
            // 转换请求参数
            IDataset reqDs = DatasetService.getInstace().getDataset(request);
            String path = reqDs.getString("path"); // 文件路径
            if (path == null || "".equals(path)) {
                //throw new Exception("path 为 空 !");
                log.error("path 为 空 !");
            }
            String jsContent = jsUtil.getJsLoading(path);
            // jsFileUploads(jsContent, path);
            if (jsContent != null) {
                this.getMap().put("jsContent", jsContent);
                this.getMap().put("msg", "SCUESS");
                this.returnMap2Json(response, getMap());
            } else {
                throw new Exception("getJsLoading() 返回异常！");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.getMap().put("msg", "ERROR");
            this.returnMap2Json(response, getMap());
        }
    }

    /**
     * 保存js文件
     *
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "saveScript")
    public void saveScript(HttpServletRequest request, HttpServletResponse response) {
        // 转换请求参数
        IDataset reqDs = DatasetService.getInstace().getDataset(request);
        String path = reqDs.getString("path"); // 文件路径
        String scriptStr = reqDs.getString("scriptStr"); // 脚本内容
        String contextPath = ParamUtil.getJsPath();
        String jsPath = contextPath + path.replace(".jsp", ".js");
        readFileByBytes(jsPath, scriptStr);
    }

    // 读取文件内容 并 对比内容 删除重复内容 保存
    private void readFileByBytes(String jsPath, String scriptStr) {
        String oldJsContent = "";
        String newJsContent = "";
        String readyjsContent = "$(document).ready(function() {});";
        System.out.println("scriptStr=[" + scriptStr + "]");
        FileReader fileReader = null;
        BufferedReader reader = null;
        FileWriter writerReady = null;
        BufferedWriter writerJs = null;
        FileWriter fileWriter = null;
        BufferedWriter writer = null;
        FileReader fileReader1 = null;
        BufferedReader reader1 = null;
        try {// 读取 js 中的 内容
            fileReader = new FileReader(jsPath);
            reader = new BufferedReader(fileReader);
            String line = null;
            if (null == reader) {
                throw new BaseException(SysErr.E_IO_ERROR, "js读取失败");
            }
            // 判断js文件内是否已经有内容
            if ((line = reader.readLine()) == null || (line = reader.readLine()) == "") {
                writerReady = new FileWriter(jsPath);
                writerJs = new BufferedWriter(writerReady);
                // 添加js初始化代码
                writerJs.write(readyjsContent);
                writerJs.flush();
                // 将新的脚本 写入 文件
                // 1.分割字符串--‘{’
                String[] strarray = readyjsContent.split("\\{");
                newJsContent = strarray[0] + "{" + "\n" + scriptStr + "\n"
                        + strarray[1];
                fileWriter = new FileWriter(jsPath);
                writer = new BufferedWriter(fileWriter);
                writer.write(newJsContent);
                writer.flush();
            } else {
                fileReader1 = new FileReader(jsPath);
                reader1 = new BufferedReader(fileReader1);
                if (null == reader1) {
                    throw new BaseException(SysErr.E_IO_ERROR, "js读取失败");
                }
                while ((line = reader1.readLine()) != null) {
                    oldJsContent += line + "\n";
                    System.out.println("oldJsContent=[" + oldJsContent + "]");
                }
                // 去除重复代码
                String[] newJs = scriptStr.split("\n");
                for (int j = 0; j < newJs.length; j++) {
                    oldJsContent = oldJsContent.replace(newJs[j], "");
                    System.out.println("newJs[j]=[" + newJs[j] + "], oldJsContent=["
                            + oldJsContent + "]");
                }
                StringBuilder sb = new StringBuilder(oldJsContent);
                System.out.println("old--sb=" + sb);
                sb.insert(30, "\n" + scriptStr);
                System.out.println("new--sb==" + sb);
                fileWriter = new FileWriter(jsPath);
                writer = new BufferedWriter(fileWriter);
                writer.write(sb.toString());
                writer.flush();
            }
        } catch (IOException e) {
                System.out.println("出现异常");
            } finally {
            if (null != reader1) {
                try {
                    reader1.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != fileReader1) {
                try {
                    fileReader1.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != fileReader) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != writerJs) {
                try {
                    writerJs.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != writerReady) {
                try {
                    writerReady.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != fileWriter) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
        }
    }

    /**
     * 预览文件上传到运行服务器
     *
     * @param content
     * @param jsPath
     * @throws IOException
     */
    private void fileUploads(String content, String jsPath) throws IOException {
        String js_Path = null;
        BufferedReader brbeg = null;
        BufferedReader brend = null;
        BufferedWriter tomcatPreviewbw = null;
        BufferedReader cstRw = null;
        InputStreamReader jspbeg = null;
        InputStreamReader jspend = null;
        try {
            log.debug("jspbeg=" + ParamUtil.getJSPBEG());
            jspbeg = new InputStreamReader(new FileInputStream(ParamUtil.getJSPBEG()), "UTF-8");
            brbeg = new BufferedReader(jspbeg);
            log.debug("jspend=" + ParamUtil.getJSPEND());
            jspend = new InputStreamReader(new FileInputStream(ParamUtil.getJSPEND()), "UTF-8");
            brend = new BufferedReader(jspend);
            String tomcatPreviewJspPath = Servlets.getRequest().getRealPath(ParamUtil.getPreviewJsp());
            log.debug("tomcatPreviewJsp=" + tomcatPreviewJspPath);
            File tomcatPreviewJsp = new File(tomcatPreviewJspPath);
            if (!tomcatPreviewJsp.exists()) {
                tomcatPreviewJsp.createNewFile();
            }
            tomcatPreviewbw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tomcatPreviewJsp), "UTF-8"));
            String str = null;
            js_Path = ParamUtil.getJsPath() + jsPath.replace(".jsp", ".js");
            js_Path = js_Path.split(ParamUtil.getConfig("web.path"))[1];
            js_Path = "<script type='text/javascript' charset='utf-8' src='<%=basePath%>" + js_Path + "'></script>";
            if (null == brbeg) {
                throw new BaseException(SysErr.E_IO_ERROR, "jspbeg读取失败");
            }
            while ((str = brbeg.readLine()) != null) {
                tomcatPreviewbw.write(str + "\n");
                if (str.startsWith("<!-- Self reference JS-->")) {
                    tomcatPreviewbw.write(js_Path);
                }
            }
            tomcatPreviewbw.write(content, 0, content.length());
            tomcatPreviewbw.write("\n");

            //获取CST放置路径
            String cstFilePath = ParamUtil.getJspPath() + jsPath.replace(".jsp", ".cst");
            log.debug("cstFilePath=" + cstFilePath);
            File cstFile = new File(cstFilePath);
            if (!cstFile.exists()) {
                FileUtil.createFile(cstFilePath);
            }

            cstRw = new BufferedReader(new InputStreamReader(new FileInputStream(cstFilePath), "UTF-8"));
            if (null == cstRw) {
                throw new BaseException(SysErr.E_IO_ERROR, "CST读取失败");
            }
            str = null;
            tomcatPreviewbw.write(CstLoadingUtilImpl.CST_BEG + "\n");
            while ((str = cstRw.readLine()) != null) {
                tomcatPreviewbw.write(str + "\n");
            }
            tomcatPreviewbw.write("\n" + CstLoadingUtilImpl.CST_END);
            if (null == brend) {
                throw new BaseException(SysErr.E_IO_ERROR, "jspend读取失败");
            }
            str = null;
            while ((str = brend.readLine()) != null) {
                tomcatPreviewbw.write(str + "\n");
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
                System.out.println("出现异常");
            } finally {
            if (null != brend) {
                try {
                    brend.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != jspend) {
                try {
                    jspend.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != brbeg) {
                try {
                    brbeg.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != jspbeg) {
                try {
                    jspbeg.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != cstRw) {
                try {
                    cstRw.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != tomcatPreviewbw) {
                try {
                    tomcatPreviewbw.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
        }
    }

    /**
     * 保存生成jsp文件
     *
     * @param content
     * @param path1
     * @throws Exception
     */
    private void jspFileUploads(String content, String path1) {
        /*String sourcePath=  this.getClass().getClassLoader().getResource("/").getPath().toString();*/
        BufferedReader brbeg = null;
        BufferedReader brend = null;
        BufferedWriter previewJspbw = null;
        BufferedReader cstRw = null;
        InputStreamReader jspbeg = null;
        InputStreamReader jspend = null;
        try {
            log.debug("jspbeg=" + ParamUtil.getJSPBEG());
            jspbeg = new InputStreamReader(new FileInputStream(ParamUtil.getJSPBEG()), "UTF-8");
            brbeg = new BufferedReader(jspbeg);
            log.debug("jspend=" + ParamUtil.getJSPEND());
            jspend = new InputStreamReader(new FileInputStream(ParamUtil.getJSPEND()), "UTF-8");
            brend = new BufferedReader(jspend);
            String jspPath = ParamUtil.getJspPath();
            String jsPath = ParamUtil.getJsPath();
            String filePath = jspPath + path1;
            String jsFilePath = jsPath + path1.replaceFirst("\\.jsp", "\\.js");
            File file = new File(filePath);
            File jsFile = new File(jsFilePath);
            log.debug("jsFilePath=" + jsFilePath);
            if (!jsFile.exists()) {
                FileUtil.createFile(jsFilePath);
            }
            if (!file.exists()) {
                FileUtil.createFile(filePath);
            }

            //获取JS放置路径
            jsFilePath = jsFilePath.split(ParamUtil.getConfig("web.path"))[1];
            previewJspbw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            if (null == brbeg) {
                throw new BaseException(SysErr.E_IO_ERROR, "jspbeg读取失败");
            }
            String str = null;
            while ((str = brbeg.readLine()) != null) {
                previewJspbw.write(str + "\n");
                if (str.startsWith("<!-- Self reference JS-->")) {
                    previewJspbw.write("<script type=\"text/javascript\" src=\"<%=basePath%>" + jsFilePath + "\" charset=\"utf-8\"></script>" + "\n");
                }
            }
            previewJspbw.write(content, 0, content.length());
            previewJspbw.write("\n");
            //获取CST放置路径
            String cstFilePath = filePath.replaceFirst("\\.jsp", "\\.cst");
            log.debug("cstFilePath=" + cstFilePath);
            File cstFile = new File(cstFilePath);
            if (!cstFile.exists()) {
                FileUtil.createFile(cstFilePath);
            }
            cstRw = new BufferedReader(new InputStreamReader(new FileInputStream(cstFilePath), "UTF-8"));
            if (null == cstRw) {
                throw new BaseException(SysErr.E_IO_ERROR, "CST读取失败");
            }
            str = null;
            previewJspbw.write(CstLoadingUtilImpl.CST_BEG + "\n");
            while ((str = cstRw.readLine()) != null) {
                previewJspbw.write(str + "\n");
            }
            previewJspbw.write("\n" + CstLoadingUtilImpl.CST_END);
            if (null == brbeg) {
                throw new BaseException(SysErr.E_IO_ERROR, "jspend读取失败");
            }
            str = null;
            while ((str = brend.readLine()) != null) {
                previewJspbw.write(str + "\n");
            }

        } catch (FileNotFoundException e) {
            throw new BaseException("file not found exception:" + e.getMessage());
        } catch (IOException e) {
            throw new BaseException("ioException:" + e.getMessage());
        } catch (Exception e) {
            throw new BaseException("其他异常:" + e.getMessage());
        } finally {
            if (null != brend) {
                try {
                    brend.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != jspend) {
                try {
                    jspend.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != brbeg) {
                try {
                    brbeg.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != jspbeg) {
                try {
                    jspbeg.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != cstRw) {
                try {
                    cstRw.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (null != previewJspbw) {
                try {
                    previewJspbw.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
        }
    }


    public static String readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        String aa = "";
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            if (null == reader) {
                throw new BaseException(SysErr.E_IO_ERROR, "文件读取失败");
            }
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                aa += tempString + "\n";
            }
            aa = replaceCST(aa);
            aa = aa.substring(aa.indexOf(VIEW_START) + VIEW_START.length(), aa.lastIndexOf(VIEW_END));
            reader.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
                System.out.println("出现异常");
            } catch (StringIndexOutOfBoundsException e) {
            aa = "";
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
        }

        return aa;
    }

    // 去掉客户化代码
    public static String replaceCST(String str) {
        String result = str;
        if (null != result && result.indexOf(CstLoadingUtilImpl.CST_BEG) > -1
                && result.indexOf(CstLoadingUtilImpl.CST_END) > -1) {
            String replaceStr = result.substring(result.indexOf(CstLoadingUtilImpl.CST_BEG),
                    (result.indexOf(CstLoadingUtilImpl.CST_END) + CstLoadingUtilImpl.CST_END.length()));
            result = replaceCST(result.replace(replaceStr, ""));
        }
        return result;
    }

}
