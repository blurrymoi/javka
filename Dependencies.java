package problem.dependencies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class TableModel {

    private List<String> rows = new ArrayList<String>();
    private Table table;

    public void addRow(String row) {
        rows.add(row);
        table.paint();
    }

    public List<String> getRows() {
        return Collections.unmodifiableList(rows);
    }

    public void setTable(Table table) { //chybalo public
        this.table = table;
    }
}

class Table {

    private TableModel tableModel;

    public void paint() {
        System.out.println("----------");
        for (String row : tableModel.getRows()) {
            System.out.println(row);
        }
        System.out.println("----------");
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
        tableModel.setTable(this);
    }
}

public class Dependencies {

    public static void main(String... args) {
        TableModel tableModel = new TableModel();
        Table table = new Table();
        table.setTableModel(tableModel);

        tableModel.addRow("Row 1");
        tableModel.addRow("Row 2");
        tableModel.addRow("Row 3");

    }

}
