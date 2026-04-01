package cn.edu.nnu.opengms.records;

public record StudentRecord(
    String name,          // 姓名
    String studentId,    // 学号
    String major,        // 专业
    String email         // 邮箱
) {}
