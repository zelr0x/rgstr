# rgstr

An example service that allows creating and listing some items. 

Uses `application` as an item and creating it is referred to as `registering`.

This project is an assigned task requiring the app to use
`/testapp/*` context and `testapp.jar` names.
I didn't want to name the whole app `testapp` thus polluting the namespaces,
so I tried to minimize the usage of this word.
Search for comments containing `task requirement` for details.

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:
```shell
    lein ring server
```
or 
```shell
    lein run
```

Or make and run an uberjar:
```shell
    lein uberjar
    java -jar testapp.jar
```

## License

Copyright © 2021 zelr0x
