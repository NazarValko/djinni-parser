## Email Password Configuration

The application can accept the email password for SMTP authentication in three different ways:

1. As a command line argument.
2. As a Java system property named "ParserPassword".
3. As an environment variable named "PARSER_PASSWORD".

### Setting the Password

- **Command Line Arguments**:
- java Main "password"

- **Java System Property**:
  You can pass the password as a system property when starting the JVM with the `-D` flag.
- java -DParserPassword=password -cp app.jar org.nazar.ParserApplication
  Replace `password` with the actual password and `app.jar` with your compiled JAR file name.

- **Environment Variable**:
  You can set an environment variable in your system named `PARSER_PASSWORD`.
