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

import com.google.gwt.user.client.ui.HTMLPanel;

/**
 *
 * @author daniel
 */
public class ListNavigator extends HTMLPanel {

    private int firstResult;
    private int maxResults;
    private int totalResultCount;
    
    public ListNavigator() {
        super("<div id=\"listnav\"></div>");
        
        
        
    }
    
    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }
    
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }
    
    public void setTotalResultCount(int totalResultCount) {
        this.totalResultCount = totalResultCount;
    }
    
}
