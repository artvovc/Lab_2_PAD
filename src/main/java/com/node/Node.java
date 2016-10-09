package com.node;

import java.io.IOException;
import java.net.*;

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
    }

    public void runUDPServer() {
        try {
            UDPServerStandardConfig();
            while (true) {
//                System.out.println("Server can receive now");
                byte[] receiveBuff = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuff, receiveBuff.length);
                serverSocket.receive(receivePacket);
//                System.out.println("Server received packet");
//                System.out.println(receivePacket.getAddress() + " " + receivePacket.getPort());
//                System.out.println(new String(receivePacket.getData()));
                String msg = new String(receivePacket.getData());



                DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName("233.0.0.1"), receivePacket.getPort());
                serverSocket.send(sendPacket);
//                System.out.println("Server sended packet");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runRequestToUDPServer() {
        try {
            multicastSocketConfig();
            byte[] data = null;
            int i = 0;
            while (true) {
                data = new byte[0];
                data = new byte[1024];

                if(!this.onlyListen) {
                    String str = "patrin[" + nodeId + "] " + i++;
                    DatagramPacket datagramPacket = new DatagramPacket(str.getBytes(), str.getBytes().length, new InetSocketAddress(this.hostname, this._UDPServer_port));
                    multicastSocket.send(datagramPacket);
                }

                DatagramPacket receiveDatagramPacket = new DatagramPacket(data, data.length);
                multicastSocket.receive(receiveDatagramPacket);
                System.out.println(nodeId+new String(receiveDatagramPacket.getData()));
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void multicastSocketConfig(){
        this.multicastSocket = null;
        try {
            this.multicastSocket = new MulticastSocket(this._MulticastSocket_port);
            this.multicastSocket.setBroadcast(true);
            this.multicastSocket.joinGroup(InetAddress.getByName(this._MulticastSocket_broadcastIp));
        } catch (IOException e) {
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


}

//    DatagramSocket socket;
//
//    @Override
//    public void run() {
//        try {
//            //Keep a socket open to listen to all the UDP trafic that is destined for this _UDPServer_port
//            socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
//            socket.setBroadcast(true);
//
//            while (true) {
//                System.out.println(getClass().getName() + ">>>Ready to receive broadcast packets!");
//
//                //Receive a packet
//                byte[] recvBuf = new byte[15000];
//                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
//                socket.receive(packet);
//
//                //Packet received
//                System.out.println(getClass().getName() + ">>>Discovery packet received from: " + packet.getAddress().getHostAddress());
//                System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(packet.getData()));
//
//                //See if the packet holds the right command (message)
//                String message = new String(packet.getData()).trim();
//                if (message.equals("DISCOVER_FUIFSERVER_REQUEST")) {
//                    byte[] sendData = "DISCOVER_FUIFSERVER_RESPONSE".getBytes();
//
//                    //Send a response
//                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
//                    socket.send(sendPacket);
//
//                    System.out.println(getClass().getName() + ">>>Sent packet to: " + sendPacket.getAddress().getHostAddress());
//                }
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(DiscoveryThread.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

//// Find the server using UDP broadcast
//        try {
//                //Open a random _UDPServer_port to send the package
//                c = new DatagramSocket();
//                c.setBroadcast(true);
//
//                byte[] sendData = "DISCOVER_FUIFSERVER_REQUEST".getBytes();
//
//                //Try the 255.255.255.255 first
//                try {
//                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8888);
//                c.send(sendPacket);
//                System.out.println(getClass().getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
//                } catch (Exception e) {
//                }
//
//                // Broadcast the message over all the network interfaces
//                Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
//                while (interfaces.hasMoreElements()) {
//                NetworkInterface networkInterface = interfaces.nextElement();
//
//                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
//                continue; // Don't want to broadcast to the loopback interface
//                }
//
//                for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
//                InetAddress broadcast = interfaceAddress.getBroadcast();
//                if (broadcast == null) {
//                continue;
//                }
//
//                // Send the broadcast package!
//                try {
//                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
//                c.send(sendPacket);
//                } catch (Exception e) {
//                }
//
//                System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
//                }
//                }
//
//                System.out.println(getClass().getName() + ">>> Done looping over all network interfaces. Now waiting for a reply!");
//
//                //Wait for a response
//                byte[] recvBuf = new byte[15000];
//                DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
//                c.receive(receivePacket);
//
//                //We have a response
//                System.out.println(getClass().getName() + ">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress());
//
//                //Check if the message is correct
//                String message = new String(receivePacket.getData()).trim();
//                if (message.equals("DISCOVER_FUIFSERVER_RESPONSE")) {
//                //DO SOMETHING WITH THE SERVER'S IP (for example, store it in your controller)
//                Controller_Base.setServerIp(receivePacket.getAddress());
//                }
//
//                //Close the _UDPServer_port!
//                c.close();
//                } catch (IOException ex) {
//                Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
//        }
