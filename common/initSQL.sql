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


INSERT INTO `wwm_questions` (difficulty, question, answer0, answer1, answer2, answer3) VALUES
    -- Einfache Fragen
    (1, 'Vervollständigen Sie den Satz: “Von allen Seiten betrachtet – oder auch: Wie man es ...?”', 'startet und gibt Gas', 'biegt ab und verfährt sich', 'hält an und fragt Passanten', 'dreht und wendet'),
    (1, 'Beschäftigt der fünfmalige Tour-de-France-Sieger Hinault entsprechende Hausangestellte, dann arbeiten bei ... ?', 'Focks Tärrier', 'Dallma Tiener', 'Re Pinnscher', 'Bernard Diener'),
    (1, 'Lässt man sich eine Beziehung mit einer Schönheitskönigin ein, hat man sozusagen …?', 'ein Ungleich-Gewicht', 'ein Über-Maß', 'eine Schief-Lage', 'ein Miss-Verhältnis'),
    (1, 'Gesundheitsbewusste Strandurlauber sind auch beim …?', 'Nünftig vernünftig', 'Sichtig umsichtig', 'Sam achtsam', 'Sonnen besonnen'),
    (1, 'Wer einen Kellner sucht, findet ihn buchstäblich im …?', 'September', 'November', 'Dezember', 'Oktober'),

    -- Mittlere Fragen
    (2, 'In welcher Sendung kamen unter anderem Jeanette Biedermann, Mark Forster und Lena Meyer-Landrut ins Tauschgeschäft?', 'Koch mein Leibgericht', 'Verführ meine Frau', 'Bewohn mein Haus', 'Sing meinen Song'),
    (2, 'Was mancher selbst im nüchternen Zustand nicht hinbekommt: Korrekt schreibt sich der beliebte Cocktail …?', 'Caipirinja', 'Cajpirinha', 'Caijpiriña', 'Caipirinha'),
    (2, 'Wobei wird vor einem sogenannten Rebound-Effekt gewarnt, der nicht selten zu einer Abhängigkeit führt?', 'Haarspray', 'Deospray', 'Pfefferspray', 'Nasenspray'),
    (2, 'Die Darstellung welcher Figur wurde schon zweimal mit einem Schauspiel-Oscar prämiert?', 'Dr. Hannibal Lecter', 'Forrest Gump', 'Truman Capote', 'Joker'),
    (2, 'Alfred Gislason ist seit Februar 2020 bereits der zweite Isländer im Amt des deutschen Männer-Nationaltrainers im …?', 'Basketball', 'Volleyball', 'Tennis', 'Handball'),

    -- Schwere Fragen
    (3, 'Wo befinden sich einige der höchsten Alpengipfel?', 'Monte-Purpur-Höhenzug', 'Monte-Lila-Gebirge', 'Monte-Magenta-Kette', 'Monte-Rosa-Massiv'),
    (3, 'Wo wurde der Schriftsteller geboren, der für den Roman „Herkunft“ 2019 mit dem Deutschen Buchpreis ausgezeichnet wurde?', 'Rhodesien', 'Ceylon', 'Tibet', 'Jugoslawien'),
    (3, 'Was war hierzulande bis in die 1950er noch gang und gäbe?', 'Beamtinnenkommunion', 'Krankenschwesterkollekte', 'Sekretärinnenbeichte', 'Lehrerinnenzöllibat'),
    (3, 'Das naturgegebene Schicksal welcher Pflanzen sieht vor, dass die Blüte bei den meisten Arten unweigerlich zu ihrem Tod führt?', 'Gingko', 'Rhododendron', 'Eukalyptus', 'Bambus'),
    (3, 'Die klassisch genormte Europalette EPAL1 besteht aus 78 Nägeln, neun Klötzen und insgesamt wie vielen Brettern?', 'neun', 'zehn', 'zwölf', 'elf');

