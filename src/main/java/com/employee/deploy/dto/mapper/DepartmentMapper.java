package com.employee.deploy.dto.mapper;

import com.employee.deploy.dto.DepartmentDto;
import com.employee.deploy.entity.Department;

public class DepartmentMapper {

    public static DepartmentDto toDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .departmentName(department.getDepartmentName())
                .departmentDescription(department.getDepartmentDescription())
                .build();
    }

    public static Department toEntity(DepartmentDto departmentDto) {
        return Department.builder()
                .id(departmentDto.getId())
                .departmentName(departmentDto.getDepartmentName())
                .departmentDescription(departmentDto.getDepartmentDescription())
                .build();
    }

}