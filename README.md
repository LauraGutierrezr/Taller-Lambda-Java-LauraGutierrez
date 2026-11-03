# Hello World AWS Lambda & API Gateway in Java

AREM Workshop — Laura Gutiérrez.

REST service that calculates the **square of an integer**, implemented as an
**AWS Lambda** function in Java and exposed to the internet through **AWS API Gateway**.

```
Cliente (navegador / curl)
   │  GET https://<id>.execute-api.us-east-1.amazonaws.com/Beta?value=14
   ▼
API Gateway (REST API "mathServices", stage "Beta")
   │  Mapping template: $input.params("value")  →  14
   ▼
AWS Lambda "square"  →  co.edu.escuelaing.services.MathServices::square
   │
   ▼
196
```

## Structure

```
├── pom.xml
└── src
    ├── main/java/co/edu/escuelaing/services/MathServices.java
    └── test/java/co/edu/escuelaing/services/MathServicesTest.java
```

## Service Code

```java
package co.edu.escuelaing.services;

public class MathServices {
    public static Integer square(Integer i) {
        return i * i;
    }
}
```

## Build and Package

Requirements: JDK 17 and Maven 3.8+.

```bash
mvn clean package
```

This generates `target/square-lambda-1.0-SNAPSHOT.jar`, which is the file uploaded to Lambda.

## Deployment

### 1. Lambda Function
1. AWS Console → **Lambda** → **Create function** → *Author from scratch*.
2. Name: `square`. Runtime: **Java 17**. Architecture: x86_64.
3. Permissions: *Use an existing role* → `LabRole` (AWS Academy).
4. **Code** → *Upload from* → *.zip or .jar file* → upload the JAR.
5. **Runtime settings** → *Edit* → Handler: `co.edu.escuelaing.services.MathServices::square`.
6. **Test** → create a new `testSquare` event with content `5` → result `25`.

### 2. API Gateway
1. **API Gateway** → *Create API* → **REST API** → *Build* → *New API*, name it `mathServices`, endpoint **Regional**.
2. On the `/` resource → **Create method** → `GET` → *Lambda function*,
   with **Lambda proxy integration disabled**, using the `square` function.
3. **Method request** → *URL query string parameters* → add `value`.
4. **Integration request** → *URL query string parameters* → `value` = `method.request.querystring.value`.
5. **Integration request** → *Mapping templates* → Content-Type `application/json`, template:
   ```
   $input.params("value")
   ```
6. **Test** with query string `value=14` → response `196`.
7. **Deploy API** → *New stage* `Beta`.

### 3. Test

```bash
curl "https://<api-id>.execute-api.us-east-1.amazonaws.com/Beta?value=14"
# 196
```

## Evidence






## Cleanup

When finished, delete the `mathServices` API and the `square` function to avoid incurring charges.
