package com.example.task81c;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ChatModel {
    // Chat model stores the current message pair separately to the rest of chat history (for FlaskAPI)
    private String userMessage; // most recent message sent by the user
    private final List<Map<String, String>> chatHistory; // history of all previous messages
    private Map<String, String> currentMessagePair;

    // Constants
    public static final String USER = "User";
    public static final String LLAMA = "Llama";

    // Initialises chat model with no initial user message, and no history
    public ChatModel() {
        userMessage = null;
        chatHistory = new ArrayList<>();
        currentMessagePair = new HashMap<>();
    }

    /**
     * Adds a message to the chat.
     *
     * @param content The message content.
     * @param sender  The sender of the message (User or Llama).
     */
    public void addMessage(String content, String sender) {
        // If the message is from the user
        if (Objects.equals(sender, USER)) {
            // If there already exists a user message
            if (currentMessagePair.containsKey(USER)) {
                // Insert an empty Llama message, and add it to chat history
                currentMessagePair.put(LLAMA, "");
                chatHistory.add(currentMessagePair);
            }

            // Update userMessage
            userMessage = content;

            // Create a new recentMessagePair and add the user message
            currentMessagePair = new HashMap<>(); // Reset recentMessagePair after adding to history
            currentMessagePair.put(USER, content);
            return;
        }

        // If the message is from the chat bot
        if (Objects.equals(sender, LLAMA)) {
            // Ensure bot message follows a user message
            if (currentMessagePair.containsKey(USER)) {
                // Insert the bot message and add this message pair to chat history
                currentMessagePair.put(LLAMA, content);
                chatHistory.add(currentMessagePair);
                currentMessagePair = new HashMap<>(); // Reset recentMessagePair after adding to history
            }
        }
    }

    /**
     * Returns all messages as a list of ChatMessage objects
     * Both the chat history and current message pair data is used.
     *
     * @return List containing ChatMessage objects of each message text and sender
     */
    public List<ChatMessage> getAllMessages() {
        List<ChatMessage> messages = new ArrayList<>();

        // Add key-value pairs from chat history
        for (Map<String, String> message : chatHistory) {
            for (Map.Entry<String, String> entry : message.entrySet()) {
                messages.add(new ChatMessage(entry.getValue(), entry.getKey()));
            }
        }

        // There may also be an unanswered question in the current message pair
        // If so, add the user's most recent message
        if (currentMessagePair.containsKey(USER)) {
            messages.add(new ChatMessage(userMessage, USER));
        }

        return messages;
    }

    /**
     * @return String representation of the chat history and current messages.
     */
    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        // Append chat history
        stringBuilder.append("Chat History:\n");
        List<ChatMessage> allMessages = getAllMessages();
        for (ChatMessage message : allMessages) {
            stringBuilder.append(message.getSender()).append(": ").append(message.getContent()).append("\n");
        }

        return stringBuilder.toString();
    }
}
