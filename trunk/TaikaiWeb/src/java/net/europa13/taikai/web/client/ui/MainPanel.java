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
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import net.europa13.taikai.web.client.TaikaiControl;
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

    public static enum Subsystem {

        SYSADMIN,
        ADMIN,
        COURT
    }

    public MainPanel() {
//        super("<div id=\"main_panel\">" +
//                "<div id=\"top\"></div>" +
//                "<div id=\"middle\"></div>" +
//                "<div id=\"bottom\"></div>" +
//                "</div>");
        setWidth("100%");


//        setCellHorizontalAlignment(contentContainerPanel, "middle");

        TaikaiControl taikaiControl = new TaikaiControl();

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


        Content tc = new Content("Taikai", new TaikaiListPanel(taikaiControl));
        registerContent(tc, Subsystem.ADMIN);
        setContent(tc);

        Content ctc = new Content("Skapa Taikai", new CreateTaikaiPanel(taikaiControl));
        registerContent(ctc, Subsystem.ADMIN);
        Content tpc = new Content("Skapa Turnering", new TournamentPanel(taikaiControl));
        registerContent(tpc, Subsystem.ADMIN);

        Logger.setTarget(new PanelHtmlLogTarget(logPanelContents));
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
        contentContainerPanel.add(contentPanel, "content");
    }
}
