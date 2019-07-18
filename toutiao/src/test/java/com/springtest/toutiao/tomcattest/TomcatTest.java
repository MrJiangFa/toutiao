package com.springtest.toutiao.tomcattest;

import org.apache.catalina.Wrapper;
import org.apache.catalina.core.StandardWrapper;
import org.apache.catalina.startup.Tomcat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class TomcatTest {
    Tomcat tomcat = new Tomcat();
    SocketChannel socketChannel = null;

    {
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8080));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Wrapper wapper = new StandardWrapper();


    }
}
