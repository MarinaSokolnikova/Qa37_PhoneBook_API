package okhttp;

import com.google.gson.Gson;
import dto.DeleteByIDResponseDTO;
import dto.ErrorDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteContactByIDOkhttp {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoic3NhQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNjg1NjE2ODY3LCJpYXQiOjE2ODUwMTY4Njd9.FwmmQsczEL4uy1P_k5mitUkKOSPFaqyveF2pSo2Yf5U";
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    String id;

    @BeforeMethod
    public void preCondition(){
        //create contact

    }
    @Test
    public void deleteContactByIdSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/4a5263a6-1652-45ff-a687-379f8e0f7fab")
                .delete()
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 200);
        DeleteByIDResponseDTO responseDTO = gson.fromJson(response.body().string(), DeleteByIDResponseDTO.class);
        Assert.assertEquals(responseDTO.getMessage(), "Contact was deleted!");
        System.out.println(responseDTO.getMessage());
    }

    @Test
    public void deleteContactByIdWrongToken() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/4a5263a6-1652-45ff-a687-379f8e0f7fab")
                .delete()
                .addHeader("Authorization", "sdfghjk")
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 401);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getError(), "Unauthorized");

    }

    @Test
    public void deleteContactByIdNotFound() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/4a5263a6-1652-45ff-a687-379f8e0f7fab")
                .delete()
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 400);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(errorDTO.getError(), "Bad Request");

    }


}
