package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.ErrorDTO;
import dto.GetAllContactsDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAllContactsTestsOkhttp {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoic3NhQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNjg1NjE2ODY3LCJpYXQiOjE2ODUwMTY4Njd9.FwmmQsczEL4uy1P_k5mitUkKOSPFaqyveF2pSo2Yf5U";
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    @Test
    public void getAllContactSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .get()
                .addHeader("Authorization", token)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);
        GetAllContactsDTO contactsDTO = gson.fromJson(response.body().string(), GetAllContactsDTO.class);
        List<ContactDTO> contacts = contactsDTO.getContacts();
        for (ContactDTO c:contacts) {
            System.out.println(c.getId());
            System.out.println(c.getEmail());

        }

    }

    @Test
    public void getAllContactWrongToken() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .get()
                .addHeader("Authorization", "hgfcvftgcut")
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 401);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getError(), "Unauthorized");
    }
}
