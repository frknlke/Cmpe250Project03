import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.System.out;

public class project3main {

    public static void main(String[] args)throws IOException {
        String inputFileName = args[0];
        File myInputFile = new File(inputFileName);
        Map<String,Vertex> cities = new HashMap<>();
        ArrayList<Vertex> shortestPath = new ArrayList<>();
        PriorityQueue<Vertex> shortest = new PriorityQueue<>();
        //PriorityQueue<Vertex> cities = new PriorityQueue<>();
        boolean didReach = false;
        boolean doMarry = false;
        boolean honeyMoon=false;
        int totalNumOfCities = 0;
        int honeymoonRoute = 0;
        String shortestPth="";
        int eastSideCounter=0;
        try {
            Scanner sc = new Scanner(myInputFile);
            sc.useLocale(Locale.US);

            //Leyla's father's time limit
            int timeLimit = 0;

            if (sc.hasNextInt()) {
                timeLimit = sc.nextInt();
            }
            if (sc.hasNextInt()) {
                //Total Number of Cities in the Anatolia
                totalNumOfCities = sc.nextInt();
            }
            String idOfLeylaCity = "";
            String idOfMecnunCity = "";

            if (sc.hasNext()){
                idOfMecnunCity = sc.next();
                //Vertex v1 = new Vertex(false,idOfMecnunCity);
                //cities.put(idOfMecnunCity,v1);
        }
            if (sc.hasNext()) {
                idOfLeylaCity = sc.next();
                //Vertex v2 = new Vertex(false,idOfLeylaCity);
                //cities.put(idOfLeylaCity,v2);
            }
            int counter = 0;
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                int weight=-1;

                Scanner scan = new Scanner(line);
                scan.useLocale(Locale.US);

                String idOfCity = "";
                if(scan.hasNext()){
                    idOfCity=scan.next();
                    Vertex v3=new Vertex(false,idOfCity);
                    cities.put(idOfCity,v3);
                    if(idOfCity.charAt(0)=='d'){
                        eastSideCounter++;
                    }
                }
                String idOfAdjacent="";

                while(scan.hasNext()){
                    idOfAdjacent=scan.next();
                    if(scan.hasNextInt()){
                        weight= scan.nextInt();
                        if(cities.get(idOfCity).edges.containsKey(idOfAdjacent)){
                            if(weight < cities.get(idOfCity).edges.get(idOfAdjacent)){
                                cities.get(idOfCity).edges.replace(idOfAdjacent,weight);
                            }
                        }else{
                            cities.get(idOfCity).edges.put(idOfAdjacent,weight);
                        }
                    }
                }
            }
            cities.get(idOfMecnunCity).parent=null;
            cities.get(idOfMecnunCity).distance=0;
            shortest.add(cities.get(idOfMecnunCity));

            while(!shortest.isEmpty()) {
                Vertex vertex = shortest.poll();
                if (vertex.equals(cities.get(idOfLeylaCity))) {
                    didReach = true;
                    doMarry = (vertex.distance <= timeLimit);
                    //out.println(vertex.id);
                    //out.println(idOfLeylaCity);
                    //out.println(doMarry);
                    while (vertex.parent != null) {
                        shortestPath.add(vertex);
                        vertex = vertex.parent;
                        if(vertex.equals(cities.get(idOfMecnunCity))){
                            shortestPath.add(vertex);
                        }
                    }
                    break;
                }
                int minDistance;

                for (String s : vertex.edges.keySet()) {
                    Vertex v = cities.get(s);
                    minDistance = vertex.distance + vertex.edges.get(s);

                    if (minDistance < v.distance) {
                        shortest.remove(v);
                        v.parent = vertex;
                        v.distance = minDistance;
                        shortest.add(v);

                    }
                }
            }
            //last added
            for(int i=shortestPath.size()-1;i>=0;i--){
                shortestPth=shortestPath.get(i).id + "  " ;
            }
            //To make graph undirected
            for(String s : cities.keySet()){
                if(s.charAt(0)=='d') {
                    cities.get(s).distance = Integer.MAX_VALUE;
                    cities.get(s).parent = null;
                    cities.get(s).isProcessed = false;
                for(String str : cities.get(s).edges.keySet()){
                    int weight=cities.get(s).edges.get(str);

                    if(cities.get(str).edges.containsKey(s)){
                        int weight1=cities.get(str).edges.get(s);
                        if(weight1 < weight){
                            cities.get(str).edges.replace(s,weight1);
                            cities.get(s).edges.replace(str,weight1);
                        }else{
                            cities.get(str).edges.replace(s,weight);
                            cities.get(s).edges.replace(str,weight);
                        }

                    }else{
                        cities.get(str).edges.put(s,weight);
                    }
                }
                }
            }
            int count=0;
            int[] cost= new int[eastSideCounter+1];
            for(int i=1;i<cost.length;i++){
                cost[i] = Integer.MAX_VALUE;
            }
            cost[0]=0;
            shortest.clear();
            cities.get(idOfLeylaCity).distance=0;
            cities.get(idOfLeylaCity).isProcessed=false;
            shortest.add(cities.get(idOfLeylaCity));
            while(!shortest.isEmpty()){
                Vertex v = shortest.remove();
                //shortest.add(v);
                //v=shortest.poll();
                //out.println(v.id);
                if(!v.isProcessed){
                    //out.println(v.id + "    ");
                        //honeymoonRoute += 2*v.edges.get(v.parent.id);
                        //honeymoonRoute += 2*cost[Integer.parseInt(v.id.substring(1))];
                    if(v.id.charAt(0)=='d') {
                        cost[Integer.parseInt(v.id.substring(1))] = v.distance;
                        //out.println(v.id + "    " + cost[Integer.parseInt(v.id.substring(1))]);
                    }
                    //out.println(honeymoonRoute);
                    count++;
                    v.isProcessed=true;
                    for(String s : v.edges.keySet()) {
                        if (s.equalsIgnoreCase(v.id)) {
                            continue;
                        } else {
                        //s.charAt(0) == 'd' && !cities.get(s).isProcessed
                        if (s.charAt(0) == 'd' && !cities.get(s).isProcessed) {
                            int num = Integer.parseInt(s.substring(1));
                            //Vertex v1 = cities.get(s);
                            //ifin içini değişitridm önceki : (v.edges.get(cities.get(s).id) < cost[num])
                            if (v.edges.get(cities.get(s).id) < cities.get(s).distance) {
                                //cities.get(s).distance = v.edges.get(cities.get(s).id);
                                //num equal to cities.get(s).distance
                                cities.get(s).distance = v.edges.get(cities.get(s).id);
                                cities.get(s).parent = v;
                                if(shortest.contains(cities.get(s))){
                                    shortest.remove(cities.get(s));
                                    shortest.add(cities.get(s));
                                }else{
                                    shortest.add(cities.get(s));
                                }
                                //aşağıdaki satır sonradan commente alındı
                                //cities.get(s).distance=v.edges.get(s);
                                //out.println("sıraya eklendi " + s + "   " + cities.get(s).distance + " " + v.edges.get(cities.get(s).id));
                                //honeymoonRoute += v.edges.get(v1.id);
                            }
                        }
                    }
                    }
                }
            }
            for(int i=0;i<cost.length;i++){
                honeymoonRoute += 2*cost[i];
            }

            if(count==(eastSideCounter+1)){
                honeyMoon=true;
            }
            //out.println(honeyMoon);
        }catch (FileNotFoundException e){
            out.println("Catch - An error occurred.");
            e.printStackTrace();
        }
        String outputFileName = args[1];
        File myOutputFile = new File(outputFileName);
        try{
            FileWriter fileWriter = new FileWriter(outputFileName);
            if(didReach){
                for(int i=shortestPath.size()-1;i>=0;i--){
                    if(i!=0){
                        fileWriter.write(shortestPath.get(i).id + " ");
                    }else{
                        fileWriter.write(shortestPath.get(i).id);
                    }

                }
                fileWriter.write("\n");
                //fileWriter.write(shortestPth);
                //out.println("ne1");
                //fileWriter.write("\n");
            }else{
                //out.println("ne2");
                fileWriter.write(-1 + "\n");
            }
            if(!doMarry){
                //out.println("ne3");
                fileWriter.write(Integer.toString(-1));
            }else{
                if(!honeyMoon){
                    //out.println("ne4");
                    fileWriter.write(Integer.toString(-2));

                }else{
                    //fileWriter.write(honeymoonRoute);
                    fileWriter.write(Integer.toString(honeymoonRoute));
                    //out.println("ne5");
                }
            }
            //out.println("ne6");
            fileWriter.close();
            //fileWriter.flush();
        }catch (IOException e) {
            out.println("Catch - An error occurred.");
            e.printStackTrace();
        }
    }
}
