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

import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import net.europa13.taikai.web.client.Navigator;

/**
 *
 * @author Daniel Wentzel
 */
public class MainPanel extends HTMLPanel {

    private SidePanel sidePanel;
    private HTMLPanel contentContainerPanel;
    private Panel toolbarPanel;
    private Panel contentPanel;
//    private Session session;
    private Content currentContent;
    private static MainPanel instance = new MainPanel();

    public static enum Subsystem {

        SESSION,
        SYSADMIN,
        ADMIN,
        COURT
    }
    
    public static MainPanel getInstance() {
        return instance;
    }

    
    
    
    /**
     * Constructor.
     */
    private MainPanel() {
        
        super("<div id=\"top\"></div>\n<div id=\"middle\"></div>\n<div id=\"bottom\">\n");

        setWidth("100%");

        HorizontalPanel topPanel = new HorizontalPanel();
//        topPanel.setWidth("100%");
        topPanel.add(new HTML("<h1>TaikaiWeb</h1>"));
//        topPanel.setBorderWidth(1);
        add(topPanel, "top");

        HorizontalPanel middlePanel = new HorizontalPanel();


        sidePanel = new SidePanel(this);
        sidePanel.setWidth("100%");
        sidePanel.setHeight("100%");
//        sidePanel.setBorderWidth(1);
        middlePanel.add(sidePanel);

        toolbarPanel = new FlowPanel();
        toolbarPanel.setStyleName("taikaiweb-Toolbar");
        toolbarPanel.add(new Label("Tools:"));

        contentContainerPanel =
                new HTMLPanel("<div id=\"toolbar\"></div><div id=\"content\"></div>");
        contentContainerPanel.setWidth("100%");
        contentContainerPanel.setHeight("100%");
        contentContainerPanel.add(toolbarPanel, "toolbar");

        DecoratorPanel decorator = new DecoratorPanel();
        decorator.add(contentContainerPanel);
        decorator.setWidth("100%");
        decorator.setHeight("100%");
//        decorator.set
//        middlePanel.add(contentContainerPanel);
        middlePanel.add(decorator);

//        middlePanel.setCellHeight(sidePanel, "100%");
        middlePanel.setCellWidth(sidePanel, "20%");
        middlePanel.setCellHeight(sidePanel, "100%");
        middlePanel.setCellWidth(contentContainerPanel, "80%");
        middlePanel.setCellWidth(contentContainerPanel, "100%");
        middlePanel.setWidth("100%");

        add(middlePanel, "middle");
        
    }

    /**
     * Registers content to be accessible from the sidebar.
     * @param content the content to register.
     * @param subsystem the subsystem that specifies where in the sidebar tree
     * to put the content.
     */
    public void registerContent(Content content, Subsystem subsystem, String historyToken) {
        Navigator.registerContent(content, historyToken);
        sidePanel.registerContent(content, subsystem, historyToken);
    }

    /**
     * Sets the currently visible content in the main panel.
     * @param content the content to set as visible.
     */
    public static void setContent(Content content) {
        instance.setContent(content, instance);
    }
    
    private void setContent(Content content, MainPanel mp) {
        if (contentPanel != null) {
            contentContainerPanel.remove(contentPanel);
            
        }
        if (currentContent != null) {
            currentContent.setActive(false);
        }

        currentContent = content;
        
        contentPanel = currentContent.getPanel();
        contentPanel.setWidth("100%");
        contentContainerPanel.add(contentPanel, "content");
        currentContent.setActive(true);
        
        toolbarPanel.clear();
        for (Widget control : content.getControlList()) {
            toolbarPanel.add(control);
        }
    }
}
