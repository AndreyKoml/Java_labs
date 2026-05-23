package util;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static PrintWriter carAILog;
    private static PrintWriter carLog;
    private static PrintWriter truckLog;
    private static PrintWriter brainLog;
    private static PrintWriter errorLog;
    private static PrintWriter deliteCar;
    private static PrintWriter deliteTruck;
    private static PrintWriter Carx1;
    private static PrintWriter Carsets;
    private static PrintWriter CarConstr;
    private static PrintWriter CarGenerate;
    private static PrintWriter TruckBrain;
    private static PrintWriter Thead;
    private static PrintWriter TruckD;
    private static PrintWriter TruckGetEnd;
    
    static {
        try {
            
            carLog = new PrintWriter(new FileWriter("carLog.log", false));
            carAILog = new PrintWriter(new FileWriter("carAI.log", false));
            truckLog = new PrintWriter(new FileWriter("truckAI.log", false));
            brainLog = new PrintWriter(new FileWriter("brain.log", false));
            errorLog = new PrintWriter(new FileWriter("error.log", false));
            deliteCar=new PrintWriter(new FileWriter("deliteCar.log", false));
            deliteTruck=new PrintWriter(new FileWriter("deliteTruck.log", false));
            Carx1=new PrintWriter(new FileWriter("Carx1.log", false));
            Carsets=new PrintWriter(new FileWriter("Carsets.log", false));
            CarConstr=new PrintWriter(new FileWriter("CarConstr.log", false));
            CarGenerate=new PrintWriter(new FileWriter("CarGenerate.log", false));
            Thead=new PrintWriter(new FileWriter("Thead.log", false));
            TruckBrain=new PrintWriter(new FileWriter("TruckBrain.log", false));
            TruckD=new PrintWriter(new FileWriter("TruckD.log", false));
            TruckGetEnd=new PrintWriter(new FileWriter("TruckGetEnd.log", false));

        } catch (Exception e) {
            System.err.println("Не удалось создать логи: " + e.getMessage());
        }
    }
    
    private static String timestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
    }
    public static void TruckGetEnd(String msg) {
        if (TruckGetEnd != null) {
            TruckGetEnd.println(timestamp() + " " + msg);
            TruckGetEnd.flush();
        }
    }
    public static void Thead(String msg) {
        if (Thead != null) {
            Thead.println(timestamp() + " " + msg);
            Thead.flush();
        }
    }
    public static void TruckD(String msg) {
        if (TruckD != null) {
            TruckD.println(timestamp() + " " + msg);
            TruckD.flush();
        }
    }
    public static void TruckBrain(String msg) {
        if (TruckBrain != null) {
            TruckBrain.println(timestamp() + " " + msg);
            TruckBrain.flush();
        }
    }
    public static void CarConstr(String msg) {
        if (CarConstr != null) {
            CarConstr.println(timestamp() + " " + msg);
            CarConstr.flush();
        }
    }
    public static void CarGenerate(String msg) {
        if (CarGenerate != null) {
            CarGenerate.println(timestamp() + " " + msg);
            CarGenerate.flush();
        }
    }
    public static void Carsets(String msg) {
        if (Carsets != null) {
            Carsets.println(timestamp() + " " + msg);
            Carsets.flush();
        }
    }

    public static void Carx1(String msg) {
        if (Carx1 != null) {
            Carx1.println(timestamp() + " " + msg);
            Carx1.flush();
        }
    }
    public static void car(String msg) {
        if (carLog != null) {
            carLog.println(timestamp() + " " + msg);
            carLog.flush();
        }
    }
    public static void deliteCar(String msg) {
        if (deliteCar != null) {
            deliteCar.println(timestamp() + " " + msg);
            deliteCar.flush();
        }
    }
    public static void deliteTruck(String msg) {
        if (deliteTruck != null) {
            deliteTruck.println(timestamp() + " " + msg);
            deliteTruck.flush();
        }
    }
    public static void carAI(String msg) {
        if (carAILog != null) {
            carAILog.println(timestamp() + " " + msg);
            carAILog.flush();
        }
    }
    
    public static void truck(String msg) {
        if (truckLog != null) {
            truckLog.println(timestamp() + " " + msg);
            truckLog.flush();
        }
    }
    
    public static void brain(String msg) {
        if (brainLog != null) {
            brainLog.println(timestamp() + " " + msg);
            brainLog.flush();
        }
    }
    
    public static void error(String msg, Exception e) {
        if (errorLog != null) {
            errorLog.println(timestamp() + " " + msg);
            e.printStackTrace(errorLog);
            errorLog.flush();
        }
    }
    
    public static void close() {
        if (carAILog != null) carAILog.close();
        if (truckLog != null) truckLog.close();
        if (brainLog != null) brainLog.close();
        if (errorLog != null) errorLog.close();
    }
}