package org.jboss.hal.testsuite.util;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

/**
 * Finds currently unused server ports.
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 * @see <a href="http://www.iana.org/assignments/port-numbers">IANA.org</a>
 */
public class AvailablePortFinder {
    /**
     * The minimum number of user port number.
     */
    public static final int MIN_PORT_NUMBER = 1024;

    /**
     * The maximum number of user port number.
     */
    public static final int MAX_PORT_NUMBER = 65535;

    /**
     * Prevents creating a new instance.
     */
    private AvailablePortFinder() {
        // Do nothing
    }

    /**
     * Returns the {@link Set} of currently unused port numbers
     * ({@link Integer}).  This method is identical to
     * <code>getAvailableTCPPorts(MIN_PORT_NUMBER, MAX_PORT_NUMBER)</code>.
     *
     * WARNING: this can take a very long time.
     */
    public static Set<Integer> getAvailableTCPPorts() {
        return getAvailableTCPPorts(MIN_PORT_NUMBER, MAX_PORT_NUMBER);
    }

    /**
     * Gets an unused port, selected by the system.
     *
     * @throws NoSuchElementException if all ports are used
     */
    public static int getNextAvailableTCPPort() {
        // Here, we simply return an unused port found by the system
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        } catch (IOException ioe) {
            throw new NoSuchElementException(ioe.getMessage());
        }
    }

    /**
     * Gets the next unused (TCP and UDP) port starting at a port.
     *
     * @param fromPort the port to scan for usage
     * @throws NoSuchElementException if all ports are used
     */
    public static int getNextAvailablePort(int fromPort) {
        if (fromPort < MIN_PORT_NUMBER || fromPort > MAX_PORT_NUMBER) {
            throw new IllegalArgumentException("Invalid start port: " + fromPort);
        }

        for (int i = fromPort; i <= MAX_PORT_NUMBER; i++) {
            if (isPortFreeToUse(i)) {
                return i;
            }
        }

        throw new NoSuchElementException("Could not find an unused port " + "above " + fromPort);
    }

    /**
     * Checks to see if a specific port is not currently used for both TCP and UDP.
     *
     * @param port the port to check for usage
     */
    public static boolean isPortFreeToUse(int port) {
        return isUDPPortFreeToUse(port) && isTCPPortFreeToUse(port);
    }

    /**
     * Checks to see if a specific port is not currently used UDP.
     *
     * @param port the port to check for usage
     */
    public static boolean isUDPPortFreeToUse(int port) {
        if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
            throw new IllegalArgumentException("Invalid start port: " + port);
        }

        try (DatagramSocket ds = new DatagramSocket(port)) {
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            // Do nothing
        }
        return false;
    }

    /**
     * Checks to see if a specific port is not currently used for TCP.
     *
     * @param port the port to check for usage
     */
    public static boolean isTCPPortFreeToUse(int port) {
        if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
            throw new IllegalArgumentException("Invalid start port: " + port);
        }

        try (ServerSocket ss = new ServerSocket(port)) {
            ss.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            // Do nothing
        }
        return false;
    }

    /**
     * Returns the {@link Set} of currently not used port numbers ({@link Integer})
     * between the specified port range.
     *
     * @throws IllegalArgumentException if port range is not between
     * {@link #MIN_PORT_NUMBER} and {@link #MAX_PORT_NUMBER} or
     * <code>fromPort</code> if greater than <code>toPort</code>.
     */
    public static Set<Integer> getAvailableTCPPorts(int fromPort, int toPort) {
        if (fromPort < MIN_PORT_NUMBER || toPort > MAX_PORT_NUMBER || fromPort > toPort) {
            throw new IllegalArgumentException("Invalid port range: " + fromPort + " ~ " + toPort);
        }

        Set<Integer> result = new TreeSet<>();

        for (int i = fromPort; i <= toPort; i++) {
            try (ServerSocket ignored = new ServerSocket(i)) {
                result.add(i);
            } catch (IOException e) {
                // Do nothing
            }
        }
        return result;
    }

    /**
     * Gets a port which is not used in both TCP and UDP.
     */
    public static int getNextAvailableNonPrivilegedPort() {
        int port = getNextAvailableTCPPort();
        if (isUDPPortFreeToUse(port)) {
            return port;
        } else {
            return getNextAvailablePort(MIN_PORT_NUMBER);
        }
    }
}
