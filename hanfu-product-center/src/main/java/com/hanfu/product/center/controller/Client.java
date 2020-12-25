package com.hanfu.product.center.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author shihao
 * @Title: Client
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public class Client extends Thread{
    //定义一个Socket对象
    Socket socket = null;


    public Client(Socket socket) {
        try {
            //需要服务器的IP地址和端口号，才能获得正确的Socket对象
            this.socket = socket;
        } catch (Exception e) {
        }
    }

    @Override
    public void run() {
        //客户端一连接就可以写数据个服务器了
        super.run();
        try {
            // 读Sock里面的数据
            InputStream s = socket.getInputStream();
            byte[] buf = new byte[1024];
            int len = 0;
//            OutputStream outputStream;
//            outputStream = socket.getOutputStream();
//            outputStream.write("ledoff 3".getBytes());
//            System.out.println("进入");
            System.out.println(s.read(buf));
            while ((len = s.read(buf)) != -1) {
                System.out.println("有消息");
                System.out.println(new String(buf, 0, len));
            }

        } catch (Exception e) {
            System.out.println("socket连接断开！");
        }
    }

}