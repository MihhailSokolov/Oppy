package server.model;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ActionRepository extends MongoRepository<Action, String> {
    List<Action> findAll();

    Action findFirstByActionName(String actionName);
}