package com.beyond.homs.company.entity;

import com.beyond.homs.company.dto.CompanyDto;
import com.beyond.homs.company.dto.UpdateCompanyDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "company")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "registration_number", nullable = false)
    private String registrationNumber;

    @Column(name = "represent_name", nullable = false)
    private String representName;

    @Column(name = "represent_call", nullable = false)
    private String representCall;

    @Column(name = "represent_phone", nullable = false)
    private String representPhone;

    @Column(name = "represent_manager_name", nullable = false)
    private String representManagerName;

    @Column(name = "represent_manager_email", nullable = false)
    private String representManagerEmail;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "is_continue_status", nullable = false)
    private boolean continueStatus;

    @Column(name = "is_approve_status", nullable = false)
    private boolean approveStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    public void setFromDto(UpdateCompanyDto companyDto) {
        this.companyName = companyDto.companyName();
        this.registrationNumber = companyDto.registrationNumber();
        this.representName = companyDto.representName();
        this.representCall = companyDto.representCall();
        this.representPhone = companyDto.representPhone();
        this.representManagerName = companyDto.representManagerName();
        this.representManagerEmail = companyDto.representManagerEmail();

        if (companyDto.continueStatus() != null) {
            this.continueStatus = companyDto.continueStatus();
        }

        if (companyDto.approveStatus() != null) {
            this.approveStatus = companyDto.approveStatus();
        }
        this.country = companyDto.country();
    }

    @Builder
    public Company(String companyName, String registrationNumber, String representName, String representCall, String representPhone, String representManagerName, String representManagerEmail, boolean continueStatus, boolean approveStatus, Country country) {
        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
        this.representName = representName;
        this.representCall = representCall;
        this.representPhone = representPhone;
        this.representManagerName = representManagerName;
        this.representManagerEmail = representManagerEmail;
        this.continueStatus = continueStatus;
        this.approveStatus = approveStatus;
        this.country = country;
    }

    public void changeApproveStatus(boolean approveStatus) {
        this.approveStatus = approveStatus;
    }

    public void changeContinueStatus(boolean continueStatus) {
        this.continueStatus = continueStatus;
    }
}
