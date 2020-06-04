package com.hanfu.user.center.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.hanfu.user.center.dao.HfMessageInstanceMapper;
import com.hanfu.user.center.dao.HfMessageTemplateMapper;
import com.hanfu.user.center.manual.model.MessageType.MessageContentTypeEnum;
import com.hanfu.user.center.manual.model.MessageType.MessageTypeEnum;
import com.hanfu.user.center.model.HfMessageInfo;
import com.hanfu.user.center.model.HfMessageInstance;
import com.hanfu.user.center.model.HfMessageInstanceExample;
import com.hanfu.user.center.model.HfMessageTemplate;
import com.hanfu.user.center.model.HfMessageTemplateExample;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.utils.response.handler.ResponseUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class GetMessageCode {
	
	@Autowired
	JavaMailSender javaMailSender;
	@Autowired
	JedisPool jedisPool;
	@Autowired
	HfMessageTemplateMapper hfMessageTemplateMapper;
	@Autowired
	HfMessageInstanceMapper hfMessageInstanceMapper;
	public static GetMessageCode getMessageCode;
	
	@PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {  
		getMessageCode = this;  
		getMessageCode.jedisPool = this.jedisPool;
		getMessageCode.javaMailSender = this.javaMailSender;
		getMessageCode.hfMessageTemplateMapper = this.hfMessageTemplateMapper;
        // 初使化时将已静态化的testService实例化
    }  
	
    /**
     * 阿里云短信服务：
     * 注意：需要 签名名称、模版CODE 以及 RAM访问控制中的 AccessKeyID 和 AccessKeySecret
     */
    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";
    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    
    private static Integer code;
    public static Integer sendSms(List<HfMessageInfo> infos,Map<String, String> map) throws ClientException{
    	Map<String, Object> maps = new HashMap<String, Object>();
    	String contentType = map.get("type");
    	String telephone = map.get("phone");
    	if(MessageContentTypeEnum.LOGIN.getMessageContentType().equals(contentType)) {
    		code = setNewcode();
    	}
    	for (int i = 0; i < infos.size(); i++) {
    		HfMessageInfo info = infos.get(i);
    		System.out.println("进入发信息，，，类型"+info.getType());
    		if(MessageTypeEnum.SHORT_BREATH.getMessageType().equals(info.getType())) {
    			maps = getInstance(info.getId(),contentType);
    			if(maps == null) {
    				continue;
    			}
        		Integer result = shortBreath(telephone,info,(HfMessageTemplate)maps.get("template"),map,(HfMessageInstance)maps.get("instance"));
        	}
        	if(MessageTypeEnum.EMAIL.getMessageType().equals(info.getType())) {
        		maps = getInstance(info.getId(),contentType);
        		if(maps == null) {
        			continue;
    			}
        		if(!StringUtils.isEmpty(map.get("email"))) {
        			Integer result = email(telephone,info,(HfMessageTemplate)maps.get("template"),map,(HfMessageInstance)maps.get("instance"));
        		}
        	}
		}
        return 1;
    }
    
    public static Integer shortBreath(String telephone,HfMessageInfo info, HfMessageTemplate template, Map<String, String> map,
    		HfMessageInstance instance) throws ClientException {
    	 //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", info.getAccessKeyId(), info.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
//        Integer code = setNewcode();
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(telephone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(info.getSignName());    // TODO 修改成自己的
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(instance.getTemplateId());    // TODO 修改成自己的
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        String[] str = instance.getTemplateParam().split(",");
        StringBuilder builder = new StringBuilder("{");
        for (int i = 0; i < str.length; i++) {
			builder.append("\"");
			builder.append(str[i]);
			builder.append("\"");
			builder.append(":");
			builder.append("\"");
			builder.append(findData(telephone,template.getType(),str[i],map));
			System.out.println("我是大类型"+template.getType());
			System.out.println("我是内容类型"+str[i]);
			builder.append("\"");
			builder.append(",");
		}
        builder.deleteCharAt(builder.toString().length()-1);
        builder.append("}");
//        request.setTemplateParam("{\""+ template.getTemplateParam() +"\":\"" + code + "\"}");
        System.out.println("我是短信模板参数"+builder.toString());
        request.setTemplateParam(builder.toString());
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            System.out.println("短信发送成功！");
        } else {
        	System.out.println(sendSmsResponse.getCode());
            System.out.println("短信发送失败！");
        }
    	return 1;
    }
    
    public static Integer email(String telephone,HfMessageInfo info, HfMessageTemplate template, Map<String, String> map,
    		HfMessageInstance instance) throws ClientException {
    	String[] str = instance.getTemplateParam().split(",");
        StringBuilder builder = new StringBuilder("{");
        String ss = instance.getContent();
        for (int i = 0; i < str.length; i++) {
        	String s = findData(telephone,template.getType(),str[i],map);
        	System.out.println(s);
        	ss = ss.replaceAll("\\$\\{"+str[i]+"\\}", s);
        	System.out.println(ss);
        }
    	SimpleMailMessage msg = new SimpleMailMessage();
		msg.setSubject("Test subject");
		msg.setText(ss);
		// 发送邮件的邮箱
		msg.setFrom("2451203734@qq.com");
		msg.setSentDate(new Date());
		// 接受邮件的邮箱
		msg.setTo(map.get("email"));
		getMessageCode.javaMailSender.send(msg);
    	return 1;
    }
    
    public static Map<String, Object> getInstance(Integer messageId,String contentType){
    	HfMessageTemplateExample example = new HfMessageTemplateExample();
		example.createCriteria().andMessageIdEqualTo(messageId).andTypeEqualTo(contentType)
				.andIsDeletedEqualTo((byte) 1);
		List<HfMessageTemplate> list = getMessageCode.hfMessageTemplateMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		HfMessageTemplate template = list.get(0);
		HfMessageInstanceExample example2 = new HfMessageInstanceExample();
		example2.createCriteria().andTemplateTypeIdEqualTo(template.getId()).andIsDeletedEqualTo((byte) 1);
		List<HfMessageInstance> list2 = getMessageCode.hfMessageInstanceMapper.selectByExample(example2);
		if (CollectionUtils.isEmpty(list2)) {
			return null;
		}
		HfMessageInstance instance = list2.get(0);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("template", template);
		map.put("instance", instance);
		return map;
    }

    public static Integer setNewcode() {
        Integer newcode = (int) (Math.round((Math.random()+1) * 1000));  //每次调用生成一位四位数的随机数
        return newcode;
    }
    
    public static String findData(String telephone, String type, String contentType, Map<String, String> map) {
    	String result = "";
    	if(MessageContentTypeEnum.LOGIN.getMessageContentType().equals(type)) {
    		switch (contentType) {
			case "code":
				result = String.valueOf(code);
				Jedis jedis = new Jedis();
				jedis = getMessageCode.jedisPool.getResource();
				jedis.set(telephone, result);
				jedis.expire(telephone, 300);
				if(jedis != null) {
					jedis.close();
				}
				return result;
			default:
				break;
			}
    	}
    	if(MessageContentTypeEnum.ORDER_CREATE.getMessageContentType().equals(type)) {
    		switch (contentType) {
			case "orderId":
				result = map.get("orderId");
				System.out.println("orderId-----------"+result);
				return result;
			case "username":
				result = map.get("username");
				return result;
			case "total":
				double a = Double.valueOf(map.get("total"))/100;
				result = String.valueOf(a);
				return result;
			default:
				break;
			}
    	}
    	return null;
    }
}


