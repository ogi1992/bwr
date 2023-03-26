CREATE TABLE `bwr`.`task`
(
    `id`       INT          NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(255) NOT NULL,
    `robot_id` INT          NOT NULL,
    `route`    VARCHAR(255) NOT NULL,
    `status`   VARCHAR(45)  NOT NULL DEFAULT 'PENDING',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
);
