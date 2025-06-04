# Opis warsztatu

W ramach warsztatu ze structured concurrency zajmiemy się modelowaniem przygotowan do grill party. 

Przygotowanie takiego przyjęcia jest skomplikowanym procesem, dlatego też podzielimy go na prostsze etapy. W każdym z nich skorzystamy z nieco innego typu `Joiner`-a dla `StructuredTaskScope`.

Do zasymulowania poszczególnych etapów przygotowan można skorzystać z gotowej klasy `Action`, natomiast oczywiście zrobienie tego na własną rękę i puszczenie wodzy fantazji jest jak najbardziej mile widziane :)

Mile widziane jest logowanie na konsolę (wraz z timestampami) tego co dzieje się w aplikacji (tak aby np. było widać to że rozpoczyna się przygotowanie sałatki). 

Osoby ambitne mogą także spróbować napisać testy jednostkowe weryfikujące działanie symulacji - podobne do tych jakie istnieją już w repozytorium.

## Niezbędne oprogramowanie

- JDK 25 (do pobrania ze strony https://jdk.java.net/25/ lub poprzez wybrany manager pakietów - np. SDKMAN)
- IntelliJ IDEA (wystarczy wersja Community)

## Dokumentacja

- https://openjdk.org/jeps/505
- https://download.java.net/java/early_access/loom/docs/api/java.base/java/util/concurrent/StructuredTaskScope.html
- [slajdy w pdf - TODO]

## Zadanie 1 - coś na ząb :)

Żaden grill nie może obyć się bez dobrego jedzenia, dlatego też pierwsza z zaproszonych osób - Alicja otrzymała zadanie przygotowania trzech potraw:
- sałatki
- pizzy
- frytek

Teoretycznie te trzy potrawy da się przygotować równolegle (tak wiem - to tylko teoria :)) - zamodeluj zatem taki proces. Oczywiście aby dało się go przetestować przyjmij że przygotowanie każdej z powyższych potrwa zajmuje maksymalnie 10 sekund (ha ha ha)

W ramach zadania spróbuj zasymulować także sytuację, w której nie uda się przygotować którejś z tych potraw (np. frytki ulegną przypaleniu) - przyjmijmy wtedy dwa możliwe warianty działania:
- niepowodzenie przy którejkolwiek potrawie niweczy cały proces (albo będa trzy dania albo nic) - w takiej sytuacji oczywiście przerywamy przygotowanie pozostałych
- lub też wydajemy te dania, które udało się dokonczyć

## Zadanie 2 - bez picia nie ma życia :)

Mały disclaimer: absolutnie nie chodzi tutaj o alkohol :) Ale jak można się spodziewać - nie da się zrobić grilla w wersji wyłącznie "na sucho" - niezbędne nam bedą napoje ! 

Ich dostarczeniem zajmie się drugi zaproszony gość - Bob. Jest on niesamowicie leniwą (i skąpą) osobą dlatego postanowił za wiele nie wydać a przy okazji niewiele się narobić.
W tym celu postanowił wysłać trójkę swoich dzieci - każde do innego sklepu:
- pierwsze dziecko miało za zadanię kupić Coca-Colę
- drugie - Fantę
- trzecie - Sprite

Przyjął także założenie, iż to dziecko któremu uda się jako pierwszemu kupić napój da znać pozostałym - by wracały do domu bez zrobienia zakupów (po co bowiem wydawać tyle pieniędzy - jeden napój wystarczy dla wszystkich gości :))

Twoim zadaniem jest zamodelowanie opisanego powyżej procesu. 

W ramach zadania spróbuj także zasymulować sytuację, w której zakładamy maksymalny czas na dokonanie zakupu - jeżeli żadnemu dziecku nie uda się w tym czasie kupić napoju to zgłaszamy błąd.

## Zadanie 3 - jeden grill by połączyć ich wszystkich :)

Zarówno Alicja jak i Bob otrzymali już swoje zadania, natomiast w przypdaku trzeciego gościa - Carol sytuacja jest inna! Jej jedynym zmartwieniem jest dobranie odpowiedniego stroju - co oczywiście jak wiadomo musi swoje potrwać :)

Jednakże dla wszystkich gości sytuacja jest jednakowa - wykonujesz swoje zadania a następnie udajesz się na imprezę

Zasymuluj zatem proces docierania wszystkich gości na przyjęcie. 

Oczywiście każda z osób wykonuje swoje zadania niezależnie od pozostałych; dodatkwo jeżeli któremukolwiek z gości nie uda się wykonać swojej części pracy całe przyjęcie zostaje anulowane. 

W ramach zadania spróbuj także zasymulować dwa inne scenariusze:
- aby impreza się rozpoczęła wystarczy że dotrą dwie zaproszone osoby z trzech
- impreza rozpoczyna się niezależnie od tego czy zaproszonym osobom uda się wykonać swoje zadania, ale czekamy na informacje od każdej z nich czy udało się je wykonać czy też nie



