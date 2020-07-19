package com.quanroon.ysq.exception;

/**
 * @author ysq
 * @since 2020/07/19 下午9:14
 **/
public class MqContextException extends Throwable {

    private String messageId;
    private String topic;
    private MqClientException exception;

    public MqContextException() {
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public MqClientException getException() {
        return this.exception;
    }

    public void setException(MqClientException exception) {
        this.exception = exception;
    }
}
