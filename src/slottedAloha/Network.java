package slottedAloha;

import java.util.*;

public class Network {

    private HashMap<String,Node> Network;
    private Set<Metrics> metrics;
    private int sucTrans;
    private int failTrans;
    private int iterations;
    private int nodes;
    private int sucTotalTrans;
    private double networkProb,incrimentProb;
    private Set<String> networkKeysSet;

    public Network(int nodes) {
        this.Network = new HashMap<String,Node>();
        this.metrics = new HashSet<Metrics>();
        this.networkKeysSet = new HashSet<String>();
        this.sucTrans=0;
        this.failTrans=0;
        this.networkProb=0.05;
        this.nodes=nodes;
        this.iterations=1;
    }//end of default constructor

    public Network(double networkProb,int incrimentProb,int nodes) {
        this.Network = new HashMap<String,Node>();
        this.metrics = new HashSet<Metrics>();
        this.networkKeysSet = new HashSet<String>();
        this.sucTrans=0;
        this.failTrans=0;
        this.networkProb=networkProb;
        this.incrimentProb=incrimentProb;
        this.nodes=nodes;
        this.iterations=1;
        this.sucTotalTrans = 0;
    }//end of constructor

    public void init(){
        for(int i = 0; i<=nodes;i++){
            Network.put(Character.toString(new Node(i).getName()),new Node(i));
        }
    }//end of init()

    public void init(int nodes){
        for(int i = 0; i<=nodes;i++){
            Network.put(Character.toString(new Node(i).getName()),new Node(i));
        }
    }//end of init()

    public void calculateProb(){
        this.networkProb = this.networkProb + (double)(this.incrimentProb)/100*this.networkProb;
        //System.out.println(this.networkProb);
    }//end of calculateProb()

    public void testNetworkEfficiency(){

        networkKeysSet = Network.keySet();
        String [] networkKeys = networkKeysSet.toArray(new String[networkKeysSet.size()]);

        do{
            for(int i = 0;i<10000;i++){
                int transmiting = 0;
                //This loop set a random probability which refers to the propability of transmiting
                for(int j = 0 ;j<Network.size();j++){
                    Network.get(networkKeys[j]).setP(new Random().nextDouble());
                    if(Network.get(networkKeys[j]).getP()<=this.networkProb){
                        //   Network.get(networkKeys[j]).setTrans(true);
                        transmiting++;
                        //System.out.println(Network.get(networkKeys[j]).getName() + " wants to Transimit!");
                    }
                }
                if(transmiting==1){
                    //System.out.println("Transmission Complete");
                    this.sucTrans++;
                }
                else{
                    //System.out.println("Transmission Failed");
                    this.failTrans++;
                }
                //
            }
            this.metrics.add(new Metrics(this.sucTrans,this.failTrans,this.networkProb,this.iterations,(double) this.sucTrans/(this.sucTrans + this.failTrans),(double)this.sucTrans/this.networkProb,this.nodes));
            this.iterations++;
            this.calculateProb(); //This method incriments the propability of the network
            sucTotalTrans = sucTotalTrans +  sucTrans;
            this.sucTrans = 0;
            this.failTrans = 0;
        }while(this.networkProb<1);
    }

    public void internalEfficiency(){
        this.init();
        this.testNetworkEfficiency();
    }

    public void setNetwork(HashMap<String, Node> Network) {
        this.Network = Network;
    }

    public HashMap<String, Node> getNetwork() {
        return Network;
    }

    public int getSucTrans() {
        return sucTrans;
    }

    public int getFailTrans() {
        return failTrans;
    }

    public Set<Metrics> getMetrics() {
        return metrics;
    }

    public String printSet(){
        String results = new String();
        double maxProp = 0;
        int maxSuc = 0;
        for(Metrics m : metrics){
            results += "Runtime: " +  m.getNum() +" Propability: " + m.getP() + " Successes: " + m.getSuccesses() + " Failures: " + m.getFailures() + " Efficiency: " + m.getEfficiency() + " Plot1: " + m.getPlot1() + "\n";
            if(m.getSuccesses()>maxSuc){
                maxSuc = m.getSuccesses();
                maxProp = m.getP();
            }
        }
        results += "Total Successful Transmissions : " + this.getSucTotalTrans() + "\n";
        results += "Highest Number of Successes : " + maxSuc + " at Probability : " + maxProp + "\n" ;
        return results;
    }

    public int getSucTotalTrans() {
        return sucTotalTrans;
    }

    public int getNodes() {
        return nodes;
    }

}//end of class
