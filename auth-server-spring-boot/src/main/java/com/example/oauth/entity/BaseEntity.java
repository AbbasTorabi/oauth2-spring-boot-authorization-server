package com.example.oauth.entity;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
@EqualsAndHashCode
public class BaseEntity implements Comparable<BaseEntity> {

    @Id
    protected String id;

    public void assignId() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public int compareTo(BaseEntity o) {
        return this.id.compareTo(o.id);
    }

}
