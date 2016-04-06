package mfrolov.jboss.jmsclustertest.client.tibco;

public class TibcoServerConfigurationFactory {
    private TibcoServerConfigurationFactory() {

    }

    public static TibcoServerConfiguration localTibcoEms() {
        return new TibcoServerConfiguration("tcp://localhost:7222", "admin", null);
    }
}
