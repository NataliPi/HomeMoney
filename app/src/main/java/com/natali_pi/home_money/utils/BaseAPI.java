package com.natali_pi.home_money.utils;

import com.natali_pi.home_money.models.Family;
import com.natali_pi.home_money.models.Human;
import com.natali_pi.home_money.models.LoginData;
import com.natali_pi.home_money.models.Message;
import com.natali_pi.home_money.models.Spending;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Natali-Pi on 13.12.2017.
 */

public interface BaseAPI {
    @GET("/")
//TODO: delete before production
    Observable<List<Family>> test();

    @POST("/rest/register/")
    Observable<Message> register(@Body Human human);

    @POST("/rest/login/")
    Observable<LoginData> login(@Body Human human);

    @GET("/rest/category/add/{familyId}/{categoryId}/{name}/")
    Observable<Message> addCategory(@Path("familyId") String familyId, @Path("categoryId") String categoryId, @Path("name") String name);
///rest/category/update/{familyId}/{categoryId}/{name}
    @POST("/rest/category/update/{familyId}/{categoryId}/{name}")
    Observable<Message> updateCategory(@Body Message message,@Path("familyId") String familyId, @Path("categoryId") String categoryId, @Path("name") String name);

    @GET("/rest/category/hide/{familyId}/{categoryId}")
    Observable<Message> hideCategory(@Path("familyId") String familyId, @Path("categoryId") String categoryId);

    @POST("/rest/spending/add/{purpose}")
    Observable<Message> setSpending(@Path("purpose") PURPOSE purpose, @Body Spending spending);


    @POST("/rest/picture/upload/{id}")
    Observable<Message> uploadPicture(@Body Message message, @Path("id") String id);

    //    @RequestMapping(value = "/rest/picture/upload/{familyId}/{id}", method = RequestMethod.POST)
    //public Message uploadPicture(@RequestBody Message message, @PathVariable String familyId, @PathVariable String id) {
    @POST("/rest/picture/upload/{familyId}/{id}")
    Observable<Message> uploadPicture(@Body Message message, @Path("familyId") String familyId, @Path("id") String id);

    @POST("/rest/settings/profile")
    Observable<Message> updateProfile(@Body Human data);

    @GET("/rest/family/invite/{familyId}")
    Observable<Message> prepareInvitation(@Path("familyId") String familyId);

    //(@PathVariable String familyId, @PathVariable String humanId, @PathVariable String password) {
    @GET("/rest/family/accept/{familyId}/{humanId}/{password}")
    Observable<Message> acceptInvitation(@Path("familyId") String familyId, @Path("humanId") String humanId, @Path("password") String password);

}
