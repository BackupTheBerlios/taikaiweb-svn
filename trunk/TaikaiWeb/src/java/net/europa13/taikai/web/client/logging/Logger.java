/*
 * TaikaiWeb - a web application for managing and running kendo tournaments.
 * Copyright (C) 2008  Daniel Wentzel & Jonatan Wentzel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.europa13.taikai.web.client.logging;

/**
 *
 * @author Daniel Wentzel
 */
public class Logger {

//    private Logger parentLogger;
    private static LogLevel level;
//    private String name;
    private static LogTarget target;
    private static LogFormatter formatter = new DefaultLogFormatter();

//    {
//        formatter = new DefaultLogFormatter();
//    }
    
    /**
     * Constructor. Package private to prevent instatiation outside of the
     * package. Instances of Loggers should be aquired using getLogger.
     * @param name the name of the logger.
     * @param parentLogger the parent logger of the new logger.
     */
//    Logger(String name, Logger parentLogger) {
//        this.name = name;
//        this.parentLogger = parentLogger;
//        this.formatter = new DefaultLogFormatter();
//        level = LogLevel.INFO;
//    }

//    public static Logger getLogger() {
//        return LogManager.getRootLogger();
//    }
//    
//    /**
//     * Retrieves a {@code Logger} with a specified name.
//     * @param name the name of the logger to aquire.
//     * @return the requested logger.
//     */
//    public static Logger getLogger(String name) {
//        return LogManager.getLogger(name);
//    }

    /**
     * Returns the maximum level this logger will log or pass through to a
     * parent.
     * @return the maximum level this logger will log or pass through to a
     * parent.
     */
    public static LogLevel getLevel() {
        return level;
    }

    public static void setLevel(LogLevel level) {
        Logger.level = level;
    }

//    private static Logger getParentLogger(String name) {
//        return null;
//    }
//
//    protected Logger getParentLogger() {
//        return parentLogger;
//    }

//    protected void setParentLogger(Logger parentLogger) {
//        this.parentLogger = parentLogger;
//    }

    public static void setTarget(LogTarget target) {
        Logger.target = target;
    }
    
    public static void log(LogLevel level, String message) {
        if (getLevel().ordinal() >= level.ordinal()) {
            if (target != null) {
                target.log(level, formatter.format(level, message));
            }
//            if (parentLogger != null) {
//                parentLogger.log(level, formatter.format(level, message));
//            }
        }
    }
    
    public static void clear() {
        target.clear();
    }

    public static void fatal(String message) {
        log(LogLevel.FATAL, message);
    }

    public static void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public static void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public static void info(String message) {
        log(LogLevel.INFO, message);
    }

    public static void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public static void trace(String message) {
        log(LogLevel.TRACE, message);
    }
}
