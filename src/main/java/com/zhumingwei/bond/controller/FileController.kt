package com.zhumingwei.bond.controller

import com.qiniu.common.QiniuException
import com.qiniu.common.Zone
import com.qiniu.storage.BucketManager
import com.qiniu.storage.Configuration
import com.qiniu.util.Auth
import com.zhumingwei.bond.BondConstant
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.util.HashMap

/**
 * @author zhumingwei
 * @date 2018/6/22 上午10:35
 */
@RestController
@RequestMapping("/file")
class FileController : BaseController() {


    //不能用同样的名字，7牛替换图片没有那么实时
    val token: Map<String, String>
        @RequestMapping("/gettoken")
        get() {
            val filename = "serverfile" + System.currentTimeMillis()
            val map = HashMap<String, String>()
            map["token"] = auth.uploadToken(bucket, filename)
            map["filename"] = filename
            map["url"] = "http://p9yjgmoug.bkt.clouddn.com/$filename"
            return map
        }

    companion object {

        internal var bucket = "zhumingwei-p"

        internal var auth: Auth
        internal var bucketManager: BucketManager

        init {
            auth = Auth.create(BondConstant.NIU7_ACCESSKEY, BondConstant.NIU7_SECRETKEY)
            val cfg = Configuration(Zone.zone0())
            bucketManager = BucketManager(auth, cfg)
        }

        fun delete(key: String) {
            try {
                bucketManager.delete(bucket, key)
            } catch (ex: QiniuException) {
                //如果遇到异常，说明删除失败
                System.err.println(ex.code())
                System.err.println(ex.response.toString())
            }

        }
    }
}
