package com.example.task81c;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MessageRequest {
    @POST("chat")
    Call<ChatResponse> sendMessage(@Body ChatModel chatData);
}
