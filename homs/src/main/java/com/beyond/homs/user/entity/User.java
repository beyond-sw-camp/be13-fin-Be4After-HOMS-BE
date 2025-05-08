package com.beyond.homs.user.entity;

import com.beyond.homs.company.entity.Company;
import com.beyond.homs.company.entity.Department;
import com.beyond.homs.user.data.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "userId")
@ToString(exclude = {"company","department"})
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name",nullable = false, unique = true)
    private String userName;

    @Column(name = "manager_name",nullable = false)
    private String managerName;

    @Column(name = "manager_email",nullable = false)
    private String managerEmail;

    @Column(name = "manager_phone",nullable = false)
    private String managerPhone;

    @Column(name = "role",nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;

    @Builder
    public User(String userName, String managerName, String managerEmail, String managerPhone
            , UserRole role, Company company, Department department) {
        this.userName = userName;
        this.managerName = managerName;
        this.managerEmail = managerEmail;
        this.managerPhone = managerPhone;
        this.role = role;
        this.company = company;
        this.department = department;
    }
}
