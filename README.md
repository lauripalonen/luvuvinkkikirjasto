# Lukuvinkkikirjasto

Helsingin yliopisto, kurssi TKT20006 Ohjelmistotuotanto. Miniprojektin repositorio, ryhmä Rebel Scrum.

## Ohjelma

Yksinkertainen kirjasto lukuvinkkien tallentamiseen, löytyy osoitteesta [lukuvinkki.herokuapp.com](http://lukuvinkki.herokuapp.com)

### Käyttöohjeet

Pääsivulta löydät tietoa projektista.

#### Tallennettujen lukuvinkkien tarkasteleminen

Ohjelma listaa kaikki aiemmin tallentamasi lukuvinkit tiivistetyssä muodossa, lisätiedot saa esiin nappia painamalla. Voit hakutoiminnon avulla rajata valikosta tarkasteltavaksi pelkät linkit tai kirjat sekä suodattaa hakutuloksia tagien avulla. Useamman tagin syöttäminen onnistuu erottamalla sanat välilyönnillä. 

#### Uuden lukuvinkin tallentaminen

Lukuvinkin tyypiksi voit valita linkin tai kirjan, oletuksena tyyppinä on kirja. Sivustolta [https://openlibrary.org/](https://openlibrary.org/) löytyvien kirjojen tiedot on mahdollista täydentää automaattisesti isbn-numeron avulla. Välilyönnillä erotettuna voit syöttää useamman tagin. Lukuvinkki tallentuu vasta nappia painamalla, tallennuksen voi keskeyttää siirtymällä pois lisäys-sivulta. 

### Testaus 

Ohjelma on testattu automatisoiduilla testeillä sekä manuaalisesti. Automaattiset testit käyttävät JUnitia. Manuaalinen testaus on koskenut lähinnä käyttöliittymää. Hyväksymätestit on toteutettu Cucumberilla. 

Luokkien yksikkötestit löytyvät omista kansioistaan [täältä](https://github.com/lauripalonen/lukuvinkkikirjasto/tree/master/src/test/java/lukuvinkkikirjasto).

User storyjen [Cucumber-featuret](https://github.com/lauripalonen/lukuvinkkikirjasto/tree/master/src/test/resources/lukuvinkkikirjasto).

#### CI ja testikattavuus

Raportteja voi tarkastella badgeja klikkaamalla

[![CircleCI](https://circleci.com/gh/lauripalonen/lukuvinkkikirjasto.svg?style=svg)](https://circleci.com/gh/lauripalonen/lukuvinkkikirjasto)  [![codecov](https://codecov.io/gh/lauripalonen/lukuvinkkikirjasto/branch/master/graph/badge.svg)](https://codecov.io/gh/lauripalonen/lukuvinkkikirjasto)

## Scrum

[Product Backlog](https://docs.google.com/spreadsheets/d/11KAIe0QhRNov_tW5voyq-2GYSEpaymTBXAMnGlPEDLc/edit#gid=0)

[Definition of done](https://github.com/lauripalonen/lukuvinkkikirjasto/blob/master/documentation/definition_of_done.md)

[Google Drive -folder](https://drive.google.com/open?id=11m9Bp5TtHezIU4JiUWpIKRGA1es3xSzW)

### Sprintti 1

[Backlog](https://docs.google.com/spreadsheets/d/11KAIe0QhRNov_tW5voyq-2GYSEpaymTBXAMnGlPEDLc/edit#gid=1301402780)

[Burndown chart](https://docs.google.com/spreadsheets/d/11KAIe0QhRNov_tW5voyq-2GYSEpaymTBXAMnGlPEDLc/edit#gid=1130181346)

### Sprintti 2

[Backlog](https://docs.google.com/spreadsheets/d/11KAIe0QhRNov_tW5voyq-2GYSEpaymTBXAMnGlPEDLc/edit#gid=588637824)

[Burndown chart](https://docs.google.com/spreadsheets/d/11KAIe0QhRNov_tW5voyq-2GYSEpaymTBXAMnGlPEDLc/edit#gid=201655712)

### Sprintti 3

[Backlog](https://docs.google.com/spreadsheets/d/11KAIe0QhRNov_tW5voyq-2GYSEpaymTBXAMnGlPEDLc/edit#gid=1248805260)

[Burndown chart](https://docs.google.com/spreadsheets/d/11KAIe0QhRNov_tW5voyq-2GYSEpaymTBXAMnGlPEDLc/edit#gid=1273086550)

### Loppuraportti

[Raportti](https://docs.google.com/document/d/1lIpLWLNGi72i6FHXeSCzzsI__V0TEB7zl6bZEi9D0bE/edit)

