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
import net.europa13.taikai.web.client.logging.Logger;

/**
 *
 * @author Daniel Wentzel
 */
public abstract class Content {

    private List<Widget> controlList = new ArrayList<Widget>();
    private String title;
    private boolean active;
    
    public Content() {
        
    }
    
    public void addControl(Widget control) {
        controlList.add(control);
    }
    
    public List<Widget> getControlList() {
        return controlList;
    }
    
    public abstract Panel getPanel();
    
    public String getTitle() {
        return title;
    }
    
    public void handleState(String state) {
//        Logger.debug(state);
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        if (this.active == active) {
            return;
        }
        
        this.active = active;
    }
    
    public void removeControl(Widget control) {
        controlList.remove(control);
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

}
