# Arkkitehtuurikuvaus

## Rakenne

Sovellus on pakkausrakenteeltaan kolmitasoinen.

<img src="https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/pakkausrakenne.png">

* application.ui sisältää JavaFX:llä luodun käyttöliittymän
* application.domain sisältää sovelluslogiikasta vastaavat luokat
* application.dao sisältää tietokantaa käsittelevät luokat

## Käyttöliittymä

Sovelluksessa on kolme päänäkymää

* Kirjautumisnäkymä
* Rekisteröitymisnäkymä
* Päänäkymä, joka sisältää ohjelman tärkeimmät toiminnallisuudet

Kaikki näkymät on toteutettu ohjelmallisesti JavaFX:llä ja ne ovat Scene-olioita. Käyttöliittymän nappeihin on liitetty toiminnallisuuksia `application.domain` -pakkauksessa sijaitsevan `PurchaseService` -luokan avulla. Käyttöliittymä kutsuu tämän luokan metodeja tarvittavien tietojen saamiseksi.

## Sovelluslogiikka

### Sovelluksen looginen datamalli luokkakaaviona:

<img src="https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/luokkakaavio.png" width="355">

### Sovelluksen pakkauskaavio:

<img src="https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/pakkauskaavio.png" width="575">

## Tietojen pysyväistalletus

Pakkauksessa `application.dao` sijaitsevat luokat `DatabaseCreator`, `PurchaseDao` ja `UserDao` huolehtivat tietojen tallentamisesta tietokantaan. Sovelluksessa käytetään H2-tietokantaa ja sovellus luo automaattisesti `foodpurchases` -nimisen tietokannan käynnistyksen yhteydessä. Tietokanta sisältää `User` ja `Purchase` taulut. Näiden välillä on toteutettu yhdestä-moneen suhde eli yhdellä käyttäjällä voi olla useampi ostos, mutta ostoksella on vain yksi käyttäjä määriteltynä.

## Päätoiminnallisuudet

### Käyttäjän luominen

Rekisteröitymisnäkymässä voidaan syöttää tekstikenttiin esimerkiksi "test", "test" ja painaa "Create user" painiketta. Tällöin sovelluksen kontrolli etenee seuraavanlaisesti, jos "test" käyttäjää ei aikaisemmin ole luotu.

<img src="https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/sekvenssikaavio_create_user.png" width="575">
