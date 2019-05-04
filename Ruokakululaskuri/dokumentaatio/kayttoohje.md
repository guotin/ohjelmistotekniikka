# Käyttöohje

Lataa tiedosto [Ruokakululaskuri.jar](https://github.com/guotin/ohjelmistotekniikka/releases/tag/loppupalautus)

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla `java -jar Ruokakululaskuri.jar`

## Kirjautuminen ja rekisteröityminen

Sovellus aukeaa kirjautumisnäkymään, jossa käyttäjä toivotetaan tervetulleeksi ja pyydetään kirjautumaan sisään
<img src="https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/kuvat/kirjautuminen.PNG">

Pystyäkseen kirjautumaan sisään ja käyttämään sovelluksen toiminnallisuuksia, täytyy käyttäjän luoda tunnukset.
Tämä tapahtuu siirtymällä kirjautumisnäkymästä rekisteröitymisnäkymään `Register` -painikkeella.
Rekisteröitymisnäkymään siirtyessä käyttäjälle esitetään seuraavanlainen ruutu:

<img src="https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/kuvat/kayttajan_luominen.PNG">

Rekisteröitymisnäkymässä syötetään haluamat tunnukset ja luodaan käyttä `Create user` -painikkeella.
Tämän jälkeen voidaan siirtyä takaisin kirjautumisnäkymään `Back to login screen` -painikkeella.

## Päänäkymä ja toiminnallisuudet

Kirjauduttuaan käyttäjä voi siirtyy päänäkymään.
Päänäkymässä on mahdollisuus lisätä ostotapahtumia ja nähdä niistä tilastointia.

#### Uuden ostotapahtuman lisääminen

Uusi ostotapahtuma lisätään päänäkymässä syöttämällä ostokseen käytetty summa ja ostotapahtuman päivämäärä ja painamalla `Add` -painiketta
<img src="https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/kuvat/oston_lisays.PNG">

#### Kuukauden ostosten tarkastelu

Ostotapahtumien lisäämisen jälkeen voidaan painaa `Purchases this month` -painiketta, jolloin näytölle piirtyy viivakaavio.
Kuvaajan X-akseli kuvastaa yksittäisiä kuukauden päiviä ja Y-akselilla on nähtävissä kumulatiivinen ruokaostoksiin käytetty rahamäärä.
Kuvaajan alle ilmestyy myös tarkka summa kuukauden ostoksista.
`Refresh total money spent` -painikkeella voidaan tarkastaa kokonaissumma, jolla käyttäjä on ostoksia tehnyt.
Tämä huomioi kaikki ajankohdat.

<img src="https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/kuvat/kuukauden_ostot.PNG">

#### Vuoden ostosten tarkastelu

`Purchases this year` -painike piirtää nykyisen vuoden ruokaostostilastot ruudulle. X-akselilla on kuvattuna kuukaudet ja Y-akselilla rahasumma.
Ohjelma huomioi vain jokaisen kuukauden loppusaldon ja piirtää niistä sopivan viivakaavion.

<img src="https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/kuvat/vuoden_ostot.PNG">

#### Kaikki ostokset listana

Käyttäjä voi halutessaan nähdä kaikki ostotapahtumat listana. Ostotapahtumat tulostuvat pieneen selattavaan laatikkoon painamalla `Refresh purchase list` -painiketta näkymän oikeassa alanurkassa.

<img src="https://github.com/guotin/ohjelmistotekniikka/blob/master/Ruokakululaskuri/dokumentaatio/kuvat/ostokset_lista.PNG">

#### Ohjelmasta poistuminen

Saadessaan kylliksi tietoa ruokaostoksistaan, käyttäjä voi painaa `Logout` -painiketta jolloin sovelluksesta kirjaudutaan ulos ja näkymä vaihtuu takaisin kirjautumisnäkymään.
Tämän jälkeen joku muu voi jatkaa ohjelman käyttöä omilla tunnuksillaan.
