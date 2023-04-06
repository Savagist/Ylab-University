package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {

    DataSource dataSource;

    @Autowired
    public SQLQueryBuilderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String queryForTable(String tableName) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(null, null, tableName, null);
            if (rs.next()) {
                List<String> columns = new ArrayList<>();
                ResultSet columnRs = metaData.getColumns(null, null, tableName, null);
                while (columnRs.next()) {
                    columns.add(columnRs.getString("COLUMN_NAME"));
                }
                if (columns.isEmpty()) {
                    return "Table with no columns";
                } else {
                    String cols = String.join(", ", columns);
                    return "SELECT " + cols + " FROM " + tableName + ";";
                }
            } else {
                return null;
            }
        }
    }

    @Override
    public List<String> getTables() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            List<String> tables = new ArrayList<>();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getTables(null, null, null, null);
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
            return tables;
        }
    }
}
