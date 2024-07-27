package com.gym.trainings.repository;


import com.gym.trainings.model.Training;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends MongoRepository <Training, Long> {
}
