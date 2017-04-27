package com.sise.bao.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by rola on 2017/4/25.
 */
public class Struts2Utils {
    // -- header 常量定义 --//
    private static final String ENCODING_PREFIX = "encoding";
    private static final String NOCACHE_PREFIX = "no-cache";
    private static final String ENCODING_DEFAULT = "UTF-8";
    private static final boolean NOCACHE_DEFAULT = true;

    // -- content-type 常量定义 --//
    private static final String TEXT_TYPE = "text/plain";
    private static final String JSON_TYPE = "application/json";
    private static final String XML_TYPE = "text/xml";
    private static final String HTML_TYPE = "text/html";
    private static final String JS_TYPE = "text/javascript";
    private static final String IMAGE_TYPE = "image/png";

    /**
     * 取得HttpSession的简化函数.
     */
    public static HttpSession getSession() {
        return ServletActionContext.getRequest().getSession();
    }

    /**
     * 取得HttpRequest的简化函数.
     */
    public static HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    /**
     * 取得HttpResponse的简化函数.
     */
    public static HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    /**
     * 取得Request Parameter的简化方法.
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }
    /**
     * 直接输出内容的简便函数.
     *
     * eg. render("text/plain", "hello", "encoding:UTF-8"); render("text/plain",
     * "hello", "no-cache:false"); render("text/plain", "hello",
     * "encoding:UTF-8", "no-cache:false");
     *
     * @param headers
     *            可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
     */
    public static void render(final String contentType, final String content,
                              final String... headers) {
        try {
            // 分析headers参数
            String encoding = ENCODING_DEFAULT;
            boolean noCache = NOCACHE_DEFAULT;
            for (String header : headers) {
                String headerName = StringUtils.substringBefore(header, ":");
                String headerValue = StringUtils.substringAfter(header, ":");

                if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
                    encoding = headerValue;
                } else if (StringUtils.equalsIgnoreCase(headerName,
                        NOCACHE_PREFIX)) {
                    noCache = Boolean.parseBoolean(headerValue);
                } else
                    throw new IllegalArgumentException(headerName
                            + "不是一个合法的header类型");
            }

            HttpServletResponse response = ServletActionContext.getResponse();

            // 设置headers参数
            String fullContentType = contentType + ";charset=" + encoding;
            response.setContentType(fullContentType);
            if (noCache) {
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0);
            }

            response.getWriter().write(content);
            response.getWriter().flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接输出JSON.
     *
     * @param jsonString
     *            json字符串.
     * @see #render(String, String, String...)
     */
    public static void renderJson(final String jsonString,
                                  final String... headers) {
        render(JSON_TYPE, jsonString, headers);
    }

    /**
     * 直接输出JSON.
     *
     * @param object
     *            Java对象,将被转化为json字符串.
     * @see #render(String, String, String...)
     */
    public static void renderJson(final Object object, final String... headers) {
        String jsonString = JSONObject.toJSONString(object);
        render(JSON_TYPE, jsonString, headers);
    }

    /**
     * 直接输出JSON.
     *
     * @param map
     *            Map对象,将被转化为json字符串.
     * @see #render(String, String, String...)
     */
    @SuppressWarnings("unchecked")
    public static void renderJson(final Map map, final String... headers) {
        String jsonString = JSON.toJSONString(map);
        render(JSON_TYPE, jsonString, headers);
    }

}
