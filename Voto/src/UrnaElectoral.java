import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;
import java.util.Map;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Date;
public class UrnaElectoral {
    private LinkedList<Candidato> listaCandidatos;
    private Stack<Voto> historialVotos;
    private Queue<Voto> votosReportados;
    private int idCounter;
    public UrnaElectoral() {
        this.listaCandidatos = new LinkedList<>();
        this.historialVotos = new Stack<>();
        this.votosReportados = new LinkedList<>();
        this.idCounter = 1;
    }
    public void agregarCandidato(Candidato candidato) {
        listaCandidatos.add(candidato);
    }
    private boolean verificarVotante(Votante votante){
        return votante.isYaVoto();
    }
    public void registrarVoto(Votante votante, int candidatoId) {
        if(verificarVotante(votante)){
            System.out.println("Ya votó");
            return;
        }
        Candidato candidato = buscarCandidatoPorID(candidatoId);
        if(candidato == null){
            System.out.println("No se encontra candidato");
            return;
        }
        Voto nuevoVoto = new Voto(idCounter++, votante.getId(), candidatoId, generarTimestamp());
        candidato.agregarVoto(nuevoVoto);
        historialVotos.push(nuevoVoto);
        votante.marcarComoVotado();
    }
    public void reportarVoto(Candidato candidato, int idVoto){
        Queue<Voto> votosDelCandidato = candidato.getVotosRecibido();
        Queue<Voto> nuevaCola = new LinkedList<>();
        Voto votoReportado = null;
        while(!votosDelCandidato.isEmpty()){
            Voto voto = votosDelCandidato.poll();
            if(voto.getId() == idVoto){
                votoReportado = voto;
            }
            else{
                nuevaCola.add(voto);
            }
        }
        if(votoReportado != null){
            candidato.setVotosRecibido(nuevaCola);
            votosReportados.add(votoReportado);
            Stack<Voto> nuevoHistorial = new Stack<>();
            while(!historialVotos.isEmpty()){
                Voto voto = historialVotos.pop();
                if(voto.getId() == idVoto){
                    nuevoHistorial.push(voto);
                }
            }
            while(!nuevoHistorial.isEmpty()){
                historialVotos.push(nuevoHistorial.pop());
            }
        }
        else{
            candidato.setVotosRecibido(nuevaCola);

        }
    }
    public Map<String, Integer> obtenerResultados(){
        Map<String, Integer> resultados = new HashMap<>();
        for(Candidato candidato : listaCandidatos){
            resultados.put(candidato.getNombre(), candidato.getVotosRecibido().size());
        }
        return resultados;
    }
    private Candidato buscarCandidatoPorID(int id){
        for(Candidato candidato : listaCandidatos){
            if(candidato.getId() == id){
                return candidato;
            }
        }
        return null;
    }
    private String generarTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }
    public LinkedList<Candidato> getListaCandidatos() {
        return listaCandidatos;
    }
}
