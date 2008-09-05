/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.europa13.taikai.web.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "TournamentAdvancement", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"qualifyingTournament", "playerPosition"})
})
public class TournamentAdvancement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "qualifyingTournament", nullable = false)
    private Tournament qualifyingTournament;
    @ManyToOne
    @JoinColumn(name = "advancementTournament", nullable = false)
    private Tournament advancementTournament;
    @Column(name = "playerPosition", nullable = false)
    private int playerPosition;

    
    
    public Tournament getAdvancementTournament() {
        return advancementTournament;
    }
    
    public Integer getId() {
        return id;
    }
    
    public int getPlayerPosition() {
        return playerPosition;
    }
    
    public Tournament getQualifyingTournament() {
        return qualifyingTournament;
    }

    public void setAdvancementTournament(Tournament advancementTournament) {
        this.advancementTournament = advancementTournament;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public void setPlayerPosition(int playerPosition) {
        this.playerPosition = playerPosition;
    }
    
    public void setQualifyingTournament(Tournament qualifyingTournament) {
        this.qualifyingTournament = qualifyingTournament;
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
        if (!(object instanceof TournamentAdvancement)) {
            return false;
        }
        TournamentAdvancement other = (TournamentAdvancement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.europa13.taikai.web.entity.TournamentAdvancement[id=" + id + "]";
    }
}
