package dao;


public class SingletonCodUsuario {
    private static SingletonCodUsuario INSTANCE = null;



    private String codUsuario;

    // Private constructor suppresses
    private SingletonCodUsuario() {
        codUsuario = null;
    }

    // creador sincronizado para protegerse de posibles problemas  multi-hilo
    // otra prueba para evitar instanciación múltiple
    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonCodUsuario();
        }
    }

    public static SingletonCodUsuario getInstance() {
        if (INSTANCE == null) createInstance();
        return INSTANCE;
    }

    public String getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(String codUsuario) {
        this.codUsuario = codUsuario;
    }
}
