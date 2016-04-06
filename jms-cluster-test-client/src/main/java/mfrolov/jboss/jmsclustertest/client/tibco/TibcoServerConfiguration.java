package mfrolov.jboss.jmsclustertest.client.tibco;

public class TibcoServerConfiguration {

    private final String serverUrl;
    private final String userName;
    private final String password;

    TibcoServerConfiguration(String serverUrl, String userName, String password) {
        this.serverUrl = serverUrl;
        this.userName = userName;
        this.password = password;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}
