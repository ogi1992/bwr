package com.example.bwr.services.handlers;

import static com.example.bwr.utils.TestUtils.ROBOT_ID;
import static com.example.bwr.utils.TestUtils.TASK_ID;
import static com.example.bwr.utils.TestUtils.USER_ID;
import static com.example.bwr.utils.TestUtils.buildTaskEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bwr.enums.Command;
import com.example.bwr.exceptions.ValidationException;
import com.example.bwr.models.TaskMessage;
import com.example.bwr.repositories.TaskRepository;
import com.example.bwr.services.AuditLogService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class StopCommandHandlerTest {

  @InjectMocks
  private StopCommandHandler stopCommandHandler;
  @Mock
  private TaskRepository taskRepository;
  @Mock
  private AuditLogService auditLogService;

  @Test
  void handle() {
    TaskMessage taskMessage = TaskMessage.buildTaskMessage(TASK_ID, USER_ID, ROBOT_ID, Command.END_TASK);

    when(taskRepository.findById(TASK_ID)).thenReturn(Optional.of(buildTaskEntity(taskMessage)));

    stopCommandHandler.handle(taskMessage);

    verify(taskRepository, times(1)).save(any());
    verify(auditLogService, times(1)).logEvent(any(), any());
  }

  @Test
  void handle_taskNotFound() {
    TaskMessage taskMessage = TaskMessage.buildTaskMessage(TASK_ID, USER_ID, ROBOT_ID, Command.END_TASK);

    when(taskRepository.findById(TASK_ID)).thenReturn(Optional.empty());

    ValidationException validationException = assertThrows(ValidationException.class,
        () -> stopCommandHandler.handle(taskMessage));

    verify(taskRepository, times(0)).save(any());
    verify(auditLogService, times(0)).logEvent(any(), any());
    assertEquals(HttpStatus.NOT_FOUND, validationException.getHttpStatus());
    assertEquals("No existing task found for given ID!", validationException.getMessage());
  }

  @Test
  void getCommand() {
    Command command = stopCommandHandler.getCommand();
    assertEquals(Command.STOP_COMMAND, command);
  }
}