# rgstr

Fullstack clojure app using clojure, ring, compojure, datomic, clojurescript, re-frame and some other libraries as dependencies.
Targets java 1.8. Uses leiningen with project.clj, lein-ring, lein-figwheel, re-frame-10x.

Note: figwheel and re-frame-10x are only present in dev profile.

This project is an assigned task requiring the app to be accessible at
`localhost:8080/testapp` and having `testapp.jar` name.


## TODO
* Add feedback to app creation form. Reset form on successful submit. Prevent accidental double submit. Should be easy with reagent-forms.
* Fix table formatting (view.cljs), namely dates and string clamping.
* Address the issues outlined in various comments in code.
* Move to a more flexible table component?


## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen


## Running
```shell
    lein uberjar
    java -jar target/uberjar/testapp.jar
```

Then go to `localhost:8080/testapp`.


## Development

```shell
# terminal 1
lein ring server

# terminal 2
lein figwheel
```

Then go to `localhost:8080/testapp`.


## License

Copyright © 2021 zelr0x
