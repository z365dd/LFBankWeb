/**
 * 系统名称: SmartWeb平台
 * 模块名称: 
 * 类  名  称: Configuration.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2019年4月4日 下午5:14:44<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.ckfinder.connector.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.adtec.framework.common.util.ParamUtil;
import com.ckfinder.connector.ServletContextFactory;
import com.ckfinder.connector.data.AccessControlLevel;
import com.ckfinder.connector.data.PluginInfo;
import com.ckfinder.connector.data.PluginParam;
import com.ckfinder.connector.data.ResourceType;
import com.ckfinder.connector.utils.FileUtils;
import com.ckfinder.connector.utils.PathUtils;

/**
 * @author chenyl
 *
 */
public class Configuration implements IConfiguration {

	protected static final int MAX_QUALITY = 100;
	  protected static final float MAX_QUALITY_FLOAT = 100.0F;
	  private long lastCfgModificationDate;
	  protected boolean enabled;
	  protected String xmlFilePath;
	  protected String baseDir;
	  protected String baseURL;
	  protected String licenseName;
	  protected String licenseKey;
	  protected Integer imgWidth;
	  protected Integer imgHeight;
	  protected float imgQuality;
	  protected Map<String, ResourceType> types;
	  protected ArrayList<String> typesOrder;
	  protected boolean thumbsEnabled;
	  protected String thumbsURL;
	  protected String thumbsDir;
	  protected String thumbsPath;
	  protected boolean thumbsDirectAccess;
	  protected Integer thumbsMaxHeight;
	  protected Integer thumbsMaxWidth;
	  protected float thumbsQuality;
	  protected List<AccessControlLevel> accessControlLevels;
	  protected List<String> hiddenFolders;
	  protected List<String> hiddenFiles;
	  protected boolean doubleExtensions;
	  protected boolean forceASCII;
	  protected boolean checkSizeAfterScaling;
	  protected String uriEncoding;
	  protected String userRoleSessionVar;
	  protected List<PluginInfo> plugins;
	  protected boolean secureImageUploads;
	  protected List<String> htmlExtensions;
	  protected List<String> defaultResourceTypes;
	  protected IBasePathBuilder basePathBuilder;
	  protected boolean disallowUnsafeCharacters;
	  private boolean loading;
	  private Events events;
	  private boolean debug;
	  protected ServletConfig servletConf;

	  public Configuration(ServletConfig servletConfig)
	  {
	    this.servletConf = servletConfig;
	    this.xmlFilePath = servletConfig.getInitParameter("XMLConfig");
	    this.plugins = new ArrayList();
	    this.htmlExtensions = new ArrayList();
	    this.hiddenFolders = new ArrayList();
	    this.hiddenFiles = new ArrayList();
	    this.defaultResourceTypes = new ArrayList();
	  }

	  private void clearConfiguration()
	  {
	    this.debug = false;
	    this.enabled = false;
	    this.baseDir = "";
	    this.baseURL = "";
	    this.licenseName = "";
	    this.licenseKey = "";
	    this.imgWidth = Integer.valueOf(500);
	    this.imgHeight = Integer.valueOf(400);
	    this.imgQuality = 0.8F;
	    this.types = new HashMap();
	    this.typesOrder = new ArrayList();
	    this.thumbsEnabled = false;
	    this.thumbsURL = "";
	    this.thumbsDir = "";
	    this.thumbsPath = "";
	    this.thumbsQuality = 0.8F;
	    this.thumbsDirectAccess = false;
	    this.thumbsMaxHeight = Integer.valueOf(100);
	    this.thumbsMaxWidth = Integer.valueOf(100);
	    this.accessControlLevels = new ArrayList();
	    this.hiddenFolders = new ArrayList();
	    this.hiddenFiles = new ArrayList();
	    this.doubleExtensions = false;
	    this.forceASCII = false;
	    this.checkSizeAfterScaling = false;
	    this.uriEncoding = "UTF-8";
	    this.userRoleSessionVar = "";
	    this.plugins = new ArrayList();
	    this.secureImageUploads = false;
	    this.htmlExtensions = new ArrayList();
	    this.defaultResourceTypes = new ArrayList();
	    this.events = new Events();
	    this.basePathBuilder = null;
	    this.disallowUnsafeCharacters = false;
	  }

	  public void init()
	    throws Exception
	  {
	    clearConfiguration();
	    this.loading = true;
	    /*根据配置文件检查是否为weblogic的war方式部署而使用不同的配置路径*/
	    File file = null;
	    String isWeblogicWarDeploy = ParamUtil.getConfig("weblogice.war.delopy");
	    if("Y".equalsIgnoreCase(isWeblogicWarDeploy)){
	    	String ckfinderPath = ParamUtil.getConfig("ckfinder.path"); // 获取ckfinder的配置文件绝对路径
	    	System.out.println("ckfinder path="+ckfinderPath);
	    	file = new File(ckfinderPath);
	    }else{
	    	file = new File(getFullConfigPath());
	    }
	    this.lastCfgModificationDate = file.lastModified();
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    Document doc = db.parse(file);
	    doc.normalize();
	    Node node = doc.getFirstChild();
	    if (node != null) {
	      NodeList nodeList = node.getChildNodes();
	      for (int i = 0; i < nodeList.getLength(); ++i) {
	        Node childNode = nodeList.item(i);
	        if (childNode.getNodeName().equals("enabled")) {
	          this.enabled = Boolean.valueOf(childNode.getTextContent().trim()).booleanValue();
	        }

	        if (childNode.getNodeName().equals("baseDir")) {
	          this.baseDir = childNode.getTextContent().trim();
	          this.baseDir = PathUtils.escape(this.baseDir);
	          this.baseDir = PathUtils.addSlashToEnd(this.baseDir);
	        }
	        if (childNode.getNodeName().equals("baseURL")) {
	          this.baseURL = childNode.getTextContent().trim();
	          this.baseURL = PathUtils.escape(this.baseURL);
	          this.baseURL = PathUtils.addSlashToEnd(this.baseURL);
	        }
	        if (childNode.getNodeName().equals("licenseName")) {
	          this.licenseName = childNode.getTextContent().trim();
	        }
	        if (childNode.getNodeName().equals("licenseKey")) {
	          this.licenseKey = childNode.getTextContent().trim();
	        }
	        if (childNode.getNodeName().equals("imgWidth")) {
	          String width = childNode.getTextContent().trim();
	          width = width.replaceAll("//D", "");
	          try {
	            this.imgWidth = Integer.valueOf(width);
	          } catch (NumberFormatException e) {
	            this.imgWidth = null;
	          }
	        }
	        if (childNode.getNodeName().equals("imgQuality")) {
	          String quality = childNode.getTextContent().trim();
	          quality = quality.replaceAll("//D", "");
	          this.imgQuality = adjustQuality(quality);
	        }

	        if (childNode.getNodeName().equals("imgHeight")) {
	          String height = childNode.getTextContent().trim();
	          height = height.replaceAll("//D", "");
	          try {
	            this.imgHeight = Integer.valueOf(height);
	          } catch (NumberFormatException e) {
	            this.imgHeight = null;
	          }
	        }
	        if (childNode.getNodeName().equals("thumbs")) {
	          setThumbs(childNode.getChildNodes());
	        }
	        if (childNode.getNodeName().equals("accessControls")) {
	          setACLs(childNode.getChildNodes());
	        }
	        if (childNode.getNodeName().equals("hideFolders")) {
	          setHiddenFolders(childNode.getChildNodes());
	        }
	        if (childNode.getNodeName().equals("hideFiles")) {
	          setHiddenFiles(childNode.getChildNodes());
	        }
	        if (childNode.getNodeName().equals("checkDoubleExtension")) {
	          this.doubleExtensions = Boolean.valueOf(childNode.getTextContent().trim()).booleanValue();
	        }

	        if (childNode.getNodeName().equals("disallowUnsafeCharacters")) {
	          this.disallowUnsafeCharacters = Boolean.valueOf(childNode.getTextContent().trim()).booleanValue();
	        }

	        if (childNode.getNodeName().equals("forceASCII")) {
	          this.forceASCII = Boolean.valueOf(childNode.getTextContent().trim()).booleanValue();
	        }

	        if (childNode.getNodeName().equals("checkSizeAfterScaling")) {
	          this.checkSizeAfterScaling = Boolean.valueOf(childNode.getTextContent().trim()).booleanValue();
	        }

	        if (childNode.getNodeName().equals("htmlExtensions")) {
	          String htmlExt = childNode.getTextContent();
	          Scanner scanner = new Scanner(htmlExt).useDelimiter(",");

	          while (scanner.hasNext()) {
	            String val = scanner.next();
	            if ((val != null) && (!(val.equals("")))) {
	              this.htmlExtensions.add(val.trim().toLowerCase());
	            }

	          }

	        }

	        if (childNode.getNodeName().equals("secureImageUploads")) {
	          this.secureImageUploads = Boolean.valueOf(childNode.getTextContent().trim()).booleanValue();
	        }

	        if (childNode.getNodeName().equals("uriEncoding")) {
	          this.uriEncoding = childNode.getTextContent().trim();
	        }
	        if (childNode.getNodeName().equals("userRoleSessionVar")) {
	          this.userRoleSessionVar = childNode.getTextContent().trim();
	        }
	        if (childNode.getNodeName().equals("defaultResourceTypes")) {
	          String value = childNode.getTextContent().trim();
	          Scanner sc = new Scanner(value).useDelimiter(",");
	          while (sc.hasNext()) {
	            this.defaultResourceTypes.add(sc.next());
	          }
	        }
	        if (childNode.getNodeName().equals("plugins")) {
	          setPlugins(childNode);
	        }
	        if (childNode.getNodeName().equals("basePathBuilderImpl")) {
	          setBasePathImpl(childNode.getTextContent().trim());
	        }
	      }
	    }
	    setTypes(doc);
	    this.events = new Events();
	    registerEventHandlers();
	    this.loading = false;
	  }

	  private String getFullConfigPath() throws Exception {
	    File file = new File(ServletContextFactory.getServletContext().getRealPath(this.xmlFilePath));
	    if ((file.exists()) && (file.isFile())) {
	      return file.getAbsolutePath();
	    }
	    return this.xmlFilePath;
	  }

	  private void setBasePathImpl(String value)
	  {
	    try
	    {
	      Class clazz = Class.forName(value);

	      this.basePathBuilder = ((IBasePathBuilder)clazz.newInstance());
	    } catch (Exception e) {
	      this.basePathBuilder = new ConfigurationPathBuilder();
	    }
	  }

	  private float adjustQuality(String imgQuality)
	  {
	    float helper = 0.0F;
	    try {
	      helper = Math.abs(Float.valueOf(imgQuality).floatValue());
	    } catch (NumberFormatException e) {
	      return 0.8F;
	    }
	    if ((helper == 0.0F) || (helper == 1.0F))
	      return helper;
	    if ((helper > 0.0F) && (helper < 1.0F))
	      helper = Math.round(helper * 100.0F) / 100.0F;
	    else if ((helper > 1.0F) && (helper <= 100.0F))
	      helper = Math.round(helper) / 100.0F;
	    else {
	      helper = 0.8F;
	    }

	    return helper;
	  }

	  protected void registerEventHandlers()
	  {
	    for (PluginInfo item : this.plugins)
	      try
	      {
	        Class clazz = Class.forName(item.getClassName());

	        Plugin plugin = (Plugin)clazz.newInstance();
	        plugin.setPluginInfo(item);
	        plugin.registerEventHandlers(this.events);
	        item.setEnabled(true);
	      } catch (ClassCastException e) {
	        item.setEnabled(false);
	      } catch (ClassNotFoundException e) {
	        item.setEnabled(false);
	      } catch (IllegalAccessException e) {
	        item.setEnabled(false);
	      } catch (InstantiationException e) {
	        item.setEnabled(false);
	      }
	  }

	  private void setHiddenFiles(NodeList childNodes)
	  {
	    for (int i = 0; i < childNodes.getLength(); ++i) {
	      Node node = childNodes.item(i);
	      if (node.getNodeName().equals("file")) {
	        String val = node.getTextContent();
	        if (!(val.equals("")))
	          this.hiddenFiles.add(val.trim());
	      }
	    }
	  }

	  private void setHiddenFolders(NodeList childNodes)
	  {
	    for (int i = 0; i < childNodes.getLength(); ++i) {
	      Node node = childNodes.item(i);
	      if (node.getNodeName().equals("folder")) {
	        String val = node.getTextContent();
	        if (!(val.equals("")))
	          this.hiddenFolders.add(val.trim());
	      }
	    }
	  }

	  private void setACLs(NodeList childNodes)
	  {
	    this.accessControlLevels = new ArrayList();
	    for (int i = 0; i < childNodes.getLength(); ++i) {
	      Node childNode = childNodes.item(i);
	      if (childNode.getNodeName().equals("accessControl")) {
	        AccessControlLevel acl = new AccessControlLevel();
	        acl = getACLFromNode(childNode);
	        if (acl != null)
	          this.accessControlLevels.add(acl);
	      }
	    }
	  }

	  private AccessControlLevel getACLFromNode(Node childNode)
	  {
	    AccessControlLevel acl = new AccessControlLevel();
	    for (int i = 0; i < childNode.getChildNodes().getLength(); ++i) {
	      Node childChildNode = childNode.getChildNodes().item(i);
	      if (childChildNode.getNodeName().equals("role")) {
	        acl.setRole(childChildNode.getTextContent().trim());
	      }
	      if (childChildNode.getNodeName().equals("resourceType")) {
	        acl.setResourceType(childChildNode.getTextContent().trim());
	      }
	      if (childChildNode.getNodeName().equals("folder")) {
	        acl.setFolder(childChildNode.getTextContent().trim());
	      }
	      if (childChildNode.getNodeName().equals("folderView")) {
	        acl.setFolderView(Boolean.valueOf(childChildNode.getTextContent().trim()).booleanValue());
	      }

	      if (childChildNode.getNodeName().equals("folderCreate")) {
	        acl.setFolderCreate(Boolean.valueOf(childChildNode.getTextContent().trim()).booleanValue());
	      }

	      if (childChildNode.getNodeName().equals("folderRename")) {
	        acl.setFolderRename(Boolean.valueOf(childChildNode.getTextContent().trim()).booleanValue());
	      }

	      if (childChildNode.getNodeName().equals("folderDelete")) {
	        acl.setFolderDelete(Boolean.valueOf(childChildNode.getTextContent().trim()).booleanValue());
	      }

	      if (childChildNode.getNodeName().equals("fileView")) {
	        acl.setFileView(Boolean.valueOf(childChildNode.getTextContent().trim()).booleanValue());
	      }
	      if (childChildNode.getNodeName().equals("fileUpload")) {
	        acl.setFileUpload(Boolean.valueOf(childChildNode.getTextContent().trim()).booleanValue());
	      }

	      if (childChildNode.getNodeName().equals("fileRename")) {
	        acl.setFileRename(Boolean.valueOf(childChildNode.getTextContent().trim()).booleanValue());
	      }

	      if (childChildNode.getNodeName().equals("fileDelete")) {
	        acl.setFileDelete(Boolean.valueOf(childChildNode.getTextContent().trim()).booleanValue());
	      }

	    }

	    if ((acl.getResourceType() == null) || (acl.getRole() == null))
	    {
	      return null;
	    }

	    if ((acl.getFolder() == null) || (acl.getFolder().equals(""))) {
	      acl.setFolder("/");
	    }
	    return acl;
	  }

	  private void setThumbs(NodeList childNodes)
	  {
	    for (int i = 0; i < childNodes.getLength(); ++i) {
	      Node childNode = childNodes.item(i);
	      if (childNode.getNodeName().equals("enabled")) {
	        this.thumbsEnabled = Boolean.valueOf(childNode.getTextContent().trim()).booleanValue();
	      }

	      if (childNode.getNodeName().equals("url")) {
	        this.thumbsURL = childNode.getTextContent().trim();
	      }
	      if (childNode.getNodeName().equals("directory")) {
	        this.thumbsDir = childNode.getTextContent().trim();
	      }
	      if (childNode.getNodeName().equals("directAccess"))
	        this.thumbsDirectAccess = Boolean.valueOf(childNode.getTextContent().trim()).booleanValue();
	      String width;
	      if (childNode.getNodeName().equals("maxHeight")) {
	        width = childNode.getTextContent().trim();
	        width = width.replaceAll("//D", "");
	        try {
	          this.thumbsMaxHeight = Integer.valueOf(width);
	        } catch (NumberFormatException e) {
	          this.thumbsMaxHeight = null;
	        }
	      }
	      if (childNode.getNodeName().equals("maxWidth")) {
	        width = childNode.getTextContent().trim();
	        width = width.replaceAll("//D", "");
	        try {
	          this.thumbsMaxWidth = Integer.valueOf(width);
	        } catch (NumberFormatException e) {
	          this.thumbsMaxWidth = null;
	        }
	      }
	      if (childNode.getNodeName().equals("quality")) {
	        String quality = childNode.getTextContent().trim();
	        quality = quality.replaceAll("//D", "");
	        this.thumbsQuality = adjustQuality(quality);
	      }
	    }
	  }

	  private void setTypes(Document doc)
	  {
	    this.types = new HashMap();
	    this.typesOrder = new ArrayList();
	    NodeList list = doc.getElementsByTagName("type");

	    for (int i = 0; i < list.getLength(); ++i) {
	      Element element = (Element)list.item(i);
	      String name = element.getAttribute("name");
	      if ((name != null) && (!(name.equals("")))) {
	        ResourceType resourceType = createTypeFromXml(name, element.getChildNodes());

	        this.types.put(name, resourceType);
	        this.typesOrder.add(resourceType.getName());
	      }
	    }
	  }

	  private ResourceType createTypeFromXml(String typeName, NodeList childNodes)
	  {
	    ResourceType resourceType = new ResourceType(typeName);
	    for (int i = 0; i < childNodes.getLength(); ++i) {
	      Node childNode = childNodes.item(i);
	      String url;
	      if (childNode.getNodeName().equals("url")) {
	        url = childNode.getTextContent().trim();
	        resourceType.setUrl(url);
	      }
	      if (childNode.getNodeName().equals("directory")) {
	        url = childNode.getTextContent().trim();
	        resourceType.setPath(url);
	      }
	      if (childNode.getNodeName().equals("maxSize")) {
	        resourceType.setMaxSize(childNode.getTextContent().trim());
	      }
	      if (childNode.getNodeName().equals("allowedExtensions")) {
	        resourceType.setAllowedExtensions(childNode.getTextContent().trim());
	      }
	      if (childNode.getNodeName().equals("deniedExtensions")) {
	        resourceType.setDeniedExtensions(childNode.getTextContent().trim());
	      }
	    }
	    return resourceType;
	  }

	  public boolean checkAuthentication(HttpServletRequest request)
	  {
	    return true;
	  }

	  public boolean enabled()
	  {
	    return ((this.enabled) && (!(this.loading)));
	  }

	  public boolean isDisallowUnsafeCharacters()
	  {
	    return this.disallowUnsafeCharacters;
	  }

	  public String getBaseDir()
	  {
	    return this.baseDir;
	  }

	  public String getBaseURL()
	  {
	    return this.baseURL;
	  }

	  public Integer getImgHeight()
	  {
	    if (this.imgHeight != null) {
	      return this.imgHeight;
	    }
	    return Integer.valueOf(400);
	  }

	  public Integer getImgWidth()
	  {
	    if (this.imgWidth != null) {
	      return this.imgWidth;
	    }
	    return Integer.valueOf(500);
	  }

	  public float getImgQuality()
	  {
	    return this.imgQuality;
	  }

	  public String getLicenseKey()
	  {
	    return this.licenseKey;
	  }

	  public String getLicenseName()
	  {
	    return this.licenseName;
	  }

	  public Map<String, ResourceType> getTypes()
	  {
	    return this.types;
	  }

	  public boolean getThumbsDirectAccess()
	  {
	    return this.thumbsDirectAccess;
	  }

	  public int getMaxThumbHeight()
	  {
	    if (this.thumbsMaxHeight != null) {
	      return this.thumbsMaxHeight.intValue();
	    }
	    return 100;
	  }

	  public int getMaxThumbWidth()
	  {
	    if (this.thumbsMaxWidth != null) {
	      return this.thumbsMaxWidth.intValue();
	    }
	    return 100;
	  }

	  public boolean getThumbsEnabled()
	  {
	    return this.thumbsEnabled;
	  }

	  public String getThumbsURL()
	  {
	    return this.thumbsURL;
	  }

	  public String getThumbsDir()
	  {
	    return this.thumbsDir;
	  }

	  public String getThumbsPath()
	  {
	    return this.thumbsPath;
	  }

	  public float getThumbsQuality()
	  {
	    return this.thumbsQuality;
	  }

	  public void setThumbsPath(String directory)
	  {
	    this.thumbsPath = directory;
	  }

	  public List<AccessControlLevel> getAccessConrolLevels()
	  {
	    return this.accessControlLevels;
	  }

	  public List<String> getHiddenFolders()
	  {
	    return this.hiddenFolders;
	  }

	  public List<String> getHiddenFiles()
	  {
	    return this.hiddenFiles;
	  }

	  public boolean ckeckDoubleFileExtensions()
	  {
	    return this.doubleExtensions;
	  }

	  public boolean forceASCII()
	  {
	    return this.forceASCII;
	  }

	  public boolean checkSizeAfterScaling()
	  {
	    return this.checkSizeAfterScaling;
	  }

	  public String getUriEncoding()
	  {
	    if ((this.uriEncoding == null) || (this.uriEncoding.equals("")))
	    {
	      return "UTF-8";
	    }
	    return this.uriEncoding;
	  }

	  public String getUserRoleName()
	  {
	    return this.userRoleSessionVar;
	  }

	  public List<String> getResourceTypesOrder()
	  {
	    return this.typesOrder;
	  }

	  public List<PluginInfo> getPlugins()
	  {
	    return this.plugins;
	  }

	  public boolean getSecureImageUploads()
	  {
	    return Boolean.valueOf(this.secureImageUploads).booleanValue();
	  }

	  public List<String> getHTMLExtensions()
	  {
	    return this.htmlExtensions;
	  }

	  public Events getEvents()
	  {
	    return this.events;
	  }

	  public List<String> getDefaultResourceTypes()
	  {
	    return this.defaultResourceTypes;
	  }

	  public boolean isDebugMode()
	  {
	    return this.debug;
	  }

	  public IBasePathBuilder getBasePathBuilder()
	  {
	    if (this.basePathBuilder == null) {
	      this.basePathBuilder = new ConfigurationPathBuilder();
	    }
	    return this.basePathBuilder;
	  }

	  public boolean checkIfReloadConfig()
	    throws Exception
	  {
	    File cfgFile;
	    try
	    {
	      cfgFile = new File(FileUtils.getFullPath(this.xmlFilePath));
	    } catch (Exception e) {
	      if (this.debug) {
	        throw e;
	      }
	      return false;
	    }
	    return (cfgFile.lastModified() > this.lastCfgModificationDate);
	  }

	  public void prepareConfigurationForRequest(HttpServletRequest request)
	  {
	  }

	  private void setPlugins(Node childNode)
	  {
	    NodeList nodeList = childNode.getChildNodes();

	    for (int i = 0; i < nodeList.getLength(); ++i) {
	      Node childChildNode = nodeList.item(i);
	      if (childChildNode.getNodeName().equals("plugin"))
	        this.plugins.add(createPluginFromNode(childChildNode));
	    }
	  }

	  private PluginInfo createPluginFromNode(Node element)
	  {
	    PluginInfo info = new PluginInfo();
	    NodeList list = element.getChildNodes();
	    for (int i = 0; i < list.getLength(); ++i) {
	      Node childElem = list.item(i);
	      if ("name".equals(childElem.getNodeName())) {
	        info.setName(childElem.getTextContent().trim());
	      }
	      if ("class".equals(childElem.getNodeName())) {
	        info.setClassName(childElem.getTextContent().trim());
	      }
	      if ("params".equals(childElem.getNodeName())) {
	        NodeList list2 = childElem.getChildNodes();
	        if (list.getLength() > 0) {
	          info.setParams(new ArrayList());
	        }
	        for (int j = 0; j < list2.getLength(); ++j) {
	          Node node = list2.item(j);
	          if ("param".equals(node.getNodeName())) {
	            NamedNodeMap map = node.getAttributes();
	            PluginParam pp = new PluginParam();
	            for (int k = 0; k < map.getLength(); ++k) {
	              if ("name".equals(map.item(k).getNodeName())) {
	                pp.setName(map.item(k).getTextContent().trim());
	              }
	              if ("value".equals(map.item(k).getNodeName())) {
	                pp.setValue(map.item(k).getTextContent().trim());
	              }
	            }
	            info.getParams().add(pp);
	          }
	        }
	      }
	    }
	    return info;
	  }

	  public void setThumbsURL(String url)
	  {
	    this.thumbsURL = url;
	  }

	  public void setThumbsDir(String dir)
	  {
	    this.thumbsDir = dir;
	  }

	  public final void setDebugMode(boolean mode)
	  {
	    this.debug = mode;
	  }

	  public final IConfiguration cloneConfiguration()
	  {
	    Configuration configuration = createConfigurationInstance();
	    copyConfFields(configuration);
	    return configuration;
	  }

	  protected Configuration createConfigurationInstance()
	  {
	    return new Configuration(this.servletConf);
	  }

	  protected void copyConfFields(Configuration configuration)
	  {
	    configuration.loading = this.loading;
	    configuration.xmlFilePath = this.xmlFilePath;
	    configuration.debug = this.debug;
	    configuration.lastCfgModificationDate = this.lastCfgModificationDate;
	    configuration.enabled = this.enabled;
	    configuration.xmlFilePath = this.xmlFilePath;
	    configuration.baseDir = this.baseDir;
	    configuration.baseURL = this.baseURL;
	    configuration.licenseName = this.licenseName;
	    configuration.licenseKey = this.licenseKey;
	    configuration.imgWidth = this.imgWidth;
	    configuration.imgHeight = this.imgHeight;
	    configuration.imgQuality = this.imgQuality;
	    configuration.thumbsEnabled = this.thumbsEnabled;
	    configuration.thumbsURL = this.thumbsURL;
	    configuration.thumbsDir = this.thumbsDir;
	    configuration.thumbsDirectAccess = this.thumbsDirectAccess;
	    configuration.thumbsMaxHeight = this.thumbsMaxHeight;
	    configuration.thumbsMaxWidth = this.thumbsMaxWidth;
	    configuration.thumbsQuality = this.thumbsQuality;
	    configuration.doubleExtensions = this.doubleExtensions;
	    configuration.forceASCII = this.forceASCII;
	    configuration.disallowUnsafeCharacters = this.disallowUnsafeCharacters;
	    configuration.checkSizeAfterScaling = this.checkSizeAfterScaling;
	    configuration.secureImageUploads = this.secureImageUploads;
	    configuration.uriEncoding = this.uriEncoding;
	    configuration.userRoleSessionVar = this.userRoleSessionVar;
	    configuration.events = this.events;
	    configuration.basePathBuilder = this.basePathBuilder;

	    configuration.htmlExtensions = new ArrayList();
	    configuration.htmlExtensions.addAll(this.htmlExtensions);
	    configuration.hiddenFolders = new ArrayList();
	    configuration.hiddenFiles = new ArrayList();
	    configuration.hiddenFiles.addAll(this.hiddenFiles);
	    configuration.hiddenFolders.addAll(this.hiddenFolders);
	    configuration.typesOrder = new ArrayList();
	    configuration.typesOrder.addAll(this.typesOrder);
	    configuration.defaultResourceTypes = new ArrayList();
	    configuration.defaultResourceTypes.addAll(this.defaultResourceTypes);
	    configuration.types = new HashMap();
	    configuration.accessControlLevels = new ArrayList();
	    configuration.plugins = new ArrayList();
	    copyTypes(configuration.types);
	    copyACls(configuration.accessControlLevels);
	    copyPlugins(configuration.plugins);
	  }

	  private void copyPlugins(List<PluginInfo> plugins2)
	  {
	    for (PluginInfo pluginInfo : this.plugins)
	      plugins2.add(new PluginInfo(pluginInfo));
	  }

	  private void copyACls(List<AccessControlLevel> accessControlLevels2)
	  {
	    for (AccessControlLevel acl : this.accessControlLevels)
	      accessControlLevels2.add(new AccessControlLevel(acl));
	  }

	  private void copyTypes(Map<String, ResourceType> types2)
	  {
	    for (String name : this.typesOrder)
	      types2.put(name, new ResourceType((ResourceType)this.types.get(name)));
	  }

}
