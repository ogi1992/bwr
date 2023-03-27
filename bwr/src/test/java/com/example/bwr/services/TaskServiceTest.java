package com.example.bwr.services;

import static com.example.bwr.utils.TestUtils.ROBOT_ID;
import static com.example.bwr.utils.TestUtils.TASK_ID;
import static com.example.bwr.utils.TestUtils.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bwr.entities.TaskEntity;
import com.example.bwr.enums.ActionType;
import com.example.bwr.enums.Command;
import com.example.bwr.enums.RobotState;
import com.example.bwr.enums.Status;
import com.example.bwr.exceptions.ValidationException;
import com.example.bwr.models.AuditLogMessage;
import com.example.bwr.models.TaskMessage;
import com.example.bwr.producers.TaskProducer;
import com.example.bwr.repositories.TaskRepository;
import com.example.bwr.utils.TestUtils;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

  @InjectMocks
  private TaskService taskService;
  @Mock
  private TaskRepository taskRepository;
  @Mock
  private AuditLogService auditLogService;
  @Mock
  private ValidationService validationService;
  @Mock
  private TaskProducer taskProducer;
  @Mock
  private RobotService robotService;

  @Captor
  ArgumentCaptor<TaskMessage> taskMessageArgumentCaptor;
  @Captor
  ArgumentCaptor<TaskEntity> taskEntityArgumentCaptor;
  @Captor
  ArgumentCaptor<AuditLogMessage> auditLogMessageArgumentCaptor;

  @Test
  void uploadTask() {
    when(taskRepository.save(any())).thenReturn(TestUtils.buildTaskEntity(Status.PENDING));

    taskService.uploadTask(TestUtils.buildTask(), USER_ID);

    verify(taskRepository).save(taskEntityArgumentCaptor.capture());
    verify(auditLogService).logEvent(auditLogMessageArgumentCaptor.capture(), eq(ROBOT_ID));
    verify(validationService).validateRobotExists(ROBOT_ID);

    TaskEntity taskEntity = taskEntityArgumentCaptor.getValue();
    AuditLogMessage auditLogMessage = auditLogMessageArgumentCaptor.getValue();

    assertEquals(Status.PENDING, taskEntity.getStatus());
    assertEquals(ActionType.UPLOAD_TASK, auditLogMessage.getActionType());
  }

  @Test
  void startCommand_robotInOffState() {
    when(taskRepository.findById(TASK_ID)).thenReturn(Optional.of(TestUtils.buildTaskEntity(Status.PENDING)));
    when(robotService.findById(ROBOT_ID)).thenReturn(Optional.of(TestUtils.buildRobotEntity(RobotState.OFF)));

    taskService.startCommand(TASK_ID, USER_ID);

    verify(taskProducer).sendMessage(taskMessageArgumentCaptor.capture(), eq(ROBOT_ID));
    verify(auditLogService).logEvent(any(), any());
    verify(validationService).validateCommand(Status.PENDING, USER_ID, Command.START_COMMAND);

    TaskMessage taskMessage = taskMessageArgumentCaptor.getValue();
    assertEquals(Command.TURN_ON_ROBOT, taskMessage.getCommand());
  }

  @Test
  void startCommand_robotInOnState() {
    when(taskRepository.findById(TASK_ID)).thenReturn(Optional.of(TestUtils.buildTaskEntity(Status.PENDING)));
    when(robotService.findById(ROBOT_ID)).thenReturn(Optional.of(TestUtils.buildRobotEntity(RobotState.ON)));

    taskService.startCommand(TASK_ID, USER_ID);

    verify(taskProducer).sendMessage(taskMessageArgumentCaptor.capture(), eq(ROBOT_ID));
    verify(auditLogService).logEvent(any(), any());
    verify(validationService).validateCommand(Status.PENDING, USER_ID, Command.START_COMMAND);

    TaskMessage taskMessage = taskMessageArgumentCaptor.getValue();
    assertEquals(Command.START_COMMAND, taskMessage.getCommand());
  }

  @Test
  void startCommand_taskNotFound() {
    when(taskRepository.findById(TASK_ID)).thenReturn(Optional.empty());

    ValidationException validationException = assertThrows(ValidationException.class,
        () -> taskService.startCommand(TASK_ID, USER_ID));

    verify(taskProducer, times(0)).sendMessage(any(), any());
    verify(auditLogService, times(0)).logEvent(any(), any());
    verify(robotService, times(0)).findById(ROBOT_ID);
    verify(validationService, times(0)).validateCommand(any(), any(), any());
    assertEquals(HttpStatus.NOT_FOUND, validationException.getHttpStatus());
    assertEquals("No existing task found for given ID!", validationException.getMessage());
  }

  @Test
  void startCommand_robotNotFound() {
    when(taskRepository.findById(TASK_ID)).thenReturn(Optional.of(TestUtils.buildTaskEntity(Status.PENDING)));
    when(robotService.findById(ROBOT_ID)).thenReturn(Optional.empty());

    ValidationException validationException = assertThrows(ValidationException.class,
        () -> taskService.startCommand(TASK_ID, USER_ID));

    verify(taskProducer, times(0)).sendMessage(any(), any());
    verify(auditLogService, times(0)).logEvent(any(), any());
    verify(robotService).findById(ROBOT_ID);
    verify(validationService).validateCommand(Status.PENDING, USER_ID, Command.START_COMMAND);
    assertEquals(HttpStatus.NOT_FOUND, validationException.getHttpStatus());
    assertEquals("No existing robot found for given ID!", validationException.getMessage());
  }

  @Test
  void stopCommand() {
    when(taskRepository.findById(TASK_ID)).thenReturn(Optional.of(TestUtils.buildTaskEntity(Status.IN_PROGRESS)));

    taskService.stopCommand(TASK_ID, USER_ID);

    verify(taskProducer).sendMessage(any(), any());
    verify(auditLogService).logEvent(any(), any());
    verify(validationService).validateCommand(Status.IN_PROGRESS, USER_ID, Command.STOP_COMMAND);
  }

  @Test
  void stopCommand_taskNotFound() {
    when(taskRepository.findById(TASK_ID)).thenReturn(Optional.empty());

    ValidationException validationException = assertThrows(ValidationException.class,
        () -> taskService.stopCommand(TASK_ID, USER_ID));

    verify(taskProducer, times(0)).sendMessage(any(), any());
    verify(auditLogService, times(0)).logEvent(any(), any());
    verify(validationService, times(0)).validateCommand(any(), any(), any());
    assertEquals(HttpStatus.NOT_FOUND, validationException.getHttpStatus());
    assertEquals("No existing task found for given ID!", validationException.getMessage());
  }

  @Test
  void endTask() {
    when(taskRepository.findById(TASK_ID)).thenReturn(Optional.of(TestUtils.buildTaskEntity(Status.IN_PROGRESS)));

    taskService.endTask(TASK_ID, USER_ID);

    verify(taskProducer).sendMessage(any(), any());
    verify(auditLogService).logEvent(any(), any());
    verify(validationService).validateCommand(Status.IN_PROGRESS, USER_ID, Command.END_TASK);
  }

  @Test
  void endTask_taskNotFound() {
    when(taskRepository.findById(TASK_ID)).thenReturn(Optional.empty());

    ValidationException validationException = assertThrows(ValidationException.class,
        () -> taskService.endTask(TASK_ID, USER_ID));

    verify(taskProducer, times(0)).sendMessage(any(), any());
    verify(auditLogService, times(0)).logEvent(any(), any());
    verify(validationService, times(0)).validateCommand(any(), any(), any());
    assertEquals(HttpStatus.NOT_FOUND, validationException.getHttpStatus());
    assertEquals("No existing task found for given ID!", validationException.getMessage());
  }
}