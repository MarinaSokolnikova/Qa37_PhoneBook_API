package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactByIDRA {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoic3NhQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNjg2NDAwNzI2LCJpYXQiOjE2ODU4MDA3MjZ9.Sq_moQArXqj1DIk1AoTVWsd06i1gmxC9tNBKwgGK-QE";
    String id;

    @BeforeMethod
    public void preCondition(){
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";

        int i = new Random().nextInt(1000);
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Sonya")
                .lastName("Smit")
                .email("sonya"+i+"@gmail.com")
                .phone("2345678678"+i)
                .address("Rehovot")
                .build();

        String message = given()
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .path("message");

        String[] all = message.split(": ");
        id = all[1];

    }

@Test
    public void deleteContactByID(){
        given()
                .header("Authorization", token)
                .when()
                .delete("contacts/"+id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact was deleted!"));
}

    @Test
    public void deleteContactByIDWrongToken(){
        given()
                .header("Authorization", "jhgg")
                .when()
                .delete("contacts/"+id)
                .then()
                .assertThat().statusCode(401);
    }




}
