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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author daniel
 */
@Entity
public class TournamentSeed implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany
    private List<Player> players = new ArrayList<Player>(4);
    
    public TournamentSeed() {
        players.add(null);
        players.add(null);
        players.add(null);
        players.add(null);
    }
    
    public Long getId() {
        return id;
    }

    public Player getPlayer(int seed) {
        if (seed < 0 || seed > 3) {
            throw new IllegalArgumentException();
        }
        return players.get(seed);
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setPlayerSeed(Player player, int seed) {
        if (seed < 0 || seed > 3) {
            throw new IllegalArgumentException();
        }
        players.set(seed, player);
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
        if (!(object instanceof TournamentSeed)) {
            return false;
        }
        TournamentSeed other = (TournamentSeed) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.europa13.taikai.entity.TournamentSeed[id=" + id + "]";
    }

}
