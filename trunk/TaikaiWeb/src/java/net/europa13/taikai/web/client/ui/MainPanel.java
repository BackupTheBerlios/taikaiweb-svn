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

package net.europa13.taikai.web.client.ui;

import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import net.europa13.taikai.web.client.TaikaiControl;
import net.europa13.taikai.web.client.logging.LogLevel;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.client.logging.PanelHtmlLogTarget;

/**
 *
 * @author daniel
 */
public class MainPanel extends DockPanel {

    public MainPanel() {
        
        HorizontalPanel topPanel = new HorizontalPanel();
        topPanel.add(new HTML("<h1>TaikaiWeb</h1>"));
        add(topPanel, DockPanel.NORTH);
        
        VerticalPanel logPanel = new VerticalPanel();
        logPanel.add(new HTML("<h2>Logg</h2>"));
        add(logPanel, DockPanel.SOUTH);
        
        
        add(new SidePanel(), DockPanel.LINE_START);
        
//        VerticalPanel contentPanel = new VerticalPanel();
//        contentPanel.add(new HTML("<h2>Content</h2>"));
//        contentPanel.set
        
        
        
        TaikaiPanel contentPanel = new TaikaiPanel(new TaikaiControl());
        add(contentPanel, DockPanel.CENTER);
        
        
        
        Logger.setTarget(new PanelHtmlLogTarget(logPanel));
//        Logger.setFormatter(new )
        Logger.setLevel(LogLevel.TRACE);
        Logger.debug("Meddelande 1");
        Logger.warn("Meddelande 2");
        
//        Logger rootLogger = LogManager.getRootLogger();
//        rootLogger.setTarget(new PanelHtmlLogTarget(logPanel));
//        rootLogger.setLevel(LogLevel.TRACE);
//        
//        Logger logger = Logger.getLogger("log");
//        logger.setLevel(LogLevel.TRACE);
//        logger.debug("Logmeddelande!");
        
    }
    
}
