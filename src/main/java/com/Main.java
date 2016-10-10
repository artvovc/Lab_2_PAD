package com;

import com.enums.WhoRequest;
import com.model.Empl;
import com.node.Node;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by Artemie on 04.10.2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {


                //DEMO ENUMS
//        System.out.println(WhoRequest.USER);
//        System.out.println(WhoRequest.valueOf(0));
//        System.out.println(WhoRequest.valueOf("USER"));
                //DEMO JSON UTILS
//        Model model = new Model();
//        model.setNodeId("asd");
//        String json = JSONUtil.getJSONStringfromJAVAObject(model);
//        System.out.println(json);
//        Model model2 = (Model)JSONUtil.getJAVAObjectfromJSONString(json,Model.class);
//        System.out.println(model2.getNodeId());

                //INSUSI LABUL

        Node nodeServer = new Node(8888,1502,"127.0.0.1","233.0.0.1");
        nodeServer.setNodeId("nodeServer");
        nodeServer.setCountNodes(7);
        Thread server = new Thread(nodeServer::runUDPServer);
        server.start();

        Node nodeOne = new Node(8888,1502,"127.0.0.1","233.0.0.1");
        nodeOne.setNodeId("nodeOne");
        nodeOne.setOnlyListen(true);
        nodeOne.setKnownNodes(Arrays.asList("nodeTwo"));
        nodeOne.setCountConnections(nodeOne.getKnownNodes().size());
        Thread node1 = new Thread(nodeOne::runListeningToUDPServer);
        node1.start();

        Node nodeTwo = new Node(8888,1502,"127.0.0.1","233.0.0.1");
        nodeTwo.setNodeId("nodeTwo");
        nodeTwo.setOnlyListen(true);
        nodeTwo.setKnownNodes(Arrays.asList("nodeOne","nodeSix","nodeThree"));
        nodeTwo.setCountConnections(nodeTwo.getKnownNodes().size());
        Thread node2 = new Thread(nodeTwo::runListeningToUDPServer);
        node2.start();

        Node nodeThree = new Node(8888,1502,"127.0.0.1","233.0.0.1");
        nodeThree.setNodeId("nodeThree");
        nodeThree.setOnlyListen(true);
        nodeThree.setKnownNodes(Arrays.asList("nodeTwo","nodeFour","nodeFive"));
        nodeThree.setCountConnections(nodeThree.getKnownNodes().size());
        Thread node3 = new Thread(nodeThree::runListeningToUDPServer);
        node3.start();

        Node nodeFour = new Node(8888,1502,"127.0.0.1","233.0.0.1");
        nodeFour.setNodeId("nodeFour");
        nodeFour.setOnlyListen(true);
        nodeFour.setKnownNodes(Arrays.asList("nodeThree"));
        nodeFour.setCountConnections(nodeFour.getKnownNodes().size());
        Thread node4 = new Thread(nodeFour::runListeningToUDPServer);
        node4.start();


        Node nodeFive = new Node(8888,1502,"127.0.0.1","233.0.0.1");
        nodeFive.setNodeId("nodeFive");
        nodeFive.setOnlyListen(true);
        nodeFive.setKnownNodes(Arrays.asList("nodeThree"));
        nodeFive.setCountConnections(nodeFive.getKnownNodes().size());
        Thread node5 = new Thread(nodeFive::runListeningToUDPServer);
        node5.start();

        Node nodeSix = new Node(8888,1502,"127.0.0.1","233.0.0.1");
        nodeSix.setNodeId("nodeSix");
        nodeSix.setOnlyListen(true);
        nodeSix.setKnownNodes(Arrays.asList("nodeTwo"));
        nodeSix.setCountConnections(nodeSix.getKnownNodes().size());
        Thread node6 = new Thread(nodeSix::runListeningToUDPServer);
        node6.start();



        //CLIENT

        Node nodeClient = new Node(8888,1502,"127.0.0.1","233.0.0.1");
        nodeClient.set_TCPServerPort(5051);
        nodeClient.setNodeId("nodeClient");
        nodeClient.setWhoRequest(WhoRequest.USER);
        nodeClient.setMessage("maven");
        nodeClient.setOnlyListen(true);
        nodeClient.setEmpls(Arrays.asList(
                new Empl(0,"jora","joranovic",21,20000, new Date().getTime()),
                new Empl(1,"wartanov","next",20,211,new Date().getTime())
        ));
        Thread client = new Thread(nodeClient::runListeningToUDPServer);
        client.start();
        nodeClient.request();

        Thread.currentThread().join();

    }
}
