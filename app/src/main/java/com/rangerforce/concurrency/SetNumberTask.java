/* Copyright © 2024 Jose Chavez. All Rights Reserved. */
package com.rangerforce.concurrency;

public class SetNumberTask implements Runnable {

  private volatile int number;
  private final int finalNumber;

  public SetNumberTask(int finalNumber) {
    this.number = 0;
    this.finalNumber = finalNumber;
  }

  @Override
  public void run() {
    number = finalNumber;
  }

  public int getNumber() {
    return number;
  }
}
