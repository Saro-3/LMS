package com.lms.lms_backend.service;

import com.lms.lms_backend.entity.LearningMaterial;
import com.lms.lms_backend.entity.Lesson;
import com.lms.lms_backend.repository.LearningMaterialRepository;
import com.lms.lms_backend.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LearningMaterialService {

    private final LearningMaterialRepository learningMaterialRepository;
    private final LessonRepository lessonRepository;

    public List<LearningMaterial> getMaterialsByLesson(Long lessonId){

        if(!lessonRepository.existsById(lessonId)){
            throw new RuntimeException(
                    "Lesson not found with id " + lessonId
            );
        }

        return learningMaterialRepository.findByLessonId(lessonId);
    }

    public LearningMaterial getMaterialById(Long id){

        return learningMaterialRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Learning material not found with id " + id
                        )
                );
    }

    public LearningMaterial createMaterial(
            Long lessonId,
            LearningMaterial material) {

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Lesson not found with id: " + lessonId
                        )
                );
        material.setLesson(lesson);

        return learningMaterialRepository.save(material);

    }

    public LearningMaterial updateMaterial(
            Long id,
            LearningMaterial updatedMaterial) {

        LearningMaterial existingMaterial = getMaterialById(id);

        existingMaterial.setTitle(
                updatedMaterial.getTitle()
        );

        existingMaterial.setDescription(
                updatedMaterial.getDescription()
        );

        existingMaterial.setMaterialType(
                updatedMaterial.getMaterialType()
        );

        existingMaterial.setFileUrl(
                updatedMaterial.getFileUrl()
        );

        existingMaterial.setExternalUrl(
                updatedMaterial.getExternalUrl()
        );

        existingMaterial.setFileSize(
                updatedMaterial.getFileSize()
        );

        return learningMaterialRepository.save(existingMaterial);
    }

    public void deleteMaterial(Long id) {

        LearningMaterial material = getMaterialById(id);

        learningMaterialRepository.delete(material);
    }

}
