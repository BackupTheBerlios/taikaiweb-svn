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

import com.google.gwt.user.client.ui.ListBox;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.Grade;

/**
 *
 * @author Daniel Wentzel
 */
public class GradeSelector extends ListBox {

    /**
     * Constructor.
     */
    public GradeSelector() {
        
        addItem(Grade.Kyu10.toString(), Grade.Kyu10.name());
        addItem(Grade.Kyu9.toString(), Grade.Kyu9.name());
        addItem(Grade.Kyu8.toString(), Grade.Kyu8.name());
        addItem(Grade.Kyu7.toString(), Grade.Kyu7.name());
        addItem(Grade.Kyu6.toString(), Grade.Kyu6.name());
        addItem(Grade.Kyu5.toString(), Grade.Kyu5.name());
        addItem(Grade.Kyu4.toString(), Grade.Kyu4.name());
        addItem(Grade.Kyu3.toString(), Grade.Kyu3.name());
        addItem(Grade.Kyu2.toString(), Grade.Kyu2.name());
        addItem(Grade.Kyu1.toString(), Grade.Kyu1.name());
        addItem(Grade.Dan1.toString(), Grade.Dan1.name());
        addItem(Grade.Dan2.toString(), Grade.Dan2.name());
        addItem(Grade.Dan3.toString(), Grade.Dan3.name());
        addItem(Grade.Dan4.toString(), Grade.Dan4.name());
        addItem(Grade.Dan5.toString(), Grade.Dan5.name());
        addItem(Grade.Dan6.toString(), Grade.Dan6.name());
        addItem(Grade.Dan7.toString(), Grade.Dan7.name());
        addItem(Grade.Dan8.toString(), Grade.Dan8.name());
        addItem(Grade.Dan9.toString(), Grade.Dan9.name());
        addItem(Grade.Dan10.toString(), Grade.Dan10.name());
        
        setSelectedIndex(10);
    }
    
    /**
     * Returns the selected grade.
     * @return the selected grade.
     */
    public Grade getSelectedGrade() {
        return Grade.valueOf(Grade.class, getValue(getSelectedIndex()));
    }
    
    /**
     * Resets the control.
     */
    public void reset() {
        setSelectedIndex(10);
    }
    
    /**
     * Sets the selected grade.
     * @param grade the grade to select.
     */
    public void setSelectedGrade(Grade grade) {
        if (grade == null) {
            Logger.debug(GradeSelector.class.getName() + ".setSelectedGrade: grade == null");
            return;
        }
        setSelectedIndex(grade.ordinal());
    }
}
