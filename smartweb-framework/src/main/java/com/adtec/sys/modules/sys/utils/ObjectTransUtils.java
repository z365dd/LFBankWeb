package com.adtec.sys.modules.sys.utils;


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
public class ObjectTransUtils {
	private final static Log log = LogFactory.getLog(ObjectTransUtils.class);
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

    /**
     * 把表字段，转成java类属性
     * @param fieldName
     * @return
     */
    public static String getPropertyName(String fieldName) {
        int c = fieldName.charAt(0);
        if (c <= 'Z' && c >= 'A') {
            fieldName = fieldName.toLowerCase();
        }
        String[] sa = fieldName.split("_");
        StringBuffer sb = new StringBuffer();
        sb.append(sa[0]);
        for (int i = 1; i < sa.length; i++) {
            sb.append(uppercaseFirstChar(sa[i]));
        }
        return sb.toString();
    }

    private static String uppercaseFirstChar(String str) {
        String ret = str;
        int c = str.charAt(0);
        if (c <= 'z' && c >= 'a') {
            c = c - 'a' + 'A';
            ret = (char) c + str.substring(1);
        }
        return ret;
    }

}
