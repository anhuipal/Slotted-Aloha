package slottedAloha;


public class Node {

    private char name = 'a';
    private boolean trans;
    private double p;

    public Node(int i) {
        this.p=0;
        this.name = (char)((int)name + i);
    }

    public void setName(char name) {
        this.name = name;
    }

    public char getName() {
        return name;
    }

    public void setTrans(boolean trans) {
        this.trans = trans;
    }

    public boolean isTrans() {
        return trans;
    }

    public void setP(double p) {
        this.p = p;
    }

    public double getP() {
        return p;
    }

}
