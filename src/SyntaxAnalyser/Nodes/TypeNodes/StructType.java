package SyntaxAnalyser.Nodes.TypeNodes;


import java.util.HashMap;

public class StructType extends TypeNode {
    public HashMap<String, TypeNode> fields;

    public StructType() {
        fields = new HashMap<>();
    }
}
