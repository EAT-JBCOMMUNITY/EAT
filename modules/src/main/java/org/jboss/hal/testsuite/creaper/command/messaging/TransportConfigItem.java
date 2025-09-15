package org.jboss.hal.testsuite.creaper.command.messaging;

enum TransportConfigItem {
    CONNECTOR("connector"),
    ACCEPTOR("acceptor");

    final String name;

    TransportConfigItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
