package ast;

import java.util.ArrayList;

public class Prog {
    public Main main;
    public ArrayList<Fun> fun;

    public Prog(Main main, ArrayList<Fun> fun) {
        this.main = main;
        this.fun = fun;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        // Main program
        result.append(main).append("\n\n");

        // Functions
        for (Fun f : fun) {
            result.append(f).append("\n\n");
        }

        return result.toString();
    }
}
