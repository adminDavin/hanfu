package com.hanfu.product.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.product.center.model.HfSeniority;
import com.hanfu.product.center.request.HfSeniorityRequest;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author shihao
 * @Title: SocketController
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
@CrossOrigin
@RestController
@RequestMapping("/socket")
@Api
public class SocketController {
    Map<String,Socket> socketList = new HashMap<>();
    @ApiOperation(value = "添加排行相关信息", notes = "添加排行相关信息")
    @RequestMapping(value = "/addSeniorityInfo", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> addSeniorityInfo() throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        String host = "192.168.1.128";
        int port = 1200;

        //与服务端建立连接
        Socket socket = new Socket(host, port);
        socketList.put(host,socket);
        socket.setOOBInline(true);

        //建立连接后获取输出流
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        String uuid = UUID.randomUUID().toString();
//        log.info("uuid: {}", uuid);
        System.out.println(uuid);
        outputStream.write(uuid.getBytes());
        while (true){
            int hasRead=0;
            System.out.println("x");
            InputStream s =socket.getInputStream();
            byte[] buf = new byte[1024];
                    System.out.println(new String(buf));

            while ((hasRead = s.read(buf)) != -1){
                String string = new String(buf);
                System.out.println(string);
//                System.out.println(new String(buf, 0, hasRead));
            }


        }
    }
    @ApiOperation(value = "get", notes = "添加排行相关信息")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> get() throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

        return builder.body(ResponseUtils.getResponseBody(socketList));
    }
    @ApiOperation(value = "add", notes = "发送")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public ResponseEntity<JSONObject> add(String aaa,String host) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);

        DataOutputStream outputStream = new DataOutputStream(socketList.get(host).getOutputStream());
        System.out.println(aaa);
        outputStream.write(aaa.getBytes());
        return builder.body(ResponseUtils.getResponseBody(0));

}
    class ServerThread extends Thread {
        private Socket socket;
        InputStream inputStream;
        OutputStream outputStream;

        public ServerThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                while (true) {
                    //接收客户端的消息并打印
                    System.out.println(socket);
                    inputStream = socket.getInputStream();
                    byte[] bytes = new byte[1024];
                    inputStream.read(bytes);
                    String string = new String(bytes);
                    System.out.println(string);

                    //向客户端发送消息
                    outputStream = socket.getOutputStream();
                    outputStream.write("ledoff 1".getBytes());
                    System.out.println("OK");

                }
            } catch (Exception e) {
                System.out.println("客户端主动断开连接了");
                //e.printStackTrace();
            }
            //操作结束，关闭socket
            try {
                System.out.println("关闭了");
                socket.close();
            } catch (IOException e) {
                System.out.println("关闭连接出现异常");
                e.printStackTrace();
            }
        }
    }
}
