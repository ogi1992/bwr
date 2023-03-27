package com.example.bwr.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.bwr.enums.Command;
import com.example.bwr.enums.Status;
import com.example.bwr.exceptions.ValidationException;
import com.example.bwr.repositories.RobotRepository;
import com.example.bwr.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {

  public static final Integer ROBOT_ID = 1;
  public static final Integer USER_ID = 1;

  @InjectMocks
  private ValidationService validationService;
  @Mock
  private RobotRepository robotRepository;
  @Mock
  private UserRepository userRepository;

  @Test
  void validateRobotExists() {
    when(robotRepository.existsById(ROBOT_ID)).thenReturn(true);

    assertDoesNotThrow(() -> validationService.validateRobotExists(ROBOT_ID));
  }

  @Test
  void validateRobotExists_doesntExist() {
    when(robotRepository.existsById(ROBOT_ID)).thenReturn(false);

    ValidationException validationException = assertThrows(ValidationException.class,
        () -> validationService.validateRobotExists(ROBOT_ID));

    assertEquals(HttpStatus.NOT_FOUND, validationException.getHttpStatus());
    assertEquals("No existing robot found for given ID!", validationException.getMessage());
  }

  @Test
  void validateCommand() {
    when(userRepository.existsById(USER_ID)).thenReturn(true);
    assertDoesNotThrow(() -> validationService.validateCommand(Status.PENDING, USER_ID, Command.START_COMMAND));
  }

  @Test
  void validateCommand_withBadCommandAndBadUser() {
    when(userRepository.existsById(USER_ID)).thenReturn(false);
    ValidationException validationException = assertThrows(ValidationException.class,
        () -> validationService.validateCommand(Status.PENDING, USER_ID, Command.STOP_COMMAND));

    assertEquals(HttpStatus.NOT_FOUND, validationException.getHttpStatus());
    assertEquals("No existing user found for given ID!", validationException.getMessage());
  }

  @Test
  void validateUserExists() {
    when(userRepository.existsById(USER_ID)).thenReturn(true);

    assertDoesNotThrow(() -> validationService.validateUserExists(USER_ID));
  }

  @Test
  void validateUserExists_doesntExist() {
    when(userRepository.existsById(USER_ID)).thenReturn(false);

    ValidationException validationException = assertThrows(ValidationException.class,
        () -> validationService.validateUserExists(USER_ID));

    assertEquals(HttpStatus.NOT_FOUND, validationException.getHttpStatus());
    assertEquals("No existing user found for given ID!", validationException.getMessage());
  }

  @Test
  void validateTaskStatus() {
    assertDoesNotThrow(() -> validationService.validateTaskStatus(Status.IN_PROGRESS, Command.STOP_COMMAND));
  }

  @Test
  void validateTaskStatus_badCommand() {
    ValidationException validationException = assertThrows(ValidationException.class,
        () -> validationService.validateTaskStatus(Status.IN_PROGRESS, Command.START_COMMAND));

    assertEquals(HttpStatus.BAD_REQUEST, validationException.getHttpStatus());
    assertEquals(
        "Can't execute " + Command.START_COMMAND.name() + " command on task in status: " + Status.IN_PROGRESS.name(),
        validationException.getMessage());
  }
}