/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client.logging;

/**
 *
 * @author daniel
 */
public class DefaultLogFormatter implements LogFormatter {

    public String format(LogLevel level, String message) {
        switch (level) {
            case FATAL:
                return "ALLVARLIGT FEL: " + message;
            case ERROR:
                return "FEL: " + message;
            case WARN:
                return "VARNING: " + message;
            case INFO:
                return message;
            case DEBUG:
                return "DEBUG: " + message;
            case TRACE:
                return "TRACE: " + message;
            default:
                return message;
        }
    }

}
