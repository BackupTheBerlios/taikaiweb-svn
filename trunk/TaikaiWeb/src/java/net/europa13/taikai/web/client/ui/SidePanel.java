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

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author daniel
 */
public class SidePanel extends HTMLPanel {

    final private MainPanel mainPanel;
    private Widget selectedLink;
    private ClickListener linkListener = new ClickListener() {

        public void onClick(Widget source) {
            selectLink(source);
        }
    };

    public SidePanel(MainPanel mainPanel) {

        super("<div id=\"sidebar_title\"></div>" +
                "<div id=\"sidebar_session\"></div>" +
                "<div id=\"sidebar_sysadmin\"></div>" +
                "<div id=\"sidebar_admin\"></div>" +
                "<div id=\"sidebar_court\"></div>");

//        setStyleName("taikaiweb-Sidebar");
        this.mainPanel = mainPanel;
//        add(new HTML("<h2>SidePanel</h2>"), "sidebar_title");

    }

    public void registerContent(Content content, MainPanel.Subsystem subsystem, String historyToken) {

        TreeItem ti = new TreeItem(content.getTitle());
        ti.setUserObject(content);

        Hyperlink link = new Hyperlink(content.getTitle(), historyToken);
        link.addClickListener(linkListener);
        link.addStyleName("taikaiweb-SidebarLink");


        switch (subsystem) {
            case SESSION:
//                link = new Hyperlink("Session", historyToken);
                link.setText("Session");
                add(link, "sidebar_session");
//                session.setUserObject(content);

                break;
            case SYSADMIN:
                add(link, "sidebar_sysadmin");
//                sysadmin.addItem(ti);
                break;
            case ADMIN:
                add(link, "sidebar_admin");
//                admin.addItem(ti);
                break;
            case COURT:
                add(link, "sidebar_court");
//                court.addItem(ti);
                break;
        }

    }

    private void selectLink(Widget link) {
        if (selectedLink != null) {
            selectedLink.removeStyleName("taikaiweb-SelectedLink");
        }
        selectedLink = link;
        selectedLink.addStyleName("taikaiweb-SelectedLink");
    }
}
