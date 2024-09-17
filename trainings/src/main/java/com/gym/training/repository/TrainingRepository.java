package com.gym.training.repository;


import com.gym.training.model.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends MongoRepository<Training, String> {

    @Override
    boolean existsById(String trainingCode);

    Page<Training> findAllByStatus(Training.Status status, Pageable page);

    Page<Training> findAllByCategory(Training.Category category, Pageable page);
}
