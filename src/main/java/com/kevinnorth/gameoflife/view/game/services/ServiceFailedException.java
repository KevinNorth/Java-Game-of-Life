package com.kevinnorth.gameoflife.view.game.services;

import javafx.concurrent.WorkerStateEvent;

public class ServiceFailedException extends IllegalStateException {
  private WorkerStateEvent workerStateEvent;

  public ServiceFailedException(WorkerStateEvent workerStateEvent) {
    super();
    this.workerStateEvent = workerStateEvent;
  }

  public ServiceFailedException(String message, WorkerStateEvent workerStateEvent) {
    super(message);
    this.workerStateEvent = workerStateEvent;
  }

  public WorkerStateEvent getWorkerStateEvent() {
    return this.workerStateEvent;
  }
}
