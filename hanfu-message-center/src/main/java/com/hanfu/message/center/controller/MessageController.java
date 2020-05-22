package com.hanfu.message.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.message.center.service.WxTemplateService;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import com.hanfu.utils.response.handler.ResponseEntity.BodyBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/message")
@Api
public class MessageController {
	@Autowired
	WxTemplateService wxTemplateService;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@RequestMapping(path = "/sendMessage", method = RequestMethod.GET)
	@ApiOperation(value = "发送模板消息", notes = "发送消息")
	public ResponseEntity<JSONObject> sendMessage(String message01, String message02) throws Exception {
		BodyBuilder builder = ResponseUtils.getBodyBuilder();
		if (StringUtils.isEmpty(message01) || StringUtils.isEmpty(message02)) {
			builder.body(ResponseUtils.getResponseBody("参数确实"));
		}

		wxTemplateService.sendTemplateToUsers(message01, message02);
		return builder.body(ResponseUtils.getResponseBody("消息发送成功"));
	}

	@RequestMapping(value = "sendDirectMessage", method = RequestMethod.GET)
	@ApiOperation(value = "消息推送", notes = "消息推送")
	public String sendDirectMessage() {
		String messageId = String.valueOf(UUID.randomUUID());
		String messageData = "今天放假今天放假今天放假";
		String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		Map<String, Object> map = new HashMap<>();
		map.put("messageId", messageId);
		map.put("messageData", messageData);
		map.put("createTime", createTime);
		// 将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange

		rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
		return "ok";
	}

	@RabbitListener(queues = "TestDirectQueue")
	@RabbitHandler
	public void process(Map testMessage) {
		try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

		System.out.println("DirectReceiver消费者收到消息  : " + testMessage.toString());
	}
}
