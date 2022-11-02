package com.briup.smart.env.client;

import com.briup.smart.env.entity.Environment;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;

/**
 * @author e
 * @create 2022-11-01 14:30
 */
public class ClientImpl implements Client {
    @Override
    public void send(Collection<Environment> c) {
        if (c == null || c.size() == 0) {
            System.out.println("接收的数据有误");
            return;
        }
        //使用网络编程技术  Socket 将数据发送到服务端
        try (Socket socket = new Socket("127.0.0.1", 9999);
             ObjectOutputStream objectOutputStream =
                     new ObjectOutputStream(socket.getOutputStream());
        ) {
            System.out.println("客户端连接成功");
            System.out.println("客户端准备发送数据");
            objectOutputStream.writeObject(c);
            System.out.println("数据发送成功,共" + c.size() + "条");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
