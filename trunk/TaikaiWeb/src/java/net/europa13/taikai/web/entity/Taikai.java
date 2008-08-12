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

package net.europa13.taikai.web.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 *
 * @author daniel
 */
@Entity
public class Taikai implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;    
    private int playerNumber = 1;
    private String name;
    
    @Version
    private Timestamp lastUpdate;
    
   
    @OneToMany(mappedBy = "taikai", cascade=CascadeType.ALL)
    private List<Tournament> tournaments = new ArrayList<Tournament>();
    
    @OneToMany
    (mappedBy = "taikai", cascade=CascadeType.ALL)
    private List<Player> players;
    
    @OneToMany
    (cascade=CascadeType.ALL)
    private List<Court> courts;
    
    public void addPlayer(Player player) {
        player.setNumber(playerNumber++);
        players.add(player);
    }
    
    public void addTournament(Tournament tournament) {
        tournaments.add(tournament);
        tournament.setTaikai(this);
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    
    public List<Player> getPlayers() {
        return new ArrayList<Player>(players);
    }
    
    public List<Tournament> getTournaments() {
        return new ArrayList<Tournament>(tournaments);
    }
    
    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void removeTournament(Tournament tournament) {
        tournaments.remove(tournament);
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Taikai)) {
            return false;
        }
        Taikai other = (Taikai) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        
        StringBuilder taikai = new StringBuilder();
        taikai.append("Taikai: ").append(id).append(", ").append(name);
        
//        for (Tournament tournament : tournaments) {
//            taikai.append(" ").append(tournament.toString()).append("\n");
//        }

        return taikai.toString();
        //        return "net.europa13.taikai.entity.Taikai[id=" + id + "]";
    }

}