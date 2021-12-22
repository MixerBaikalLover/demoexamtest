package ru.mixer.demoexam.util;

import lombok.AllArgsConstructor;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.List;
@AllArgsConstructor
public class CustomTableModel<T> extends AbstractTableModel {
    private Class<T> cls;
    private List<T> rows;

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return cls.getDeclaredFields().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Field field = cls.getDeclaredFields()[columnIndex];
            field.setAccessible(true);
            return field.get(rows.get(rowIndex));
        } catch (IllegalAccessException e) {
            return "error";
        }
    }
}
