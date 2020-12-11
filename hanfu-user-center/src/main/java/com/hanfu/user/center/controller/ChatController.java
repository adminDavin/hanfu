package com.hanfu.user.center.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hanfu.user.center.config.WebSocketTest;
import com.hanfu.user.center.dao.HfMessageMapper;
import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.user.center.manual.model.ChatWindow;
import com.hanfu.user.center.manual.model.MiniMessage;
import com.hanfu.user.center.model.HfMessage;
import com.hanfu.user.center.model.HfMessageExample;
import com.hanfu.user.center.model.HfUser;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shihao
 * @Title: ChatController
 * @ProjectName Second-order-center
 * @Description: 聊天
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/Chat")
@CrossOrigin
public class ChatController {
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private HfMessageMapper hfMessageMapper;
    @Autowired
    private WebSocketTest webSocketTest;
    @Autowired
    private HfUserMapper hfUserMapper;
    @ApiOperation(value = "发送公告消息", notes = "发送公告消息")
    @RequestMapping(value = "/announcement", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> announcement(String message) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        webSocketTest.onMessage(message);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "保存消息", notes = "保存消息")
    @RequestMapping(value = "/addMessage", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "headline", value = "标题", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "secondDesc", value = "消息类容", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "file", value = "展示图", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "bossId", value = "展示图", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> addMessage(
            @RequestParam(value = "headline", required = false) String headline,
            @RequestParam(value = "secondDesc", required = false) String secondDesc,
            @RequestParam(value = "bossId", required = false) Integer bossId,
            @RequestParam(value = "file", required = false) String file
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfMessage hfMessage = new HfMessage();
        hfMessage.setBossId(bossId);
        hfMessage.setHeadline(headline);
        hfMessage.setFile(file);
        hfMessage.setSecondDesc(secondDesc);
        hfMessage.setCreateTime(LocalDateTime.now());
        hfMessage.setModifyTime(LocalDateTime.now());
        hfMessage.setIsDeleted((byte) 0);
        hfMessageMapper.insertSelective(hfMessage);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "删除消息", notes = "删除消息")
    @RequestMapping(value = "/deletedMessage", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "消息id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> deletedMessage(
            @RequestParam(value = "id", required = false) Integer id
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfMessage secondMessage = new HfMessage();
        secondMessage.setId(id);
        secondMessage.setModifyTime(LocalDateTime.now());
        secondMessage.setIsDeleted((byte) 1);
        hfMessageMapper.updateByPrimaryKeySelective(secondMessage);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "修改消息", notes = "修改消息")
    @RequestMapping(value = "/updateMessage", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "headline", value = "标题", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "secondDesc", value = "消息类容", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "file", value = "展示图", required = true, type = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "消息id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> updateMessage(
            @RequestParam(value = "headline", required = false) String headline,
            @RequestParam(value = "secondDesc", required = false) String secondDesc,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "file", required = false) String file
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfMessage hfMessage = new HfMessage();
        hfMessage.setId(id);
        hfMessage.setHeadline(headline);
        hfMessage.setFile(file);
        hfMessage.setSecondDesc(secondDesc);
        hfMessage.setCreateTime(LocalDateTime.now());
        hfMessage.setModifyTime(LocalDateTime.now());
        hfMessage.setIsDeleted((byte) 0);
        hfMessageMapper.updateByPrimaryKeySelective(hfMessage);
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    @ApiOperation(value = "查询消息", notes = "查询消息")
    @RequestMapping(value = "/selectMessage", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectMessage(
            Integer bossId
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfMessageExample secondMessageExample = new HfMessageExample();
        secondMessageExample.createCriteria().andIsDeletedEqualTo((byte) 0)
        .andBossIdEqualTo(bossId);
        List<HfMessage> secondMessages =
                hfMessageMapper.selectByExampleWithBLOBs(secondMessageExample);

        return builder.body(ResponseUtils.getResponseBody(secondMessages));
    }

    @ApiOperation(value = "查询小程序消息", notes = "查询小程序消息")
    @RequestMapping(value = "/selectMiniMessage", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectMiniMessage(
            Integer bossId
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfMessageExample secondMessageExample = new HfMessageExample();
        secondMessageExample.createCriteria().andIsDeletedEqualTo((byte) 0)
        .andBossIdEqualTo(bossId)
        ;
        secondMessageExample.setOrderByClause("create_time desc");
        List<HfMessage> hfMessages =
                hfMessageMapper.selectByExampleWithBLOBs(secondMessageExample);
        List<MiniMessage> miniMessages = new ArrayList<>();
        hfMessages.forEach(hfMessage -> {
            MiniMessage miniMessage = new MiniMessage();
            miniMessage.setCreateTime(hfMessage.getCreateTime());
            miniMessage.setHfMessage(hfMessage);
            miniMessages.add(miniMessage);
        });
        return builder.body(ResponseUtils.getResponseBody(miniMessages));
    }

    @ApiOperation(value = "查询消息详情", notes = "查询消息详情")
    @RequestMapping(value = "/selectMessageDetails", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> selectMessageDetails(
            Integer messageId
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        HfMessage hfMessage =
                hfMessageMapper.selectByPrimaryKey(messageId);
        return builder.body(ResponseUtils.getResponseBody(hfMessage));
    }
    //用户
//    @Autowired
//    private SecondUserMapper secondUserMapper;

    /**
     * 建立聊天窗口
     */
    @ApiOperation(value = "建立聊天窗口", notes = "建立聊天窗口")
    @RequestMapping(value = "/addChatWindow", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "ByUserId", value = "收消息用户id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> addChatWindow(
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "ByUserId", required = false) Integer byUserId
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        List<ChatWindow> chatWindows = new ArrayList<>();
        HfUser secondUser = hfUserMapper.selectByPrimaryKey(userId);
        HfUser bySecondUser = hfUserMapper.selectByPrimaryKey(byUserId);
        ChatWindow chatWindow = new ChatWindow();
        chatWindow.setUserId(userId);
        chatWindow.setUserName(secondUser.getNickName());
        chatWindow.setUserFile(secondUser.getFileId());
        chatWindow.setByUserId(byUserId);
        chatWindow.setByUserName(bySecondUser.getNickName());
        chatWindow.setByUserFile(bySecondUser.getFileId());
        chatWindow.setCreateTime(LocalDateTime.now());
        chatWindow.setModifyTime(LocalDateTime.now());
        Object object =
                redisTemplate.opsForValue().get(String.valueOf(userId)+"window");
        if (object!=null){
            System.out.println(chatWindows);
            chatWindows = JSON.parseObject(String.valueOf(object), new TypeReference<List<ChatWindow>>(){});
            //被建立用户id
            Set<Integer> byUser = chatWindows.stream().map(ChatWindow::getByUserId).collect(Collectors.toSet());
            chatWindows.forEach(chatWindow1 ->{
                if (chatWindow1.getByUserId().equals(byUserId)){
                    chatWindow1.setModifyTime(LocalDateTime.now());
                }
            });
            //存在
            boolean result =
            byUser.contains(byUserId);
            if (!result){
                chatWindows.add(chatWindow);
            }
            String json = JSONObject.toJSONString(chatWindows);
            redisTemplate.opsForValue().set(String.valueOf(userId)+"window", json);
        } else {
            chatWindows.add(chatWindow);
            String json = JSONObject.toJSONString(chatWindows);
            redisTemplate.opsForValue().set(String.valueOf(userId)+"window", json);
        }

        return builder.body(ResponseUtils.getResponseBody(0));
    }
//    /**
//     * 设置未读
//     */
//    @ApiOperation(value = "设置未读", notes = "设置未读")
//    @RequestMapping(value = "/setUnread", method = RequestMethod.POST)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
//            @ApiImplicitParam(paramType = "query", name = "ByUserId", value = "收消息用户id", required = true, type = "Integer"),
//    })
//    public ResponseEntity<JSONObject> setUnread(
//            @RequestParam(value = "userId", required = false) Integer userId,
//            @RequestParam(value = "Unread", required = false) Integer Unread,
//            @RequestParam(value = "ByUserId", required = false) Integer byUserId
//    ) throws Exception {
//        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//        List<ChatWindow> chatWindows = new ArrayList<>();
//        SecondUser secondUser = secondUserMapper.selectByPrimaryKey(userId);
//        SecondUser bySecondUser = secondUserMapper.selectByPrimaryKey(byUserId);
//        ChatWindow chatWindow = new ChatWindow();
//
//        chatWindow.setUserId(userId);
//        chatWindow.setUserName(secondUser.getNickName());
//        chatWindow.setUserFile(secondUser.getFile());
//        chatWindow.setByUserId(byUserId);
//        chatWindow.setByUserName(bySecondUser.getNickName());
//        chatWindow.setByUserFile(bySecondUser.getFile());
//        chatWindow.setModifyTime(LocalDateTime.now());
//        System.out.println(chatWindows);
//        Object object =
//                redisTemplate.opsForValue().get(String.valueOf(userId)+"window");
//        chatWindows = JSON.parseObject(String.valueOf(object), new TypeReference<List<ChatWindow>>(){});
//        //被建立用户id
//        Set<Integer> byUser = chatWindows.stream().map(ChatWindow::getByUserId).collect(Collectors.toSet());
//        chatWindows.forEach(chatWindow1 ->{
//            if (chatWindow1.getByUserId().equals(byUserId)){
//                chatWindow1.setUnread(Unread);
//                chatWindow1.setModifyTime(LocalDateTime.now());
//            }
//        });
//        //存在
//        boolean result =
//                byUser.contains(byUserId);
//        if (!result){
//            chatWindows.add(chatWindow);
//        }
//        String json = JSONObject.toJSONString(chatWindows);
//        redisTemplate.opsForValue().set(String.valueOf(userId)+"window", json);
//
//
//        return builder.body(ResponseUtils.getResponseBody(0));
//    }
    /**
     * 删除聊天窗口
     */
    @ApiOperation(value = "删除聊天窗口", notes = "删除聊天窗口")
    @RequestMapping(value = "/delChatWindow", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "ByUserId", value = "收消息用户id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> delChatWindow(
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "ByUserId", required = false) Integer ByUserId
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        System.out.println(userId);
        System.out.println(ByUserId);
        List<ChatWindow> chatWindows = new ArrayList<>();
        Object object =
                redisTemplate.opsForValue().get(String.valueOf(userId)+"window");
        chatWindows = JSON.parseObject(String.valueOf(object), new TypeReference<List<ChatWindow>>(){});
        //        redisTemplate.delete(String.valueOf(userId)+"window");
        for (int i=0;i<chatWindows.size();i++){
            if (chatWindows.get(i).getUserId().equals(userId) && chatWindows.get(i).getByUserId().equals(ByUserId)){
                chatWindows.remove(i);

                String json = JSONObject.toJSONString(chatWindows);
                System.out.println(json);
                redisTemplate.opsForValue().set(String.valueOf(userId)+"window",json);
            }
        }
        return builder.body(ResponseUtils.getResponseBody(0));
    }
    /**
     * 查询聊天窗口
     */
    @ApiOperation(value = "查询聊天窗口", notes = "查询聊天窗口")
    @RequestMapping(value = "/selChatWindow", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户id", required = true, type = "Integer"),
    })
    public ResponseEntity<JSONObject> selChatWindow(
            @RequestParam(value = "userId", required = false) Integer userId
    ) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        Object object =
        redisTemplate.opsForValue().get(String.valueOf(userId)+"window");
        System.out.println(object);
        List<ChatWindow> chatWindows = new ArrayList<>();
        if (object!=null){
            chatWindows =
                    JSON.parseObject(String.valueOf(object), new TypeReference<List<ChatWindow>>(){});
        }
        List<ChatWindow> latestItem = chatWindows.stream().sorted(Comparator.comparing(ChatWindow::getModifyTime).reversed()).collect(Collectors.toList());
        latestItem.forEach(latestItem1->{
            LocalDateTime time = latestItem1.getModifyTime();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String createTime = dateTimeFormatter.format(time);
            LocalDateTime localDateTime = LocalDateTime.parse(createTime,dateTimeFormatter);
            latestItem1.setModifyTime(localDateTime);
        });
        return builder.body(ResponseUtils.getResponseBody(latestItem));
    }


    public static void main(String[] args) {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createTime = dateTimeFormatter.format(time);
        System.out.println(createTime);
        LocalDateTime localDateTime = LocalDateTime.parse(createTime,dateTimeFormatter);
        ChatWindow chatWindow = new ChatWindow();
        chatWindow.setCreateTime(localDateTime);
        System.out.println(chatWindow);
    }
}
