# Arkkitehtuurikuvaus

## Rakenne

Sovellus on pakkausrakenteeltaan kolmitasoinen.

<img src="https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/kuvat/pakkausrakenne.png">

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

### Sovelluksen toiminta luokkakaaviona:

<img src="https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/kuvat/luokkakaavio.png">

Käyttäjällä on useita ostoksia ja yhdelle ostotapahtumalle on määritelty yksi käyttäjä.

### Sovelluksen pakkauskaavio:

<img src="https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/kuvat/pakkauskaavio.png">

## Tietojen tallennus

Pakkauksessa `application.dao` sijaitsevat luokat `DatabaseCreator`, `PurchaseDao` ja `UserDao` huolehtivat tietojen tallentamisesta tietokantaan. Sovelluksessa käytetään H2-tietokantaa ja sovellus luo automaattisesti `foodpurchases` -nimisen tietokannan käynnistyksen yhteydessä. Tietokanta sisältää `User` ja `Purchase` taulut. Näiden välillä on toteutettu yhdestä-moneen suhde eli yhdellä käyttäjällä voi olla useampi ostos, mutta ostoksella on vain yksi käyttäjä määriteltynä.

### Tietokannan taulujen CREATE TABLE -lauseet

Ostotapahtumaa kuvaava tietokantataulu:

~~~~sql
CREATE TABLE Purchase (
      id INTEGER PRIMARY KEY AUTO_INCREMENT,
      user_id INTEGER,
      sum INTEGER,
      date DATE,
      FOREIGN KEY (user_id) REFERENCES User(id)
);
~~~~

Käyttäjää kuvaava tietokantataulu:

~~~~sql
CREATE TABLE User (
      id INTEGER PRIMARY KEY AUTO_INCREMENT,
      username VARCHAR(100),
      password VARCHAR(100)
);
~~~~

## Päätoiminnallisuudet

### Käyttäjän luominen

Rekisteröitymisnäkymässä voidaan syöttää tekstikenttiin esimerkiksi "test", "test" ja painaa `Create user` -painiketta. Tällöin sovelluksen kontrolli etenee seuraavanlaisesti, jos "test" käyttäjää ei aikaisemmin ole luotu.

<img src="https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/kuvat/sekvenssikaavio_create_user.png">

### Käyttäjän kirjautuminen sisään

Kirjautumisnäkymässä voidaan syöttää aikaisemmin luodun tunnuksen tiedot ja painaa `Login` -painiketta. Sovellus tarkistaa onko kyseistä paria olemassa tietokannassa ja toimii seuraavanlaisesti jos vastaavuus löytyy:

<img src="https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/kuvat/sekvenssi_login.png">

### Ostotapahtuman lisääminen tietokantaan

Kirjautumisen yhteydessä ohjelma on siirtynyt päänäkymään eli ostosten lisääminen ja niiden tarkastelu on nyt mahdollista. Lisätäkseen ostoksen käyttäjän täytyy syöttää ostotapahtumaan käytetty rahamäärä ja sen päivämäärä. Tämän jälkeen voidaan painaa `Add` -painiketta. Jos syötteet olivat järkeviä, niin sovellus käsittelee tapahtuman seuraavanlaisesti:

<img src="https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/kuvat/sekvenssi_ostos.png">

### Kuukauden ostosten tarkastelu

Kun tietokantaan on lisätty ostotapahtumia, niitä voidaan tarkastella JavaFX:llä toteutetun viivakaavion avulla. `Purchases this month` -painike hakee nykyisen kuukauden ostotapahtumat ja piirtää ne näytölle. Sovelluksen kontrolli etenee seuraavanlaisesti:

<img src="https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/kuvat/sekvenssi_kuukausi.png">

### Vuoden ostosten tarkastelu

Vuoden ostosten tarkastelu eroaa sovelluslogiikaltaan kuukauden ostosten tarkastelusta ainoastaan sillä, että `PurchaseService` -luokan metodi `getPurchasesOfCurrentYear` palauttaa `Map` -tietorakenteessa ostokset. Tässä avaimena toimii kuukausi ja arvona kuukauden aikana käytetty rahasumma. `Purchases this year` -painikkeella sovellus etenee siis tällä tavalla:

<img src="https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/kuvat/sekvenssi_vuosi.png">

### Muut toiminnallisuudet

#### Kokonaisrahamäärän selvittäminen

Käyttäjä voi painaa päänäkymässä `Refresh total money spent` -painiketta, joka hakee kokonaissumman ruokaostoksista. Tämä on toteutettu yksinkertaisella SQL-kyselyllä. Kysely on seuraavanlainen:

~~~~sql
SELECT sum(sum) AS allsums FROM Purchase WHERE user_id = ?;
~~~~

Kysely saa parametrikseen nykyistä käyttäjää kuvaavan avaimen tietokannassa. Kyselyn lopputulos piirretään päänäkymään Label-oliolla.

#### Kaikki ostokset listaan

Käyttäjällä on mahdollisuus nähdä kaikki ostotapahtumat listana. Painamalla `Refresh purchase list` -painiketta suoritetaan SQL-kysely, jonka datan perusteella piirretään ListView -olioon kaikki ostotapahtumat käyttäjän nähtäviksi. Kysely on seuraavanlainen:

~~~~sql
SELECT sum, date FROM Purchase WHERE user_id = ?;
~~~~

Kysely saa taas parametrikseen nykyistä käyttäjää kuvaavan avaimen tietokannassa.

#### Uloskirjautuminen

Uloskirjautuessa kaikki päänäkymän kentät tyhjennetään ja näkymä siirtyy takaisin kirjautumisnäkymään.

## Sovellukseen jääneet heikkoudet
