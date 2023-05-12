# RestAndGraphql
Java 17, Spring boot

Launch with `mvn spring-boot:run`

REST API
  * [/](http://localhost:8080/)
  * [/account](http://localhost:8080/account)
  * [/account-admin](http://localhost:8080/account-admin)

GRAPHQL

* Open a browser to view UIs at the following links:
  * [GraphiQL](http://localhost:8080/graphiql)
  * [Altair](http://localhost:8080/altair)
  * [Playground](http://localhost:8080/playground)
  * [Voyager](http://localhost:8080/voyager)

Go to [http://localhost:8080/graphiql](http://localhost:8080/graphiql) to start executing queries. For example:
```
{
  findAllBooks {
    id
    isbn
    title
    pageCount
    author {
      firstName
      lastName
    }
  }
}
```
Or:
```
 {
  findAllAuthors {
    id
  } 
}
```

Or:
```
mutation {
  newBook(
    title: "Java: The Complete Reference, Tenth Edition", 
    isbn: "1259589331", 
    author: 1) {
      id title
  }
}
```


