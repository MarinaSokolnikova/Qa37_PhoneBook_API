package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import okhttp3.*;
import okio.BufferedSink;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class RegistrationTestsOkhttp {
    Gson gson = new Gson();

    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    @Test
    public void registrationSuccess() throws IOException {
        int i = new Random().nextInt(1000);
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("testM"+i+"@gmail.com")
                .password("testM12345$")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        AuthResponseDTO responseDTO = gson.fromJson(response.body().string(), AuthResponseDTO.class);
        System.out.println(responseDTO.getToken());
    }

    @Test
    public void registrationWrongEmail() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("testMgmail.com")
                .password("testM12345$")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);

        ErrorDTO responseDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(responseDTO.getError(), "Bad Request");
    }

    @Test
    public void registrationWrongPassword() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("testM@gmail.com")
                .password("testM1$")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(), 400);

        ErrorDTO responseDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        Assert.assertEquals(responseDTO.getError(), "Bad Request");
    }
}
