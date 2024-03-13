package ast;

public class VarDecl {
   public String type;
   public String var;

   public VarDecl(String type, String var) {
      this.type = type;
      this.var = var;
   }

   @Override
   public String toString() {
      return type + " " + var;
   }
}
