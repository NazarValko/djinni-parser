package org.nazar.service.properties;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.nazar.service.notification.bot.listener.ChatIdAvailableListener;

/**
 * Singleton class, holds required application data
 */
public enum ApplicationProperties {
    INSTANCE;

    private String password;

    private final List<ChatIdAvailableListener> listeners = new ArrayList<>();

    private Long chatId;

    /**
     * Checks for password presence and put it in data
     *
     * @param passwordForGmail password from program argument
     */
    public void setPassword(String passwordForGmail) {
        if (passwordForGmail != null) {
            password = passwordForGmail;
        } else if (System.getProperty("ParserPassword") != null) {
            password = System.getProperty("ParserPassword");
        } else {
            password = System.getenv("Parser_Password");
        }
    }

    /**
     * Getter for password
     *
     * @return field password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Provides basic smtp configuration
     */
    public Properties getSmtpProperties() {
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        return properties;
    }

    public Long getChatId() {
        return chatId;
    }

    public void addChatIdAvailableListener(ChatIdAvailableListener listener) {
        listeners.add(listener);
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
        for (ChatIdAvailableListener listener : listeners) {
            listener.onChatIdAvailable(chatId);
        }
    }
}
