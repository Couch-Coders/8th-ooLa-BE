package com.couchcoding.oola.entity;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class MemberTechStack {
    private UUID uid;
    private String techStack;
}
