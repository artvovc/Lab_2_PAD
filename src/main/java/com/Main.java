package com;

import com.database.Database;
import com.enums.WhoRequest;
import com.model.Empl;
import com.node.Node;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.Arrays;
import java.util.Comparator;
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
        nodeOne.setEmpls(Arrays.asList(
                new Empl(13,"rtuertu","vurturb",19,44534444, new Date().getTime()),
                new Empl(12,"dfghdfgh","bvurtu",14,21555541,new Date().getTime())
        ));
        Thread node1 = new Thread(nodeOne::runListeningToUDPServer);
        node1.start();

        Node nodeTwo = new Node(8888,1502,"127.0.0.1","233.0.0.1");
        nodeTwo.setNodeId("nodeTwo");
        nodeTwo.setOnlyListen(true);
        nodeTwo.setKnownNodes(Arrays.asList("nodeOne","nodeSix","nodeThree"));
        nodeTwo.setCountConnections(nodeTwo.getKnownNodes().size());
        nodeTwo.setEmpls(Arrays.asList(
                new Empl(11,"wertwert","vertkghb",19,445254444, new Date().getTime()),
                new Empl(10,"tuertuerra","bvuetruertu",14,215255541,new Date().getTime())
        ));
        Thread node2 = new Thread(nodeTwo::runListeningToUDPServer);
        node2.start();

        Node nodeThree = new Node(8888,1502,"127.0.0.1","233.0.0.1");
        nodeThree.setNodeId("nodeThree");
        nodeThree.setOnlyListen(true);
        nodeThree.setKnownNodes(Arrays.asList("nodeTwo","nodeFour","nodeFive"));
        nodeThree.setCountConnections(nodeThree.getKnownNodes().size());
        nodeThree.setEmpls(Arrays.asList(
                new Empl(9,"awerrt","vyrtyb",19,441234444, new Date().getTime()),
                new Empl(8,"trwerta","bertyev",14,21545551,new Date().getTime())
        ));
        Thread node3 = new Thread(nodeThree::runListeningToUDPServer);
        node3.start();

        Node nodeFour = new Node(8888,1502,"127.0.0.1","233.0.0.1");
        nodeFour.setNodeId("nodeFour");
        nodeFour.setOnlyListen(true);
        nodeFour.setKnownNodes(Arrays.asList("nodeThree"));
        nodeFour.setCountConnections(nodeFour.getKnownNodes().size());
        nodeFour.setEmpls(Arrays.asList(
                new Empl(6,"arte","evb",19,44442244, new Date().getTime()),
                new Empl(7,"trae","bev",34,45541,new Date().getTime())
        ));
        Thread node4 = new Thread(nodeFour::runListeningToUDPServer);
        node4.start();


        Node nodeFive = new Node(8888,1502,"127.0.0.1","233.0.0.1");
        nodeFive.setNodeId("nodeFive");
        nodeFive.setOnlyListen(true);
        nodeFive.setKnownNodes(Arrays.asList("nodeThree"));
        nodeFive.setCountConnections(nodeFive.getKnownNodes().size());
        nodeFive.setEmpls(Arrays.asList(
                new Empl(4,"art","vb",19,444444, new Date().getTime()),
                new Empl(5,"tra","bv",14,21555541,new Date().getTime())
        ));
        Thread node5 = new Thread(nodeFive::runListeningToUDPServer);
        node5.start();

        Node nodeSix = new Node(8888,1502,"127.0.0.1","233.0.0.1");
        nodeSix.setNodeId("nodeSix");
        nodeSix.setOnlyListen(true);
        nodeSix.setKnownNodes(Arrays.asList("nodeTwo"));
        nodeSix.setCountConnections(nodeSix.getKnownNodes().size());
        nodeSix.setEmpls(Arrays.asList(
                new Empl(2,"joras","joranovics",34,204000, new Date().getTime()),
                new Empl(3,"wartanovs","nexts",24,2141,new Date().getTime())
        ));
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

        Thread.sleep(5000);

        Comparator<Empl> byEmployeeNumber = (e1, e2) -> Integer.compare(
                e1.getId(), e2.getId());

        Database.getInstance().getEmplList().stream().sorted(byEmployeeNumber).forEach(System.out::println);

        Thread.currentThread().join();

    }
}
