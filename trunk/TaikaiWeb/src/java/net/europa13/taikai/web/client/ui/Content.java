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

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Wentzel
 */
public class Content {

    private List<Widget> controlList = new ArrayList<Widget>();
    private Panel panel;
    private String title;
    
    public Content() {
        
    }
    
    public Content(String title, Panel panel) {
        this.title = title;
        this.panel = panel;
    }
    
    public void addControl(Widget control) {
        controlList.add(control);
    }
    
    public List<Widget> getControlList() {
        return controlList;
    }
    
    public Panel getPanel() {
        return panel;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void removeControl(Widget control) {
        controlList.remove(control);
    }
    
    public void setPanel(Panel panel) {
        this.panel = panel;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
}
