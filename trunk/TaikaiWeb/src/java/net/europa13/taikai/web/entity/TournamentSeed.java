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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "TournamentSeed", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"seedNumber", "tournamentId"})
})
public class TournamentSeed implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "seedNumber", nullable = false)
    private int seedNumber;
    @ManyToOne
    @JoinColumn(name = "tournamentId", nullable = false)
    private Tournament tournament;
    @ManyToOne
    @JoinColumn(name = "playerId", nullable = false)
    private Player player;

    public TournamentSeed() {
    }

    public int getSeedNumber() {
        return seedNumber;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public Player getPlayer() {
        return player;
    }

    public void setSeedNumber(int seedNumber) {
        this.seedNumber = seedNumber;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TournamentSeed other = (TournamentSeed) obj;
        if (this.seedNumber != other.seedNumber) {
            return false;
        }
        if (this.tournament != other.tournament && (this.tournament == null || !this.tournament.equals(other.tournament))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.seedNumber;
        hash = 97 * hash + (this.tournament != null ? this.tournament.hashCode() : 0);
        return hash;
    }
}
