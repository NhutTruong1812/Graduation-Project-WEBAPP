package com.biglobby.utils.recommender.contentbased;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DocumentModel {
    private Long id;
    private String content;
}
