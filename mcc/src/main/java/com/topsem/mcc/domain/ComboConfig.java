package com.topsem.mcc.domain;

import com.topsem.common.domain.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@SqlResultSetMapping(name = "comboDataResults",
        classes = {
                @ConstructorResult(
                        targetClass = ComboConfig.ComboData.class,
                        columns = {
                                @ColumnResult(name = "text"),
                                @ColumnResult(name = "value")
                        }
                )
        }
)

@Entity
@Data
@EqualsAndHashCode(of = "nameField", callSuper = false)
@Table(name = "T_COMBOCONFIG")
public class ComboConfig extends IdEntity {

    private static final String SQL = " select %s as text, %s as value from %s ";
    private static final String COUNT_SQL = " select count(1) from %s ";

    @NotNull
    private String nameField; // 名称
    @NotNull
    private String valueField; // 值域
    @NotNull
    private String tableName; // 表名
    private String whereCase;

    private String queryParam;

    @NotNull
    private String code;

    public String toSQL() {
        return appendWhereCase(String.format(SQL, nameField, valueField, tableName));
    }

    public String toCountSQL() {
        return appendWhereCase(String.format(COUNT_SQL, tableName));
    }

    private String appendWhereCase(String sql) {
        if (!StringUtils.isEmpty(queryParam)) {
            whereCase =  nameField + " like  '%" + queryParam + "%' ";
        }
        return StringUtils.isEmpty(whereCase) ? sql : (sql += " where " + whereCase);
    }

    @Data
    @NoArgsConstructor
    public static class ComboData {

        private String text; // 名称
        private String value; // 值

        public ComboData(String text, String value) {
            this.text = text;
            this.value = value;
        }

    }
}
