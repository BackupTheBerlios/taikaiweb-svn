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

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import net.europa13.taikai.web.client.Controllers;
import net.europa13.taikai.web.client.TaikaiWeb;
import net.europa13.taikai.web.client.View;
import net.europa13.taikai.web.client.logging.LogLevel;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.client.logging.PanelHtmlLogTarget;

/**
 *
 * @author Daniel Wentzel
 */
public class MainPanel extends VerticalPanel {

    private SidePanel sidePanel;
    private HTMLPanel contentContainerPanel;
    private HorizontalPanel toolBarPanel = new HorizontalPanel();
    private Panel contentPanel;
//    private Session session;
    private View currentView;

    public static enum Subsystem {

        SESSION,
        SYSADMIN,
        ADMIN,
        COURT
    }

    /**
     * Constructor.
     */
    public MainPanel() {

        setWidth("100%");

        HorizontalPanel topPanel = new HorizontalPanel();
        topPanel.setWidth("100%");
        topPanel.add(new HTML("<h1>TaikaiWeb</h1>"));
        topPanel.setBorderWidth(1);
        add(topPanel);

        HorizontalPanel middlePanel = new HorizontalPanel();


        sidePanel = new SidePanel(this);
        sidePanel.setWidth("100%");
        sidePanel.setHeight("100%");
        sidePanel.setBorderWidth(1);
        middlePanel.add(sidePanel);

        toolBarPanel.add(new Label("Tools:"));

        contentContainerPanel =
                new HTMLPanel("<div id=\"toolbar\"></div><div id=\"content\"></div>");
        contentContainerPanel.setWidth("100%");
        contentContainerPanel.setHeight("100%");
        contentContainerPanel.add(toolBarPanel, "toolbar");
        middlePanel.add(contentContainerPanel);

//        middlePanel.setCellHeight(sidePanel, "100%");
        middlePanel.setCellWidth(sidePanel, "20%");
        middlePanel.setCellHeight(sidePanel, "100%");
        middlePanel.setCellWidth(contentContainerPanel, "80%");
        middlePanel.setCellWidth(contentContainerPanel, "100%");
        middlePanel.setWidth("100%");

        add(middlePanel);

        VerticalPanel logPanel = new VerticalPanel();
        VerticalPanel logPanelContents = new VerticalPanel();
        logPanel.setWidth("100%");
        logPanel.setBorderWidth(1);
        Button logClearBTN = new Button("Clear Log", new ClickListener() {
            public void onClick(Widget source) {
                Logger.clear();
            }
        });
        
        HorizontalPanel logPanelHeader = new HorizontalPanel();
        logPanelHeader.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        logPanelHeader.add(new HTML("<h2>Logg</h2>"));
        logPanelHeader.add(logClearBTN);
        
        logPanel.add(logPanelHeader);
        logPanel.add(logPanelContents);
        add(logPanel);

        
        Controllers.taikaiControl.updateTaikaiList();
        
        //*********************************************************************
        // Session Content
        Content sessionContent = new Content("Session",
                new SessionPanel(TaikaiWeb.getSession()));
        registerContent(sessionContent, Subsystem.SESSION);

        //*********************************************************************
        // Taikai Content
        Content taikaiListContent = new Content("Taikai", new TaikaiListPanel());
        registerContent(taikaiListContent, Subsystem.ADMIN);
        final Content createTaikaiContent = new Content("Skapa Taikai", new CreateTaikaiPanel());
        
        Button btnCreateTaikai = new Button("Ny Taikai...");
        btnCreateTaikai.addClickListener(new ClickListener() {

            public void onClick(Widget arg0) {
                setContent(createTaikaiContent);
            }
        });
        
        taikaiListContent.addControl(btnCreateTaikai);
        
        //*********************************************************************
        // Tournament Content
        Content tournamentListContent = 
                new Content("Turnering", new TournamentListPanel());
        registerContent(tournamentListContent, Subsystem.ADMIN);
        final Content tournamentContent =
                new Content("Turnering", new TournamentPanel());
        
        Button btnCreateTournament = new Button("Ny Turnering...");
        btnCreateTournament.addClickListener(new ClickListener() {

            public void onClick(Widget arg0) {
                setContent(tournamentContent);
            }
        });
        
        tournamentListContent.addControl(btnCreateTournament);
        
        
        
        
        setContent(sessionContent);
        
        //*********************************************************************
        // Logger
        Logger.setTarget(new PanelHtmlLogTarget(logPanelContents));
        Logger.setLevel(LogLevel.TRACE);

    }

    /**
     * Registers content to be accessible from the sidebar.
     * @param content the content to register.
     * @param subsystem the subsystem that specifies where in the sidebar tree
     * to put the content.
     */
    public void registerContent(Content content, Subsystem subsystem) {
        sidePanel.registerContent(content, subsystem);
    }

    /**
     * Sets the currently visible content in the main panel.
     * @param content the content to set as visible.
     */
    public void setContent(Content content) {
        if (contentPanel != null) {
            contentContainerPanel.remove(contentPanel);
            
        }
        if (currentView != null) {
            currentView.setActive(false);
        }

        currentView = content.getView();
        
        contentPanel = currentView.getPanel();
        contentPanel.setWidth("100%");
        contentContainerPanel.add(contentPanel, "content");
        currentView.setActive(true);
        
        toolBarPanel.clear();
        for (Widget control : content.getControlList()) {
            toolBarPanel.add(control);
        }
    }
}
