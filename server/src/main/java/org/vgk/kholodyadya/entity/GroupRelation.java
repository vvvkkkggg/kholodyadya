package org.vgk.kholodyadya.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "group_relations")
public class GroupRelation {
    @EmbeddedId
    private GroupRelationId relationId;

    private String role;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Embeddable
    public static class GroupRelationId implements Serializable {
        private int groupId;
        private int userId;
    }
}