package com.example.bwr.services.handlers;

import com.example.bwr.enums.Command;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskMessageHandlerFactory {

  private Map<Command, Handler> handlerByCommand;

  @Autowired
  public TaskMessageHandlerFactory(Set<Handler> handlers) {
    createStrategy(handlers);
  }

  private void createStrategy(Set<Handler> handlers) {
    this.handlerByCommand = new HashMap<>();
    handlers.forEach(handler -> handlerByCommand.put(handler.getCommand(), handler));
  }

  public Handler getHandler(Command command) {
    return handlerByCommand.get(command);
  }
}
