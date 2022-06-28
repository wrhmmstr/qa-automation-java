import io.restassured.*;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Nested;

import java.lang.reflect.Type;

import static org.assertj.core.api.Assertions.*;
import static io.restassured.RestAssured.*;

@DisplayName("API Country Resource Tests")
public class ApiCountryTests {

    @BeforeAll
    public static void setUpAuth() {
        PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
        authScheme.setUserName("admin");
        authScheme.setPassword("admin");
        RestAssured.authentication = authScheme;
    }
    @BeforeAll
    public static void setUpErrorLogging() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    @DisplayName("Get Country By Id")
    class getCountryById {

        @Test
        @DisplayName("Should Get Country When Id Exists")
        public void shouldGetCountryWhenIdExits() {
            CountryDTO newCountry = new CountryDTO()
                    .setId("1")
                    .setCountryName("vo")
                    .setLocations(null);

            CountryDTO retrievedCountry =
                    given()
                            .contentType("*/*")
                            .when()
                            .get("/api/countries/" + newCountry.getId())
                            .then()
                            .statusCode(200)
                            .extract().as(CountryDTO.class);
            assertThat(retrievedCountry.getId())
                    .isEqualTo(newCountry.getId());
            assertThat(retrievedCountry.getCountryName())
                    .isEqualTo(newCountry.getCountryName());
            assertThat(retrievedCountry.getLocations())
                    .isEqualTo(newCountry.getLocations());
        }

        @Test
        @DisplayName("Should Not Get Country When Id Does Not Exists")
        public void shouldNotGetCountryWhenIdDoesNotExits() {
            CountryDTO newCountry = new CountryDTO()
                    .setId("999");
            ErrorResponseDTO newError = new ErrorResponseDTO()
                    .setType("https://www.jhipster.tech/problem/problem-with-message")
                    .setTitle("Not Found")
                    .setStatus("404")
                    .setDetail("404 NOT_FOUND")
                    .setPath("/api/countries/999")
                    .setMessage("error.http.404");

            ErrorResponseDTO retrievedError =
                    given()
                            .contentType("*/*")
                            .when()
                            .get("/api/countries/" + newCountry.getId())
                            .then()
                            .statusCode(404)
                            .extract().as(ErrorResponseDTO.class);
            assertThat(retrievedError.getType())
                    .isEqualTo(newError.getType());
            assertThat(retrievedError.getTitle())
                    .isEqualTo(newError.getTitle());
            assertThat(retrievedError.getStatus())
                    .isEqualTo(newError.getStatus());
            assertThat(retrievedError.getDetail())
                    .isEqualTo(newError.getDetail());
            assertThat(retrievedError.getPath())
                    .isEqualTo(newError.getPath());
            assertThat(retrievedError.getMessage())
                    .isEqualTo(newError.getMessage());
        }

        @Test
        @DisplayName("Should Not Get Country When Id Is Null")
        public void shouldNotGetCountryWhenIdIsNull() {
            CountryDTO newCountry = new CountryDTO()
                    .setId(null);
            ErrorResponseDTO newError = new ErrorResponseDTO()
                    .setType("https://www.jhipster.tech/problem/problem-with-message")
                    .setTitle("Bad Request")
                    .setStatus("400")
                    .setDetail("Unexpected runtime exception")
                    .setPath("/api/countries/null")
                    .setMessage("error.http.400");

            ErrorResponseDTO retrievedError =
                    given()
                            .contentType("*/*")
                            .when()
                            .get("/api/countries/" + newCountry.getId())
                            .then()
                            .statusCode(400)
                            .extract().as(ErrorResponseDTO.class);
            assertThat(retrievedError.getType())
                    .isEqualTo(newError.getType());
            assertThat(retrievedError.getTitle())
                    .isEqualTo(newError.getTitle());
            assertThat(retrievedError.getStatus())
                    .isEqualTo(newError.getStatus());
            assertThat(retrievedError.getDetail())
                    .isEqualTo(newError.getDetail());
            assertThat(retrievedError.getPath())
                    .isEqualTo(newError.getPath());
            assertThat(retrievedError.getMessage())
                    .isEqualTo(newError.getMessage());
        }

        @Test
        @DisplayName("Should Not Get Country When Unauthorized")
        public void shouldNotGetCountryWhenIdUnauthorized() {
            PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
            authScheme.setUserName("admin1");
            authScheme.setPassword("admin1");
            RestAssured.authentication = authScheme;

            CountryDTO newCountry = new CountryDTO()
                    .setId("999");
            ErrorResponseDTO newError = new ErrorResponseDTO()
                    .setStatus("401")
                    .setPath("/api/countries/999");

            ErrorResponseDTO retrievedError =
                    given()
                            .contentType("*/*")
                            .when()
                            .get("/api/countries/" + newCountry.getId())
                            .then()
                            .statusCode(401)
                            .extract().as(ErrorResponseDTO.class);
            assertThat(retrievedError.getStatus())
                    .isEqualTo(newError.getStatus());
            assertThat(retrievedError.getPath())
                    .isEqualTo(newError.getPath());
        }
    }

    @Nested
    @DisplayName("Put Country By Id")
    class putCountryById {

        @Test
        @DisplayName("Should Put Country When Id Exists")
        public void shouldPutCountryWhenIdExits() {
            CountryDTO newCountry = new CountryDTO()
                    .setId("10")
                    .setCountryName("yy");

            CountryDTO retrievedCountry =
                    given()
                            .contentType(ContentType.JSON).accept(ContentType.JSON)
                            .body(newCountry)
                            .when()
                            .put("/api/countries/" + newCountry.getId())
                            .then()
                            .statusCode(200)
                            .extract().as(CountryDTO.class);
            assertThat(retrievedCountry.getId())
                    .isEqualTo(newCountry.getId());
            assertThat(retrievedCountry.getCountryName())
                    .isEqualTo(newCountry.getCountryName());
        }

        @Test
        @DisplayName("Should Not Put Country When Id Does Not Exists")
        public void shouldNotPutCountryWhenIdDoesNotExits() {
            CountryDTO newCountry = new CountryDTO()
                    .setId("999")
                    .setCountryName("yy");

            ErrorResponseDTO newError = new ErrorResponseDTO()
                    .setType("https://www.jhipster.tech/problem/problem-with-message")
                    .setTitle("Entity not found")
                    .setStatus("400")
                    .setMessage("error.idnotfound");

            ErrorResponseDTO retrievedError =
                    given()
                            .contentType(ContentType.JSON).accept(ContentType.JSON)
                            .body(newCountry)
                            .when()
                            .put("/api/countries/" + newCountry.getId())
                            .then()
                            .statusCode(400)
                            .extract().as(ErrorResponseDTO.class);
            assertThat(retrievedError.getType())
                    .isEqualTo(newError.getType());
            assertThat(retrievedError.getTitle())
                    .isEqualTo(newError.getTitle());
            assertThat(retrievedError.getStatus())
                    .isEqualTo(newError.getStatus());
            assertThat(retrievedError.getMessage())
                    .isEqualTo(newError.getMessage());
        }

        @Test
        @DisplayName("Should Not Put Country When Id Is Null")
        public void shouldNotPutCountryWhenIdIsNull() {
            CountryDTO newCountry = new CountryDTO()
                    .setId(null)
                    .setCountryName("yy");

            ErrorResponseDTO newError = new ErrorResponseDTO()
                    .setType("https://www.jhipster.tech/problem/problem-with-message")
                    .setTitle("Bad Request")
                    .setStatus("400")
                    .setDetail("Unexpected runtime exception")
                    .setPath("/api/countries/null")
                    .setMessage("error.http.400");

            ErrorResponseDTO retrievedError =
                    given()
                            .contentType(ContentType.JSON).accept(ContentType.JSON)
                            .body(newCountry)
                            .when()
                            .put("/api/countries/" + newCountry.getId())
                            .then()
                            .statusCode(400)
                            .extract().as(ErrorResponseDTO.class);
            assertThat(retrievedError.getType())
                    .isEqualTo(newError.getType());
            assertThat(retrievedError.getTitle())
                    .isEqualTo(newError.getTitle());
            assertThat(retrievedError.getStatus())
                    .isEqualTo(newError.getStatus());
            assertThat(retrievedError.getDetail())
                    .isEqualTo(newError.getDetail());
            assertThat(retrievedError.getPath())
                    .isEqualTo(newError.getPath());
            assertThat(retrievedError.getMessage())
                    .isEqualTo(newError.getMessage());
        }

        @Test
        @DisplayName("Should Not Put Country When Country Name Is Invalid")
        public void shouldNotPutCountryWhenCountryNameIsInvalid() {
            CountryDTO newCountry = new CountryDTO()
                    .setId("10")
                    .setCountryName("yyy");

            ErrorResponseDTO newError = new ErrorResponseDTO()
                    .setType("https://www.jhipster.tech/problem/constraint-violation")
                    .setTitle("Method argument not valid")
                    .setStatus("400")
                    .setPath("/api/countries/10")
                    .setMessage("error.validation");

            ErrorResponseDTO retrievedError =
                    given()
                            .contentType(ContentType.JSON).accept(ContentType.JSON)
                            .body(newCountry)
                            .when()
                            .put("/api/countries/" + newCountry.getId())
                            .then()
                            .statusCode(400)
                            .extract().as(ErrorResponseDTO.class);
            assertThat(retrievedError.getType())
                    .isEqualTo(newError.getType());
            assertThat(retrievedError.getTitle())
                    .isEqualTo(newError.getTitle());
            assertThat(retrievedError.getStatus())
                    .isEqualTo(newError.getStatus());
            assertThat(retrievedError.getPath())
                    .isEqualTo(newError.getPath());
            assertThat(retrievedError.getMessage())
                    .isEqualTo(newError.getMessage());
        }
        @Test
        @DisplayName("Should Not Put Country When Unauthorized")
        public void shouldNotPutCountryWhenIdUnauthorized() {
            PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
            authScheme.setUserName("admin1");
            authScheme.setPassword("admin1");
            RestAssured.authentication = authScheme;

            CountryDTO newCountry = new CountryDTO()
                    .setId("999");
            ErrorResponseDTO newError = new ErrorResponseDTO()
                    .setStatus("401")
                    .setPath("/api/countries/999");

            ErrorResponseDTO retrievedError =
                    given()
                            .contentType(ContentType.JSON).accept(ContentType.JSON)
                            .body(newCountry)
                            .when()
                            .put("/api/countries/" + newCountry.getId())
                            .then()
                            .statusCode(401)
                            .extract().as(ErrorResponseDTO.class);
            assertThat(retrievedError.getStatus())
                    .isEqualTo(newError.getStatus());
            assertThat(retrievedError.getPath())
                    .isEqualTo(newError.getPath());
        }
    }

    @Nested
    @DisplayName("Delete Country By Id")
    class deleteCountryById {

        @Test
        @DisplayName("Should Delete Country When Id Exists")
        public void shouldPutCountryWhenIdExits() {
            CountryDTO newCountry = new CountryDTO()
                    .setId("1001");

                    given()
                            .contentType(ContentType.JSON).accept(ContentType.JSON)
                            .when()
                            .delete("/api/countries/" + newCountry.getId())
                            .then()
                            .statusCode(204);

        }

        @Test
        @DisplayName("Should Not Delete Country When Id Does Not Exists")
        public void shouldNotDeleteCountryWhenIdDoesNotExits() {
            CountryDTO newCountry = new CountryDTO()
                    .setId("999");

            ErrorResponseDTO newError = new ErrorResponseDTO()
                    .setType("https://www.jhipster.tech/problem/problem-with-message")
                    .setTitle("Internal Server Error")
                    .setStatus("500")
                    .setDetail("Failure during data access")
                    .setPath("/api/countries/" + newCountry.getId())
                    .setMessage("error.http.500");

            ErrorResponseDTO retrievedError =
                    given()
                            .contentType(ContentType.JSON).accept(ContentType.JSON)
                            .when()
                            .delete("/api/countries/" + newCountry.getId())
                            .then()
                            .statusCode(500)
                            .extract().as(ErrorResponseDTO.class);
            assertThat(retrievedError.getType())
                    .isEqualTo(newError.getType());
            assertThat(retrievedError.getTitle())
                    .isEqualTo(newError.getTitle());
            assertThat(retrievedError.getStatus())
                    .isEqualTo(newError.getStatus());
            assertThat(retrievedError.getDetail())
                    .isEqualTo(newError.getDetail());
            assertThat(retrievedError.getPath())
                    .isEqualTo(newError.getPath());
            assertThat(retrievedError.getMessage())
                    .isEqualTo(newError.getMessage());
        }

        @Test
        @DisplayName("Should Not Delete Country When Id Is Null")
        public void shouldNotDeleteCountryWhenIdIsNull() {
            CountryDTO newCountry = new CountryDTO()
                    .setId(null)
                    .setCountryName("yy");

            ErrorResponseDTO newError = new ErrorResponseDTO()
                    .setType("https://www.jhipster.tech/problem/problem-with-message")
                    .setTitle("Bad Request")
                    .setStatus("400")
                    .setDetail("Unexpected runtime exception")
                    .setPath("/api/countries/null")
                    .setMessage("error.http.400");

            ErrorResponseDTO retrievedError =
                    given()
                            .contentType(ContentType.JSON).accept(ContentType.JSON)
                            .when()
                            .put("/api/countries/" + newCountry.getId())
                            .then()
                            .statusCode(400)
                            .extract().as(ErrorResponseDTO.class);
            assertThat(retrievedError.getType())
                    .isEqualTo(newError.getType());
            assertThat(retrievedError.getTitle())
                    .isEqualTo(newError.getTitle());
            assertThat(retrievedError.getStatus())
                    .isEqualTo(newError.getStatus());
            assertThat(retrievedError.getDetail())
                    .isEqualTo(newError.getDetail());
            assertThat(retrievedError.getPath())
                    .isEqualTo(newError.getPath());
            assertThat(retrievedError.getMessage())
                    .isEqualTo(newError.getMessage());
        }

        @Test
        @DisplayName("Should Not Put Country When Country Name Is Invalid")
        public void shouldNotPutCountryWhenCountryNameIsInvalid() {
            CountryDTO newCountry = new CountryDTO()
                    .setId("10")
                    .setCountryName("yyy");

            ErrorResponseDTO newError = new ErrorResponseDTO()
                    .setType("https://www.jhipster.tech/problem/constraint-violation")
                    .setTitle("Method argument not valid")
                    .setStatus("400")
                    .setPath("/api/countries/10")
                    .setMessage("error.validation");

            ErrorResponseDTO retrievedError =
                    given()
                            .contentType(ContentType.JSON).accept(ContentType.JSON)
                            .body(newCountry)
                            .when()
                            .put("/api/countries/" + newCountry.getId())
                            .then()
                            .statusCode(400)
                            .extract().as(ErrorResponseDTO.class);
            assertThat(retrievedError.getType())
                    .isEqualTo(newError.getType());
            assertThat(retrievedError.getTitle())
                    .isEqualTo(newError.getTitle());
            assertThat(retrievedError.getStatus())
                    .isEqualTo(newError.getStatus());
            assertThat(retrievedError.getPath())
                    .isEqualTo(newError.getPath());
            assertThat(retrievedError.getMessage())
                    .isEqualTo(newError.getMessage());
        }
        @Test
        @DisplayName("Should Not Delete Country When Unauthorized")
        public void shouldNotDeleteCountryWhenIdUnauthorized() {
            PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
            authScheme.setUserName("admin1");
            authScheme.setPassword("admin1");
            RestAssured.authentication = authScheme;

            CountryDTO newCountry = new CountryDTO()
                    .setId("999");
            ErrorResponseDTO newError = new ErrorResponseDTO()
                    .setStatus("401")
                    .setPath("/api/countries/999");

            ErrorResponseDTO retrievedError =
                    given()
                            .contentType(ContentType.JSON).accept(ContentType.JSON)
                            .when()
                            .delete("/api/countries/" + newCountry.getId())
                            .then()
                            .statusCode(401)
                            .extract().as(ErrorResponseDTO.class);
            assertThat(retrievedError.getStatus())
                    .isEqualTo(newError.getStatus());
            assertThat(retrievedError.getPath())
                    .isEqualTo(newError.getPath());
        }
    }

    @Nested
    @DisplayName("Post Country By Name")
    class postCountryById {

        @Test
        @DisplayName("Should Create Country When Name Is Valid")
        public void shouldCreateCountryWhenNameIsValid() {
            CountryDTO newCountry = new CountryDTO()
                    .setCountryName("xz");

            CountryDTO retrievedCountry =
                    given()
                            .contentType(ContentType.JSON).accept(ContentType.JSON)
                            .body(newCountry)
                            .when()
                            .post("/api/countries")
                            .then()
                            .statusCode(201)
                            .extract().as(CountryDTO.class);
            assertThat(retrievedCountry.getId())
                    .isNotNull();
            assertThat(retrievedCountry.getCountryName())
                    .isEqualTo(newCountry.getCountryName());
        }

        @Test
        @DisplayName("Should Not Create Country When Name Exists")
        public void shouldNotCreateCountryWhenNameExits() {
            CountryDTO newCountry = new CountryDTO()
                    .setCountryName("yy");

            ErrorResponseDTO newError = new ErrorResponseDTO()
                    .setType("https://www.jhipster.tech/problem/problem-with-message")
                    .setTitle("Internal Server Error")
                    .setStatus("500")
                    .setDetail("Failure during data access")
                    .setPath("/api/countries")
                    .setMessage("error.http.500");

            ErrorResponseDTO retrievedError =
                    given()
                            .contentType(ContentType.JSON).accept(ContentType.JSON)
                            .body(newCountry)
                            .when()
                            .post("/api/countries")
                            .then()
                            .statusCode(500)
                            .extract().as(ErrorResponseDTO.class);
            assertThat(retrievedError.getType())
                    .isEqualTo(newError.getType());
            assertThat(retrievedError.getTitle())
                    .isEqualTo(newError.getTitle());
            assertThat(retrievedError.getStatus())
                    .isEqualTo(newError.getStatus());
            assertThat(retrievedError.getDetail())
                    .isEqualTo(newError.getDetail());
            assertThat(retrievedError.getPath())
                    .isEqualTo(newError.getPath());
            assertThat(retrievedError.getMessage())
                    .isEqualTo(newError.getMessage());
        }

        @Test
        @DisplayName("Should Not Create Country When Name Is Invalid")
        public void shouldNotCreateCountryWhenNameIsInvalid() {
            CountryDTO newCountry = new CountryDTO()
                    .setCountryName("yyy");

            ErrorResponseDTO newError = new ErrorResponseDTO()
                    .setType("https://www.jhipster.tech/problem/constraint-violation")
                    .setTitle("Method argument not valid")
                    .setStatus("400")
                    .setPath("/api/countries")
                    .setMessage("error.validation");

            ErrorResponseDTO retrievedError =
                    given()
                            .contentType(ContentType.JSON).accept(ContentType.JSON)
                            .body(newCountry)
                            .when()
                            .post("/api/countries")
                            .then()
                            .statusCode(400)
                            .extract().as(ErrorResponseDTO.class);
            assertThat(retrievedError.getType())
                    .isEqualTo(newError.getType());
            assertThat(retrievedError.getTitle())
                    .isEqualTo(newError.getTitle());
            assertThat(retrievedError.getStatus())
                    .isEqualTo(newError.getStatus());
            assertThat(retrievedError.getPath())
                    .isEqualTo(newError.getPath());
            assertThat(retrievedError.getMessage())
                    .isEqualTo(newError.getMessage());
        }

        @Test
        @DisplayName("Should Not Create Country When Country Name Is Null")
        public void shouldNotCreateCountryWhenCountryNameIsNull() {
            CountryDTO newCountry = new CountryDTO()
                    .setCountryName(null);

            ErrorResponseDTO newError = new ErrorResponseDTO()
                    .setType("https://www.jhipster.tech/problem/constraint-violation")
                    .setTitle("Method argument not valid")
                    .setStatus("400")
                    .setPath("/api/countries")
                    .setMessage("error.validation");

            ErrorResponseDTO retrievedError =
                    given()
                            .contentType(ContentType.JSON).accept(ContentType.JSON)
                            .body(newCountry)
                            .when()
                            .post("/api/countries")
                            .then()
                            .statusCode(400)
                            .extract().as(ErrorResponseDTO.class);
            assertThat(retrievedError.getType())
                    .isEqualTo(newError.getType());
            assertThat(retrievedError.getTitle())
                    .isEqualTo(newError.getTitle());
            assertThat(retrievedError.getStatus())
                    .isEqualTo(newError.getStatus());
            assertThat(retrievedError.getPath())
                    .isEqualTo(newError.getPath());
            assertThat(retrievedError.getMessage())
                    .isEqualTo(newError.getMessage());
        }
        @Test
        @DisplayName("Should Not Create Country When Unauthorized")
        public void shouldNotCreateCountryWhenIdUnauthorized() {
            PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
            authScheme.setUserName("admin1");
            authScheme.setPassword("admin1");
            RestAssured.authentication = authScheme;

            CountryDTO newCountry = new CountryDTO()
                    .setCountryName("vv");
            ErrorResponseDTO newError = new ErrorResponseDTO()
                    .setStatus("401")
                    .setPath("/api/countries");

            ErrorResponseDTO retrievedError =
                    given()
                            .contentType(ContentType.JSON).accept(ContentType.JSON)
                            .body(newCountry)
                            .when()
                            .post("/api/countries")
                            .then()
                            .statusCode(401)
                            .extract().as(ErrorResponseDTO.class);
            assertThat(retrievedError.getStatus())
                    .isEqualTo(newError.getStatus());
            assertThat(retrievedError.getPath())
                    .isEqualTo(newError.getPath());
        }
    }
}
