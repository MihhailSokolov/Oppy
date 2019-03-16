package server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import server.model.Action;

import java.util.List;

public interface ActionRepository extends MongoRepository<Action, String> {
    List<Action> findAll();

    Action findFirstByActionName(String actionName);
}