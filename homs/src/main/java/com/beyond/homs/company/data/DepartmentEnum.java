package com.beyond.homs.company.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum DepartmentEnum {
    SALES("SALES"),
    BUY("BUY"),
    DELIVERY("DELIVERY"),
    MATERIALS("MATERIALS");

    @Getter
    private final String department;

    DepartmentEnum(String department) {
        this.department = department;
    }

    @JsonCreator
    DepartmentEnum from(String department) {
        for (DepartmentEnum departmentEnum : DepartmentEnum.values()) {
            if (departmentEnum.getDepartment().equalsIgnoreCase(department)) {
                return departmentEnum;
            }
        }
        throw new IllegalArgumentException("Invalid department: " + department);
    }

    @JsonValue
    public String getValue() {
        return department;
    }
}
