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
import java.util.List;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

/**
 *
 * @author Daniel Wentzel
 */
@Entity
@Table(name = "Tournament",
uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name", "taikaiId"})
})
@NamedQueries({
    @NamedQuery(name = "getCheckedPlayers",
    query = "SELECT p FROM Tournament tmt JOIN tmt.players p WHERE p.checkedIn = TRUE AND tmt = :tournament"),
    @NamedQuery(name = "getUncheckedPlayers",
    query = "SELECT p FROM Tournament tmt JOIN tmt.players p WHERE p.checkedIn = FALSE AND tmt = :tournament"),
    @NamedQuery(name = "getTournamentsForPlayer",
    query = "SELECT t FROM Tournament t JOIN t.players p WHERE p = :player")
})
public class Tournament implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "poolSize")
    private Integer poolSize;
    @Column(name = "preferringLargerPools")
    private boolean preferringLargerPools;
    @Version
    @Column(name = "lastUpdate")
    private Timestamp lastUpdate;
    @ManyToOne
    @JoinColumn(name = "taikaiId", nullable = false)
    private Taikai taikai;
    @ManyToMany
    private List<Player> players;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tournament")
    private List<TournamentSeed> tournamentSeeds;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tournament")
    private List<Pool> pools;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "qualifyingTournament")
//    @OrderBy(value = "playerPosition")
//    private List<TournamentAdvancement> tournamentAdvancements;
//    @OneToMany
//    private Map<Integer, Tournament> advancements;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "treeId")
    private Tree tree;

    public Tournament() {
    }

//    public void addAdvancement(TournamentAdvancement advancement) {
//        tournamentAdvancements.add(advancement);
//    }
    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addPool(Pool pool) {
        pools.add(pool);
    }

    public void addSeed(TournamentSeed seed) {
        tournamentSeeds.add(seed);
    }

    public void draw() {
        // Generate pools
        // Generate tree
    }

//    public List<TournamentAdvancement> getAdvancements() {
//        return tournamentAdvancements;
//    }
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Pool> getPools() {
        return pools;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public Player getSeededPlayer(int seed) {
//        return tournamentSeed.getPlayer(seed);
        return null;
    }

    public List<TournamentSeed> getSeeds() {
        return tournamentSeeds;
    }

    public Taikai getTaikai() {
        return taikai;
    }

    public Tree getTree() {
        return tree;
    }

    public boolean isPreferringLargerPools() {
        return preferringLargerPools;
    }

//    public void removeAdvancement(TournamentAdvancement advancement) {
//        tournamentAdvancements.remove(advancement);
//    }
    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void removeSeed(TournamentSeed seed) {
        tournamentSeeds.remove(seed);
    }

//    public void setAdvancements(List<TournamentAdvancement> advancements) {
//        this.tournamentAdvancements = advancements;
//    }
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public void setPlayerSeed(Player player, int seed) {
////        tournamentSeed.setPlayerSeed(player, seed);
////        seededPlayers.put(seed, player);
//    }
    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public void setPreferringLargerPools(boolean preferringLargerPools) {
        this.preferringLargerPools = preferringLargerPools;
    }

    public void setTaikai(Taikai taikai) {
        this.taikai = taikai;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
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
        if (!(object instanceof Tournament)) {
            return false;
        }
        Tournament other = (Tournament) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "net.europa13.taikai.entity.Tournament[id=" + id + "]";
        return "Tournament " + id + ", " + name;
    }
}
