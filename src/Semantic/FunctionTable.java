package Semantic;

import java.util.Objects;

public class FunctionTable {
    public String name;
    public String type;
    public int scope;

    @Override
    public String toString() {
        return "FunctionTable{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", scope=" + scope +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionTable that = (FunctionTable) o;
        return scope == that.scope && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, scope);
    }
}
