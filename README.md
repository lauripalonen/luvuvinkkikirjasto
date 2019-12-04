# Lukuvinkkikirjasto

Helsingin yliopisto, Ohjelmistotuotanto-kurssi. Miniprojektin repositorio, ryhmä Rebel Scrum.

## Ohjelma

Yksinkertainen kirjasto lukuvinkkien tallentamiseen, löytyy osoitteesta [lukuvinkki.herokuapp.com](http://lukuvinkki.herokuapp.com)

### Käyttöohjeet

Pääsivulta voit käyttäjänä valita haluatko tarkastella vanhoja muistiinpanoja vai tallentaa uuden muistiinpanon.

#### Tallennettujen lukuvinkkien tarkasteleminen

Ohjelma listaa automaattisesti kaikki aiemmin tallentamasi lukuvinkit. Voit rajata valikosta tarkasteltavaksi pelkät linkit tai kirjat.

#### Uuden lukuvinkin tallentaminen

Lukuvinkin tyypiksi voit valita linkin tai kirjan, oletuksena tyyppinä on linkki. Syöttämällä tiedot tarjottuihin kenttiin, ja painamalla "Lisää" lukuvinkki tallentuu. Kenttään "Tags" voit halutessasi syöttää tunnisteen, välilyönnillä erotettuna voit syöttää useamman tagin. Painamalla "Takaisin etusivulle", voit siirtyä takaisin pääsivulle tallentamatta mitään. 

### Testaus 

Ohjelma on testattu automatisoiduilla testeillä sekä manuaalisesti. Automaattiset testit käyttävät JUnitia. Manuaalinen testaus on koskenut lähinnä käyttöliittymää. Hyväksymätestit on toteutettu Cucumberilla. 

#### Yksikkötestaus ja integraatiotestaus

Luokkien yksikkötestit löytyvät omista kansioistaan [täältä](https://github.com/lauripalonen/lukuvinkkikirjasto/tree/master/src/test/java/lukuvinkkikirjasto).

Testikattavuusraportti nähtävissä: [CodeCov](https://codecov.io/gh/lauripalonen/lukuvinkkikirjasto).

#### Hyväksymätestit

User storyjen [Cucumber-featuret](https://github.com/lauripalonen/lukuvinkkikirjasto/tree/master/src/test/resources/lukuvinkkikirjasto).

User storylla "As a user I can save writer and title of book to note" ei ole omia hyväksymätestejä, koska sen scenaariot on testattu muiden featurejen yhteydessä. 

#### Raportit

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

