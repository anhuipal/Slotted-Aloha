package slottedAloha;


public class Metrics implements Comparable {

    private int successes,failures,num,nodes,totalSuccesses;
    private double p,efficiency,plot1,plot2;

    public Metrics(int successes, int failures, double p,int num,double efficiency,double plot1,int nodes){
        this.successes = successes;
        this.failures = failures;
        this.p = p;
        this.num = num;
        this.efficiency = efficiency;
        this.plot1 = plot1;
        this.nodes = nodes;
    }

    public void setSuccesses(int successes) {
        this.successes = successes;
    }

    public int getSuccesses() {
        return successes;
    }

    public void setFailures(int failures) {
        this.failures = failures;
    }

    public int getFailures() {
        return failures;
    }

    public void setP(double p) {
        this.p = p;
    }

    public double getP() {
        return p;
    }

    public int getNum() {
        return num;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }

    public double getEfficiency() {
        return efficiency;
    }


    public void setPlot1(double plot1) {
        this.plot1 = plot1;
    }

    public double getPlot1() {
        return plot1;
    }

    public void setPlot2(double plot2) {
        this.plot2 = plot2;
    }

    public double getPlot2() {
        return plot2;
    }

    public void setNodes(int nodes) {
        this.nodes = nodes;
    }

    public int getNodes() {
        return nodes;
    }

    public void setTotalSuccesses(int totalSuccesses) {
        this.totalSuccesses = totalSuccesses;
    }

    public int getTotalSuccesses() {
        return totalSuccesses;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
