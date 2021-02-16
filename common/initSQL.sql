CREATE TABLE IF NOT EXISTS `wwm_questions` (

    `ID`                INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `category`          INT(1) NOT NULL,
    `question`          VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci,
    `answer0`           VARCHAR(100) NOT NULL COLLATE utf8_unicode_ci,
    `answer1`           VARCHAR(100) NOT NULL COLLATE utf8_unicode_ci,
    `answer2`           VARCHAR(100) NOT NULL COLLATE utf8_unicode_ci,
    `answer3`           VARCHAR(100) NOT NULL COLLATE utf8_unicode_ci,
    `rightAnswer`       INT(1) NOT NULL

    `createdAt`         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) engine=innodb DEFAULT charset=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `wwm_leaderboard` (

    `ID`                INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`              VARCHAR(60) NOT NULL COLLATE utf8_unicode_ci,
    `euro`              VARCHAR(10) NOT NULL COLLATE utf8_unicode_ci,
    `jokersLeft`        INT(1) NOT NULL,

    `createdAt`         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) engine=innodb DEFAULT charset=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `wwm_savedGames` (

    `ID`                INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`              VARCHAR(60) NOT NULL COLLATE utf8_unicode_ci,
    `questionID`        INT NOT NULL,
    `question`          INT NOT NULL,
    `usedQuestions`     VARCHAR NULL DEFAULT NULL,
    `jokersLeft`        INT(1) NOT NULL,

    `createdAt`         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `lastUpdate`        TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
    ) engine=innodb DEFAULT charset=utf8 COLLATE=utf8_unicode_ci;