package com.adtec.framework.common.config;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adtec.cache.cacheagent.util.DataUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.StringUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * 防护http处理
 * 1.过滤xss
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(XssHttpServletRequestWrapper.class);

    boolean isUpData = false;//判断是否是上传 上传忽略

    //不期待被过滤的的链接和字段（管理后台使用了富文本，希望有可编辑的内容）
    HashMap<String, String> doNotFilterURLAndParamMap = new HashMap<String, String>() {
        {
            String ignoreurl = ParamUtil.getString("sm.xxs.protect.ignoreurl");
            String ignoreparam = ParamUtil.getString("sm.xxs.protect.ignoreparam");
            if(!DataUtil.isNullStr(ignoreurl)){
                String[] urls = ignoreurl.split(";");
                for(String url:urls){
                    try{
                        String k = url.split(",")[0];
                        String v = url.split(",")[1];
                        put(k, v);
                    }catch(Exception e){
                        LOGGER.warn("XXS配置参数不合法【"+url+"】");
                    }

                }
            }
        }
    };



    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        String contentType = request.getContentType ();
        if (null != contentType)
            isUpData =contentType.startsWith ("multipart");
    }

    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = super.getParameterMap();
        Map<String, String[]> encodedMap = new HashMap<String, String[]>();
        encodedMap.putAll(map);

        for (Map.Entry<String, String[]> entry : encodedMap.entrySet()) {
            String[] value = entry.getValue();
            String[] encodedValues = new String[value.length];
            for (int i = 0; i < value.length; i++) {
                encodedValues[i] = cleanXSS(value[i]);
            }
            encodedMap.put(entry.getKey(), encodedValues);
        }
        return encodedMap;
    }

    /**
     * 过滤单个参数
     * @param name
     * @return
     */
    @Override
    public String getParameter(String name) {
        String parameter = super.getParameter(name);
        if(StringUtil.isNotBlank(parameter) && XSSFilterConfigUtil.getOpenXssProtect()){
            //这里使用的阿帕奇的common-lang3中的转义html方法,也可以自己实现,
            String escapeParameter = this.cleanXSS(parameter);
            return escapeParameter;
        }
        return parameter;
    }

    /**
     * 过滤实体的每个参数
     * @param name
     * @return
     */
    @Override
    public String[] getParameterValues(String name) {

        String[] parameterValues = super.getParameterValues(name);
        if (parameterValues == null) {
            return null;
        }
        if (XSSFilterConfigUtil.getOpenXssProtect()) {
            for (int i = 0; i < parameterValues.length; ++i) {
                String value = parameterValues[i];
                parameterValues[i] = this.cleanXSS(value);
            }
        }

        return parameterValues;

    }

    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null)
            return null;
        return cleanXSS(value);
    }

    /**
     * 处理@RequestBody的形式传入的json数据
     * @return
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream () throws IOException {
        if(!XSSFilterConfigUtil.getOpenXssProtect()) {
            return super.getInputStream ();
        }

        if (isUpData){
            return super.getInputStream ();
        }else{

            final ByteArrayInputStream bais = new ByteArrayInputStream(inputHandlers(super.getInputStream ()).getBytes ());

            return new ServletInputStream() {

                @Override
                public int read() throws IOException {
                    return bais.read();
                }
            };
        }

    }


    public String inputHandlers(ServletInputStream servletInputStream){
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(servletInputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
                System.out.println("出现异常");
            } finally {
            if (servletInputStream != null) {
                try {
                    servletInputStream.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                System.out.println("出现异常");
            }
            }
        }

        String requestUrl = StringUtil.replaceOnce(this.getRequestURI(), this.getContextPath(), StringUtil.EMPTY);
        boolean needFilter = false;
        String key = "";
        String param = "";
        for(Map.Entry<String, String> entry : doNotFilterURLAndParamMap.entrySet()){
            key = entry.getKey();
            int index = StringUtil.indexOf(key, "*");
            if (index > 0) {
                String[] array = key.split("\\*");
                StringBuffer stringBuffer = new StringBuffer();
                for (String s : array) {
                    stringBuffer.append(s).append("(.*)");
                }
                Pattern p = Pattern.compile(stringBuffer.toString());
                Matcher m = p.matcher(requestUrl);
                if (m.find()) {
                    needFilter = true;
                    param = entry.getValue();
                    break;
                }
            } else {
                if (requestUrl.equals(key)) {
                    needFilter = true;
                    param = entry.getValue();
                    break;
                }
            }
        }

        if(needFilter) {   //有需要特殊处理的字段，不希望过滤标签
            try {
                /*String param = doNotFilterURLAndParamMap.get(requestUrl);*/
                JSONObject jsonObject = JSONObject.parseObject(sb.toString());
                if(jsonObject.containsKey(param)) {
                    Object notFilterValue = jsonObject.get(param);
                    String cleanXSSParams = cleanXSS(sb.toString ());
                    JSONObject filteredJson = JSONObject.parseObject(cleanXSSParams);
                    filteredJson.put(param, notFilterValue);
                    return filteredJson.toJSONString();
                }else {
                    return cleanXSS(sb.toString());
                }

            }catch (Exception e) {
                LOGGER.error("XssHttpServletRequestWrapper转换json数据失败",e);
                return cleanXSS(sb.toString ());  //异常时，就直接过滤，不管需要特殊处理的参数
            }
        }else {
            return cleanXSS(sb.toString());
        }
    }

    /**
     * 过滤规则，这里不直接使用StringEscapeUtils.escapeHtml，因为获取的是一个json字符串，会将" 替换导致数据异常，没有""进行分割，无法正常注入到@RequestBody
     * @param value
     * @return
     */
    private static String cleanXSS(String value) {
        if (value != null) {
            // Avoid null characters
            value = value.replaceAll("", "");
            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid anything in a
            // src="http://www.yihaomen.com/article/java/..." type of
            // e­xpression
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid eval(...) e­xpressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid e­xpression(...) e­xpressions
            scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid javascript:... e­xpressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid vbscript:... e­xpressions
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid οnlοad= e­xpressions
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
        }
        return filter(value);

//        value = value.replaceAll("<", "<").replaceAll(">", ">");
//        value = value.replaceAll("%3C", "<").replaceAll("%3E", ">");
////        value = value.replaceAll("\\(", "(").replaceAll("\\)", ")");
//        value = value.replaceAll("%28", "(").replaceAll("%29", ")");
////        value = value.replaceAll("'", "'");
//       /* value = value.replaceAll("eval\\((.*)\\)", "");
//        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
//        value = value.replaceAll("script", "");*/
//        return value;
    }

    /**
     * 过滤特殊字符
     */
    public static String filter(String value) {
        if (value == null) {
            return null;
        }
        StringBuffer result = new StringBuffer(value.length());
        for (int i = 0; i < value.length(); ++i) {
            switch (value.charAt(i)) {
                case '<':
                    result.append("& lt;");
                    break;
                case '>':
                    result.append("& gt;");
                    break;
                case '"':
                    result.append("\"");
                    break;
                default:
                    result.append(value.charAt(i));
                    break;
            }
        }
        return result.toString();
    }

}

