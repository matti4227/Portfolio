# Portfolio
Na portfolio składa się kod źródłowy z aplikacji do zarządzania przepisami kulinarnymi, będącą tematem mojej pracy inżynierskiej.
Celem pracy było zaprojektowanie i stworzenie aplikacji umożliwiającej użytkownikowi przeglądanie przepisów kulinarnych, jak i publikowanie własnych. Głównym priorytetem przy jej tworzeniu było postawienie na prostotę w zarządzaniu przepisami oraz szybkość i sprawność w ich wyszukiwaniu. W tym celu zaimplementowano niezbędne mechaniki wyszukiwania, filtrowania i sortowania. Architektura systemu była stworzona na podstawie metodyki REST, do której implementacji został wykorzystany Spring, zaś Angular został wybrany do zaprogramowania warstwy klienckiej.

## Angular
- Tworzone wg zasady one-module-per-component
- Osobne moduły routing
- Stosowane mechanizmy resolve, canActivate, CanDeactivate, loadChildren
- Interceptor przy zapytaniach HTTP
- Stosowanie mechanizmów i operatorów rxjs przy pobieraniu danych ze strony serwerowej 
- Template driven oraz reactive forms
- Stworzone własne pipe, directive, validator

## Spring Boot
- Maven
- Podłączone z bazą danych MySQL
- Podział na model, controler, service, repository
- Ochrona danych za pomocą @PreAuthorize
- Stosowane tokeny JWT
