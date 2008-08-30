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
        
        addItem("10 kyu", Grade.Kyu10.name());
        addItem("9 kyu", Grade.Kyu9.name());
        addItem("8 kyu", Grade.Kyu8.name());
        addItem("7 kyu", Grade.Kyu7.name());
        addItem("6 kyu", Grade.Kyu6.name());
        addItem("5 kyu", Grade.Kyu5.name());
        addItem("4 kyu", Grade.Kyu4.name());
        addItem("3 kyu", Grade.Kyu3.name());
        addItem("2 kyu", Grade.Kyu2.name());
        addItem("1 kyu", Grade.Kyu1.name());
        addItem("1 dan", Grade.Dan1.name());
        addItem("2 dan", Grade.Dan2.name());
        addItem("3 dan", Grade.Dan3.name());
        addItem("4 dan", Grade.Dan4.name());
        addItem("5 dan", Grade.Dan5.name());
        addItem("6 dan", Grade.Dan6.name());
        addItem("7 dan", Grade.Dan7.name());
        addItem("8 dan", Grade.Dan8.name());
        addItem("9 dan", Grade.Dan9.name());
        addItem("10 dan", Grade.Dan10.name());
    }
    
    public Grade getSelectedGrade() {
        return Grade.valueOf(Grade.class, getValue(getSelectedIndex()));
    }
    
    public void setSelectedGrade(Grade grade) {
        if (grade == null) {
            Logger.debug(GradeSelector.class.getName() + ".setSelectedGrade: grade == null");
            return;
        }
        setSelectedIndex(grade.ordinal());
    }
}
