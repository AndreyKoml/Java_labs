package alfragment;

import java.util.ArrayList;
import java.util.List;

import model.Transport;
import repository.TransportRepository;

public abstract class BaisAIimp implements Runnable {
  private TransportRepository repository;
  private Thread thread;
  protected volatile boolean running = true; 
  protected volatile boolean paused = false;

  public BaisAIimp(TransportRepository repository) {
    this.repository = repository;
  }

  public void run() {
    List<Transport>stnapshert= new ArrayList<>();
    while (running ) {
      //System.out.println("Поток " + this.getClass().getSimpleName() + " начал итерацию");
      synchronized (this) {
        while (paused && running) {
         // ← проверяем, на паузе ли
          try {
            wait();
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
          }
        }
      }
      if (!running) break;

      synchronized (repository) {
        for (Transport t : repository.getAll()) {
           if (isMyType(t)) {
            move(t);
           }
        }
      }
      //System.out.println("Поток " + this.getClass().getSimpleName() + " завершил итерацию");
      try {
        Thread.sleep(20);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  public abstract void move(Transport t);
  public abstract boolean isMyType(Transport t);

  public synchronized void start() {
    running = true;
    if (thread == null || !thread.isAlive()) {
      thread = new Thread(this);
      thread.start();
    }
  }

  public synchronized void stop() {
    running = false;
    if (thread != null) {
      thread.interrupt();
    }
  }

  public synchronized void resume() {
    paused = false;
    notify();
  }
public synchronized void pause() {
    paused = true;
}
  
  public boolean getrunning(){return running;}
  public boolean getPaused(){return paused;}
  public void setpause(boolean paused){
    this.paused=paused;
    if (!paused){
      synchronized(this){
        notify();
      }
    }
  }
   
}

