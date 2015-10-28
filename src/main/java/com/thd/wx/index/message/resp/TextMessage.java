package com.thd.wx.index.message.resp;

import com.thd.wx.index.message.resp.BaseMessage;

/**
 * 文本消息
 */
public class TextMessage extends BaseMessage {
    // 回复的消息内容
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}