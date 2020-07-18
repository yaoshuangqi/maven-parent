package com.quanroon.ysq.producer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface StreamInput {

    String input = "test-input";

    @Input(StreamInput.input)
    SubscribableChannel input();
}
