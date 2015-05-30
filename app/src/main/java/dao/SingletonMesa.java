package dao;

/**
 * Created by RAMON on 25/01/2015.
 */
public class SingletonMesa {

    private static SingletonMesa INSTANCE = null;



    private int mesa;

    // Private constructor suppresses
    private SingletonMesa() {
        mesa = 0;
    }

    // creador sincronizado para protegerse de posibles problemas  multi-hilo
    // otra prueba para evitar instanciación múltiple
    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonMesa();
        }
    }

    public static SingletonMesa getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }


    public  int getMesa() {
        return mesa;
    }

    public  void setMesa(int mesa) {
        this.mesa = mesa;
    }


}
