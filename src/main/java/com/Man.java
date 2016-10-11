package com;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

/**
 * Created by Artemie on 04.10.2016.
 */
public class Man implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread th = new Thread(new Man());
        th.start();

        Thread.currentThread().join();
    }

    public void run() {
        try {
            MulticastSocket multicastSocket = null;
            multicastSocket = new MulticastSocket(1502);
            multicastSocket.setBroadcast(true);
            multicastSocket.joinGroup(InetAddress.getByName("233.0.0.1"));
            byte[] data = null;
            int i = 0;
            while (true) {
                data = new byte[0];

                String str = "patrin[1] " + i++;
                DatagramPacket datagramPacket = new DatagramPacket(str.getBytes(), str.getBytes().length, new InetSocketAddress("127.0.0.1", 8888));
                multicastSocket.send(datagramPacket);

                data = new byte[1024];
                DatagramPacket receiveDatagramPacket = new DatagramPacket(data, data.length);
                multicastSocket.receive(receiveDatagramPacket);
                System.out.println(new String(receiveDatagramPacket.getData()));
//                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
