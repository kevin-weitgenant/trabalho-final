PARSER_BEGIN(Simple)

import java.io.*;
import java.util.ArrayList;

class SimpleA {
  ArrayList<Comando> comandos;

  SimpleA(ArrayList comandos) {
    this.comandos = comandos;
  }
}

class Comando {
}

class Atrib extends Comando {

  String id;
  Exp exp;

  Atrib(String id, Exp exp) {
    this.id = id;
    this.exp = exp;
  }

}

class Print extends Comando {

  Exp exp;

  Print(Exp exp) {
    this.exp = exp;
  }
}

class Exp {
}

class Num extends Exp {
  int num;

  Num(int num) {
    this.num = num;
  }

}

class Var extends Exp {
  String var;

  Var(String var) {
    this.var = var;
  }
}

public class Simple {
  public static void main(String[] args) throws ParseException, IOException {
    Simple parser = new Simple(new FileInputStream(args[0]));
    SimpleA arvore = parser.Simple();
    print(arvore);
  }

  public static void print(SimpleA arv) {

    System.out.println("main(){");
    printComandos(arv.comandos);
    System.out.println("}");

  }

  public static void printComandos(ArrayList<Comando> comandos) {
    for (Comando c : comandos) {
      if (c instanceof Print) {
        System.out.println("print(" + stringExp(((Print) c).exp) + ");");
      } else {
        System.out.println(((Atrib) c).id + " := " + stringExp(((Atrib) c).exp) + ";");
      }
    }

  }

  public static String stringExp(Exp e) {
    if (e instanceof Num)
      return (((Num) e).num + "");
    else
      return (((Var) e).var);

  }

}

  PARSER_END(Simple)

  SKIP:{" "|"\t"|"\n"|"\r"}

  TOKEN:{<MAIN:"main">|<ACHAVES:"{">|<FCHAVES:"}">|<APARENTESES:"(">|<FPARENTESES:")">|<PRINT:"print">|<ATRIB:":=">|<PV:";">}

  TOKEN:{<NUM:(["0"-"9"])+>|<ID:["a"-"z","A"-"Z"](["a"-"z","A"-"Z","0"-"9"])*>

  }

  // SIMPLE -> "main" "{" COMANDOS "}"

SimpleA Simple () :
{ArrayList comandos= new ArrayList();}
{

<MAIN> <ACHAVES> Comandos(comandos) <FCHAVES>
<EOF> 
 {return new SimpleA(comandos);}
}

  // COMANDOS -> COM ";" COMANDOS'

void Comandos (ArrayList comandos) :
{Comando c;}
{

 c=Com() {comandos.add(c);}

  <PV>  ComandosL(comandos)

}

  // COMANDOS'-> COM ";" COMANDOS' | epsilon

void ComandosL (ArrayList comandos) :
{Comando c;}
{

 ( c=Com()  {comandos.add(c);}

  <PV>  ComandosL(comandos))?

}

  // COM -> id ":=" EXP | "print" "(" EXP ")"

Comando Com () :
{Token id=null; Exp e=null; Comando c=null; Comando result=null;}
{

  ((id=<ID> <ATRIB> e=Exp() {result = new Atrib(id.image,e);})|(<PRINT>

  <APARENTESES> e=Exp() <FPARENTESES> {result = new Print(e);}))
  
  {return result;} 

}

  // EXP -> num | id

Exp Exp () :
{Token t = null; Exp result=null;}
{
   ( (t=<NUM> {result = new Num(Integer.parseInt(t.image));}) 
    | (t=<ID>  {result = new Var(t.image);}))

    {return result;}

}
