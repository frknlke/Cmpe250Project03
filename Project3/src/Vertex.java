import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Vertex implements Comparable<Vertex> {
    public boolean isProcessed;
    public String id;
    //public String adjacents;
    public Map<String,Integer> edges;
    public Vertex parent;
    public int distance=Integer.MAX_VALUE;

    public Vertex(boolean isProcessed, String id) {
        this.isProcessed = isProcessed;
        this.id = id;
        this.edges=new HashMap<>();
    }
/*
 @Override
    public int compareTo(Vertex o) {
        if(this.distance < o.distance){
            return -1;
        }else{
            return 1;
        }
 */
    @Override
    public int compareTo(Vertex o) {
        if(this.distance < o.distance){
            return -1;
        }else if(this.distance > o.distance) {
            return 1;
        }
            else{
            if (Integer.parseInt(this.id.substring(1)) < Integer.parseInt(o.id.substring(1))){
                return -1;
            }else{
                return 1;
            }
        }



    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(id, vertex.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
