# Testausdokumentti

Ruokakululaskuria on testattu JUnit-testeillä ja käymällä läpi sovelluksen käyttötapauksia manuaalisesti käyttöliittymän kautta. JUnit-testeissä otettiin huomioon vain sovelluslogiikasta ja pysyväistalletuksesta vastaavat luokat pakkauksissa application.domain ja application.dao.

## JUnit-testaus

Sovelluksen JUnit-testaus onnistui kolmen testiluokan avulla:

* PurchaseTest

* UserTest

* PurchaseServiceTest

PurchaseTest ja UserTest testasivat lähinnä olioiden samankaltaisuuksia. Tästä oli hyötyä esimerkiksi ostosten järjestämisessä ja käyttäjän sisäänkirjaantumisen validoinnissa.

PurchaseServiceTest huolehti pääasiallisen sovelluslogiikan ja tietokantatalletusten testaamisesta. PurhchaseServiceTest loi erillisen testitietokannan ja testasi erilaisia tietokantatapahtumia valeluokkien avulla.

### Testikattavuus

JUnit-testeillä saavutettu testikattavuus sovelluslogiikan osalta:

* Rivikattavuus 86 %

* Haarakattavuus 75 %

Testaamatta jäi muutamia metodeita, jotka palauttivat vain jonkun tietyn arvon. Esimerkiksi metodi lastDayOfMonth jäi testikattavuuden ulkopuolelle.

## Järjestelmätestaus

Sovellusta testattiin käyttöohjeiden mukaisesti Linux-ympäristössä. Myös Windows-ympäristössä ohjelman päätoiminnallisuudet saatiin testattua, vaikka sovelluksen käynnistäminen erosikin hieman ohjeista. Määrittelydokumentin kaikki toiminnallisuudet saatiin käytyä läpi eikä suurempia epäkohtia noussut esiin.

## Sovellukseen jääneet heikkoudet

Järjestelmätestauksesta ilmeni, että esimerkiksi kirjautumisnäkymän virheilmoitukset voisivat olla kuvaavampia. 


