package cn.qkmango.ccms.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json工具类
 *
 * @author qkmango
 * @version 1.0
 * @date 2023-02-02 17:44
 */
public class JsonUtil {
    private final ObjectMapper objectMapper;

    public JsonUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 将对象转换为json字符串
     *
     * @param obj 对象
     * @return json字符串
     */
    public String toJSONString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("对象转换为json字符串失败");
        }
    }

}
