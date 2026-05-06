package com.adtec.sys.modules.sys.log;

import com.adtec.framework.common.util.*;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.interfaces.log.ILogCounter;
import com.adtec.sys.common.utils.IdGen;
import com.adtec.sys.modules.sys.dao.SysReqIpMsgDao;
import com.adtec.sys.modules.sys.entity.Log;
import com.adtec.sys.modules.sys.entity.LogMappingDO;
import com.adtec.sys.modules.sys.entity.SysReqIpMsgDO;
import com.adtec.sys.modules.sys.service.LogMappingService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import sun.net.util.IPAddressUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Map;

public class LogCounter implements ILogCounter {
    protected final static Logger logger = LoggerFactory.getLogger(LogCounter.class);

    public static final int CONNECT_TIMEOUT = 3000;	//获取连接超时(3秒)
    public static final int RESPONSE_TIMEOUT = 30000;//获取响应超时 (30秒)

    @Override
    public void dealLog(final Log log) {
        Date st = new Date();
        String ip = log.getTermIp();
        if (internalIp(ip)) {
            logger.info("内网地址无需登记");
            return;
        }
        logger.info("登记["+ip+"]请求信息开始");
        String date = DateUtil.getNumNowDate();
        String dateTime = DateUtil.getDateTime();
        SysReqIpMsgDO obj = new SysReqIpMsgDO();
        obj.setTermIp(ip);
        SysReqIpMsgDao sysReqIpMsgDao = SpringContextHolder.getBean("sysReqIpMsgDao");
        SysReqIpMsgDO qryObj = sysReqIpMsgDao.get(obj);
        if (null!=qryObj) {
            BeanUtils.copyProperties(qryObj, obj);
            if (null==qryObj.getStatus() || !"success".equals(qryObj.getStatus())) {
                genIpObj(obj, ip);
            }
            if ("200".equals(qryObj.getChkFlg())){
                logger.info("已查询过["+ip+"]的拥有者");
            } else if ((DataUtil.isNullStr(qryObj.getMobile())|| "false".equals(qryObj.getMobile()))) {
                logger.info("查询["+ip+"]的拥有者开始");
//                qryIpOwner(obj, ip);
                logger.info("查询["+ip+"]的拥有者结束");
            } else {
                logger.info("不执行查询["+ip+"]的拥有者");
            }
            obj.setUptTime(dateTime);
            sysReqIpMsgDao.update(obj);
        } else {
            genIpObj(obj, ip);
//            if (DataUtil.isNullStr(qryObj.getMobile()) || "false".equals(qryObj.getMobile()))  {
//                qryIpOwner(obj, ip);
//            }
            obj.setId(IdGen.uuid());
            obj.setCrtr("1");
            obj.setCrtTime(dateTime);
            obj.setUptTime(dateTime);
            sysReqIpMsgDao.insert(obj);
        }
        Date et = new Date();
        logger.info("登记请求信息完成：总耗时[" + (et.getTime() - st.getTime()) + "]ms");

    }

    private boolean internalIp(String ip) {
        byte[] addr = IPAddressUtil.textToNumericFormatV4(ip);
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        //10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        //172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        //192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        switch (b0) {
            case SECTION_1:
                return true;
            case SECTION_2:
                if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                    return true;
                }
            case SECTION_5:
                switch (b1) {
                    case SECTION_6:
                        return true;
                }
            default:
                return false;
        }
    }

    /**
     * 组装IP信息实体类
     * @param obj
     * @param ip
     */
    private void genIpObj(SysReqIpMsgDO obj, String ip) {
        Map<String, Object> resultMap = callIpApi(ip);
        obj.setCountryCode(""+resultMap.get("countryCode"));
        obj.setCountryName(""+resultMap.get("country"));
        obj.setRegionCode(""+resultMap.get("region"));
        obj.setRegionName(""+resultMap.get("regionName"));
        obj.setCity(""+resultMap.get("city"));
        obj.setZipCode(""+resultMap.get("zip"));
        obj.setLat(DataUtil.formatDouble(resultMap.get("lat")));
        obj.setLon(DataUtil.formatDouble(resultMap.get("lon")));
        obj.setTimezone(""+resultMap.get("timezone"));
        obj.setIsp(""+resultMap.get("isp"));
        obj.setOrg(""+resultMap.get("org"));
        obj.setAddrMsg(""+resultMap.get("as"));
        obj.setStatus(""+resultMap.get("status"));
        obj.setMobile(null!=resultMap.get("mobile")?(String)resultMap.get("mobile"):"true");
        obj.setProxy(""+resultMap.get("proxy"));

    }

    /**
     * 获取ip信息
     * @param ip
     * @return
     */
    private Map<String, Object> callIpApi (String ip) {
        Map<String, Object> jsonMap = Maps.newHashMap();
        String retMsg = "";
        long startTime = 0;
        long endTime = 0;
        HttpClient httpClient = null;
        PostMethod postMethod = null;
        String requestUrl = ParamUtil.getString("ip.req.url");
//        String requestUrl = "http://ip-api.com/json/{IP}?fields=66842623&lang=zh-CN";
        requestUrl = requestUrl.replace("{IP}", ip);
        try {
            httpClient = new HttpClient();
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECT_TIMEOUT);// 获取连接超时(3秒)
            httpClient.getHttpConnectionManager().getParams().setSoTimeout(RESPONSE_TIMEOUT);// 获取响应超时
            // (30秒)
            postMethod = new PostMethod(requestUrl);
            postMethod.setRequestHeader("Content-type", "application/json; charset=utf-8");
            postMethod.setRequestHeader("Accept", "application/json");
            startTime = System.currentTimeMillis();
            int status = httpClient.executeMethod(postMethod);
            StringBuffer repMsg = new StringBuffer();
            String repJSON = "";
            if (HttpStatus.SC_OK == status) {// 发送成功，状态为200
                InputStream is = postMethod.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String str = "";
                while (null != (str = br.readLine())) {
                    repMsg.append(str);
                }

                repJSON = new String(repMsg.toString().getBytes("UTF-8"));

                if (DataUtil.isNullStr(repJSON)) {
                    repJSON = "{}";
                }
                logger.info("接收的响应报文： \r\n" + repJSON);
            }
            endTime = System.currentTimeMillis();
            logger.info("调用远程服务花费时间(单位：毫秒)：" + (endTime - startTime));

            jsonMap = JSONObject.parseObject(repJSON, Map.class);
        } catch (MalformedURLException e) {
            retMsg = "调用[" + requestUrl + "]请求地址异常！";
            logger.error(SysErr.E_DEFAULT, "调用[" + requestUrl + "]请求地址异常！");
        } catch (IOException e) {
            retMsg = "调用[" + requestUrl + "]网络IO异常！";
            logger.error(SysErr.E_DEFAULT, "调用[" + requestUrl + "]网络IO异常！");
        } catch (BaseException e) {
            logger.error(SysErr.E_DEFAULT, "调用[" + requestUrl + "]失败，"+retMsg);
        }  catch (Exception e) {
            retMsg = "调用[" + requestUrl + "]发生未知异常！";
            logger.error(SysErr.E_DEFAULT, "调用[" + requestUrl + "]发生未知异常！");
        } finally {
            // 关闭httpClient
            if(null!=postMethod){
                postMethod.releaseConnection();
            }
            if(null!=httpClient){
                httpClient.getHttpConnectionManager().closeIdleConnections(0);
            }
        }
        if (null==jsonMap){
            jsonMap = Maps.newHashMap();
        }
        return jsonMap;
    }
}
