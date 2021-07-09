package com.techproed.day12;

import com.techproed.pojos.TodosPojo;
import com.techproed.testBase.TestBaseJsonPlaceHolder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.Request;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class PostRequestWithPojo01 extends TestBaseJsonPlaceHolder {

    //POJO  Plain Old Java Object : Basit Eski Java Objesi
    //Pojo encapsulation yöntemi ile oluşturulur.
    //basit manada oluşturacağımız expectedData, requestBody ve actualDatalar için birer kalıptır.

    //Pojo classlar nasıl oluşturulu
    //1- json objectten gelen tüm keyleri private bir değişken olarak tanımlıyorum.
    //2-Her değişken için getter ve setter methodlar oluşturuyorum.
    //3-Parametresiz constructor oluşturuyorum
    //4-Parametreli constructor oluşturuyorum
    //5-toString methodunu oluşturuyorum



     /*
     https://jsonplaceholder.typicode.com/todos
    Request body  {
                      "userId": 21,
                      "id": 201,
                      "title": "Tidy your room",
                      "completed": false
                    }
   Status code is 201
    response body {
                      "userId": 21,
                      "id": 201,
                      "title": "Tidy your room",
                      "completed": false
                    }

 */


    @Test
    public void test(){

        //url endpoint oluşturdum
        spec01.pathParam("parametre1","todos");


        //requestBody oluşturuyorum

        TodosPojo todos=new TodosPojo(21,201,"Tidy your room",false);
        System.out.println(todos);
        // request

        Response response=given().
                contentType(ContentType.JSON).
                spec(spec01).auth().basic("admin","password123").
                body(todos).when().post("/{parametre1}");

        response.prettyPrint();

        //De-serialization (Gson)  ----  POJO

        TodosPojo actualData=response.as(TodosPojo.class);
        Assert.assertEquals(201,response.getStatusCode());
        Assert.assertEquals(todos.getId(),actualData.getId());
        Assert.assertEquals(todos.getUserId(),actualData.getUserId());
        Assert.assertEquals(todos.getTitle(),actualData.getTitle());
        Assert.assertEquals(todos.isCompleted(),actualData.isCompleted());

        //JsonPath ----POJO

        JsonPath json=response.jsonPath();

        Assert.assertEquals(todos.getId(),json.getInt("id"));
        Assert.assertEquals(todos.getUserId(),json.getInt("userId"));
        Assert.assertEquals(todos.getTitle(),json.getString("title"));
        Assert.assertEquals(todos.isCompleted(),json.getBoolean("completed"));

        //Macther class --POJO

        response.
                then().
                assertThat().
                body("userId", equalTo(todos.getUserId()),
                        "id",equalTo(todos.getId()),
                        "title",equalTo(todos.getTitle()),
                        "completed",equalTo(todos.isCompleted()));












    }





}
