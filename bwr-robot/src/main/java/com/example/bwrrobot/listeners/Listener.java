package com.example.bwrrobot.listeners;

import com.example.bwrrobot.enums.MessageType;
import com.example.bwrrobot.models.messages.Message;
import org.springframework.kafka.annotation.KafkaListener;

public abstract class Listener {

  @KafkaListener(topics = "${kafka.topic-name}", groupId = "${kafka.group-id}")
  public void listen(String message) {
    Message messageModel = convertToTaskMessage(message);
    if (!accept(messageModel.getType())) {
      return;
    }

    acknowledge(messageModel);

    listenTopic(messageModel);
  }

  abstract void listenTopic(Message message);
  abstract boolean accept(MessageType type);
  abstract Message convertToTaskMessage(String message);
  abstract void acknowledge(Message message);
}
