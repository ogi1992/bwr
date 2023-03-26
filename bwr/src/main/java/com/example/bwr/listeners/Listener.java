package com.example.bwr.listeners;

import com.example.bwr.enums.MessageType;
import com.example.bwr.models.Message;
import org.springframework.kafka.annotation.KafkaListener;

public abstract class Listener {

  @KafkaListener(topics = "${kafka.bwr-topic-name}", groupId = "${kafka.group-id}")
  public void listen(String message) {
    Message messageModel = convertToMessage(message);
    if (!accept(messageModel.getType())) {
      return;
    }

    listenTopic(messageModel);
  }

  abstract void listenTopic(Message message);
  abstract boolean accept(MessageType type);
  abstract Message convertToMessage(String message);
}
