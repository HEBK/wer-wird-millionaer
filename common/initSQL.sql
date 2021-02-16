CREATE TABLE IF NOT EXISTS `wwm_questions` (

    `ID`                INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `difficulty`        INT(1) NOT NULL,
    `question`          VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci,
    `answer0`           VARCHAR(100) NOT NULL COLLATE utf8_unicode_ci,
    `answer1`           VARCHAR(100) NOT NULL COLLATE utf8_unicode_ci,
    `answer2`           VARCHAR(100) NOT NULL COLLATE utf8_unicode_ci,
    `answer3`           VARCHAR(100) NOT NULL COLLATE utf8_unicode_ci,

    `createdAt`         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP

) engine=innodb DEFAULT charset=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `wwm_savedGames` (

    `ID`                    INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `gameName`              VARCHAR(20) NOT NULL COLLATE utf8_unicode_ci,
    `gamerTag`              VARCHAR(16) NOT NULL COLLATE utf8_unicode_ci,
    `questionNumber`        INT(2) NOT NULL,
    `currentQuestionID`     INT NOT NULL,
    `usedQuestions`         VARCHAR(255) COLLATE utf8_unicode_ci NULL DEFAULT NULL,
    `jokersLeft`            VARCHAR(255) NOT NULL COLLATE utf8_unicode_ci,

    `createdAt`             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `lastUpdate`            TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP

) engine=innodb DEFAULT charset=utf8 COLLATE=utf8_unicode_ci;