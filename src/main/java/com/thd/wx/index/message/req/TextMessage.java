package com.thd.wx.index.message.req;

import com.thd.wx.index.message.req.BaseMessage;

/**
 * 文本消息
 */
public class TextMessage extends BaseMessage {
    // 消息内容
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}