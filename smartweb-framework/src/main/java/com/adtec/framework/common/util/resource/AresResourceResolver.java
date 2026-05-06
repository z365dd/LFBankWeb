/*
 * 系统名称: 
 * 模块名称:
 * 类 名 称: PathPatternMatchResourceResolver.java
 * 软件版权: 
 * 相关文档:
 * 修改记录:
 * 修改日期      修改人员                     修改说明<BR>
 * ========     ======  ============================================
 *   
 * ========     ======  ============================================
 * 评审记录：
 * 
 * 评审人员：
 * 评审日期：
 * 发现问题：
 */
package com.adtec.framework.common.util.resource;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.adtec.framework.common.util.PathMatcher;
import com.adtec.framework.common.util.ResourceUtil;
import com.adtec.framework.common.util.StringUtil;

/**
 * 功能说明: <BR>
 * 系统版本: v1.0 <BR>
 * 开发人员: <BR>
 * 开发时间: <BR>
 * 审核人员:<BR>
 * 相关文档:<BR>
 * 修改记录: <BR>
 * 修改日期 修改人员 修改说明<BR>
 * ======== ====== ============================================<BR>
 *<BR>
 */
public class AresResourceResolver {
	private PathMatcher pathMatcher = new AntPathMatcher(); // 匹配器
	private ClassLoader _classLoader; // 缺省Class Loader
	// public AresResourceResolver(){
	// this(Thread.currentThread().getContextClassLoader());
	// }

	public AresResourceResolver(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	String CLASSPATH_ALL_URL_PREFIX = "classpath*:";

	/**
	 * 按照给定的模板搜索所有资源文件
	 */
	public Enumeration<IAdapterURLBean> getResources(String locationPattern)
			throws IOException {
		if (locationPattern.startsWith(CLASSPATH_ALL_URL_PREFIX)) {
			// 如果有classpath*开头，
			if (getPathMatcher().isPattern(
					locationPattern
							.substring(CLASSPATH_ALL_URL_PREFIX.length()))) {
				// 是模板格式，按模板匹配方式查找
				return findPathMatchingResources(locationPattern);
			} else {
				// 不是模板格式，按制定Class 名称查找
				return findAllClassPathResources(locationPattern
						.substring(CLASSPATH_ALL_URL_PREFIX.length()));
			}
		} else {
			// 没有以classpath*开头
			int prefixEnd = locationPattern.indexOf(":") + 1;
			if (getPathMatcher()
					.isPattern(locationPattern.substring(prefixEnd))) {
				// 如果包含:, 并且是模板格式
				return findPathMatchingResources(locationPattern);
			} else {
				// 不是模板格式，直接调用ClassLoader的getResources返回
				return findAllClassPathResources(locationPattern);
			}
		}
	}

	/**
	 * Return the PathMatcher that this resource pattern resolver uses.
	 */
	public PathMatcher getPathMatcher() {
		return this.pathMatcher;
	}

	public Enumeration<IAdapterURLBean> findAllClassPathResources(String location)
			throws IOException {
		String path = location;
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		Enumeration<URL> resourceUrls = _classLoader.getResources(path);

		return createURLBeanEnumeration(resourceUrls);
	}

	private Enumeration<IAdapterURLBean> createURLBeanEnumeration(
			Enumeration<URL> urlEnum) {
		Vector<IAdapterURLBean> vector = new Vector();
		while (urlEnum.hasMoreElements()) {
			IAdapterURLBean urlBean = new IAdapterURLBean(urlEnum.nextElement(), 0, "",
					IAdapterURLBean.PATHTYPE_UNKNOWN);
			vector.add(urlBean);
		}
		return vector.elements();
	}

	/**
	 * 按照模板格式从所有ClassPath搜索资源文件和类，包括从Jar和ZIP文件
	 * 
	 * @param locationPattern
	 *            匹配格式
	 * @return 搜索到的资源列表
	 * @throws IOException
	 *             I/O错误
	 * @see #doFindPathMatchingJarResources
	 * @see #doFindPathMatchingFileResources
	 */
	protected Enumeration<IAdapterURLBean> findPathMatchingResources(
			String locationPattern) throws IOException {
		String rootDirPath = determineRootDir(locationPattern);
		String subPattern = locationPattern.substring(rootDirPath.length());
		Enumeration<IAdapterURLBean> rootDirResources = getResources(rootDirPath);
		Vector result = new Vector(16);

		while (rootDirResources.hasMoreElements()) {
			IAdapterURLBean rootDirResourceURLBean = rootDirResources.nextElement();
			URL rootDirResource = rootDirResourceURLBean.getUrl();
			if (ResourceUtil.isJarURL(rootDirResource)) {
				// 从JAR 文件搜索
				result.addAll(doFindPathMatchingJarResources(rootDirResource,
						subPattern));
			} else {
				// 从文件路径搜索
				result.addAll(doFindPathMatchingFileResources(rootDirResource,
						subPattern));
			}
		}

		return result.elements();
	}

	/**
	 * 判断给定格式模板的根路径 例如/WEB-INF/*.xml 将返回/WEB-INF
	 */
	protected String determineRootDir(String location) {
		int prefixEnd = location.indexOf(":") + 1;
		int rootDirEnd = location.length();
		while (rootDirEnd > prefixEnd
				&& getPathMatcher().isPattern(
						location.substring(prefixEnd, rootDirEnd))) {
			rootDirEnd = location.lastIndexOf('/', rootDirEnd - 2) + 1;
		}
		if (rootDirEnd == 0) {
			rootDirEnd = prefixEnd;
		}
		return location.substring(0, rootDirEnd);
	}

	/**
	 * 从JAR文件中搜索和匹配资源文件
	 */
	public Set doFindPathMatchingJarResources(URL rootDirResource,
			String subPattern) throws IOException {
		URLConnection con = rootDirResource.openConnection();
		JarFile jarFile = null;
		String jarFileUrl = null;
		String rootEntryPath = null;
		boolean newJarFile = false;

		if (con instanceof JarURLConnection) {
			JarURLConnection jarCon = (JarURLConnection) con;
			jarCon.setUseCaches(false);
			jarFile = jarCon.getJarFile();
			jarFileUrl = jarCon.getJarFileURL().toExternalForm();
			JarEntry jarEntry = jarCon.getJarEntry();
			rootEntryPath = (jarEntry != null ? jarEntry.getName() : "");
		} else {
			String urlFile = rootDirResource.getFile();
			int separatorIndex = urlFile
					.indexOf(ResourceUtil.JAR_URL_SEPARATOR);
			if (separatorIndex != -1) {
				jarFileUrl = urlFile.substring(0, separatorIndex);
				rootEntryPath = urlFile.substring(separatorIndex
						+ ResourceUtil.JAR_URL_SEPARATOR.length());
				jarFile = getJarFile(jarFileUrl);
			} else {
				jarFile = new JarFile(urlFile);
				jarFileUrl = urlFile;
				rootEntryPath = "";
			}
			newJarFile = true;
		}

		try {

			if (!"".equals(rootEntryPath) && !rootEntryPath.endsWith("/")) {
				rootEntryPath = rootEntryPath + "/";
			}
			Set result = new LinkedHashSet(8);
			for (Enumeration entries = jarFile.entries(); entries
					.hasMoreElements();) {
				JarEntry entry = (JarEntry) entries.nextElement();
				String entryPath = entry.getName();
				if (entryPath.startsWith(rootEntryPath)) {
					String relativePath = entryPath.substring(rootEntryPath
							.length());
					if (getPathMatcher().match(subPattern, relativePath)) {
						//URL url = _classLoader.getResource(entryPath);
						// result.add(url);
						String rootDirResourceString=rootDirResource.toString();
						if (!"".equals(rootDirResourceString) && !rootDirResourceString.endsWith("/")) {
							rootDirResourceString = rootDirResourceString + "/";
						}
						URL url = new URL(rootDirResourceString+entryPath.substring(rootEntryPath.length()));
						IAdapterURLBean urlBean = new IAdapterURLBean(url, entry.getTime(),
								rootDirResource.toString(),
								IAdapterURLBean.PATHTYPE_IN_JAR);
						result.add(urlBean);
					}
				}
			}
			return result;
		} finally {
			if (newJarFile) {
				jarFile.close();
			}
		}
	}

	/**
	 * 获取JAR文件URL
	 */
	protected JarFile getJarFile(String jarFileUrl) throws IOException {
		if (jarFileUrl.startsWith(ResourceUtil.FILE_URL_PREFIX)) {
			try {
				return new JarFile(ResourceUtil.toURI(jarFileUrl)
						.getSchemeSpecificPart());
			} catch (URISyntaxException ex) {
				// Fallback for URLs that are not valid URIs (should hardly ever
				// happen).
				return new JarFile(jarFileUrl
						.substring(ResourceUtil.FILE_URL_PREFIX.length()));
			}
		} else {
			return new JarFile(jarFileUrl);
		}
	}

	/**
	 * 从文件路径安装模板格式搜索所有资源文件
	 * 
	 * @param rootDirResource
	 *            根目录
	 * @param subPattern
	 *            子模板，相对于根目录
	 * @return 匹配的资源列表
	 * @throws IOException
	 *             I/O errors
	 */
	public Set doFindPathMatchingFileResources(URL rootDirResource,
			String subPattern) throws IOException {
		File rootDir = null;
		try {
			// rootDir = new File(rootDirResource.toURI());//modify at
			// 2009-09-25,toURI有空格出错
			rootDir = new File(URLDecoder.decode(rootDirResource.getFile(),
					"utf-8"));
		} catch (Exception e) {
			e.printStackTrace();

			// throw new ApplicationRuntimeException(e);
			return Collections.EMPTY_SET;
		}
		return doFindMatchingFileSystemResources(rootDir, subPattern);
	}

	protected Set doFindMatchingFileSystemResources(File rootDir,
			String subPattern) throws IOException {

		Set matchingFiles = retrieveMatchingFiles(rootDir, subPattern);
		Set result = new LinkedHashSet(matchingFiles.size());
		for (Iterator it = matchingFiles.iterator(); it.hasNext();) {
			// 如果匹配
			File file = (File) it.next();
			// result.add(file.toURL());
			IAdapterURLBean urlBean = new IAdapterURLBean(file.toURL(), file.lastModified(),
					rootDir.toURL().toString(), IAdapterURLBean.PATHTYPE_IN_FOLDER);
			result.add(urlBean);
		}
		return result;
	}

	/**
	 * 从当前根目录搜索所有子目录中匹配的文件
	 */
	protected Set retrieveMatchingFiles(File rootDir, String pattern)
			throws IOException {
		if (!rootDir.isDirectory()) {
			throw new IllegalArgumentException("Resource path [" + rootDir
					+ "] does not denote a directory");
		}
		String fullPattern = StringUtil.replace(rootDir.getAbsolutePath(),
				File.separator, "/");
		if (!pattern.startsWith("/")) {
			fullPattern += "/";
		}
		fullPattern = fullPattern
				+ StringUtil.replace(pattern, File.separator, "/");
		Set result = new LinkedHashSet(8);
		doRetrieveMatchingFiles(fullPattern, rootDir, result);
		return result;
	}

	/**
	 * 递归搜索文集匹配并且加到返回结果
	 */
	protected void doRetrieveMatchingFiles(String fullPattern, File dir,
			Set result) throws IOException {

		File[] dirContents = dir.listFiles();
		if (dirContents == null) {
			throw new IOException("Could not retrieve contents of directory ["
					+ dir.getAbsolutePath() + "]");
		}
		for (int i = 0; i < dirContents.length; i++) {
			File content = dirContents[i];
			String currPath = StringUtil.replace(content.getAbsolutePath(),
					File.separator, "/");
			if (content.isDirectory()
					&& getPathMatcher().matchStart(fullPattern, currPath + "/")) {
				doRetrieveMatchingFiles(fullPattern, content, result);
			}
			if (getPathMatcher().match(fullPattern, currPath)) {
				result.add(content);
			}
		}
	}

}
