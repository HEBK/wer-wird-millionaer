# noinspection SqlNoDataSourceInspectionForFile

# noinspection SqlNoDataSourceInspection

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
    `currentQuestionID`     INT NULL DEFAULT NULL,
    `usedQuestions`         VARCHAR(255) COLLATE utf8_unicode_ci NULL DEFAULT NULL,
    `usedJokers`            VARCHAR(255) NULL DEFAULT NULL COLLATE utf8_unicode_ci,

    `createdAt`             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `lastUpdate`            TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

) engine=innodb DEFAULT charset=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `wwm_highscores` (

    `ID`                    INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `gamerTag`              VARCHAR(50) NOT NULL COLLATE utf8_unicode_ci,
    `gameName`              VARCHAR(20) NOT NULL COLLATE utf8_unicode_ci,
    `solvedQuestions`       INT(2) NOT NULL,
    `usedJokersCount`       INT(1) NOT NULL

) engine=innodb DEFAULT charset=utf8 COLLATE=utf8_unicode_ci;




INSERT INTO `wwm_questions` (difficulty, question, answer0, answer1, answer2, answer3) VALUES
    -- Einfache Fragen
    (1, 'Vervollständigen Sie den Satz: “Von allen Seiten betrachtet – oder auch: Wie man es ...?”', 'startet und gibt Gas', 'biegt ab und verfährt sich', 'hält an und fragt Passanten', 'dreht und wendet'),
    (1, 'Beschäftigt der fünfmalige Tour-de-France-Sieger Hinault entsprechende Hausangestellte, dann arbeiten bei ... ?', 'Focks Tärrier', 'Dallma Tiener', 'Re Pinnscher', 'Bernard Diener'),
    (1, 'Lässt man sich eine Beziehung mit einer Schönheitskönigin ein, hat man sozusagen …?', 'ein Ungleich-Gewicht', 'ein Über-Maß', 'eine Schief-Lage', 'ein Miss-Verhältnis'),
    (1, 'Gesundheitsbewusste Strandurlauber sind auch beim …?', 'Nünftig vernünftig', 'Sichtig umsichtig', 'Sam achtsam', 'Sonnen besonnen'),
    (1, 'Wer einen Kellner sucht, findet ihn buchstäblich im …?', 'September', 'November', 'Dezember', 'Oktober'),
    (1, 'Was ist die Hauptstadt von Portugal?', 'Madrid', 'Valencia', 'Barcelona', 'Lissabon'),
    (1, 'Wie heißt das größte Technologieunternehmen in Südkorea?', 'LG Electronics', 'Hyundai Motor Company', 'Huawei', 'Samsung Electronics'),
    (1, 'Wie lange dauerte der 30-jährige Krieg?', '25 Jahre', '15 Jahre', '45 Jahre', '30 Jahre'),
    (1, 'Welcher ist der „rote Planet“ unseres Sonnensystems?', 'Venus', 'Merkur', 'Jupiter', 'Mars'),
    (1, 'Welcher Planet unseres Sonnensystems ist der Sonne am nächsten?', 'Venus', 'Uranus', 'Pluto', 'Merkur'),
    (1, 'Welches Land ist flächenmäßig das zweitgrößte der Erde?', 'China', 'Russland', 'USA', 'Kanada'),
    (1, 'Wie viele Planeten hat unser Sonnensystem?', '7', '9', '10', '8'),
    (1, 'Wie heißt Pippi Langstrumpfs Affe?', 'Herr Schuler', 'Herr Peterson', 'Herr Nielson', 'Herr Mayer'),
    (1, 'Wie viele Sekunden hat ein Tag?', '24', '32400', '6400', '86400'),
    (1, 'Wie nennt man einen jungen Hund?', 'Fohlen', 'Ferkel', 'Kitz', 'Welpe'),
    (1, 'Wer wählt den Bundespräsidenten?', 'Bundeskanzler', 'Bundesrat', 'Bundestag', 'Bundesversammlung'),
    (1, 'Wofür steht die Abkürzung KGaA?', 'Kreditgesellschaft auf Aktien', 'Kompetenzgesellschaft auf Aktien', 'Kardinalgesellschaft auf Aktien', 'Kommanditgesellschaft auf Aktien'),
    (1, 'Wer ist der derzeitige Bundestagspräsident? (2021)', 'Thomas Oppermann', 'Wolfgang Kubicki', 'Petra Pau', 'Wolfgang Schäuble'),
    (1, 'Welcher Partei gehörte der 16. US-Präsident Abraham Lincoln an?', 'Demokratische Partei', 'Grüne Partei', 'Parteilos', 'Republikanische Partei'),
    (1, 'Gegen welchen US-Präsident wurden erstmals mehr als ein Impeachment-Verfahren gestartet?', 'Richard Nixon', 'Dwight D. Eisenhower', 'George H. W. Bush', 'Donald Trump'),
    (1, 'Was ist das beste Fach der Welt?', 'Soziologie', 'Englisch', 'Mathematik', 'Informatik'),
    (1, 'Was ist H2O?', 'Helium', 'Wasserstoff', 'Sauerstoff', 'Wasser'),
    (1, 'In welchem Land wohnen die meisten Menschen?', 'Kenia', 'Russland', 'USA', 'China'),
    (1, 'Auf welchem Kontinent liegt die Wüste Sahara?', 'Asien', 'Europa', 'Amerika', 'Afrika'),
    (1, 'Wie heißt der höchste Berg der Welt?', 'Alpen', 'Zugspitze', 'Annapurna', 'Mount Everest'),
    (1, 'Wenn du die Buchstaben im Wort "Tatalink" anders anordnest, erhältst du den Namen...', '... eines Baumes', '... eines Landes', '... einer Stadt', '... eines Ozeans'),


    -- Mittlere Fragen
    (2, 'Die Freiheitsstatue in New York war ein Geschenk von:', 'Großbritannien', 'Kanada', 'Kanada', 'Frankreich'),
    (2, 'Wann ging der Erste Weltkrieg zu Ende?', '1914', '1944', '1948', '1918'),
    (2, 'In welcher Sendung kamen unter anderem Jeanette Biedermann, Mark Forster und Lena Meyer-Landrut ins Tauschgeschäft?', 'Koch mein Leibgericht', 'Verführ meine Frau', 'Bewohn mein Haus', 'Sing meinen Song'),
    (2, 'Was mancher selbst im nüchternen Zustand nicht hinbekommt: Korrekt schreibt sich der beliebte Cocktail …?', 'Caipirinja', 'Cajpirinha', 'Caijpiriña', 'Caipirinha'),
    (2, 'Wobei wird vor einem sogenannten Rebound-Effekt gewarnt, der nicht selten zu einer Abhängigkeit führt?', 'Haarspray', 'Deospray', 'Pfefferspray', 'Nasenspray'),
    (2, 'Die Darstellung welcher Figur wurde schon zweimal mit einem Schauspiel-Oscar prämiert?', 'Dr. Hannibal Lecter', 'Forrest Gump', 'Truman Capote', 'Joker'),
    (2, 'Alfred Gislason ist seit Februar 2020 bereits der zweite Isländer im Amt des deutschen Männer-Nationaltrainers im …?', 'Basketball', 'Volleyball', 'Tennis', 'Handball'),
    (2, 'Wie stark ist die Erdachse zur Umlaufbahn (Ekliptik) geneigt?', '25°', '19.25°', '21.5°', '23.5°'),
    (2, 'Wie viele Herzen besitzt ein Oktopus?', 'zwei', 'fünf', 'vier', 'drei'),
    (2, '1930 erhielten Albert Einstein und ein Kollege das US-Patent 1781541. Wofür war es?', 'Mikrowelle', 'Tauchsieder', 'Brotbackautomat', 'Kühlschrank'),
    (2, 'Wie viele Weihnachtsbäume werden in Deutschland pro Jahr verkauft?', 'Etwa 45 Millionen', 'Etwa 20 Millionen', 'Etwa 55 Millionen', 'Etwa 30 Millionen'),
    (2, 'Wie viele Einkerbungen hat ein Golfball?', '512', '128', '256', '336'),
    (2, 'Wer war während des 2. Weltkriegs US-Präsident?', 'John F. Kennedy', 'Harry S. Truman', 'Bill Clinton', 'Roosevelt'),
    (2, 'Aus wie vielen Kräutern ist Jägermeister gemacht?', '54', '46', '36', '56'),
    (2, 'Wie viele Bandscheiben hat ein Mensch?', '22', '28', '27', '23'),
    (2, 'Woraus besteht ein Diamant?', 'Helium', 'Francium', 'Wasserstoff', 'Kohlenstoff'),
    (2, 'Wie viele Stachel hat ein Igel ungefähr?', '2.500', '500', '1.000', '5.000'),
    (2, 'Wo fand 1816 die erste Sitzung des ersten Deutschen Bundestages statt?', 'Berlin', 'Bonn', 'München', 'Frankfurt am Main'),
    (2, 'J.R.R. Tolkien schrieb…', '… Die unendliche Geschichte', '… Hänsel und Gretel', '… Alice im Wunderland', '… Der Herr der Ringe'),
    (2, 'Wie heißt die Hauptstadt Lettlands?', 'Rabat', 'Riat', 'Reykjavik', 'Riga'),
    (2, 'Welches Land gehört nicht zu Afrika?', 'Niger', 'Kamerun', 'Algerien', 'Paraguay'),
    (2, 'Wie heißt die Schicht der Atmosphäre, die der Erde am nächsten ist?', 'Stratosphäre', 'Mesosphäre', 'Thermosphäre', 'Troposphäre'),
    (2, 'Wie viel Liter Luft kann die Lunge eines Blauwals maximal mit einmal einatmen fassen?', '2.500 Liter', '1.200 Liter', '3.900 Liter', '3.000 Liter'),
    (2, 'Womit sind viele Schneidebretter ausgestattet?', 'Wassergrube', 'Bierfurche', 'Weinkerbe', 'Saftrille'),
    (2, 'Welchem Motto hat sich der als "Upcycling" populär gewordene Trend verschrieben?', 'klein, aber oho', 'ohne Moos nix los', 'wer rastet, der rostet', 'aus Alt mach Neu'),
    (2, 'Welches deutsche KFZ-Kennzeichenkürzel steht nicht für die einwohnerreichste deutsche Stadt, deren Name mit diesem Buchstaben beginnt?', 'K', 'S', 'D', 'H'),
    (2, 'In welchem Staat ist Queen Elisabeth II. nicht das Staatsoberhaupt?', 'Jamaika', 'Kanada', 'Australien', 'Costa Rica'),
    (2, 'Wie lauten die ersten 7 Nachkommstellen von π?', '2,7182818', '3,0425925', '3,1515821', '3,1415926'),
    (2, 'Wann wurde das Hermann-Emanuel-Berufskolleg gegründet?', '1904', '1917', '1899', '1909'),
    (2, 'Wann endete das Zeitalter der Antike?', '400 n. Christi', '700 n. Christi', '550 n. Christi', '600 n. Christi'),
    (2, 'Welches Instrument hat Tasten, Pedale und Saiten?', 'Gitarre', 'Violine', 'Harfe', 'Klavier'),

    -- Schwere Fragen
    (3, 'Wo befinden sich einige der höchsten Alpengipfel?', 'Monte-Purpur-Höhenzug', 'Monte-Lila-Gebirge', 'Monte-Magenta-Kette', 'Monte-Rosa-Massiv'),
    (3, 'Was ist die durchschnittliche Oberflächentemperatur auf der Venus?', '215 °C', '615 °C', '395 °C', '460 °C'),
    (3, 'Wie viel Prozent der Masse des Sonnensystems befindet sich in der Sonne auf 1 Prozent genau?', '96 %', ' 89 %', '85 %', '99 %'),
    (3, 'Wo wurde der Schriftsteller geboren, der für den Roman „Herkunft“ 2019 mit dem Deutschen Buchpreis ausgezeichnet wurde?', 'Rhodesien', 'Ceylon', 'Tibet', 'Jugoslawien'),
    (3, 'Was war hierzulande bis in die 1950er noch gang und gäbe?', 'Beamtinnenkommunion', 'Krankenschwesterkollekte', 'Sekretärinnenbeichte', 'Lehrerinnenzöllibat'),
    (3, 'Das naturgegebene Schicksal welcher Pflanzen sieht vor, dass die Blüte bei den meisten Arten unweigerlich zu ihrem Tod führt?', 'Gingko', 'Rhododendron', 'Eukalyptus', 'Bambus'),
    (3, 'Welches ist das einzige deutsche Bundesland, in dem keine kreisfreie Stadt existiert?', 'Brandenburg', 'Sachsen', 'Bremen', 'Saarland'),
    (3, 'Wer bekämpfte sich im „Falklandkrieg“?', 'Deutschland und Frankreich', 'Kanada und Irland', 'Süd- und Nordkorea', 'Argentinien und Großbritannien'),
    (3, 'Welcher Schweizer bekam 1901 den ersten Friedensnobelpreis?', 'Ernst Abbe', 'Antonio Abetti', 'Max Aub', 'Henry Dunant'),
    (3, 'Welche Farbe hat ein Regenbogen ganz außen (oben)?', 'blau', 'gelb', 'grün', 'rot'),
    (3, 'Wie lautet die Hauptstadt des US-Bundesstaates Alaska?', 'Atlanta', 'Denver', 'Frankfort', 'Juneau'),
    (3, 'Welche Ordnungszahl hat Kupfer im Periodensystem der Elemente?', '31', '30', '28', '29'),
    (3, 'Wer war der zweite Bundeskanzler Deutschlands (BRD)?', 'Helmut Schmidt', 'Konrad Adenauer', 'Gerhard Schröder', 'Ludwig Erhard'),
    (3, 'Welcher Schauspieler verkörperte im Jahr 1965 James Bond?', 'Timothy Dalton', 'Roger Moore', 'Pierce Brosnan', 'Sean Connery'),
    (3, 'Wie viele Jahre dauert es in etwa bis der Jupiter die Sonne umrundet hat?', '24 Jahre', '16 Jahre', '20 Jahre', '12 Jahre'),
    (3, 'Wie lauten die ersten 4 Nachkommastellen der Eulerschen Zahl e?', '2,8621', '2,5171', '3,1415', '2,7182'),
    (3, 'Wer hat einen Hammer und eine Feder auf den Mond fallen lassen, um zu demonstrieren, dass sie ohne Luft gleich schnell fallen?', 'Buzz Lightyear', 'Jack Matthew', 'Neil Armstrong', 'David R. Scott'),
    (3, 'Wie weit ist Alpha Centauri ungefähr von der Sonne entfernt?', '3,105 Lichtjahre', '7,295 Lichtjahre', '10,203 Lichtjahre', '4,367 Lichtjahre'),
    (3, 'Die klassisch genormte Europalette EPAL1 besteht aus 78 Nägeln, neun Klötzen und insgesamt wie vielen Brettern?', 'neun', 'zehn', 'zwölf', 'elf');

