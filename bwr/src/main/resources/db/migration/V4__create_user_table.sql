CREATE TABLE `bwr`.`user`
(
    `id`   INT          NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE
);

INSERT INTO `user` (`id`, `name`)
VALUES (1, "test_user");