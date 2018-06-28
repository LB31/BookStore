//package controllers;
//
//import javax.inject.Inject;
//
//import play.mvc.*;
//import play.libs.ws.*;
//import java.util.concurrent.CompletionStage;
//
//public class MyClient implements WSBodyReadables, WSBodyWritables {
//    private final WSClient ws;
//    WSRequest request = ws.url("http://example.com");
//
//    @Inject
//    public MyClient(WSClient ws) {
//        this.ws = ws;
//    }
//    // ...
//}
