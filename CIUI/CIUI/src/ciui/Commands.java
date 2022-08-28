package ciui;


public enum Commands {
    ACTIVEMQ("-activemq"),
    ACTIVEMQ_ARTEMIS("-activemq-artemis"),
    ALL("-all"),
    AT("-at"),
    CLEAR("-clear"),
    JBOSS_MODULES("-jboss-modules"),
    JBOSS_THREADS("-jboss-threads"),
    OPENLIBERTY("-openliberty"),
    SPRINGBOOT("-springboot"),
    V("-v"),
    WILDFLY("-wildfly"),
    WILDFLYJAKARTA("-wildfly-jakarta");
    
    private String command;
    
    Commands(String command) {
        this.command = command;
    }
    
    public String getCommand() {
        return command;
    }
}
