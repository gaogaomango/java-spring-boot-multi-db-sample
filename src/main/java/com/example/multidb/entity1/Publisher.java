package com.example.multidb.entity1;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Publishers")
@Setter
@Getter
public class Publisher implements Serializable {
	
	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;
	
	@Column(name="Name", length=255, nullable=false)
	private String name;
	
}
