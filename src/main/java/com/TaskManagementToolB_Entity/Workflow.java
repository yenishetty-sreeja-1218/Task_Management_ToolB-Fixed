package com.TaskManagementToolB_Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "work_flows")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workflow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(
        mappedBy = "workflow",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Builder.Default
    private List<WorkflowTransaction> transactions = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {  
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<WorkflowTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<WorkflowTransaction> transactions) {
		this.transactions = transactions;
	}
}