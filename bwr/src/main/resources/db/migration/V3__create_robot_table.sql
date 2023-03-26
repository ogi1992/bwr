CREATE TABLE `bwr`.`robot`
(
    `id`    INT          NOT NULL AUTO_INCREMENT,
    `name`  VARCHAR(255) NOT NULL,
    `state` VARCHAR(45)  NOT NULL DEFAULT 'OFF',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
);

INSERT INTO `robot` (`id`, `name`)
VALUES (1, "test_robot");