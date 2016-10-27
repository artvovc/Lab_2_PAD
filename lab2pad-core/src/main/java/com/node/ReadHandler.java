package com.node;

import com.database.Database;
import com.model.Model;
import com.util.json.JSONUtil;

import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;

class ReadHandler implements CompletionHandler<Integer, Attachment> {
    @Override
    public void completed(Integer result, Attachment attachment) {
        attachment.getBuffer().flip();
        int limits = attachment.getBuffer().limit();
        byte bytes[] = new byte[limits];
        attachment.getBuffer().get(bytes, 0, limits);
        attachment.getBuffer().rewind();
        Charset cs = Charset.forName("UTF-8");
        String msg = new String(bytes, cs);
        Model model = null;
        try {
            model = (Model) JSONUtil.getJAVAObjectfromJSONString(msg,Model.class);
            System.out.println(msg);
            Database.getInstance().getEmplList().addAll(model.getEmpls());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable e, Attachment attachment) {
        e.printStackTrace();
    }
}