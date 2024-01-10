package com.kevinnorth.gameoflife.logic.generations;

import static org.junit.jupiter.api.Assertions.*;

import com.kevinnorth.gameoflife.state.Grid;
import com.kevinnorth.gameoflife.testutil.GridFactory;
import org.junit.jupiter.api.*;

public class GenerationControllerTest {
  @Test
  public void IfADeadCellHasNoLivingNeighborsThenItRemainsDead() {
    boolean[][] initialState = {
      {false, false, false},
      {false, false, false},
      {false, false, false}
    };
    Grid grid = GridFactory.generateGridFromArray(initialState);

    var nextGeneration = GenerationController.determineNextGeneration(grid, EdgeBehavior.BOUNDED);

    assertEquals(false, nextGeneration.getCell(1, 1).alive());
  }

  @Test
  public void IfADeadCellHas1LivingNeighborThenItRemainsDead() {
    boolean[][] initialState = {
      {true, false, false},
      {false, false, false},
      {false, false, false}
    };
    Grid grid = GridFactory.generateGridFromArray(initialState);

    var nextGeneration = GenerationController.determineNextGeneration(grid, EdgeBehavior.BOUNDED);

    assertEquals(false, nextGeneration.getCell(1, 1).alive());
  }

  @Test
  public void IfADeadCellHas2LivingNeighborsThenItRemainsDead() {
    boolean[][] initialState = {
      {true, false, false},
      {true, false, false},
      {false, false, false}
    };
    Grid grid = GridFactory.generateGridFromArray(initialState);

    var nextGeneration = GenerationController.determineNextGeneration(grid, EdgeBehavior.BOUNDED);

    assertEquals(false, nextGeneration.getCell(1, 1).alive());
  }

  @Test
  public void IfADeadCellHas3LivingNeighborsThenItBecomesAlive() {
    boolean[][] initialState = {
      {true, false, false},
      {false, false, true},
      {false, true, false}
    };
    Grid grid = GridFactory.generateGridFromArray(initialState);

    var nextGeneration = GenerationController.determineNextGeneration(grid, EdgeBehavior.BOUNDED);

    assertEquals(true, nextGeneration.getCell(1, 1).alive());
  }

  @Test
  public void IfADeadCellHas4LivingNeighborsThenItRemainsDead() {
    boolean[][] initialState = {
      {true, false, true},
      {true, false, false},
      {false, false, true}
    };
    Grid grid = GridFactory.generateGridFromArray(initialState);

    var nextGeneration = GenerationController.determineNextGeneration(grid, EdgeBehavior.BOUNDED);

    assertEquals(false, nextGeneration.getCell(1, 1).alive());
  }

  @Test
  public void IfADeadCellHas5LivingNeighborsThenItRemainsDead() {
    boolean[][] initialState = {
      {true, true, false},
      {true, false, false},
      {true, false, true}
    };
    Grid grid = GridFactory.generateGridFromArray(initialState);

    var nextGeneration = GenerationController.determineNextGeneration(grid, EdgeBehavior.BOUNDED);

    assertEquals(false, nextGeneration.getCell(1, 1).alive());
  }

  @Test
  public void IfADeadCellHas6LivingNeighborsThenItRemainsDead() {
    boolean[][] initialState = {
      {true, false, false},
      {true, false, true},
      {true, true, true}
    };
    Grid grid = GridFactory.generateGridFromArray(initialState);

    var nextGeneration = GenerationController.determineNextGeneration(grid, EdgeBehavior.BOUNDED);

    assertEquals(false, nextGeneration.getCell(1, 1).alive());
  }

  @Test
  public void IfADeadCellHas7LivingNeighborsThenItRemainsDead() {
    boolean[][] initialState = {
      {true, true, false},
      {true, false, true},
      {true, true, true}
    };
    Grid grid = GridFactory.generateGridFromArray(initialState);

    var nextGeneration = GenerationController.determineNextGeneration(grid, EdgeBehavior.BOUNDED);

    assertEquals(false, nextGeneration.getCell(1, 1).alive());
  }

  @Test
  public void IfADeadCellHas8LivingNeighborsThenItRemainsDead() {
    boolean[][] initialState = {
      {true, true, true},
      {true, false, true},
      {true, true, true}
    };
    Grid grid = GridFactory.generateGridFromArray(initialState);

    var nextGeneration = GenerationController.determineNextGeneration(grid, EdgeBehavior.BOUNDED);

    assertEquals(false, nextGeneration.getCell(1, 1).alive());
  }

  @Test
  public void IfALivingCellHasNoLivingNeighborsThenItBecomesDead() {
    boolean[][] initialState = {
      {false, false, false},
      {false, true, false},
      {false, false, false}
    };
    Grid grid = GridFactory.generateGridFromArray(initialState);

    var nextGeneration = GenerationController.determineNextGeneration(grid, EdgeBehavior.BOUNDED);

    assertEquals(false, nextGeneration.getCell(1, 1).alive());
  }

  @Test
  public void IfALivingCellHas1LivingNeighborThenItBecomesDead() {
    boolean[][] initialState = {
      {true, false, false},
      {false, true, false},
      {false, false, false}
    };
    Grid grid = GridFactory.generateGridFromArray(initialState);

    var nextGeneration = GenerationController.determineNextGeneration(grid, EdgeBehavior.BOUNDED);

    assertEquals(false, nextGeneration.getCell(1, 1).alive());
  }

  @Test
  public void IfALivingCellHas2LivingNeighborsThenItRemainsAlive() {
    boolean[][] initialState = {
      {true, false, false},
      {true, true, false},
      {false, false, false}
    };
    Grid grid = GridFactory.generateGridFromArray(initialState);

    var nextGeneration = GenerationController.determineNextGeneration(grid, EdgeBehavior.BOUNDED);

    assertEquals(true, nextGeneration.getCell(1, 1).alive());
  }

  @Test
  public void IfALivingCellHas3LivingNeighborsThenItRemainsAlive() {
    boolean[][] initialState = {
      {true, false, false},
      {false, true, true},
      {false, true, false}
    };
    Grid grid = GridFactory.generateGridFromArray(initialState);

    var nextGeneration = GenerationController.determineNextGeneration(grid, EdgeBehavior.BOUNDED);

    assertEquals(true, nextGeneration.getCell(1, 1).alive());
  }

  @Test
  public void IfALivingCellHas4LivingNeighborsThenItBecomesDead() {
    boolean[][] initialState = {
      {true, false, true},
      {true, true, false},
      {false, false, true}
    };
    Grid grid = GridFactory.generateGridFromArray(initialState);

    var nextGeneration = GenerationController.determineNextGeneration(grid, EdgeBehavior.BOUNDED);

    assertEquals(false, nextGeneration.getCell(1, 1).alive());
  }

  @Test
  public void IfALivingCellHas5LivingNeighborsThenItBecomesDead() {
    boolean[][] initialState = {
      {true, true, false},
      {true, true, false},
      {true, false, true}
    };
    Grid grid = GridFactory.generateGridFromArray(initialState);

    var nextGeneration = GenerationController.determineNextGeneration(grid, EdgeBehavior.BOUNDED);

    assertEquals(false, nextGeneration.getCell(1, 1).alive());
  }

  @Test
  public void IfALivingCellHas6LivingNeighborsThenItBecomesDead() {
    boolean[][] initialState = {
      {true, false, false},
      {true, true, true},
      {true, true, true}
    };
    Grid grid = GridFactory.generateGridFromArray(initialState);

    var nextGeneration = GenerationController.determineNextGeneration(grid, EdgeBehavior.BOUNDED);

    assertEquals(false, nextGeneration.getCell(1, 1).alive());
  }

  @Test
  public void IfALivingCellHas7LivingNeighborsThenItBecomesDead() {
    boolean[][] initialState = {
      {true, true, false},
      {true, true, true},
      {true, true, true}
    };
    Grid grid = GridFactory.generateGridFromArray(initialState);

    var nextGeneration = GenerationController.determineNextGeneration(grid, EdgeBehavior.BOUNDED);

    assertEquals(false, nextGeneration.getCell(1, 1).alive());
  }

  @Test
  public void IfALivingCellHas8LivingNeighborsThenItBecomesDead() {
    boolean[][] initialState = {
      {true, true, true},
      {true, true, true},
      {true, true, true}
    };
    Grid grid = GridFactory.generateGridFromArray(initialState);

    var nextGeneration = GenerationController.determineNextGeneration(grid, EdgeBehavior.BOUNDED);

    assertEquals(false, nextGeneration.getCell(1, 1).alive());
  }

  @Test
  public void Glider() {
    boolean[][] initialState = {
      {false, false, true, false, false, false, false},
      {true, false, true, false, false, false, false},
      {false, true, true, false, false, false, false},
      {false, false, false, false, false, false, false},
      {false, false, false, false, false, false, false},
      {false, false, false, false, false, false, false}
    };
    Grid initialGrid = GridFactory.generateGridFromArray(initialState);

    boolean[][] secondGenInit = {
      {false, true, false, false, false, false, false},
      {false, false, true, true, false, false, false},
      {false, true, true, false, false, false, false},
      {false, false, false, false, false, false, false},
      {false, false, false, false, false, false, false},
      {false, false, false, false, false, false, false}
    };
    Grid expectedSecondGen = GridFactory.generateGridFromArray(secondGenInit);

    boolean[][] thirdGenInit = {
      {false, false, true, false, false, false, false},
      {false, false, false, true, false, false, false},
      {false, true, true, true, false, false, false},
      {false, false, false, false, false, false, false},
      {false, false, false, false, false, false, false},
      {false, false, false, false, false, false, false}
    };
    Grid expectedThirdGen = GridFactory.generateGridFromArray(thirdGenInit);

    boolean[][] fourthGenInit = {
      {false, false, false, false, false, false, false},
      {false, true, false, true, false, false, false},
      {false, false, true, true, false, false, false},
      {false, false, true, false, false, false, false},
      {false, false, false, false, false, false, false},
      {false, false, false, false, false, false, false}
    };
    Grid expectedFourthGen = GridFactory.generateGridFromArray(fourthGenInit);

    boolean[][] fifthGenInit = {
      {false, false, false, false, false, false, false},
      {false, false, false, true, false, false, false},
      {false, true, false, true, false, false, false},
      {false, false, true, true, false, false, false},
      {false, false, false, false, false, false, false},
      {false, false, false, false, false, false, false}
    };
    Grid expectedFifthGen = GridFactory.generateGridFromArray(fifthGenInit);

    Grid actualSecondGen =
        GenerationController.determineNextGeneration(initialGrid, EdgeBehavior.WRAPPED);
    Grid actualThirdGen =
        GenerationController.determineNextGeneration(actualSecondGen, EdgeBehavior.WRAPPED);
    Grid actualFourthGen =
        GenerationController.determineNextGeneration(actualThirdGen, EdgeBehavior.WRAPPED);
    Grid actualFifthGen =
        GenerationController.determineNextGeneration(actualFourthGen, EdgeBehavior.WRAPPED);

    assertEquals(expectedSecondGen, actualSecondGen);
    assertEquals(expectedThirdGen, actualThirdGen);
    assertEquals(expectedFourthGen, actualFourthGen);
    assertEquals(expectedFifthGen, actualFifthGen);
  }

  @Test
  public void Oscillator() {
    boolean[][] initialState = {
      {false, false, true, false},
      {true, false, false, true},
      {true, false, false, true},
      {false, true, false, false}
    };
    Grid initialGrid = GridFactory.generateGridFromArray(initialState);

    boolean[][] secondGenInit = {
      {false, false, false, false},
      {false, true, true, true},
      {true, true, true, false},
      {false, false, false, false}
    };
    Grid expectedSecondGen = GridFactory.generateGridFromArray(secondGenInit);
    Grid expectedThirdGen = GridFactory.generateGridFromArray(initialState);

    Grid actualSecondGen =
        GenerationController.determineNextGeneration(initialGrid, EdgeBehavior.BOUNDED);
    Grid actualThirdGen =
        GenerationController.determineNextGeneration(actualSecondGen, EdgeBehavior.BOUNDED);

    assertEquals(expectedSecondGen, actualSecondGen);
    assertEquals(expectedThirdGen, actualThirdGen);
  }

  @Test
  public void StillLife() {
    boolean[][] initialState = {
      {false, true, true, false},
      {true, false, false, true},
      {false, true, true, false}
    };
    Grid initialGrid = GridFactory.generateGridFromArray(initialState);
    Grid expectedSecondGen = GridFactory.generateGridFromArray(initialState);

    Grid actualSecondGen =
        GenerationController.determineNextGeneration(initialGrid, EdgeBehavior.BOUNDED);

    assertEquals(expectedSecondGen, actualSecondGen);
  }

  @Test
  public void WrappingOscillator() {
    boolean[][] initialState = {
      {true, true, true, false},
      {true, false, false, false},
      {false, false, false, false},
      {true, false, false, false}
    };
    Grid initialGrid = GridFactory.generateGridFromArray(initialState);

    boolean[][] secondGenInit = {
      {true, false, false, false},
      {true, false, false, true},
      {false, false, false, false},
      {true, false, false, true}
    };
    Grid expectedSecondGen = GridFactory.generateGridFromArray(secondGenInit);

    boolean[][] thirdGenInit = {
      {false, true, false, false},
      {true, false, false, true},
      {false, false, false, false},
      {true, false, false, true}
    };
    Grid expectedThirdGen = GridFactory.generateGridFromArray(thirdGenInit);

    boolean[][] fourthGenInit = {
      {false, true, true, false},
      {true, false, false, false},
      {false, false, false, false},
      {true, false, false, false}
    };
    Grid expectedFourthGen = GridFactory.generateGridFromArray(fourthGenInit);

    boolean[][] fifthGenInit = {
      {true, true, false, true},
      {false, true, false, false},
      {false, false, false, false},
      {false, true, false, false}
    };
    Grid expectedFifthGen = GridFactory.generateGridFromArray(fifthGenInit);

    boolean[][] sixthGenInit = {
      {false, true, false, false},
      {false, true, true, false},
      {false, false, false, false},
      {false, true, true, false}
    };
    Grid expectedSixthGen = GridFactory.generateGridFromArray(sixthGenInit);

    boolean[][] seventhGenInit = {
      {true, false, false, false},
      {false, true, true, false},
      {false, false, false, false},
      {false, true, true, false}
    };
    Grid expectedSeventhGen = GridFactory.generateGridFromArray(seventhGenInit);

    boolean[][] eigthGenInit = {
      {true, false, false, true},
      {false, true, false, false},
      {false, false, false, false},
      {false, true, false, false}
    };
    Grid expectedEigthGen = GridFactory.generateGridFromArray(eigthGenInit);
    Grid expectedNinthGen = GridFactory.generateGridFromArray(initialState);

    Grid actualSecondGen =
        GenerationController.determineNextGeneration(initialGrid, EdgeBehavior.WRAPPED);
    Grid actualThirdGen =
        GenerationController.determineNextGeneration(actualSecondGen, EdgeBehavior.WRAPPED);
    Grid actualFourthGen =
        GenerationController.determineNextGeneration(actualThirdGen, EdgeBehavior.WRAPPED);
    Grid actualFifthGen =
        GenerationController.determineNextGeneration(actualFourthGen, EdgeBehavior.WRAPPED);
    Grid actualSixthGen =
        GenerationController.determineNextGeneration(actualFifthGen, EdgeBehavior.WRAPPED);
    Grid actualSeventGen =
        GenerationController.determineNextGeneration(actualSixthGen, EdgeBehavior.WRAPPED);
    Grid actualEigthGen =
        GenerationController.determineNextGeneration(actualSeventGen, EdgeBehavior.WRAPPED);
    Grid actualNinthGen =
        GenerationController.determineNextGeneration(actualEigthGen, EdgeBehavior.WRAPPED);

    assertEquals(expectedSecondGen, actualSecondGen);
    assertEquals(expectedThirdGen, actualThirdGen);
    assertEquals(expectedFourthGen, actualFourthGen);
    assertEquals(expectedFifthGen, actualFifthGen);
    assertEquals(expectedSixthGen, actualSixthGen);
    assertEquals(expectedSeventhGen, actualSeventGen);
    assertEquals(expectedEigthGen, actualEigthGen);
    assertEquals(expectedNinthGen, actualNinthGen);
  }
}
