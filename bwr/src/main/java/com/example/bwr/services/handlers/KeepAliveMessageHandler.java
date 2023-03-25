package com.example.bwr.services.handlers;

import com.example.bwr.models.KeepAliveMessage;
import com.example.bwr.services.RobotService;
import com.example.bwr.utils.KeepAliveThread;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeepAliveMessageHandler {

  private final Map<Integer, KeepAliveThread> keepAliveThreadMap;
  private final RobotService robotService;

  public void handle(KeepAliveMessage keepAliveMessage) {
    Integer robotId = keepAliveMessage.getSourceId();
    if (!keepAliveThreadMap.containsKey(robotId)) {
      putAndStartNewThread(robotId);
      return;
    }

    KeepAliveThread keepAliveThread = keepAliveThreadMap.get(robotId);
    keepAliveThread.interrupt();

    putAndStartNewThread(robotId);
  }

  private void putAndStartNewThread(Integer robotId) {
    KeepAliveThread keepAliveThread = new KeepAliveThread(robotId, robotService);
    keepAliveThreadMap.put(robotId, keepAliveThread);
    keepAliveThread.start();
  }
}
