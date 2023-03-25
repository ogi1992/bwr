package com.example.bwr.enums;

import java.util.List;

public enum Status {

  PENDING{
    @Override
    public List<Command> getPossibleCommands() {
      return List.of(Command.START_COMMAND);
    }
  },
  IN_PROGRESS {
    @Override
    public List<Command> getPossibleCommands() {
      return List.of(Command.STOP_COMMAND, Command.END_TASK);
    }
  }, STOPPED {
    @Override
    public List<Command> getPossibleCommands() {
      return List.of(Command.START_COMMAND, Command.END_TASK);
    }
  }, DONE {
    @Override
    public List<Command> getPossibleCommands() {
      return List.of(Command.START_COMMAND);
    }
  };

  public abstract List<Command> getPossibleCommands();
}
