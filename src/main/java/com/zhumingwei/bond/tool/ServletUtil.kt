package com.zhumingwei.bond.tool

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.*
import com.zhumingwei.bond.tool.response.BaseResponse

import javax.servlet.http.HttpServletResponse
import java.io.IOException
import java.io.PrintWriter
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.Date
import java.util.HashMap

/**
 * @author zhumingwei
 * @date 2018/6/17 下午2:37
 */
object ServletUtil {
    //响应编码
    private val RESPONSE_CHARACTERENCODING = "utf-8"
    //响应的ContentType内容
    private val RESPONSE_CONTENTTYPE = "application/json"

    init {
        SerializeConfig.getGlobalInstance().put(Date::class.java, ObjectSerializer { serializer, `object`, fieldName, fieldType, features ->
            if (`object` == null) {
                serializer.out.writeNull()
                return@ObjectSerializer
            }

            val date = `object` as Date
            serializer.write(date.time / 1000)
        })
    }

    fun createResponse(content: BaseResponse<*>, response: HttpServletResponse): String {
        var printWriter: PrintWriter? = null
        var jsonString = ""
        try {
            response.characterEncoding = RESPONSE_CHARACTERENCODING
            response.contentType = RESPONSE_CONTENTTYPE
            response.status = 200
            printWriter = response.writer
            jsonString = JSON.toJSONString(content, SerializerFeature.WriteMapNullValue)
            printWriter!!.write(jsonString)
            printWriter.flush()
        } catch (e: Exception) {
            LogUtil.loge("createResponse failed=====" + e.message)
        } finally {
            printWriter?.close()
        }
        return jsonString
    }


}
