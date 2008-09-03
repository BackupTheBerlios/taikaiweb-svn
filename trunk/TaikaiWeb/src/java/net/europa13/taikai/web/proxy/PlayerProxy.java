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

package net.europa13.taikai.web.proxy;

import java.io.Serializable;

/**
 *
 * @author daniel
 */
public class PlayerProxy implements Serializable {
    
    private int age;
    private boolean checkedIn;
    private Gender gender;
    private Grade grade;
    private int id = 0;
    private String name;
    private int number;
    private String surname;
    private TaikaiProxy taikai;
    
    
    public PlayerProxy() {
        
    }
    
    
    
    public int getAge() {
        return age;
    }
    
    public Gender getGender() {
        return gender;
    }
    
    public Grade getGrade() {
        return grade;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public int getNumber() {
        return number;
    }

    public String getSurname() {
        return surname;
    }
    
    public TaikaiProxy getTaikai() {
        return taikai;
    }
    
    public boolean isCheckedIn() {
        return checkedIn;
    }
    
    
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
                  
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setNumber(int number) {
        this.number = number;
    }
    
    
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public void setTaikai(TaikaiProxy taikai) {
        this.taikai = taikai;
    }
}
