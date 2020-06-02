/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import au.coaas.pizzaentity.Pizza;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * REST Web Service
 *
 * @author ali
 */
@Path("api")
public class ApiResources {

    private static final Logger LOGGER = Logger.getLogger(ApiResources.class.getName());

    private void updatePizzaStatus(String status, Long pizzaId) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(status, mediaType);
        Request request = new Request.Builder()
                .url("http://order:8080/PizzaOrder-1.0/webresources/api/order/" + pizzaId)
                .method("PUT", body)
                .addHeader("Content-Type", "text/plain")
                .build();
        client.newCall(request).execute();
    }

    public ApiResources() {
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void cook(final Pizza pizza) {
        LOGGER.info("request recived");
        try {
            updatePizzaStatus("Coocking now", pizza.getId());

            new Thread(
                    new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10000);
                        updatePizzaStatus("Delivering now", pizza.getId());
                    } catch (InterruptedException | IOException ex) {
                        Logger.getLogger(ApiResources.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        } catch (IOException ex) {
            Logger.getLogger(ApiResources.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
