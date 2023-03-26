CREATE TABLE `bwr-audit`.`audit_log`
(
    `id`          INT          NOT NULL AUTO_INCREMENT,
    `date_time`   DATETIME     NOT NULL,
    `task_id`     INT NULL,
    `action_type` VARCHAR(255) NOT NULL,
    `source_id`   INT NULL,
    `source_type` VARCHAR(255) NOT NULL,
    `target_id`   INT NULL,
    `target_type` VARCHAR(255) NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
);
