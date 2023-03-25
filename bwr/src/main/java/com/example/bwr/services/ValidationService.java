package com.example.bwr.services;

import com.example.bwr.enums.Command;
import com.example.bwr.enums.Status;
import com.example.bwr.exceptions.ExceptionSuppliers;
import com.example.bwr.exceptions.ValidationException;
import com.example.bwr.repositories.RobotRepository;
import com.example.bwr.repositories.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationService {

  private final RobotRepository robotRepository;
  private final UserRepository userRepository;

  public void validateRobotExists(Integer robotId) {
    boolean robotExists = robotRepository.existsById(robotId);

    if (!robotExists) {
      throw ExceptionSuppliers.robotNotFound.get();
    }
  }

  public void validateCommand(Status status, Integer userId, Command command) {
    validateUserExists(userId);
    validateTaskStatus(status, command);
  }

  public void validateUserExists(Integer userId) {
    boolean userExists = userRepository.existsById(userId);

    if (!userExists) {
      throw ExceptionSuppliers.userNotFound.get();
    }
  }

  public void validateTaskStatus(Status currentStatus, Command command) {
    List<Command> possibleCommands = currentStatus.getPossibleCommands();

    if (!possibleCommands.contains(command)) {
      throw ValidationException.builder()
          .httpStatus(HttpStatus.BAD_REQUEST)
          .message("Can't execute :" + command.name() + " command on task in status: " + currentStatus.name())
          .build();
    }
  }
}
