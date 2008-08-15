/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client.logging;

/**
 *
 * @author daniel
 */
public interface LogFormatter {

    public String format(LogLevel level, String message);
}
