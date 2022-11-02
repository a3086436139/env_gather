package com.briup.smart.env.client;

import com.briup.smart.env.entity.Environment;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * @author e
 * @create 2022-11-01 9:29
 */
public class GatherImplTest {
    @Test
    public void test1(){
        GatherImpl gather = new GatherImpl();
        gather.gather();
    }

    @Test
    public void test2(){
        String s1 = "100|101|2|16|1|3|5d806ff802|1|1516323615936";
        String[] split = s1.split("[|]");
        System.out.println(Arrays.toString(split));
    }

    @Test
    public void test3(){
        String s1 = "5d806ff802";
        String substring = s1.substring(0, 4);
        System.out.println(substring);
    }

    @Test
    public void test4(){
        int i = Integer.parseInt("5d80", 16);
        System.out.println(i);
    }

    @Test
    public void test5(){
        Collection<Environment> gather = new GatherImpl().gather();
        gather.forEach(System.out::println);
    }

}