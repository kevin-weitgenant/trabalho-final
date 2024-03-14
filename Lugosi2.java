PARSER_BEGIN(Lugosi)

import ast.*;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;

public class Lugosi {
  public static void main(String args[]) throws ParseException, IOException {
    Lugosi parser = new Lugosi(new FileInputStream(args[0]));
    Prog arvore = parser.Lugosi();
    System.out.println(arvore.toString());
  }

}PARSER_END(Lugosi)

  SKIP:{" "|"\t"|"\n"|"\r"}

  TOKEN:{<MAIN:"main">|<VOID:"void">|<IF:"if">|<WHILE:"while">|<DO:"do">|<RETURN:"return">|<PRINTIO:"printIO">|<LET:"let">|<DEF:"def">|<FLOAT:"float">|<BOOL:"bool">|<READIO:"readIO">|<ACHAVES:"{">|<FCHAVES:"}">|<APARENTESES:"(">|<FPARENTESES:")">|<VIRGULA:",">|<TRUE:"true">|<FALSE:"false">|<PONTOEVIRGULA:";">|<ATR:":=">|<SOMA:"+">|<SUB:"-">|<MULT:"*">|<DIV:"/">|<AND:"&&">|<OR:"||">|<MENOR:"<">|<MAIOR:">">|<IGUAL:"==">}

  TOKEN:{<NUM:(["0"-"9"])+(["."]["0"-"9"])+>|<ID:["a"-"z","A"-"Z"](["a"-"z","A"-"Z","0"-"9"])*>}

  // LUGOSI -> MAIN Fun?
Prog LUGOSI():
{ 
    Main main; 
    ArrayList<Fun> fun = new ArrayList<Fun>(); 
}
{
    main = Main() 
    fun = FUNC()
    { return new Prog(main, fun); }
    

}

Main Main(): 
    {
    ArrayList<VarDecl>variavel_list=new ArrayList<VarDecl>();
    ArrayList<Comando>comando_list=new ArrayList<Comando>();
    }

  {<VOID><MAIN><ACHAVES>(VarDecl(variavel_list))*(Comando(comando_list))*<FCHAVES>
    {
      return new Main(variavel_list,comando_list);
    }
  }

  void VarDecl(ArrayList<VarDecl> variavel_list): 
  {
    String t,id;
  }
    {<LET>t=Tipo()id=<ID><PONTOEVIRGULA>
      {lv.add(new VarDecl(t,id.image));}
    }

  String Tipo():
  {Token t;}
  {(t=<FLOAT>|t=<BOOL>|t=<VOID>)
    {return t.image;}
  }



  ArrayList<Fun> FUNC() :
{ 
    ArrayList<Fun> fun = new ArrayList<Fun>(); 
    String nome; ArrayList<ParamFormalFun> params; 
    String retorno; 
    ArrayList<VarDecl> vars; 
    ArrayList<Comando> body; 
}
{
    (<DEF> retorno = TIPO() nome = <ID>.image "(" params = LISTAARG() ")" "{" vars = VARDECL() body = SEQCOMANDOS() "}" { fun.add(new Fun(nome, params, retorno, vars, body)); })*
    { return fun; }
}