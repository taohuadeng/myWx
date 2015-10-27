微信Token，加密密钥存储位置
/wx/wx/src/main/resources/config.properties

微信菜单更新参考微信消息接口使用指南:
<http://mp.weixin.qq.com/wiki/index.php?title=消息接口指南>

微信测试地址
登陆页面：
test.tbc.com/wx/loginOut.do?functionName=checkLogin&weChatId=test110
端午连接（网页测试）：
http://hf.21tb.com/wx/html/front/duanwu/welcome.do

（duanwuLottery）

微信菜单设置说明：
1、通用设置如下
如：我的课程 （网页测试必须先登录才能进入）
http://hf.21tb.com/wx/checkLogin.do?functionName=myCourse
functionName：对应具体的菜单跳转action名称必须存在

菜单端午定制：
http://hf.21tb.com/wx/checkLogin.do?functionName=duanwuLottery

2、服务号定制设置如下
方案一：
http://hf.21tb.com/wx/checkLogin.do?functionName=elnIndex&corpCode=ladeng.com&weChatId=test001

corpCode:定制公司编号或者定制域名，可为空（建议必须存在）
weChatId:微信的openId,由定制方提供参数，必须存在

方案二：
要求：定制方公众号必须配置对应的网页授权（hf.21tb.com）
http://hf.21tb.com/wx/checkLogin.do?functionName=myCourse&corpCode=ladeng.com&appid=wxc701114d7e989dc4&appsecret=f1f5327ec1b4f7042e7f017f9d051331

corpCode:定制公司编号或者定制域名，可为空（建议必须存在）
appid:定制方公众号对应的appid 必须存在
appsecret:定制方公众号对应的appsecret 必须存在


企业号定制路径如下:
corpId 和corpSecret保存地址
http://v4.21tb.com/wx/qy/qyInfo.do
1、广东证券定制企业号（提供id和secret）
[id=wx4d5f0a15ddadb186][secret=PgwHtc8aMbkzE74Nz9iRUxiiQ2lEh4IKkOzIZn1BmUVs6Ss1b1iD0z79FaZGuylA]
appId=5
http://v4.21tb.com/wx/qy/qyOauth.do?corp_code=dfzq&returnUrl=myCourse
2、明远定制，对应方公司定制接口获取授权调用
http://v4.21tb.com/wx/mingYuanOauth.do?corp_code=my&returnUrl=selectCourse

微平台入口【前面通用  后面定制】
http://dev.test2.com/wx/wxIndexLogin.do
http://dev.test2.com/wx/wxIndexLogin.do?corpCode=ladeng.com