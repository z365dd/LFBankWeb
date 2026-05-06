package com.adtec.sys.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.UriUtils;

import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;

/**
 * 查看CK上传的图片
 * 
 * @version 2014-06-25
 */
public class UserfilesDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final static Logger logger = LoggerFactory.getLogger(UserfilesDownloadServlet.class);

	public void fileOutputStream(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String filepath = req.getRequestURI();
		filepath = FileUtil.path(filepath);
		int index = filepath.indexOf(ParamUtil.USERFILES_BASE_URL);
		if(index >= 0) {
			filepath = filepath.substring(index + ParamUtil.USERFILES_BASE_URL.length());
		}
		try {
			filepath = UriUtils.decode(filepath, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error(String.format("解释文件路径失败，URL地址为%s", filepath), e1);
		}
		File file = new File(FileUtil.path(ParamUtil.getUserfilesBaseDir() + ParamUtil.USERFILES_BASE_URL + filepath));
		try {
			/*20200407 add by chenyl for 对文件为text是设置响应字符集为utf8*/
			if(file.getName().endsWith(".txt")||file.getName().endsWith(".json")){
				// 浏览器用utf8来解析返回的数据
				resp.setHeader("Content-type", "text/html;charset=UTF-8");	// text/html对应返回文件类型：*.htm *.html *.shtml
				// servlet用UTF-8转码，而不是用默认的ISO8859
				resp.setCharacterEncoding("UTF-8");
				String data = FileUtil.readTxtFileByPath(file.getAbsolutePath());
				PrintWriter pw = resp.getWriter();
				//如果是类似xml数据，页面会当html输出，所以替换"<"和">"
				pw.write(data.replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
			}else{
				FileCopyUtils.copy(new FileInputStream(file), resp.getOutputStream());
				resp.setHeader("Content-Type", "application/octet-stream");	// application/octet-stream对应返回文件类型：*.bin *.exe *.com *.dll *.class
			}
			return;
		} catch (FileNotFoundException e) {
			req.setAttribute("exception", new FileNotFoundException("请求的文件不存在"));
			req.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		fileOutputStream(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		fileOutputStream(req, resp);
	}
}
