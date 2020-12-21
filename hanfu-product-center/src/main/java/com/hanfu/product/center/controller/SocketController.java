//package com.hanfu.product.center.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.hanfu.product.center.config.SocketText;
//import com.hanfu.product.center.model.HfCategory;
//import com.hanfu.product.center.model.HfCategoryExample;
//import com.hanfu.utils.response.handler.ResponseEntity;
//import com.hanfu.utils.response.handler.ResponseUtils;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.websocket.ContainerProvider;
//import javax.websocket.Session;
//import javax.websocket.WebSocketContainer;
//import java.io.*;
//import java.net.Socket;
//import java.net.URI;
//import java.util.List;
//import java.util.UUID;
//
///**
// * @author shihao
// * @Title: SocketController
// * @ProjectName Second-order-center
// * @Description:
// * @date Created in
// * @Version: $
// */
//@CrossOrigin
//@RestController
//@RequestMapping("/socket")
//@Api
//public class SocketController {
//    private  static  String host = "192.168.1.15";
//    private  static int port = 1200;
//
//    @ApiOperation(value = "简单获取所有类目app", notes = "简单获取所有类目app")
//    @RequestMapping(value = "/getCategoryAll", method = RequestMethod.GET)
//    public ResponseEntity<JSONObject> getCategoryAll() throws Exception {
//        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
//        String host = "192.168.1.15";
//        int port = 1200;
//
//        //与服务端建立连接
//        Socket socket = new Socket(host, port);
//        socket.setOOBInline(true);
//
//        //建立连接后获取输出流
//        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
//        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
//        String uuid = UUID.randomUUID().toString();
////        log.info("uuid: {}", uuid);
//        System.out.println("uuid: {}"+ uuid);
//        outputStream.write(uuid.getBytes());
//        DataInputStream inputStream1 = new DataInputStream(socket.getInputStream());
//        String content = "";
//        while (true){
//            byte[] buff = new byte[1024];
//            inputStream.read(buff);
//            String buffer = new String(buff, "utf-8");
//            content += buffer;
////            log.info("info: {}", buff);
//            System.out.println("info: {}"+ buff);
//            File file = new File("json.json");
//            FileWriter fileWriter = new FileWriter(file);
//            fileWriter.write(content);
//            fileWriter.flush();
//        }
//    }
//}
