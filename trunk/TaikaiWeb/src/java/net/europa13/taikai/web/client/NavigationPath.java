/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.europa13.taikai.web.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author daniel
 */
public class NavigationPath {

    private LinkedList<String> navPath;
    
    public NavigationPath(String[] pathItems) {
        navPath = new LinkedList<String>();
        
        for (int i = 0; i < pathItems.length; ++i) {
            navPath.add(pathItems[i]);
        }
    }
    
    public NavigationPath(List<String> navPath) {
        this.navPath = new LinkedList<String>(navPath);
    }
    
    public String getPathItem(int item) {
        if (navPath.size() < item + 1) {
            return "";
        }
        return navPath.get(item);
    }
    
    public NavigationPath getSubPath() {
//        if (navPath.isEmpty()) {
//            return new NavigationPath(new LinkedList<String>());
//        }
//        else {
            LinkedList<String> newList = new LinkedList<String>();
            for (int i = 1; i < navPath.size(); ++i) {
                newList.add(navPath.get(i));
            }
            return new NavigationPath(newList);
//            
//        }
    }
    
    public boolean isDominating(NavigationPath other) {
        if (navPath.size() < other.navPath.size()) {
            return false;
        }
        else {
            for (int i = 0; i < navPath.size(); ++i) {
                if (!navPath.get(i).equals(other.navPath.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }
    
    public boolean isEmpty() {
        return navPath.isEmpty();
    }
    
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (String item : navPath) {
            text.append(item).append("/");
        }
        return text.toString();
    }
}
