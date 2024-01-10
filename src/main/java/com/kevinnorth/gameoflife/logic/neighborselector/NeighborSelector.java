package com.kevinnorth.gameoflife.logic.neighborselector;

import com.kevinnorth.gameoflife.state.Cell;
import com.kevinnorth.gameoflife.state.Grid;
import java.util.Set;

public interface NeighborSelector {
  public Set<Cell> selectNeighbors(Grid grid, int x, int y);
}
