# Jaspe
REST API Partial Response Library for Spring Boot Applications

Jaspe provides JSON partial response (partial resource) support for Spring Boot Applications.

## Getting started
    1. Add the jaspe dependency to your project.
    2. Add the annotation @EnableJaspe to either your SpringBootApplication Main class or your Configuraion class. 

## Understanding the fields parameter
The `fields` parameter filters the API response so that the response only includes a specific set of fields. The `fields` parameter lets you remove nested properties from an API response and thereby reduce your bandwidth usage.

The following rules explain the supported syntax for the `fields` parameter value, which is loosely based on XPath syntax:

* Use a comma-separated list (`fields=a,b`) to select multiple fields.
* Use an asterisk (`fields=*`) as a wildcard to identify all fields.
* Use parentheses (`fields=a(b,c)`) to specify a group of nested properties that will be included in the API response.
* Use a dot-separated (`fields=a.b,a.c)`) to specify a group of nested properties that will be included in the API response.

In practice, these rules often allow several different `fields` parameter values to retrieve the same API response. For example, if you want to retrieve the playlist item ID, title, and position for every item in a playlist, you could use any of the following values:

* `fields=items(id,snippet(title,position))`
* `fields=items.id,items.snippet(title,position))`
* `fields=items.id,items.snippet.title,items.snippet.position`

**Note:** As with all query parameter values, the `fields` parameter value must be URL encoded. For better readability, the examples in this document omit the encoding.
