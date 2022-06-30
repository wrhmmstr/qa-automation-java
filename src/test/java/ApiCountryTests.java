import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("API Country Resource Tests")
public class ApiCountryTests {

    Integer countryId;
    String countryName;
    static Collection<Integer> countries = new ArrayList<>();
    static Connection connection;
    static String endpoint = "/api/countries";

    @BeforeAll
    public static void connect() throws SQLException {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/app-db",
                "app-db-admin",
                "P@ssw0rd"
        );
    }

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

    @BeforeEach
    public void SetUp() throws SQLException {
        countryName = RandomStringUtils.randomAlphabetic(2);
        countryId = createCountry(countryName);
    }

    @AfterAll
    public static void deleteTestData() throws SQLException {
        if (countries != null) {
            deleteTestCountriesFromDb(countries);
        }
        if (connection != null) {
            connection.close();
        }
    }

    static Integer createCountry(String countryName) throws SQLException {
        PreparedStatement sql = connection.prepareStatement(
                "insert into country as o (id, country_name) values (?::bigint, ?::varchar)",
//                "insert into country as o (country_name) values (?::varchar)",
                Statement.RETURN_GENERATED_KEYS);
        sql.setInt(1, Integer.valueOf(RandomStringUtils.randomNumeric(4)));
        sql.setString(2, countryName);
        sql.executeUpdate();
        ResultSet keys = sql.getGeneratedKeys();
        keys.next();
        int id = keys.getInt(1);
        countries.add(id);
        return id;
    }
//static Integer createCountry(String countryName) throws SQLException {
//        int countryId = Integer.parseInt(RandomStringUtils.randomNumeric(4));
//    PreparedStatement sql = connection.prepareStatement(
//            "insert into country as o (id, country_name) values (?::bigint, ?::varchar)");
//    sql.setInt(1, countryId);
//    sql.setString(2, countryName);
//    sql.executeUpdate();
//    countries.add(countryId);
//    return countryId;
//}

    private static void deleteTestCountriesFromDb(Collection<Integer> countries) throws SQLException {
        for (int id : countries) {
            PreparedStatement sql = connection.prepareStatement("delete from country where id = ?");
            sql.setInt(1, id);
            System.out.println(sql);
            sql.executeUpdate();
        }
        PreparedStatement sql1 = connection.prepareStatement("select * from country where country_name = ?");
        sql1.setString(1, "xz");
        ResultSet resultSet = sql1.executeQuery();
        while (resultSet.next()) {
            PreparedStatement sql2 = connection.prepareStatement("delete from country where country_name = ?");
            sql2.setString(1, "xz");
            System.out.println(sql2);
            sql2.executeUpdate();
        }
    }

    @Nested
    @DisplayName("Get Country By Id")
    class getCountryById {

        @Test
        @DisplayName("Should Get Country When Id Exists")
        public void shouldGetCountryWhenIdExits() {
            CountryDTO newCountry = new CountryDTO()
                    .setId(String.valueOf(countryId))
                    .setCountryName(countryName);

            CountryDTO retrievedCountry =
                    given()
                            .contentType("*/*")
                            .when()
                            .get(endpoint + "/" + newCountry.getId())
                            .then()
                            .statusCode(200)
                            .extract().as(CountryDTO.class);
            assertThat(retrievedCountry.getId())
                    .isEqualTo(newCountry.getId());
            assertThat(retrievedCountry.getCountryName())
                    .isEqualTo(newCountry.getCountryName());
        }

        @Test
        @DisplayName("Should Not Get Country When Id Does Not Exists")
        public void shouldNotGetCountryWhenIdDoesNotExits() {
            CountryDTO newCountry = new CountryDTO()
                    .setId("9999");
            ErrorResponseDTO newError = new ErrorResponseDTO()
                    .setType("https://www.jhipster.tech/problem/problem-with-message")
                    .setTitle("Not Found")
                    .setStatus("404")
                    .setDetail("404 NOT_FOUND")
                    .setPath("/api/countries/" + newCountry.getId())
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
        @Order(999)
        @DisplayName("Should Not Get Country When Unauthorized")
        public void shouldNotGetCountryWhenIdUnauthorized() {
            PreemptiveBasicAuthScheme authScheme1 = new PreemptiveBasicAuthScheme();
            authScheme1.setUserName("admin1");
            authScheme1.setPassword("admin1");
            RestAssured.authentication = authScheme1;

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
                    .setId(String.valueOf(countryId))
                    .setCountryName(countryName);

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
                    .setId("9999")
                    .setCountryName(countryName);

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
                    .setCountryName("zz");

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
                    .setId(String.valueOf(countryId))
                    .setCountryName("yyy");

            ErrorResponseDTO newError = new ErrorResponseDTO()
                    .setType("https://www.jhipster.tech/problem/constraint-violation")
                    .setTitle("Method argument not valid")
                    .setStatus("400")
                    .setPath("/api/countries/"+ newCountry.getId())
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
//        @Test
//        @DisplayName("Should Not Put Country When Unauthorized")
//        public void shouldNotPutCountryWhenIdUnauthorized() {
//            PreemptiveBasicAuthScheme authScheme1 = new PreemptiveBasicAuthScheme();
//            authScheme1.setUserName("admin1");
//            authScheme1.setPassword("admin1");
//            RestAssured.authentication = authScheme1;
//
//            CountryDTO newCountry = new CountryDTO()
//                    .setId(String.valueOf(countryId));
//            ErrorResponseDTO newError = new ErrorResponseDTO()
//                    .setStatus("401")
//                    .setPath("/api/countries/"+ newCountry.getId());
//
//            ErrorResponseDTO retrievedError =
//                    given()
//                            .contentType(ContentType.JSON).accept(ContentType.JSON)
//                            .body(newCountry)
//                            .when()
//                            .put("/api/countries/" + newCountry.getId())
//                            .then()
//                            .statusCode(401)
//                            .extract().as(ErrorResponseDTO.class);
//            assertThat(retrievedError.getStatus())
//                    .isEqualTo(newError.getStatus());
//            assertThat(retrievedError.getPath())
//                    .isEqualTo(newError.getPath());
//        }
    }

    @Nested
    @DisplayName("Delete Country By Id")
    class deleteCountryById {

        @Test
        @DisplayName("Should Delete Country When Id Exists")
        public void shouldPutCountryWhenIdExits() {
            CountryDTO newCountry = new CountryDTO()
                    .setId(String.valueOf(countryId));

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
                    .setId("9999");

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
                    .setCountryName(countryName);

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
                    .setId(String.valueOf(countryId))
                    .setCountryName("yyy");

            ErrorResponseDTO newError = new ErrorResponseDTO()
                    .setType("https://www.jhipster.tech/problem/constraint-violation")
                    .setTitle("Method argument not valid")
                    .setStatus("400")
                    .setPath("/api/countries/" + newCountry.getId())
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
//        @Test
//        @DisplayName("Should Not Delete Country When Unauthorized")
//        public void shouldNotDeleteCountryWhenIdUnauthorized() {
//            PreemptiveBasicAuthScheme authScheme1 = new PreemptiveBasicAuthScheme();
//            authScheme1.setUserName("admin1");
//            authScheme1.setPassword("admin1");
//            RestAssured.authentication = authScheme1;
//
//            CountryDTO newCountry = new CountryDTO()
//                    .setId("999");
//            ErrorResponseDTO newError = new ErrorResponseDTO()
//                    .setStatus("401")
//                    .setPath("/api/countries/999");
//
//            ErrorResponseDTO retrievedError =
//                    given()
//                            .contentType(ContentType.JSON).accept(ContentType.JSON)
//                            .when()
//                            .delete("/api/countries/" + newCountry.getId())
//                            .then()
//                            .statusCode(401)
//                            .extract().as(ErrorResponseDTO.class);
//            assertThat(retrievedError.getStatus())
//                    .isEqualTo(newError.getStatus());
//            assertThat(retrievedError.getPath())
//                    .isEqualTo(newError.getPath());
//        }
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
                    .setCountryName(countryName);

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
//        @Test
//        @DisplayName("Should Not Create Country When Unauthorized")
//        public void shouldNotCreateCountryWhenIdUnauthorized() {
//            PreemptiveBasicAuthScheme authScheme1 = new PreemptiveBasicAuthScheme();
//            authScheme1.setUserName("admin1");
//            authScheme1.setPassword("admin1");
//            RestAssured.authentication = authScheme1;
//
//            CountryDTO newCountry = new CountryDTO()
//                    .setCountryName("vv");
//            ErrorResponseDTO newError = new ErrorResponseDTO()
//                    .setStatus("401")
//                    .setPath("/api/countries");
//
//            ErrorResponseDTO retrievedError =
//                    given()
//                            .contentType(ContentType.JSON).accept(ContentType.JSON)
//                            .body(newCountry)
//                            .when()
//                            .post("/api/countries")
//                            .then()
//                            .statusCode(401)
//                            .extract().as(ErrorResponseDTO.class);
//            assertThat(retrievedError.getStatus())
//                    .isEqualTo(newError.getStatus());
//            assertThat(retrievedError.getPath())
//                    .isEqualTo(newError.getPath());
//        }
    }
}
