package SyntaxAnalyser.Nodes.TypeNodes;


public class ArrayType extends TypeNode {
    public TypeNode type;
    public int size;

    public ArrayType(int size) {
        this.size = size;
    }
}
