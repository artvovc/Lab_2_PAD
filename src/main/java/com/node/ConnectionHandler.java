package com.node;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created by Artemie on 03.09.2016.
 */
public class ConnectionHandler implements CompletionHandler<AsynchronousSocketChannel, Attachment> {



    @Override
    public void completed(AsynchronousSocketChannel client, Attachment attachment) {
        attachment.getServer().accept(attachment,this);
        Attachment newAttachment = new Attachment();
        newAttachment.setServer(attachment.getServer());
        newAttachment.setClient(client);
        newAttachment.setBuffer(ByteBuffer.allocate(2048));
        client.read(newAttachment.getBuffer(), newAttachment,new ReadHandler());
    }

    @Override
    public void failed(Throwable exc, Attachment attachment) {

    }
}