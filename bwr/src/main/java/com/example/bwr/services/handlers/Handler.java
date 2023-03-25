package com.example.bwr.services.handlers;

import com.example.bwr.enums.Command;
import com.example.bwr.models.TaskMessage;

public interface Handler {

  void handle(TaskMessage message);
  Command getCommand();
}
