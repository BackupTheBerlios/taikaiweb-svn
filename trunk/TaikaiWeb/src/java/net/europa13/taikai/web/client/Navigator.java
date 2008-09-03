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
package net.europa13.taikai.web.client;

import java.util.HashMap;
import net.europa13.taikai.web.client.ui.Content;

/**
 *
 * @author daniel
 */
public class Navigator {

    private static HashMap<String, Content> contentMap =
            new HashMap<String, Content>();
    
    private static Content defaultContent;
    
    private Navigator() {
        
    }
    
    public static Content getContent(String historyToken) {
        
//        int pos = historyToken.indexOf('/');
//        int handlerNameEnd = pos == -1 ? historyToken.length() : pos;
//        
//        String handlerName = historyToken.substring(0, handlerNameEnd);
//        Content content = contentMap.get(handlerName);
//        if (content != null) {
//            content.handleState(historyToken.substring(handlerNameEnd + 1));
//        }
        
        NavigationPath path = new NavigationPath(historyToken.split("/"));
        
        Content rootContent;
        
        if (path.isEmpty()) {
            rootContent = defaultContent;
        }
        else {
            rootContent = contentMap.get(path.getPathItem(0));
        }
        
        if (rootContent == null) {
            rootContent = defaultContent;
        }
        
//        Content content
        
        try {
            return rootContent.handleState(path.getSubPath());
        }
        catch (ContentHandlerNotFoundException ex) {
            return defaultContent;
        }
        
    }
    
    public static void registerContent(Content content, String historyToken) {
        contentMap.put(historyToken, content);
    }
    
    public static void registerDefaultContent(Content content) {
        Navigator.defaultContent = content;
    }
    
}
