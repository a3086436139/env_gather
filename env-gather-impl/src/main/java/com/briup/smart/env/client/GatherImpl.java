package com.briup.smart.env.client;

import com.briup.smart.env.entity.Environment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author e
 * @create 2022-10-31 11:16
 */
public class GatherImpl implements Gather {

    @Override
    public Collection<Environment> gather() {
        /*
        自动生成引用：ctrl + alt + v 或.var
        智能键： alt + enter
         */
        //1.用IO流读取文件中的数据 用BufferedReader
        String filePath = "C:\\Users\\Xspana\\Desktop\\data-file-simple";
        ArrayList<Environment> list = new ArrayList<>();
        try (
                BufferedReader bufferedReader =
                        new BufferedReader(new FileReader(filePath));
        ) {
            String str = null;
            while ((str = bufferedReader.readLine())!=null){
                //字符串分割操作，按 | 进行分割
                String[] arr = str.split("[|]");
                Environment environment = new Environment();

                //环境种类名称
//                private String name;

                //发送端id
                environment.setSrcId(arr[0]);
                //树莓派系统id
                environment.setDesId(arr[1]);
                //实验箱区域模块id(1-8)
                environment.setDevId(arr[2]);
                //模块上传感器地址
                environment.setSersorAddress(arr[3]);
                //传感器个数
                environment.setCount(Integer.parseInt(arr[4]));
                //发送指令标号 3表示接收数据 16表示发送命令
                environment.setCmd(arr[5]);
                //状态 默认1表示成功
                environment.setStatus(Integer.parseInt(arr[7]));
                //环境值
//                private float data;
                //采集时间
                environment.setGather_date(
                        new Timestamp(Long.parseLong(arr[8])));

                switch (arr[3]){
                    case "16":
                        //表示温度,前两个字节是温度,四个十六进制的数
                        //5d806ff802
                        environment.setName("温度");
                        String temperature = arr[6].substring(0, 4);
                        int t = Integer.parseInt(temperature, 16);
                        environment.setData((t*(0.00268127F))-46.85F);
                        list.add(environment);

                        //表示湿度,中间两个字节是湿度
                        Environment environment1 = new Environment();
                        //发送端id
                        environment1.setSrcId(arr[0]);
                        //树莓派系统id
                        environment1.setDesId(arr[1]);
                        //实验箱区域模块id(1-8)
                        environment1.setDevId(arr[2]);
                        //模块上传感器地址
                        environment1.setSersorAddress(arr[3]);
                        //传感器个数
                        environment1.setCount(Integer.parseInt(arr[4]));
                        //发送指令标号 3表示接收数据 16表示发送命令
                        environment1.setCmd(arr[5]);
                        //状态 默认1表示成功
                        environment1.setStatus(Integer.parseInt(arr[7]));
                        environment1.setName("湿度");
                        String temperature1 = arr[6].substring(4, 8);
                        int t1 = Integer.parseInt(temperature1, 16);
                        environment1.setData((t1*0.00190735F)-6);
                        list.add(environment1);
                        break;
                    case "256":
                        //光照强度的数据，则前两个字节就是数据值，剩余字节不用管
                        environment.setName("光照强度");
                        environment.setData(Integer.parseInt(arr[6].substring(0,4),16));
                        list.add(environment);
                        break;
                    case "1280":
                        //二氧化碳的数据，则前两个字节就是数据值，剩余字节不用管
                        environment.setName("二氧化碳浓度");
                        environment.setData(Integer.parseInt(arr[6].substring(0,4),16));
                        list.add(environment);
                        break;
                    default:
                        System.out.println("数据格式错误:"+str);
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
