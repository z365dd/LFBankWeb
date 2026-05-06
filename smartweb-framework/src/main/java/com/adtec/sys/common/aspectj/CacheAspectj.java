package com.adtec.sys.common.aspectj;

import com.adtec.framework.common.util.CacheUtil;
import com.adtec.framework.common.util.ClassUtil;
import com.adtec.sys.common.annotation.CacheMethod;
import com.adtec.sys.common.annotation.CacheParams;
import com.adtec.sys.common.annotation.GetCache;
import com.adtec.sys.common.annotation.UpdateCache;
import com.adtec.sys.common.persistence.BaseDO;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 缓存切面：对加上@GetCache的方法进行拦截，优先从缓存里拿数据，若缓存里没有，则执行原方法，并把返回值添加到缓存
 *
 * @author lijunbin
 * @version 1.0
 * @date 2019-10-17 16:54
 */
@Aspect
@Component
public class CacheAspectj {

    private static final String PATTERN = "#\\{[0-9a-zA-Z]+}$";

    @Pointcut("@annotation(com.adtec.sys.common.annotation.GetCache)")
    public void pointcut() {
    }

    @Pointcut("@annotation(com.adtec.sys.common.annotation.UpdateCache)")
    public void updateCachePointcut() {
    }

    @Pointcut("@annotation(com.adtec.sys.common.annotation.CacheMethod)")
    public void cacheMethodPointcut() {
    }

    @Around("updateCachePointcut()")
    public Object aroundUpdateCache(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        UpdateCache updateCache = method.getAnnotation(UpdateCache.class);
        String cacheType = updateCache.cacheType();
        CacheManager cacheManager = CacheUtil.getCacheManager();
        Cache cache = cacheManager.getCache(cacheType);
        if (cache == null) {
            cacheManager.addCache(cacheType);
            cache = cacheManager.getCache(cacheType);
            cache.getCacheConfiguration().setEternal(true);
        }
        cache.removeAll();
        return result;
    }

    /**
     * 环绕操作
     *
     * @param point 切入点
     * @return 原方法返回值
     * @throws Throwable 异常信息
     */
    @Around("pointcut()")
    public Object aroundExecMethod(ProceedingJoinPoint point) throws Throwable {
        Object result;
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        GetCache getCache = method.getAnnotation(GetCache.class);
        String cacheName = processCacheName(point, getCache);
        String cacheType = getCache.cacheType();
        Object obj = CacheUtil.get(cacheType, cacheName);
        if (method.getReturnType().isInstance(obj)) {
            // 缓存里找到，并且类型一致，直接返回缓存里的值
            result = obj;
        } else {
            // 缓存里没找到，继续执行方法，并把结果加到缓存里面
            result = point.proceed();
            CacheUtil.put(cacheType, cacheName, result);
        }
        return result;
    }

    @Around("cacheMethodPointcut()")
    public Object aroundCacheMethod(ProceedingJoinPoint point) throws Throwable {
        Object result;
        Method method = getMethod(point);

        CacheMethod cacheMethod = method.getAnnotation(CacheMethod.class);
        String cacheType = cacheMethod.cacheType();

        String cacheKey = cacheMethod.cacheKey();

        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(cacheKey);
        if (matcher.matches()) {
            cacheKey = cacheKey.replace("#{", "").replace("}", "");
            Object[] args = point.getArgs();
            Object cacheKeyParam = null;
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            int index = 0;
            for (Annotation[] annotations : parameterAnnotations) {
                for (Annotation annotation : annotations) {
                    if (annotation instanceof CacheParams) {
                        String fieldName = ((CacheParams) annotation).value();
                        if (cacheKey.equals(fieldName)) {
                            cacheKeyParam = args[index];
                            break;
                        }
                    }
                }
                if (null != cacheKeyParam) {
                    break;
                }
                index++;
            }
            // 如果是字符串
            if (cacheKeyParam instanceof String) {
                cacheKey = String.valueOf(cacheKeyParam);
            } else {
                cacheKey = String.valueOf(ClassUtil.getFieldValue(cacheKeyParam, cacheKey));
            }
        }
        Object obj = CacheUtil.get(cacheType, cacheKey);
        if (method.getReturnType().isInstance(obj)) {
            // 缓存里找到，并且类型一致，直接返回缓存里的值
            result = obj;
        } else {
            // 缓存里没找到，继续执行方法，并把结果加到缓存里面
            result = point.proceed();
            if (null != result && !"null".equals(cacheKey)) {
                CacheUtil.put(cacheType, cacheKey, result);
            }
        }
        return result;
    }

    public Method getMethod(ProceedingJoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        return methodSignature.getMethod();
    }

    public String processCacheName(ProceedingJoinPoint point, GetCache getCache) {
        StringBuilder cacheName = new StringBuilder();
        Object[] args = point.getArgs();
        if (args.length == 1 && args[0] instanceof BaseDO) {
            BaseDO baseDO = (BaseDO) args[0];
            cacheName = new StringBuilder(baseDO.getId());
        } else if (args.length > 1) {
            boolean allString = true;
            for (Object object : args) {
                if (!(object instanceof String)) {
                    allString = false;
                    break;
                }
            }
            if (allString) {
                for (Object object : args) {
                    cacheName.append("_").append(object);
                }
            }
        } else {
            cacheName = new StringBuilder(getCache.cacheName());
        }
        return cacheName.toString();
    }

}
