package com.vkg.kholodyadya;

import android.os.StrictMode;

import androidx.annotation.Nullable;

import com.google.mlkit.vision.barcode.common.Barcode;
import com.vkg.kholodyadya.models.Group;
import com.vkg.kholodyadya.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okio.Buffer;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;


public class APIWrapper {

    private String token;
    private String url;
    private OkHttpClient client = new OkHttpClient();;

    APIWrapper(String token, String url) {
        this.token = token;
        this.url = url;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private String getRequestBody(okhttp3.RequestBody body) throws IOException {
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        return buffer.readUtf8();
    }

    public void loadBarCode(Barcode barcode) {
        RequestBody body = RequestBody.create(
                MediaType.parse("text/plain; charset=utf-8"),
                barcode.getRawValue());
        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "*/*")
                .url(url + "/products/qr")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.code());
        } catch (IOException e) {
            System.out.println("fatal: unable to execute request");
        }
    }

    public void changeProductCategory(int productId, String category) {

        RequestBody body = new FormBody.Builder()
                .add("productId", Integer.valueOf(productId).toString())
                .add("category", category)
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "*/*")
                .url(url + "/products/category")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.code());
        } catch (IOException e) {
            System.out.println("fatal: unable to execute request");
        }
    }

    public void addProduct(Product product) {
        RequestBody body = new FormBody.Builder()
                .add("productId", Integer.valueOf(product.getProductId()).toString())
                .add("productName", product.getTitle())
                .add("category", product.getCategory())
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "*/*")
                .url(url + "/products")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.code());
        } catch (IOException e) {
            System.out.println("fatal: unable to execute request");
        }
    }

    public void deleteProduct(Product product) {
        RequestBody body = new FormBody.Builder()
                .add("productId", Integer.valueOf(product.getProductId()).toString())
                .add("productName", product.getTitle())
                .add("category", product.getCategory())
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "*/*")
                .url(url + "/products")
                .delete(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.code());
        } catch (IOException e) {
            System.out.println("fatal: unable to execute request");
        }
    }

    public @Nullable List<Product> getProducts() {
        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "*/*")
                .url(url + "/products")
                .get()
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            System.out.println(response.code());
        } catch (IOException e) {
            System.out.println("fatal: unable to execute request");
            return null;
        }
        ArrayList<Product> products = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(response.body().string());
            response.close();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject object = arr.getJSONObject(i);
                products.add(new Product(object));
            }
        } catch (JSONException | IOException e) {

        }
        return products;
    }

    public @Nullable List<Group> getGroups() {
        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "*/*")
                .url(url + "/groups")
                .get()
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            System.out.println(response.code());
        } catch (IOException e) {
            System.out.println("fatal: unable to execute request");
            return null;
        }
        ArrayList<Group> groups = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(response.body().string());
            response.close();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject object = arr.getJSONObject(i);
                groups.add(new Group(object));
            }
        } catch (JSONException | IOException e) {

        }
        return groups;
    }

    public void createGroup(Group group) {
        RequestBody body = new FormBody.Builder()
                .add("id", Integer.valueOf(group.getId()).toString())
                .add("groupName", group.getName())
                .add("description", group.getDescription())
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "*/*")
                .url(url + "/groups")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.code());
        } catch (IOException e) {
            System.out.println("fatal: unable to execute request");
        }
    }

    public void addUserToGroup(String groupName, int userId) {
        RequestBody body = RequestBody.create(
                MediaType.parse("text/plain; charset=utf-8"),
                Integer.valueOf(userId).toString());

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "*/*")
                .url(url + "/groups/" + groupName)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.code());
        } catch (IOException e) {
            System.out.println("fatal: unable to execute request");
        }
    }

    public void registerUser(int id, String username, String firstName, String password) {
        RequestBody body = new FormBody.Builder()
                .add("id", Integer.valueOf(id).toString())
                .add("username", username)
                .add("firstName", firstName)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "*/*")
                .url(url + "/auth/register")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.code());
        } catch (IOException e) {
            System.out.println("fatal: unable to execute request");
        }
    }

    public void loginUser(int id, String username, String firstName, String password) {
        RequestBody body = new FormBody.Builder()
                .add("id", Integer.valueOf(id).toString())
                .add("username", username)
                .add("firstName", firstName)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "*/*")
                .url(url + "/auth/register")
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.code());
        } catch (IOException e) {
            System.out.println("fatal: unable to execute request");
        }
    }
}
