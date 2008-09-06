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
package net.europa13.taikai.web.server.tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import net.europa13.taikai.web.entity.Pool;
import net.europa13.taikai.web.entity.Tournament;
import net.europa13.taikai.web.entity.Tree;
import net.europa13.taikai.web.proxy.TournamentGenerationInfo;

/**
 *
 * @author Daniel Wentzel
 */
public class Generator {
    
    private static Logger logger = Logger.getLogger(Generator.class.getName());

    public static void generate(TournamentGenerationData data, EntityManager em) throws GenerationException {
        List<PoolCounter> poolCounts = getPoolCount(data);
        
        Tournament tournament = data.getTournament();
        
        for (PoolCounter poolCount : poolCounts) {
            
            for (int i = 0; i < poolCount.poolCount; ++i) {
                Pool pool = new Pool();
                pool.setTournament(tournament);
                tournament.addPool(pool);
            }
        }
        
        em.merge(tournament);
        
        Tree tree = new Tree();
        tournament.setTree(tree);
    }

    private static class PoolCounter {
        public int poolSize;
        public int poolCount;
    }
    
    public static void getGenerationInfo(TournamentGenerationData data, TournamentGenerationInfo info) throws GenerationException {
        
        List<PoolCounter> poolCounts = getPoolCount(data);
        for (PoolCounter poolCount : poolCounts) {
            logger.fine("setting pool size count: size = " + poolCount.poolSize + " count = " +poolCount.poolCount);
            info.setPoolCount(poolCount.poolSize, poolCount.poolCount);
            logger.fine("info pool count = " + info.getPoolCount(poolCount.poolSize));
        }
        
    }
    
    private static List<PoolCounter> getPoolCount(TournamentGenerationData data) throws GenerationException {
//        List<PoolCounter> poolCounts = new ArrayList<PoolCounter>();
        
        boolean preferringLargerPools = data.getTournament().isPreferringLargerPools();
        
        if (preferringLargerPools) {
            return getPoolCountLarger(data);
        }
        else {
            return getPoolCountSmaller(data);
        }
    }
    
    private static List<PoolCounter> getPoolCountLarger(TournamentGenerationData data) throws GenerationException {
        List<PoolCounter> poolCounts = new ArrayList<PoolCounter>();
        
        int playerCount = data.getCheckedPlayers().size();
        int defaultPoolSize = data.getTournament().getPoolSize();
        
        if (playerCount < defaultPoolSize * 2) {
            logger.fine("generation failed: too few players to make two pools.");
            throw new GenerationException("generation failed: too few players to make two pools.");
        }
        
        int poolCountDefaultSize = playerCount / defaultPoolSize;
        int poolCountLargerSize = 0;
        int playersLeft = playerCount % defaultPoolSize;
        
        if (poolCountDefaultSize % 2 == 1) {
            poolCountDefaultSize -= 1;
            playersLeft += defaultPoolSize;
        }
        
        if (playersLeft > poolCountDefaultSize) {
            logger.fine("generation failed: too many players to make two pools, but too few to make four pools.");
            throw new GenerationException("generation failed: too many players to make two pools, but too few to make four pools.");
        }
        
        poolCountDefaultSize -= playersLeft;
        poolCountLargerSize = playersLeft;
        
        PoolCounter defaultSizeCounter = new PoolCounter();
        defaultSizeCounter.poolSize = defaultPoolSize;
        defaultSizeCounter.poolCount = poolCountDefaultSize;
        poolCounts.add(defaultSizeCounter);
        
        PoolCounter largerSizeCounter = new PoolCounter();
        largerSizeCounter.poolSize = defaultPoolSize + 1;
        largerSizeCounter.poolCount = poolCountLargerSize;
        poolCounts.add(largerSizeCounter);
        
        return poolCounts;
    }
    
    private static List<PoolCounter> getPoolCountSmaller(TournamentGenerationData data) throws GenerationException {
        List<PoolCounter> poolCounts = new ArrayList<PoolCounter>();
        
        int playerCount = data.getCheckedPlayers().size();
        int defaultPoolSize = data.getTournament().getPoolSize();
        
        if (playerCount < (defaultPoolSize - 1) * 2) {
            logger.fine("generation failed: too few players to make two pools.");
            throw new GenerationException("generation failed: too few players to make two pools.");
        }
        
        int poolCountDefaultSize = playerCount / defaultPoolSize;
        int poolCountSmallerSize = 0;
        int playersLeft = playerCount % defaultPoolSize;
        
//        if (defaultSizePoolCount % 2 == 1) {
//            defaultSizePoolCount += 1;
//            playersLeft -= defaultPoolSize;
//        }
        
        if (playersLeft > 0 || poolCountDefaultSize % 2 == 1) {
            int poolsToAdd = 2 - poolCountDefaultSize % 2;
            poolCountDefaultSize += poolsToAdd;
            playersLeft -= poolsToAdd * defaultPoolSize;
        }
        
        if (playersLeft < -poolCountDefaultSize) {
            logger.fine("generation failed: too many players to make " + (poolCountDefaultSize - 2) + " pools, but too few to make " + poolCountDefaultSize + " pools.");
            throw new GenerationException("generation failed: too many players to make two pools, but too few to make four pools.");
        }
        
        poolCountDefaultSize += playersLeft;
        poolCountSmallerSize = -playersLeft;
        
        PoolCounter defaultSizeCounter = new PoolCounter();
        defaultSizeCounter.poolSize = defaultPoolSize;
        defaultSizeCounter.poolCount = poolCountDefaultSize;
        poolCounts.add(defaultSizeCounter);
        
        PoolCounter smallerSizeCounter = new PoolCounter();
        smallerSizeCounter.poolSize = defaultPoolSize - 1;
        smallerSizeCounter.poolCount = poolCountSmallerSize;
        poolCounts.add(smallerSizeCounter);
        
        return poolCounts;
    }
    
    private int getTreeNodeCount(int poolCount, int poolAdvancementCount) {
        
        int openings = poolCount * poolAdvancementCount;
        
        int fullLevels = 0;
        
        
        
        return 0;
    }
     
    
}
