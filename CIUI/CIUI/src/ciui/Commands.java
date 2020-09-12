package ciui;


public enum Commands {
    ACTIVEMQ("-activemq"),
    ACTIVEMQ_ARTEMIS("-activemq-artemis"),
    ALL("-all"),
    AT("-at"),
    JBOSS_MODULES("-jboss-modules"),
    JBOSS_THREADS("-jboss-threads"),
    OPENLIBERTY("-openliberty"),
    V("-v"),
    WILDFLY("-wildfly");
    
    private String command;
    
    Commands(String command) {
        this.command = command;
    }
    
    public String getCommand() {
        return command;
    }
}
