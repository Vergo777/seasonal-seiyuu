# Seasonal Seiyuu - a way to easily track what seasonal anime your favourite voice actors are involved in

## The problem statement

Every new season of anime brings with it about 30-40+ new different shows. Most of the main anime tracking websites such
as [MyAnimeList](https://myanimelist.net/) now offer a [dedicated seasonal page](https://myanimelist.net/anime/season) to track all these new shows in one place,
making it convenient to browse what's coming up. 

However, a sizeable number of anime fans choose what to watch in a new season based on the voice cast for the show. Specifically, dedicated fans of certain
[seiyuus](https://en.wiktionary.org/wiki/seiyuu) (roughly "voice actor" in Japanese) choose what to watch based on whichever shows their favourite seiyuu is involved in.

As things stand, while it's simple enough to look up the voice cast for a given single anime, no major anime platform provides the ability to do the "reverse" search - finding 
out what shows in the current season a particular given seiyuu is involved in. This is the problem that **Seasonal Seiyuu** aims to solve. 

The live version of the app is normally accessible here: https://www.vergo.moe/seiyuu

## How it works

Seasonal Seiyuu makes use of the incredible [Jikan API](https://jikan.moe/) to fetch all related information from MAL. The rough flow of how the application works 
to produce a mapping of each seiyuu to the shows they're working on in the current season is documented in the following
Cucumber feature file: https://github.com/Vergo777/seasonal-seiyuu/blob/master/src/test/resources/moe/vergo/seasonalseiyuuapi/domain/generate_current_season_summary.feature

## Getting started

This project is built using Spring Boot 3.0 and Java 17, and can be imported into any IDE by pointing it to the build.gradle file. I've personally used IntelliJ to develop, test and run this. 

## Interesting features/challenges

- Spring Boot 3/Spring 6 brings with it [HTTP Interfaces](docs.spring.io/spring-framework/docs/current/reference/html/integration.html#rest-http-interface) - now allowing us to define an HTTP service as a Java interface with annotated methods for HTTP exchanges. Compared to the previous way of doing this with `RestTemplate` or `WebClient`, this massively cuts down on boilerplate code for simple REST client usages.
- JDK 14 introduced [Records](https://docs.oracle.com/en/java/javase/14/language/records.html), which are a new kind of type declaration and ideal for use as "plain data carriers"
  - These are used liberally throughout the project, and leads to significantly more succinct and cleaner code. [See usage here](https://github.com/Vergo777/seasonal-seiyuu/blob/master/src/test/java/moe/vergo/seasonalseiyuuapi/domain/GenerateCurrentSeasonSummarySteps.java#L187) as an example
- Jikan's [seasons API endpoint](https://docs.api.jikan.moe/#tag/seasons) uses the concept of "pagination", where instead of returning all shows for a given season in a single API call, it instead splits these up between multiple "pages" and any individual call provides only a subset of the overall results
  - To fetch all results, custom logic in the adapter was required to first check the total number of pages available as specified in the API response, and then loop through each page by making individual API calls to fetch the per-page results. Finally these individual page results are all combined together at the adapter level itself, and passed to the domain as a single List of consolidated results - hence keeping "complexity at the edges". 
- The seasonal seiyuu data effectively only changes at the start of each season (typically 12-13 weeks). To make use of the relative static nature of this data over this period, in addition to [Spring's standard in-memory caching](https://reflectoring.io/spring-boot-cache/) we also make use of [MapDB](https://mapdb.org/) to provide a readily available cached version of all required data using a file-based approach.
  - Hence once the data is initially generated for a given season, all future requests throughout the season can simply be served from the file-based cache
- For convenience, the UI for the application is currently served up by this Spring project itself by making use of a custom [Resource Handler](https://stackoverflow.com/questions/21123437/how-do-i-use-spring-boot-to-serve-static-content-located-in-dropbox-folder)
  - This UI was written by me almost 6 years ago now in a [previous iteration of Seasonal Seiyuu](https://github.com/Vergo777/seasonal-seiyuu-nodeOld/tree/master/seasonal-seiyuu-ui), as a way to learn React. This hasn't been touched since, so the REST API which serves the UI still needs to adhere to whatever models/contracts I came up with as a React noob all those years ago!

## Overall design and architecture 

This application follows the principles of Hexagonal Architecture (aka "Ports and Adapters" architecture): https://reflectoring.io/spring-hexagonal/. The basic idea is to "keep complexity at the edges" at the
adapter level, and allow the core business logic to be clearly and simply expressed in the domain layer.

## Testing strategy

In line with Hexagonal Architecture guidelines, the following tests have been used for different parts of the application: 

- Domain logic is covered by unit tests written with [Cucumber](https://cucumber.io/). This provides a descriptive overview of how the core logic works.
  - See `generate_current_season_summary.feature`
  - Input adapter (REST Controller) tests are covered using the standard `WebMvcTest` Spring integration testing approach
  - Output adapter (using Spring 6's new HTTP Interface) tests are covered using `MockServer` as [laid out here](https://www.baeldung.com/spring-6-http-interface). As this is still a relatively new feature, this is currently the only mainstream guidance that exists for how to go about testing these new interfaces.

Note that due to lack of time, tests have not been written for each and every part of the application. Instead, a representative test has been written for each main "subset" to demonstrate how other similar components will eventually be tested 
as well (eg. one input adapter test, one output adapter test, one usecase test etc.)

## Additional backlog items

- Add more detailed error/exception handling. Currently the only custom exception is `GetSeiyuuDetailsException` - there is further scope to focus on capturing specific error situations
- Add tests for remaining input adapters, output adapters and usecases
- Add an end-to-end integration test covering the main paths/functionalities of the application
- Add tests for the MapDB caching
- Add additional logging to help with tracking the application flow/debugging in case of any issues
- Have the static UI files be served separately via nginx on vergo.moe, rather than being bundled as part of this API project