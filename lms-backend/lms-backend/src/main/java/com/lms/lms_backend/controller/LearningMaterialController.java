package com.lms.lms_backend.controller;

import com.lms.lms_backend.entity.LearningMaterial;
import com.lms.lms_backend.service.LearningMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LearningMaterialController {

    private final LearningMaterialService learningMaterialService;

    @GetMapping("/lessons/{lessonId}/materials")
    public ResponseEntity<List<LearningMaterial>> getMaterials(
            @PathVariable("lessonId") Long lessonId) {

        return ResponseEntity.ok(
                learningMaterialService.getMaterialsByLesson(lessonId)
        );
    }

    @GetMapping("/materials/{id}")
    public ResponseEntity<LearningMaterial> getMaterial(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                learningMaterialService.getMaterialById(id));
    }

    @PostMapping("/lessons/{lessonId}/materials")
    public ResponseEntity<LearningMaterial> createMaterial(
            @PathVariable Long lessonId,
            @RequestBody LearningMaterial material) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        learningMaterialService.createMaterial(
                                lessonId, material
                        )
                );
    }

    @PutMapping("/materials/{id}")
    public ResponseEntity<LearningMaterial> updateMaterial(
            @PathVariable Long id,
            @RequestBody LearningMaterial material) {

        return ResponseEntity.ok(
                learningMaterialService.updateMaterial(
                        id, material
                )
        );
    }

    @DeleteMapping("/materials/{id}")
    public ResponseEntity<Void>  deleteMaterial(
            @PathVariable Long id
    ) {
        learningMaterialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }

}
