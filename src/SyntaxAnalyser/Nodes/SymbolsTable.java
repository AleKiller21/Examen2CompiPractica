package SyntaxAnalyser.Nodes;


import SyntaxAnalyser.Nodes.TypeNodes.TypeNode;

import java.util.HashMap;

public class SymbolsTable {
    public static HashMap<String, TypeNode> variables = new HashMap<>();
}
