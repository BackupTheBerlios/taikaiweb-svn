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

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.TreeListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import net.europa13.taikai.web.client.logging.Logger;

/**
 *
 * @author daniel
 */
public class SidePanel extends VerticalPanel {
    
    final private MainPanel mainPanel;
    final private TreeItem session;
    final private TreeItem sysadmin;
    final private TreeItem admin;
    final private TreeItem court;
    
    public SidePanel(MainPanel mainPanel) {
        
        
        this.mainPanel = mainPanel;
        add(new HTML("<h2>SidePanel</h2>"));
        
        
        Tree tree = new Tree();
        
        tree.addTreeListener(new TreeListener() {

            public void onTreeItemSelected(TreeItem treeItem) {
//                treeItem.setState(true);
                Content content = (Content) treeItem.getUserObject();
                
                if (content != null) {
                    SidePanel.this.mainPanel.setContent(content);
                }
//                Logger.debug(treeItem.getText() + " selected");
            }

            public void onTreeItemStateChanged(TreeItem treeItem) {
//                Logger.debug(treeItem.getText() + " state changed");
            }
        });
        
        session = new TreeItem("Session");
        tree.addItem(session);
        
        sysadmin = new TreeItem("System");
        tree.addItem(sysadmin);
        
        admin = new TreeItem("Administration");
        admin.setState(true);
        tree.addItem(admin);
        
        court = new TreeItem("Match");
        tree.addItem(court);
     
        
        add(tree);
        
    }
    
    public void registerContent(Content content, MainPanel.Subsystem subsystem) {
        
        TreeItem ti = new TreeItem(content.getTitle());
        ti.setUserObject(content);
        
        switch (subsystem) {
            case SESSION:
                session.setUserObject(content);
                break;
            case SYSADMIN:
                sysadmin.addItem(ti);
                break;
            case ADMIN:
                admin.addItem(ti);
                break;
            case COURT:
                court.addItem(ti);
                break;
        }
        
    }

}
