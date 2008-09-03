/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.europa13.taikai.web.client.ui;

import com.google.gwt.user.client.ui.ListBox;
import net.europa13.taikai.web.client.logging.Logger;
import net.europa13.taikai.web.proxy.Grade;

/**
 *
 * @author daniel
 */
public class GradeSelector extends ListBox {

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
    
    public Grade getSelectedGrade() {
        return Grade.valueOf(Grade.class, getValue(getSelectedIndex()));
    }
    
    public void reset() {
        setSelectedIndex(10);
    }
    
    public void setSelectedGrade(Grade grade) {
        if (grade == null) {
            Logger.debug(GradeSelector.class.getName() + ".setSelectedGrade: grade == null");
            return;
        }
        setSelectedIndex(grade.ordinal());
    }
}
