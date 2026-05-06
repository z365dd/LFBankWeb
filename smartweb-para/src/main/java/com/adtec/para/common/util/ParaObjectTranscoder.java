/**
 * 系统名称: 微服务框架
 * 模块名称: redis序列化对象
 * 类  名  称: ObjectTranscoder.java
 * 软件版权: 北京先进数通信息技术股份公司
 * 开发人员: chenyl <br>
 * 开发时间: 2018年6月4日 下午4:04:49<br>
 * 系统版本: V1.0.0<br>
 ** 修改记录:
 * 修改日期                            修改人员          修改说明 <br>
 * ========     ======  ============================================
 * 
 * ========     ======  ============================================
 */
package com.adtec.para.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author chenyl
 * List、Map对象直接存redis需要序列化，取时需要反序列化
 */
public class ParaObjectTranscoder {
	private final static Log log = LogFactory.getLog(ParaObjectTranscoder.class);
	/**
	 * 对象序列化
	 * @param value
	 * @return
	 */
	public static byte[] serialize(Object value) {    
        if (value == null) {
        	log.error("值为空无法序列化");
            throw new NullPointerException("Can't serialize null");    
        }    
        byte[] rv=null;    
        ByteArrayOutputStream bos = null;    
        ObjectOutputStream os = null;    
        try {    
            bos = new ByteArrayOutputStream();    
            os = new ObjectOutputStream(bos);    
            os.writeObject(value);    
            os.close();    
            bos.close();    
            rv = bos.toByteArray();    
        } catch (IOException e) {
        	log.error("输入对象没有实现序列化接口Serializable", e);
            throw new IllegalArgumentException("Non-serializable object", e);    
        } finally {    
            try {  
                 if(os!=null)os.close();  
                 if(bos!=null)bos.close();  
            }catch (Exception e2) {  
             e2.printStackTrace();  
            }    
        }    
        return rv;    
    }    
  
	/**
	 * 对象反序列化
	 * @param in
	 * @return
	 */
    public static Object deserialize(byte[] in) {    
        Object rv=null;    
        ByteArrayInputStream bis = null;    
        ObjectInputStream is = null;    
        try {    
            if(in != null) {    
                bis=new ByteArrayInputStream(in);    
                is=new ObjectInputStream(bis);    
                rv=is.readObject();    
                is.close();    
                bis.close();    
            }    
        } catch (Exception e) {
        	log.error("反序列化失败", e);
            e.printStackTrace();  
         }finally {    
             try {  
                 if(is!=null)is.close();  
                 if(bis!=null)bis.close();  
             } catch (Exception e2) {  
                 e2.printStackTrace();  
             }  
         }  
        return rv;    
    } 
}
