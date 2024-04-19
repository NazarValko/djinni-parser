package org.nazar.service.properties;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.nazar.service.notification.bot.listener.ChatIdAvailableListener;
import org.nazar.service.notification.strategy.EmailStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton class, holds required application data
 */
public enum ApplicationProperties {
    INSTANCE;

    private String password;

    private final List<ChatIdAvailableListener> listeners = new ArrayList<>();

    private Long chatId;

    private static final String JDBC_URL = "jdbc:h2:file:C:/Program Files/parser_database";

    private static final String USER = "sa";

    private static final Logger logger = LoggerFactory.getLogger(EmailStrategy.class);

    /**
     * Connects to database and returns connection object
     * @return connection object
     */
    public Connection connect() {
        try {
            Class.forName ("org.h2.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, "");
            initTables(connection);
        } catch (SQLException e) {
            logger.error("Failed to connect to database");
        } catch (ClassNotFoundException e) {
            logger.error("Cannot find driver");
        }
        return null;
    }

    /**
     * Init tables to store data
     * @param connection connection with database object
     */
    private void initTables(Connection connection) {
        String sqlCreateLinks = "CREATE TABLE IF NOT EXISTS parsers_links ("
                + "id SERIAL PRIMARY KEY,"
                + "FK_provider_id INT NOT NULL,"
                + "link VARCHAR(255) NOT NULL,"
                + "FOREIGN KEY (FK_provider_id) REFERENCES link_providers(id)"
                + ");";

        String sqlCreateProviders = "CREATE TABLE IF NOT EXISTS link_providers ("
                + "id INT PRIMARY KEY,"
                + "name VARCHAR(255) NOT NULL"
                + ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlCreateProviders);
            statement.execute(sqlCreateLinks);
            logger.info("Checked and created tables if not exists.");
        } catch (SQLException e) {
            logger.error("Error creating tables if not exists", e);
        }
    }

    /**
     * Checks args and stores it in ApplicationProperties class
     * @param args args from program
     */
    public void readArgsFromCommandLine(String[] args) {
        args = args == null ? new String[]{} : args;
        if (args.length == 0) {
            setPassword(null);
        } else {
            setPassword(args[0]);
        }
    }

    /**
     * Checks for password presence and put it in data
     *
     * @param passwordForGmail password from program argument
     */
    private void setPassword(String passwordForGmail) {
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
