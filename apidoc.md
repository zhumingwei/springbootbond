#接口文档
    HOST:localhost:8080
    webapp: /bond
    除了登录注册意外其他接口都需要在header中传递token参数。key为IVAKA
###用户登录 POST `/user/login`
```接口参数，手机号为必须字段，有用户密码优先使用密码验证。没有密码使用短信验证码验证```
<table>
<tr><td>参数</td><td>值</td></tr>
<tr><td>phonenum</td><td>用户手机号</td></tr>
<tr><td>code</td><td>短信验证码</td></tr>
<tr><td>password</td><td>用户密码</td></tr>
</table>

```返回结果```
```$xslt
   {
       "code": 20000,
       "data": {
           "nickname": "朱明伟",
           "token": "Y7SuGerauWb9PNp9GHeqcuVAWQKGmoTm",
           "phonenum": "17621791737",
           "uid": 100000,
           "avatar": "http://p9yjgmoug.bkt.clouddn.com/5000000025385-4KpbGt7bNRVkQcYvGRqV9VngvU_Z7rV_"
       },
       "message": "success"
   }
```

###用户注册 POST `/user/register`
```接口参数，三个参数均为必须字段```

<table>
<tr><td>参数</td><td>值</td></tr>
<tr><td>phonenum</td><td>用户手机号</td></tr>
<tr><td>code</td><td>短信验证码</td></tr>
<tr><td>password</td><td>用户密码</td></tr>
</table>

```返回结果```
```$xslt
   {
       "code": 20000,
       "data":注册成功,
       "message": "success"
   }
```

###用户信息 POST，GET `/user/info`

```返回结果```
```
   {
       "code": 20000,
       "data": {
           "avatar": "http://p9yjgmoug.bkt.clouddn.com/5000000025385-4KpbGt7bNRVkQcYvGRqV9VngvU_Z7rV_",
           "cid": 0,
           "createby": 0,
           "createdate": 1529724007,
           "id": 100000,
           "is_delete": 0,
           "nickname": "朱明伟",
           "updateby": 0,
           "updatedate": 1529724007,
           "user_level": 999,
           "user_state": 0
       },
       "message": "success"
   }
```


###修改用户信息 POST `/user/update`  content-type: application/json'
```接口参数```

```
   {
   	"avatar":"图片地址路径",
   	"nickname":"用户昵称"
   }

```

```返回结果```
```
  {
      "code": 20000,
      "data": "修改成功",
      "message": "success"
  }
```



###修改用户信息 POST `/user/modify/password`  
```接口参数，三个参数均为必须字段```


  <table>
  <tr><td>参数</td><td>值</td></tr>
  <tr><td>phonenum</td><td>用户手机号</td></tr>
  <tr><td>code</td><td>短信验证码</td></tr>
  <tr><td>password</td><td>用户密码</td></tr>
  </table>



```返回结果```
```
  {
      "code": 20000,
      "data": "修改成功",
      "message": "success"
  }
```

###获取上传token GET `/file/gettoken`  

```返回结果```
```
{
    "code": 20000,
    "data": {
        "filename": "826b07c5e610c4f3b0d9d6c0c0b9ccb2",
        "url": "http://p9yjgmoug.bkt.clouddn.com/826b07c5e610c4f3b0d9d6c0c0b9ccb2",
        "token": "uB-9nDVe9nZ6b-yl2EbEZf-WZHWICYwl_Se_whE5:UgsF7bPHTPVex5P4aRtPhQ24wMw=:eyJzY29wZSI6InpodW1pbmd3ZWktcDo4MjZiMDdjNWU2MTBjNGYzYjBkOWQ2YzBjMGI5Y2NiMiIsImRlYWRsaW5lIjoxNTI5NzI4NzAwfQ=="
    },
    "message": "success"
}
```

###上传地址 POST 七牛地址 'http://upload-z0.qiniup.com/' 
```接口参数```
```表单提交file，token为必须参数```
    
###发送验证码 POST `/msg/sendmsg/{type}`
```url参数```

  <table>
  <tr><td>type</td><tr>
  <tr><td>register</td><td>注册</td><tr>
  <tr><td>login</td><td>登录</td><tr>
  <tr><td>changePwd</td><td>修改密码</td><tr>
  </table>




    



