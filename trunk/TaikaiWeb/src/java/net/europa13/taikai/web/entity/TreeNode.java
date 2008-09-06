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
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author daniel
 */
@Entity
@Table(name = "TreeNode")
public class TreeNode implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "rootNodeId")
    private TreeNode rootNode;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "leafNode1Id")
    private TreeNode leafNode1;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "leadNode2Id")
    private TreeNode leafNode2;
    
    @OneToOne
    @JoinColumn(name = "matchId")
    private Match match;

    public Integer getId() {
        return id;
    }
    
    public TreeNode getLeafNode1() {
        return leafNode1;
    }
    
    public TreeNode getLeafNode2() {
        return leafNode2;
    }
    
    public Match getMatch() {
        return match;
    }
    
    public TreeNode getRootNode() {
        return rootNode;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setLeadNode1(TreeNode leafNode1) {
        this.leafNode1 = leafNode1;
    }
    
    public void setLeadNode2(TreeNode leadNode2) {
        this.leafNode2 = leadNode2;
    }
    
    public void setMatch(Match match) {
        this.match = match;
    }
    
    public void setRootNode(TreeNode rootNode) {
        this.rootNode = rootNode;
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
        if (!(object instanceof TreeNode)) {
            return false;
        }
        TreeNode other = (TreeNode) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.europa13.taikai.entity.TreeNode[id=" + id + "]";
    }
}
