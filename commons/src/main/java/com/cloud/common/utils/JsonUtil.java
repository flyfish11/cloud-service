package com.cloud.common.utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.util.List;

/***
 * @author: yuansq
 * @date: 2018-08-09 21:34
 * @param:
 * @desc: JSON操作
 * @return: 
 */
public class JsonUtil
{
    private static final SerializerFeature[] features = { SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.UseISO8601DateFormat };

    private static final SerializerFeature[] featuresWithTab = { SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.UseISO8601DateFormat, SerializerFeature.WriteTabAsSpecial };

    public JsonUtil() {}

    public static String toString(Object object)
    {
        String result = JSON.toJSONString(object, features);
        return result.replaceAll("\t", "");
    }
    public static String toStringWithTab(Object object)
    {
        return JSON.toJSONString(object, featuresWithTab);
    }

    public static List toList(String jsonText, Class clazz)
    {
        return JSON.parseArray(jsonText, clazz);
    }

    public static Object toObject(String jsonText, Class clazz)
    {
        return JSON.parseObject(jsonText, clazz);
    }
}

