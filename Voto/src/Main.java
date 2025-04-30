import java.util.Map;
public class Main {
    public static void main(String[] args) {
        UrnaElectoral urna = new UrnaElectoral();
        Candidato c1 = new Candidato(1, "Agustin", "Partido A");
        Candidato c2 = new Candidato(2, "Nicolas", "Partido N");
        urna.agregarCandidato(c1);
        urna.agregarCandidato(c2);
        Votante v1 = new Votante(100,"lorenzo");
        Votante v2 = new Votante(200,"Cristobal");
        Votante v3 = new Votante(300,"Tomas");
        Votante v4 = new Votante(400,"Javier");
        urna.registrarVoto(v1,1);
        urna.registrarVoto(v2,1);
        urna.registrarVoto(v3,2);
        urna.registrarVoto(v4,1);
        System.out.println("Resultados (sin filtro)");
        Map<String, Integer> resultados = urna.obtenerResultados();
        for(String nombre : resultados.keySet()){
            System.out.println(nombre + ": " + resultados.get(nombre) + " " + "votos");
        }
        urna.reportarVoto(c1,1);
        System.out.println("Resultados finales (filtrados)");
        resultados = urna.obtenerResultados();
        for(String nombre : resultados.keySet()){
            System.out.println(nombre + ": " + resultados.get(nombre) + " " + "votos");
        }
        for(Candidato candidato : urna.getListaCandidatos()){
            System.out.println(candidato.getNombre());
            for(Voto voto : candidato.getVotosRecibido()){
                System.out.println("Voto ID " + voto.getId() + " a las " + voto.getTimestamp());
            }
        }
    }
}