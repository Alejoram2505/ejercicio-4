import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Jugador {
    private String nombre;
    private String pais;
    protected int faltas;
    protected int golesDirectos;
    protected int totalLanzamientos;

    public Jugador(String nombre, String pais, int faltas, int golesDirectos, int totalLanzamientos) {
        this.nombre = nombre;
        this.pais = pais;
        this.faltas = faltas;
        this.golesDirectos = golesDirectos;
        this.totalLanzamientos = totalLanzamientos;
    }

    public double calcularEfectividad() {
        // Fórmula general para efectividad de jugadores
        return ((golesDirectos * 100.0) / totalLanzamientos);
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", País: " + pais;
    }
}

class Portero extends Jugador {
    private int paradasEfectivas;
    private int golesRecibidos;

    public Portero(String nombre, String pais, int faltas, int golesDirectos, int totalLanzamientos,
                   int paradasEfectivas, int golesRecibidos) {
        super(nombre, pais, faltas, golesDirectos, totalLanzamientos);
        this.paradasEfectivas = paradasEfectivas;
        this.golesRecibidos = golesRecibidos;
    }

    @Override
    public double calcularEfectividad() {
        // Fórmula específica para porteros
        return (((paradasEfectivas - golesRecibidos) * 100.0) / (paradasEfectivas + golesRecibidos))
                + ((golesDirectos * 100.0) / totalLanzamientos);
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo: Portero";
    }
}

class Extremo extends Jugador {
    private int pases;
    private int asistenciasEfectivas;

    public Extremo(String nombre, String pais, int faltas, int golesDirectos, int totalLanzamientos,
                   int pases, int asistenciasEfectivas) {
        super(nombre, pais, faltas, golesDirectos, totalLanzamientos);
        this.pases = pases;
        this.asistenciasEfectivas = asistenciasEfectivas;
    }

    @Override
    public double calcularEfectividad() {
        // Fórmula específica para extremos
        return (((pases + asistenciasEfectivas - getFaltas()) * 100.0) /
                (pases + asistenciasEfectivas + getFaltas()))
                + ((golesDirectos * 100.0) / getTotalLanzamientos());
    }

    private double getTotalLanzamientos() {
        return totalLanzamientos;
    }

    private int getFaltas() {
        return faltas ;
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo: Extremo";
    }
}

public class CampeonatoBalonmano {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Jugador> jugadores = new ArrayList<>();

        System.out.println("Registro de Jugadores en el Campeonato de Balonmano");
        while (true) {
            System.out.println("Seleccione el tipo de jugador (1: Portero, 2: Extremo, 0: Salir):");
            int tipoJugador = scanner.nextInt();
            if (tipoJugador == 0) {
                break;
            }

            System.out.print("Nombre: ");
            String nombre = scanner.next();
            System.out.print("País: ");
            String pais = scanner.next();
            System.out.print("Faltas: ");
            int faltas = scanner.nextInt();
            System.out.print("Goles Directos: ");
            int golesDirectos = scanner.nextInt();
            System.out.print("Total de Lanzamientos: ");
            int totalLanzamientos = scanner.nextInt();

            if (tipoJugador == 1) {
                System.out.print("Paradas Efectivas: ");
                int paradasEfectivas = scanner.nextInt();
                System.out.print("Goles Recibidos: ");
                int golesRecibidos = scanner.nextInt();
                jugadores.add(new Portero(nombre, pais, faltas, golesDirectos, totalLanzamientos,
                        paradasEfectivas, golesRecibidos));
            } else if (tipoJugador == 2) {
                System.out.print("Pases: ");
                int pases = scanner.nextInt();
                System.out.print("Asistencias Efectivas: ");
                int asistenciasEfectivas = scanner.nextInt();
                jugadores.add(new Extremo(nombre, pais, faltas, golesDirectos, totalLanzamientos,
                        pases, asistenciasEfectivas));
            }
        }

        // Mostrar todos los jugadores registrados
        System.out.println("Jugadores registrados:");
        for (Jugador jugador : jugadores) {
            System.out.println(jugador + " - Efectividad: " + jugador.calcularEfectividad());
        }

        // Los 3 mejores porteros en función de su efectividad
        List<Portero> porteros = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            if (jugador instanceof Portero) {
                porteros.add((Portero) jugador);
            }
        }
        porteros.sort((p1, p2) -> Double.compare(p2.calcularEfectividad(), p1.calcularEfectividad()));
        System.out.println("Los 3 mejores porteros:");
        for (int i = 0; i < Math.min(3, porteros.size()); i++) {
            System.out.println(porteros.get(i));
        }

        // Cantidad de extremos con más de un 85% de efectividad
        int extremosCon85PorcientoEfectividad = 0;
        for (Jugador jugador : jugadores) {
            if (jugador instanceof Extremo && jugador.calcularEfectividad() > 85.0) {
                extremosCon85PorcientoEfectividad++;
            }
        }
        System.out.println("Cantidad de extremos con más de un 85% de efectividad: " + extremosCon85PorcientoEfectividad);
    }
}
