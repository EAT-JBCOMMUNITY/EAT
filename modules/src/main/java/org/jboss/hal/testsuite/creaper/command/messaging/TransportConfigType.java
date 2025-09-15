package org.jboss.hal.testsuite.creaper.command.messaging;

enum TransportConfigType {
    IN_VM("in-vm-"),
    GENERIC(""),
    REMOTE("remote-");

    final String prefix;

    TransportConfigType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
