package com.node;

import com.database.Database;
import com.enums.RequestType;
import com.enums.WhoRequest;
import com.model.Empl;
import com.model.Model;
import com.util.JSONUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.List;
import java.util.Objects;

/**
 * Created by Artemie on 04.10.2016.
 */
public class Node {
    private String nodeId;
    private Boolean onlyListen = Boolean.FALSE;
    private int _UDPServer_port;
    private int _MulticastSocket_port;//5051
    private String hostname;
    private String _MulticastSocket_broadcastIp;//233.0.0.1
    private DatagramSocket serverSocket;
    private SocketAddress socketAddress;
    private MulticastSocket multicastSocket;
    private WhoRequest whoRequest;
    private RequestType requestType;
    private Model model = new Model();
    private String message;
    private int countConnections;
    private Model maven = new Model();
    private int countNodes;
    private List<String> knownNodes;
    private int _TCPServerPort;


    public Node(String hostname, int _UDPServer_port) throws Exception {
        this._UDPServer_port = _UDPServer_port;
        this.hostname = hostname;
    }

    public Node(int _UDPServer_port, int _MulticastSocket_port, String hostname, String _MulticastSocket_broadcastIp) {
        this._UDPServer_port = _UDPServer_port;
        this._MulticastSocket_port = _MulticastSocket_port;
        this.hostname = hostname;
        this._MulticastSocket_broadcastIp = _MulticastSocket_broadcastIp;
    }

    private void UDPServerStandardConfig() throws Exception {
        socketAddress = new InetSocketAddress(this.hostname, this._UDPServer_port);
        serverSocket = new DatagramSocket(this._UDPServer_port);
        serverSocket.setBroadcast(true);
        maven.setCountConnections(0);
    }

    public void runUDPServer() {
        try {
            UDPServerStandardConfig();
            int temp = 0;
            while (true) {
                byte[] receiveBuff = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuff, receiveBuff.length);
                serverSocket.receive(receivePacket);
                String msg = new String(receivePacket.getData());
                Model model = (Model) JSONUtil.getJAVAObjectfromJSONString(msg, Model.class);

                if (model.getWhoRequest() == WhoRequest.USER && Objects.equals(model.getMessage(), "maven")) {
                    model = new Model();
                    model.setWhoRequest(WhoRequest.NODE);
                    model.setMessage("getCount");
                    msg = JSONUtil.getJSONStringfromJAVAObject(model);
                    DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName("233.0.0.1"), receivePacket.getPort());
                    serverSocket.send(sendPacket);
                }
                if (Objects.equals(model.getMessage(), "count")) {
                    if (maven.getCountConnections() < model.getCountConnections())
                        maven = model;
                    temp++;
                    msg = JSONUtil.getJSONStringfromJAVAObject(new Model());
                    if (temp == countNodes) {
                        maven.setMessage("Imaven");
                        msg = JSONUtil.getJSONStringfromJAVAObject(maven);
                        DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName("233.0.0.1"), receivePacket.getPort());
                        serverSocket.send(sendPacket);
                    }
                }

                if (model.getWhoRequest() == WhoRequest.USER && Objects.equals(model.getMessage(), "getTuples")) {
                    this.maven.setMessage("sendTuplesToClient");
                    this.maven.setTcpServerPort(model.getTcpServerPort());
                    msg = JSONUtil.getJSONStringfromJAVAObject(maven);
                    DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName("233.0.0.1"), receivePacket.getPort());
                    serverSocket.send(sendPacket);
                }

                if (model.getMessage() == null) msg = JSONUtil.getJSONStringfromJAVAObject(new Model());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runListeningToUDPServer() {
        try {
            multicastSocketConfig();
            byte[] data = null;
            int i = 0;
            while (true) {
                data = new byte[0];
                data = new byte[1024];
                if (!this.onlyListen) {
                    request();
                }
                DatagramPacket receiveDatagramPacket = new DatagramPacket(data, data.length);
                multicastSocket.receive(receiveDatagramPacket);
                String msg = new String(receiveDatagramPacket.getData());
                Model model = (Model) JSONUtil.getJAVAObjectfromJSONString(msg, Model.class);
                if (model.getWhoRequest() == WhoRequest.NODE && Objects.equals(model.getMessage(), "getCount")) {
                    setModelPack();
                    String msgJson = null;
                    this.model.setMessage("count");
                    msgJson = JSONUtil.getJSONStringfromJAVAObject(this.model);
                    DatagramPacket datagramPacket = new DatagramPacket(msgJson.getBytes(), msgJson.getBytes().length, new InetSocketAddress(this.hostname, this._UDPServer_port));
                    multicastSocket.send(datagramPacket);
                }
                if (Objects.equals(this.nodeId, "nodeClient") && Objects.equals(model.getMessage(), "Imaven")) {
                    this.maven = model;
                    //AICI STARTEZ TCP SERVER PE UN PORT

                    runTCPServer();

                    this.model.setMessage("getTuples");
                    String msgJson = JSONUtil.getJSONStringfromJAVAObject(this.model);
                    DatagramPacket datagramPacket = new DatagramPacket(msgJson.getBytes(), msgJson.getBytes().length, new InetSocketAddress(this.hostname, this._UDPServer_port));
                    multicastSocket.send(datagramPacket);
                    System.out.println("MAVEN " + JSONUtil.getJSONStringfromJAVAObject(maven));
                }
                if (Objects.equals(model.getMessage(), "sendTuplesToClient")) {
                    if (Objects.equals(this.nodeId, model.getNodeId()) || check(this.nodeId, model.getKnownNodes())) {
                        Socket subscriberSocketClient = new Socket(model.getFromHostname(), model.getTcpServerPort());
                        OutputStream out = subscriberSocketClient.getOutputStream();
                        DataOutputStream DataSend = new DataOutputStream(out);
                        String str = JSONUtil.getJSONStringfromJAVAObject(this.model);
                        byte[] bytes = str.getBytes();
                        DataSend.write(bytes, 0, bytes.length);
                        out.close();
                        DataSend.close();
                        subscriberSocketClient.close();
                    }
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runTCPServer() {
        Database.getInstance().setEmplList(this.model.getEmplList());
        new Thread(() -> {
            AsynchronousServerSocketChannel server = null;
            try {
                server = AsynchronousServerSocketChannel.open(null);
                server.bind(new InetSocketAddress("127.0.0.1", this._TCPServerPort));
                Attachment attachment = new Attachment();
                attachment.setServer(server);
                server.accept(attachment, new ConnectionHandler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private boolean check(String nodeId, List<String> knownNodes) {
        for (String knownNode : knownNodes)
            if (Objects.equals(knownNode, nodeId)) return true;
        return false;
    }

    private void multicastSocketConfig() {
        this.multicastSocket = null;
        try {
            this.multicastSocket = new MulticastSocket(this._MulticastSocket_port);
            this.multicastSocket.setBroadcast(true);
            this.multicastSocket.joinGroup(InetAddress.getByName(this._MulticastSocket_broadcastIp));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setModelPack() {
        this.model.setNodeId(this.nodeId);
        this.model.setMessage(this.message);
        this.model.setWhoRequest(this.whoRequest);
        this.model.setFromHostname(this.hostname);//localhost
        this.model.setFromPort(this._UDPServer_port);//8888
        this.model.setToHostname(this._MulticastSocket_broadcastIp);//233.0.0.1
        this.model.setToPort(this._MulticastSocket_port);//1502
        this.model.setKnownNodes(this.knownNodes);
        this.model.setCountConnections(this.countConnections);
        this.model.setTcpServerPort(this._TCPServerPort);
    }

    public void request() {
        setModelPack();
        String msgJson = null;
        try {
            msgJson = JSONUtil.getJSONStringfromJAVAObject(model);
            DatagramPacket datagramPacket = new DatagramPacket(msgJson.getBytes(), msgJson.getBytes().length, new InetSocketAddress(this.hostname, this._UDPServer_port));
            multicastSocket.send(datagramPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public void setOnlyListen(Boolean onlyListen) {
        this.onlyListen = onlyListen;
    }

    public void setEmpls(List<Empl> empls) {
        this.model.setEmplList(empls);
    }

    public WhoRequest getWhoRequest() {
        return whoRequest;
    }

    public void setWhoRequest(WhoRequest whoRequest) {
        this.whoRequest = whoRequest;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCountConnections() {
        return countConnections;
    }

    public void setCountConnections(int countConnections) {
        this.countConnections = countConnections;
    }

    public int getCountNodes() {
        return countNodes;
    }

    public void setCountNodes(int countNodes) {
        this.countNodes = countNodes;
    }

    public List<String> getKnownNodes() {
        return knownNodes;
    }

    public void setKnownNodes(List<String> knownNodes) {
        this.knownNodes = knownNodes;
    }

    public int get_TCPServerPort() {
        return _TCPServerPort;
    }

    public void set_TCPServerPort(int _TCPServerPort) {
        this._TCPServerPort = _TCPServerPort;
    }
}