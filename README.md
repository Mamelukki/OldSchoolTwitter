# Server-side Web Development Java - Project 2021 

A messaging, following and picture sharing app with features similar to Twitter. Requirements for the application are described [here](https://web-palvelinohjelmointi-21.mooc.fi/projekti). The name "OldSchool" refers to traditional functionalities.

### Technical implementation

Technical implementation: Java, Spring Boot, JPA, SQL database (h2 on local, PostgreSQL on Heroku)

### Heroku
[The app is available in Heroku](https://nameless-river-37493.herokuapp.com/).

The app opens the login page by default so I recommend that you start using the app by navigating to the register page (just add /register at the end of the address or [click here](https://nameless-river-37493.herokuapp.com/register)).

### Worth mentioning / needs improvement
- So far there are no automatic tests
- There are also no notifications or error messages
- There are also some other flaws, such as the app crashing with too long messages or too big photos
- The way the messages are limited to the last 25 and comments to the last 10 are clumsy and inefficient and there is a better way to use the database to get the relevant data; due to time limitation I had to make some bad choices to get all the desired features into the app in time before the project deadline so the technical debt was purposeful

### How to use the app

Start by creating a user. You can also test the app with by logging in with either of the following users:

##### EITHER

Username: Joulupukki

Password: secret

##### OR

Username: Punakuono

Password: salainen

- You can write messages on your own profile page and you can comment and likeyour own and other people's messages. The profile page also shows who you follow, who follow you, the messages of the people you follow (if there are any). You can also access either your or other users' photo album through the profile page.

- You can upload photos through the photo album.You can also add a description for the pictures. You can comment, like and delete your own pictures and others can also like and comment your pictures. 

- There is a user search on the navigation bar. The users can be found using a name (not username). The search shows a table of the matching users and they have a send follow request botton next to them. Clicking that will send a follow request to the chosen user. Unfortunately, the app shows you the send follow request button even if you already follow the person. The controller however makes sure that no one gets a request from a person who already follow them or has sent a request. You can view the requests you have made under the Follow requests tab where you can cancel the request if the other person has not yet reacted to it. 

- A user who has received a follow request can view the requests under the Follow requests tab. The user can either reject the request which leads to the deletion of the request or accept it after which the person will show up in the following of the other user and the followers of the opposite user. 

- You can log out using the logout button on the navigation bar. 

## Project requirements (in Finnish)
#### Käyttäjien rekisteröityminen
Käyttäjä rekisteröityy sovellukseen kirjaamalla sovellukseen käyttäjätunnuksen, salasanan sekä nimen. Tämän lisäksi käyttäjältä kysytään profiilin näyttämisessä käytettävää merkkijonoa, jonka perusteella käyttäjän sivu voidaan löytää sovelluksesta. Esim. “https://sovellus.net/kayttajat/profiili-merkkijono”
#### Käyttäjien etsiminen ja seuraaminen
Käyttäjä voi etsiä muita käyttäjiä nimen perusteella. Käyttäjä voi seurata muita järjestelmässä olevia käyttäjiä. Käyttäjä myös tietää keitä hän seuraa.
#### Seuraajat
Käyttäjä voi tarkastella omia seuraajiaan. Seurauksen yhteydessä näytetään seuraajan nimi sekä seurauksen aloitusaika. Seuraajan voi myös halutessaan torjua seuraamasta, tällöin seuraus ei näy kummankaan profiilissa.
#### Kuva-albumi
Jokaisella käyttäjällä on kuva-albumi. Käyttäjä voi lisätä albumiinsa kuvia ja myös poistaa niitä. Kunkin käyttäjän kuva-albumi voi sisältää korkeintaan 10 kuvaa. Jokaiseen kuvaan liittyy myös tekstimuotoinen kuvaus, joka lisätään kuvaan kuvan lisäyksen yhteydessä.
#### Profiilikuva
Käyttäjä voi määritellä yhden kuva-albumissa olevan kuvan profiilikuvaksi.
#### Henkilökohtainen etusivu
Jokaisella käyttäjällä on henkilökohtainen “seinä”, joka sisältää henkilön nimen sekä mahdollisesti määritellyn profiilikuvan. Vain käyttäjä voi lähettää seinälle tekstimuotoisia viestejä, mutta seinällä näkyy myös seurattavien henkilöiden omat viestit. Jokaisesta viestistä näytetään viestin lähettäjän nimi, viestin lähetysaika, sekä viestin tekstimuotoinen sisältö. Viestit näytetään seinällä niiden saapumisjärjestyksessä siten, että seinällä näkyy aina korkeintaan 25 uusinta viestiä.
#### Tykkääminen
Kirjautuneet käyttäjät voivat tykätä kuvista ja seinällä olevista viesteistä. Tykkääminen tapahtuu viestin ja kuvan yhteydessä olevaa tykkäysnappia painamalla. Kukin käyttäjä voi tykätä tietystä kuvasta ja tietystä viestistä korkeintaan kerran (sama käyttäjä ei saa lisätä useampaa tykkäystä tiettyyn kuvaan tai viestiin). Viestien ja kuvien näytön yhteydessä näytetään niihin liittyvä tykkäysten lukumäärä.
#### Kommentointi
Seuraajat voivat kommentoida kuvia ja viestejä. Kommentointi tapahtuu viestin ja kuvan yhteydessä olevan kommentointikentän avulla. Kuvien ja viestien yhteydessä näytetään aina korkeintaan 10 uusinta kommenttia.
#### Apuresursseja:
Apumateriaalia tietokannassa olevan ajan käsittelyyn: https://web-palvelinohjelmointi-21.mooc.fi/ekstra/ajan-kasittely-tietokannassa
