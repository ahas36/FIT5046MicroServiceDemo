/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.coaas.pizzaorder;

import au.coaas.pizzaentity.Pizza;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * REST Web Service
 *
 * @author ali
 */
@Path("api")
public class ApiResource {

    private static final Logger LOGGER = Logger.getLogger(ApiResource.class.getName());

    private final static Map<Long, Pizza> pizzaRepo = new HashMap<>();

    private static Long baseID = 1L;

    public ApiResource() {
    }

    @Path("order")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String orderPizza(final Pizza pizza) throws IOException {
        pizza.setStataus("Order Confirmed");

        pizza.setId(baseID++);

        pizzaRepo.put(pizza.getId(), pizza);

        new Thread(
                new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    cookPizza(pizza);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ApiResource.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    LOGGER.info(ex.getMessage());
                    Logger.getLogger(ApiResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();

        return "Thanks for your order. Your order id is : " + pizza.getId();
    }

    @Path("order/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Pizza getPizzaByID(@PathParam("id") Long id) {
        return pizzaRepo.get(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Pizza> getAllPizza() {
        return pizzaRepo.values();
    }

    @PUT
    @Path("order/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public void updateStatus(@PathParam("id") Long id, String status) {
        pizzaRepo.get(id).setStataus(status);
        LOGGER.info("The status of Pizza " + id + " is changed to " + status);
    }

    private void cookPizza(Pizza pizza) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
        RequestBody body = RequestBody.create(new Gson().toJson(pizza), mediaType);
        Request request = new Request.Builder()
                .url("http://cook:8080/CookPizza-1.0/webresources/api")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        client.newCall(request).execute();
    }

}
