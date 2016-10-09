package com;

import com.enums.WhoRequest;
import com.model.Model;
import com.node.Node;
import com.util.JSONUtil;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

/**
 * Created by Artemie on 04.10.2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("hello");


//        System.out.println(WhoRequest.USER);
//        System.out.println(WhoRequest.valueOf(0));
//        System.out.println(WhoRequest.valueOf("USER"));

        Model model = new Model();
        model.setNodeId("asd");
        String json = JSONUtil.getJSONStringfromJAVAObject(model);
        System.out.println(json);
        Model model2 = (Model)JSONUtil.getJAVAObjectfromJSONString(json,Model.class);
        System.out.println(model2.getNodeId());



//        Node nodeClient = new Node(8888,1502,"127.0.0.1","233.0.0.1");
//        nodeClient.setNodeId("nodeClient");
//        Thread client = new Thread(nodeClient::runUDPServer);
//        client.start();
//
//        Node nodeServer = new Node(8888,1502,"127.0.0.1","233.0.0.1");
//        nodeServer.setNodeId("nodeServer");
//        Thread server = new Thread(nodeServer::runUDPServer);
//        server.start();
////        Thread send = new Thread(nodeServer::runRequestToUDPServer);
////        send.start();
//
//
//        Node nodeOne = new Node(8888,1502,"127.0.0.1","233.0.0.1");
//        nodeOne.setNodeId("nodeOne");
//        nodeOne.setOnlyListen(true);
//        Thread node1 = new Thread(nodeOne::runRequestToUDPServer);
//        node1.start();
//
//        Thread.currentThread().join();

    }
}
