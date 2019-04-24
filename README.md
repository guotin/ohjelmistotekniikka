# Food purchase tracker

Sovellus ruokakulujen kirjanpitoon käyttäjäkohtaisesti. Sovelluksen avulla käyttäjä pystyy lisäämään paikallisesti säilytettyyn tietokantaan omia ruokaostoksiaan ja nähdä niistä halutessaan erilaisia tilastoja.

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/vaatimusmaarittely.md)

[Tuntikirjanpito](https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/tuntikirjanpito.md)

[Arkkitehtuuri](https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/arkkitehtuuri.md)

[Käyttöohjeet](https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/kayttoohje.md)

## Releaset

[Viikko 5](https://github.com/guotin/ohjelmistotekniikka/releases/tag/Viikko5)

[Viikko 6](https://github.com/guotin/ohjelmistotekniikka/releases/tag/viikko6)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

`mvn test`

Testikattavuusraportti luodaan komennolla

`mvn jacoco:report`

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedoston _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

`mvn package`

generoi hakemistoon _target_ suoritettavan jar-tiedoston _Ruokakululaskuri-1.0-SNAPSHOT_

### Javadoc

Javadocin voi generoida komennolla

`mvn javadoc:javadoc`

Javadocia voi tarkastella avaamalla selaimella tiedoston _target/site/apidocs/index.html_

### Checkstyle

Tiedoston [checkstyle.xml](https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

`mvn jxr:jxr checkstyle:checkstyle`

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedoston _target/site/checkstyle.html_
