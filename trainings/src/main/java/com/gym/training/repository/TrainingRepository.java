package com.gym.training.repository;


import com.gym.training.model.Training;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository extends MongoRepository<Training, String> {
    @Override
    boolean existsById(String trainingCode);

    List<Training> findAllByStatus(Training.Status status);
}
