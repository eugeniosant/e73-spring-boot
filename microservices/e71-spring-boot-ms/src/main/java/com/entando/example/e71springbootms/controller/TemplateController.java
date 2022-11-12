package com.entando.example.e71springbootms.controller; 
import org.springframework.web.bind.annotation.*; 
@RestController 
public class TemplateController { 
@CrossOrigin @GetMapping("/api/example") 
public MyResponse getExample() { return new MyResponse("test Data"); } 
public static class MyResponse{ 
private final String payload; 
public MyResponse(String payload) { this.payload = payload; } 
public String getPayload() { return payload; } 
} 
}

