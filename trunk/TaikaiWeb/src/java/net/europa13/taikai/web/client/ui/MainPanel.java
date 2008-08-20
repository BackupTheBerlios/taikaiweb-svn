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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import net.europa13.taikai.web.client.TaikaiControl;
import net.europa13.taikai.web.client.logging.LogLevel;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.client.logging.PanelHtmlLogTarget;

/**
 *
 * @author Daniel Wentzel
 */
public class MainPanel extends DockPanel {
    
    private SidePanel sidePanel;
    private VerticalPanel contentContainerPanel = new VerticalPanel();
    private HorizontalPanel toolBarPanel = new HorizontalPanel();
    private Panel contentPanel;
    
    public static enum Subsystem {
        SYSADMIN,
        ADMIN,
        COURT
    }
    
    public MainPanel() {
        
        setWidth("100%");
        
        toolBarPanel.add(new Label("Tools:"));
        
        contentContainerPanel.setBorderWidth(1);
        contentContainerPanel.add(toolBarPanel);
        contentContainerPanel.setWidth("100%");
        add(contentContainerPanel, DockPanel.CENTER);
        
        TaikaiControl taikaiControl = new TaikaiControl();
        
        HorizontalPanel topPanel = new HorizontalPanel();
        topPanel.setWidth("100%");
        topPanel.add(new HTML("<h1>TaikaiWeb</h1>"));
        topPanel.setBorderWidth(1);
        add(topPanel, DockPanel.NORTH);
        
        VerticalPanel logPanel = new VerticalPanel();
        logPanel.setWidth("100%");
        logPanel.setBorderWidth(1);
        logPanel.add(new HTML("<h2>Logg</h2>"));
        add(logPanel, DockPanel.SOUTH);
        
        sidePanel = new SidePanel(this);
        sidePanel.setBorderWidth(1);
        add(sidePanel, DockPanel.LINE_START);
        
        Content tc = new Content("Taikai", new TaikaiListPanel(taikaiControl));
        registerContent(tc, Subsystem.ADMIN);
        setContent(tc);
        
        Content ctc = new Content("Skapa Taikai", new CreateTaikaiPanel(taikaiControl));
        registerContent(ctc, Subsystem.ADMIN);
        Content tpc  = new Content("Skapa Turnering", new TournamentPanel(taikaiControl));
        registerContent(tpc, Subsystem.ADMIN);
        
        Logger.setTarget(new PanelHtmlLogTarget(logPanel));
        Logger.setLevel(LogLevel.TRACE);
        
    }
    
    public void registerContent(Content content, Subsystem subsystem) {
        sidePanel.registerContent(content, subsystem);
    }
    
    public void setContent(Content content) {
        if (contentPanel != null) {
            contentContainerPanel.remove(contentPanel);
        }
        
        contentPanel = content.getPanel();
        contentPanel.setWidth("100%");
        contentContainerPanel.add(contentPanel);
    }
    
}
