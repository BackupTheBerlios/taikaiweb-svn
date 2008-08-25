/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
