package com.quanroon.ysq.producer.annotation;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface StreamOutput {
    String output = "test-output";

    @Output(StreamOutput.output)
    MessageChannel output();
}
